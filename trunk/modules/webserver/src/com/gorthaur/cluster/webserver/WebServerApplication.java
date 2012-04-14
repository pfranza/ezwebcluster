package com.gorthaur.cluster.webserver;

import java.io.ByteArrayOutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.jetty.server.AbstractConnector;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.server.ssl.SslSocketConnector;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

import com.google.common.net.InetAddresses;
import com.google.inject.Inject;
import com.gorthaur.cluster.applications.Application;
import com.gorthaur.cluster.channels.AdministrationChannel;
import com.gorthaur.cluster.protocol.Cluster.WebServerState;
import com.gorthaur.cluster.protocol.Cluster.WebServerState.Builder;
import com.gorthaur.cluster.webserver.TestWebServer.HelloHandler;

public class WebServerApplication implements Application {

	private Server server = new Server();
	private ArrayList<Connector> connectors = new ArrayList<Connector>();
	
	private int servers = 5;
	
	@Inject
	AdministrationChannel adminChannel;
	
	private String applicationChecksum = "0";
	
	private Timer timer = new Timer();
	private TimerTask task = new TimerTask() {
		
		@Override
		public void run() {
			try {
				sendStateMessage();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	@Override
	public void run() {
		try {
			
			QueuedThreadPool threadPool = new QueuedThreadPool();
			threadPool.setMinThreads(10);
			threadPool.setMaxThreads(200);

			server.setThreadPool(threadPool);
			server.setConnectors(connectors.toArray(new Connector[connectors.size()]));
			server.setHandler(new HelloHandler());
			server.start();
			
			sendStateMessage();		
			timer.scheduleAtFixedRate(task, 1000, 5000);
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}

	@Override
	public void configure(Properties properties) {
		servers = Integer.valueOf(properties.getProperty("servers", Integer.toString(servers)));
		
		for (int i = 0; i < servers; i++) {
			AbstractConnector connector = new SelectChannelConnector();
			connector.setMaxIdleTime(15000);
			connector.setAcceptors(1);
			connectors.add(connector);
		}
	}

	@Override
	public void stop() {
		try {
			task.cancel();
			server.stop();
			connectors.clear();
			sendStateMessage();		
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private void sendStateMessage() throws Exception {
		Builder stateBuilder = WebServerState.newBuilder();

		if(connectors.size() > 0) {
			try {
				InetAddress localhost = InetAddress.getLocalHost();
				InetAddress[] allMyIps = InetAddress.getAllByName(localhost.getCanonicalHostName());
				if (allMyIps != null && allMyIps.length > 1) {
					for (int i = 0; i < allMyIps.length; i++) {
						if(allMyIps[i] instanceof Inet4Address) {
							stateBuilder.addIpaddress(InetAddresses.toAddrString(allMyIps[i]));
						}
					}
				}
			} catch (UnknownHostException e) {
				System.out.println(" (error retrieving server host name)");
			}

			for(Connector c: server.getConnectors()) {
				stateBuilder.addPort("" + c.getLocalPort());
			}
		}
		
		stateBuilder.setWebappChecksum(applicationChecksum);
		
		WebServerState state = stateBuilder.build();	
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		state.writeTo(out);
		
		adminChannel.publishMessage(state.getClass(), out.toByteArray());
	}

	public static void main(String[] args) {
		Server server = new Server();
		SslSocketConnector connector = new SslSocketConnector();
		connector.setPort(8443);
	}
	
}
