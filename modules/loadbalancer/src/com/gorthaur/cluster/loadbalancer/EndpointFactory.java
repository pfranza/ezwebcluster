package com.gorthaur.cluster.loadbalancer;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import javax.inject.Singleton;

@Singleton
public class EndpointFactory {

	public SocketAddress getNextEndpoint() {
		System.out.println("Fetch Address");
		return new InetSocketAddress("72.215.147.158", 80);
	}

}
