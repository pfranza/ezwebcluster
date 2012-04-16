package com.gorthaur.cluster.web;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.google.common.net.InetAddresses;
import com.gorthaur.cluster.protocol.Cluster.WebServerState;

@Singleton
public class DefaultWebServerStateManager implements WebServerStateManager {

	private LoadingCache<String, ApplicationCluster> cache = CacheBuilder.newBuilder()
		    .expireAfterAccess(1, TimeUnit.MINUTES)
		    .removalListener(new RemovalListener<String, ApplicationCluster>() {
				@Override
				public void onRemoval(RemovalNotification<String, ApplicationCluster> arg0) {
					System.out.println("Dropping Application: " + arg0.getKey());
				}
			})
		    .build(new CacheLoader<String, ApplicationCluster>(){
				@Override
				public ApplicationCluster load(String arg0) throws Exception {
					return new ApplicationCluster(arg0);
				}
		    });
	
	@Override
	public void processUpdate(WebServerState data) throws Exception {
		ApplicationCluster application = cache.get(data.getWebappChecksum());
		application.update(data);
	}
	
	@Override
	public SocketAddress getAccessableEndpointFor(String checksum) throws Exception {
		return cache.get(checksum).getAccessableEndpoint();
	}
	
	@Override
	public String getAccessableEndpointURLFor(String checksum) throws Exception {
		return cache.get(checksum).getAccessableEndpointURL();
	}
	
	private static class ApplicationCluster {

		private String checksum;
		private int currentIndex = 0;
		
		private Cache<String, List<SocketAddress>> accessablePorts = CacheBuilder.newBuilder()
				.removalListener(new RemovalListener<String, List<SocketAddress>>() {
					@Override
					public void onRemoval(RemovalNotification<String, List<SocketAddress>> arg0) {
						completeList.removeAll(arg0.getValue());
						System.out.println("List removal: " + arg0.getValue().size());
						System.out.println(completeList.size());
					}
				})
			    .expireAfterWrite(30, TimeUnit.SECONDS)
			    .build(); 
		
		private Cache<String, String> blackListedPorts = CacheBuilder.newBuilder()
			    .expireAfterWrite(10, TimeUnit.MINUTES)
			    .build(); 
		
		private ArrayList<SocketAddress> completeList = new ArrayList<SocketAddress>();
		

		public ApplicationCluster(String checksum) {
			setChecksum(checksum);
		}



		public void update(WebServerState data) {
			for(String ipaddress: data.getIpaddressList()) {
				//Check to see if address is blacklisted
				if(blackListedPorts.getIfPresent(ipaddress) == null) {				
					InetAddress addr = InetAddresses.forString(ipaddress);
					try {
						List<SocketAddress> list = accessablePorts.getIfPresent(ipaddress);
						if(list != null && list.size() > 0) {

						} else if(addr.isReachable(5000)) {
							System.out.println("Is reachable: " + ipaddress);
							list = new ArrayList<SocketAddress>();
							for(String port: data.getPortList()) {
								System.out.println("   adding: " + port);
								list.add(new InetSocketAddress(addr, Integer.valueOf(port)));
							}
							accessablePorts.put(ipaddress, list);
							completeList.addAll(list);
						} else {
							System.out.println("blacklisting port: " + ipaddress);
							blackListedPorts.put(ipaddress, ipaddress);
						}
					} catch (IOException e) {e.printStackTrace();}
				} else {
					System.out.println("Port BlackListed: " + ipaddress);
				}
			}
		}
		
//		public String getChecksum() {
//			return checksum;
//		}
		
		public void setChecksum(String checksum) {
			this.checksum = checksum;
		}
		
		public SocketAddress getAccessableEndpoint() {
			
			if(completeList.size() <= 0) {
				System.out.println("Empty List");
				return null;
			}
			
			currentIndex = (currentIndex + 1) % completeList.size();
			return completeList.get(currentIndex);
		}
		
		public String getAccessableEndpointURL() {
			SocketAddress addr = getAccessableEndpoint();
			if(addr instanceof InetSocketAddress) {
				InetSocketAddress iaddr = (InetSocketAddress) addr;
				return "http://" + iaddr.getHostName() + ":" + iaddr.getPort() + "/";
			}
			return "";
		}
		
	}



}
