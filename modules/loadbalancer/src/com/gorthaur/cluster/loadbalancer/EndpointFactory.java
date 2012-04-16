package com.gorthaur.cluster.loadbalancer;

import java.net.SocketAddress;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.gorthaur.cluster.web.WebServerStateManager;

@Singleton
public class EndpointFactory {

	@Inject WebServerStateManager stateManager;
	
	public SocketAddress getNextEndpoint() throws Exception {
		return stateManager.getAccessableEndpointFor("0");
	}

	public String getNextEndpointURL() throws Exception {
		return stateManager.getAccessableEndpointURLFor("0");
	}

}
