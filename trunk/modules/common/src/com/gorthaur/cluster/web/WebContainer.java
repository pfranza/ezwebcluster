package com.gorthaur.cluster.web;

import java.io.File;

import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.webapp.WebAppContext;

public class WebContainer {

	public WebContainer(int port, File warFile) throws Exception {
		Server server = new Server();
		Connector connector=new SelectChannelConnector();
		connector.setPort(port);
		
		server.setConnectors(new Connector[]{connector});
		
		WebAppContext webapp = new WebAppContext();
		webapp.setContextPath("/");
		webapp.setWar(warFile.getAbsolutePath());
		
		server.setHandler(webapp);
		server.start();
	}
	
}
