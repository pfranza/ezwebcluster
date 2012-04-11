package com.gorthaur.cluster.web;

import java.io.File;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.webapp.WebAppContext;

public class WebContainer {

	public WebContainer(int port, File warFile) throws Exception {
		Server server = new Server();
		Connector connector=new SelectChannelConnector();
		connector.setPort(port);
		
		server.setConnectors(new Connector[]{connector});
		
		WebAppContext webapp = new WebAppContext();
		webapp.setContextPath("/");
		webapp.setWar(warFile.getAbsolutePath());
		webapp.setClassLoader(WebContainer.class.getClassLoader());
		
		server.setHandler(webapp);
		server.start();
	}
	
}
