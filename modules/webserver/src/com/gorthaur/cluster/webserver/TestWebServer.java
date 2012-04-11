package com.gorthaur.cluster.webserver;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.nio.AbstractNIOConnector;
import org.eclipse.jetty.server.nio.BlockingChannelConnector;
import org.eclipse.jetty.util.thread.QueuedThreadPool;



public class TestWebServer {

	public static void main(String[] args) throws Exception {
		Server server = new Server();
		
		int servers = 1;
		int baseAddress = 49000;
		
		ArrayList<Connector> connectors = new ArrayList<Connector>();
		for (int i = 0; i < servers; i++) {
			AbstractNIOConnector connector = new BlockingChannelConnector(); //new SocketConnector();
			connector.setPort(baseAddress + i);
			connector.setMaxIdleTime(30000);
			connector.setAcceptQueueSize(500);
			connectors.add(connector);
		}
		
		System.out.println("Binding: " + connectors.size());
		
		QueuedThreadPool threadPool = new QueuedThreadPool();
			threadPool.setMinThreads(10);
			threadPool.setMaxThreads(200);
			
		server.setThreadPool(threadPool);
		
		server.setConnectors(connectors.toArray(new Connector[connectors.size()]));
		
		server.setHandler(new HelloHandler());
		server.start();
		server.join();
	}
	
	public static class HelloHandler extends AbstractHandler
	{

		@Override
		public void handle(String arg0, Request arg1, HttpServletRequest arg2,
				HttpServletResponse response) throws IOException, ServletException {
			arg1.setHandled(true);
			response.setContentType("text/html;charset=utf-8");
	        response.setStatus(HttpServletResponse.SC_OK);
	        response.getWriter().println("OK");
		}

		
	}
	
}
