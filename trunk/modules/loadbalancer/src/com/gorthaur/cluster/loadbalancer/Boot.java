package com.gorthaur.cluster.loadbalancer;

import java.net.InetSocketAddress;

import javax.inject.Inject;

import org.jboss.netty.bootstrap.ServerBootstrap;

import com.google.inject.Guice;

public class Boot {
	
	@Inject 
	ServerBootstrap bootstrap;
	
	@Inject
	HexDumpProxyPipelineFactory pipelineFactory;
	
	public void run() {
		bootstrap.setPipelineFactory(pipelineFactory);
		bootstrap.bind(new InetSocketAddress(8443));
		System.out.println("Balancer Running");
	}

	public static void main(String[] args) {
		 Guice.createInjector(new ApplicationModule()).getInstance(Boot.class).run();	 
	}
	
}
