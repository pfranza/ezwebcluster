package com.gorthaur.cluster.webserver;

import java.util.ArrayList;
import java.util.Properties;

import org.eclipse.jetty.server.AbstractConnector;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

import com.gorthaur.cluster.applications.Application;
import com.gorthaur.cluster.webserver.TestWebServer.HelloHandler;

public class WebServerApplication implements Application {

	private Server server = new Server();
	private ArrayList<Connector> connectors = new ArrayList<Connector>();
	
	private int servers = 5;

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
			
			for(Connector c: server.getConnectors()) {
				System.out.println(c.getLocalPort());
			}
			
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
			server.stop();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {
		WebServerApplication m = new WebServerApplication();
		m.run();
	}
	
}
