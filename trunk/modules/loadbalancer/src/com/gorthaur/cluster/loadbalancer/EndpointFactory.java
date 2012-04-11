package com.gorthaur.cluster.loadbalancer;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import javax.inject.Singleton;

@Singleton
public class EndpointFactory {

	int baseAddress = 49000;
	int i = 0;
	int max = 10;
	
	public SocketAddress getNextEndpoint() {
		i = (i + 1) % max;
		return new InetSocketAddress("127.0.0.1", baseAddress + i);
	}

}
