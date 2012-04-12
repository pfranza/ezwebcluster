package com.gorthaur.cluster.web;

import java.io.File;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.webapp.WebAppContext;

public class WebContainer {

	public WebContainer(int port, File warFile) throws Exception {
		Server server = new Server();
		Connector connector = new SelectChannelConnector();
		connector.setPort(port);

		server.setConnectors(new Connector[] { connector });

		WebAppContext webapp = new WebAppContext();
		webapp.setContextPath("/");
		webapp.setWar(warFile.getAbsolutePath());
		webapp.setClassLoader(WebContainer.class.getClassLoader());

		server.setHandler(webapp);
		server.start();
		browse("http://localhost:" + port + "/");
	}

	private static void browse(String url) {

		if (!java.awt.Desktop.isDesktopSupported()) {
			System.err.println("Desktop is not supported (fatal)");
			return;
		}

		java.awt.Desktop desktop = java.awt.Desktop.getDesktop();

		if (!desktop.isSupported(java.awt.Desktop.Action.BROWSE)) {
			System.err
					.println("Desktop doesn't support the browse action (fatal)");
			return;
		}

		try {
			desktop.browse(new java.net.URI(url));
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

	}

}
