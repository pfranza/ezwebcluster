package com.gorthaur.cluster;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.jgroups.Address;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.gorthaur.cluster.applications.DefaultLocalApplicationManager;
import com.gorthaur.cluster.channels.AdministrationChannel;
import com.gorthaur.cluster.datafiles.DataFileManager;
import com.gorthaur.cluster.protocol.Cluster.ClusterNode;
import com.gorthaur.cluster.protocol.Cluster.ClusterNode.Builder;
import com.jezhumble.javasysmon.CpuTimes;
import com.jezhumble.javasysmon.JavaSysMon;

@Singleton
public class ClusterStateManager {

	@Inject
	AdministrationChannel channel;
	
	@Inject DefaultLocalApplicationManager applicationManager;
	@Inject DataFileManager dataFileManager;
	
	private JavaSysMon monitor = new JavaSysMon();
	private CpuTimes prevTimes;
	
	private Cache<String, ClusterNode> cache = CacheBuilder.newBuilder()
		    .expireAfterWrite(10, TimeUnit.SECONDS)
		    .build(); 
	
	private Cache<String, Address> addressCache = CacheBuilder.newBuilder()
		    .expireAfterWrite(10, TimeUnit.SECONDS)
		    .build(); 
	
	private Timer t = new Timer();
	
	private TimerTask task = new TimerTask() {		
		@Override
		public void run() {
			try {
				forceStatusSend();
				cache.cleanUp();
				addressCache.cleanUp();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}			
		}
	};
	
	{
		t.scheduleAtFixedRate(task, 500, 5000);
	}

	public void processNewState(ClusterNode node, Address address) {
		cache.put(node.getName(), node);
		addressCache.put(node.getName(), address);
	}
	
	public Address forNodeName(String name) {
		return addressCache.getIfPresent(name);
	}
	
	public Collection<ClusterNode> getAllNodes() {
		ArrayList<ClusterNode> list = new ArrayList<ClusterNode>();
		list.addAll(cache.asMap().values());
		return list;
	}

	public void forceStatusSend() {
		try {
			
			if(channel == null || channel.getName() == null) {
				return;
			}
			
			Builder builder = ClusterNode.newBuilder()
					.setName(channel.getName())
					.setCpuFrequency(monitor.cpuFrequencyInHz())
					.setCpuUtilization(prevTimes != null ? (monitor.cpuTimes().getCpuUsage(prevTimes) * 100.0f) : 0 )
					.setMemoryFreeBytes(monitor.physical().getFreeBytes())
					.setMemoryTotalBytes(monitor.physical().getTotalBytes());

			prevTimes = monitor.cpuTimes();
			
			applicationManager.populateStatus(builder);
			dataFileManager.populateStatus(builder);

			ClusterNode node = builder.build();

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			node.writeTo(out);

			channel.publishMessage(ClusterNode.class, out.toByteArray());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
