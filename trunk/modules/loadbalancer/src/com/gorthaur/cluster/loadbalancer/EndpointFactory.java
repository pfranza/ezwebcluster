package com.gorthaur.cluster.loadbalancer;

import java.net.SocketAddress;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.gorthaur.cluster.web.WebServerStateManager;

@Singleton
public class EndpointFactory {

//	int baseAddress = 49000;
//	int i = 0;
//	int max = 5;
	
	@Inject WebServerStateManager stateManager;
	
	public SocketAddress getNextEndpoint() throws Exception {
//		i = (i + 1) % max;
//		return new InetSocketAddress("127.0.0.1", baseAddress + i);
		return stateManager.getAccessableEndpointFor("0");
	}

}
