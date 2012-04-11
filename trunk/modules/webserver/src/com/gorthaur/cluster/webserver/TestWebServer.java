package com.gorthaur.cluster.webserver;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mortbay.jetty.Connector;
import org.mortbay.jetty.HttpConnection;
import org.mortbay.jetty.Request;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.AbstractHandler;
import org.mortbay.jetty.nio.SelectChannelConnector;

public class TestWebServer {

	public static void main(String[] args) throws Exception {
		Server server = new Server();
		
		int servers = 10;
		int baseAddress = 49000;
		
		ArrayList<Connector> connectors = new ArrayList<Connector>();
		for (int i = 0; i < servers; i++) {
			Connector connector=new SelectChannelConnector();
			connector.setPort(baseAddress + i);
			connectors.add(connector);
		}
		
		System.out.println("Binding: " + connectors.size());
		
		server.setConnectors(connectors.toArray(new Connector[connectors.size()]));
		
		server.setHandler(new HelloHandler());
		server.start();
		server.join();
	}
	
	public static class HelloHandler extends AbstractHandler
	{

		@Override
		public void handle(String arg0, HttpServletRequest request, HttpServletResponse response, int arg3) throws IOException,
				ServletException {
			
				Request base_request = (request instanceof Request) ? (Request)request:HttpConnection.getCurrentConnection().getRequest();
	            base_request.setHandled(true);
	            
			 	response.setContentType("text/html;charset=utf-8");
		        response.setStatus(HttpServletResponse.SC_OK);
		        response.getWriter().println("OK");
		}

		
	}
	
}
