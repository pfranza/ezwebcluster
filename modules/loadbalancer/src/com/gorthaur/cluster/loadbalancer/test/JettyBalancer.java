package com.gorthaur.cluster.loadbalancer.test;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.ArrayList;

import javax.inject.Inject;

import org.eclipse.jetty.http.HttpURI;
import org.eclipse.jetty.server.AbstractConnector;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlets.ProxyServlet;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

import com.gorthaur.cluster.loadbalancer.EndpointFactory;

public class JettyBalancer {

	@Inject
	public JettyBalancer(final EndpointFactory endPoints) throws Exception {
		Server server = new Server();

		ArrayList<Connector> connectors = new ArrayList<Connector>();
			AbstractConnector connector = new SelectChannelConnector();
			connector.setPort(8080);
			connectors.add(connector);
			
		QueuedThreadPool threadPool = new QueuedThreadPool();
			threadPool.setMinThreads(10);
			threadPool.setMaxThreads(200);
			
		server.setThreadPool(threadPool);
		
		server.setConnectors(connectors.toArray(new Connector[connectors.size()]));
		
		
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);
 
        context.addServlet(new ServletHolder(new ProxyServlet.Transparent("/", "127.0.0.1", 80){
        	@Override
        	protected HttpURI proxyHttpURI(final String scheme, final String serverName, int serverPort, final String uri) throws MalformedURLException {
        		try {
        			String b = endPoints.getNextEndpointURL();
					return new HttpURI(new URI(b + uri).normalize().toString());
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
        	}
        }),"/*");
		
		server.start();
		server.join();
	}
	
}
