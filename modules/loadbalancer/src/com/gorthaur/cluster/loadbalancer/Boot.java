package com.gorthaur.cluster.loadbalancer;

import java.net.InetSocketAddress;

import javax.inject.Inject;

import org.jboss.netty.bootstrap.ServerBootstrap;

import com.google.inject.Guice;

public class Boot {
	
	@Inject 
	ServerBootstrap bootstrap;
	
	@Inject
	ProxyPipelineFactory pipelineFactory;
	
	private int port = 8443;
	
	public void setPort(int port) {
		this.port = port;
	}
	
	public int getPort() {
		return port;
	}
	
	public void run() {
		bootstrap.setPipelineFactory(pipelineFactory);
		bootstrap.bind(new InetSocketAddress(port));
		System.out.println("Balancer Running on port " + port);
	}

	public static void main(String[] args) {
		 Guice.createInjector(new ApplicationModule()).getInstance(Boot.class).run();	 
	}

	public void stop() {
		bootstrap.getFactory().releaseExternalResources();
		System.out.println("Shutdown Balancer");
	}
	
}
