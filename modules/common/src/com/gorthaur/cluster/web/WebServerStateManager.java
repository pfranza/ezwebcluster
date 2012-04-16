package com.gorthaur.cluster.web;

import java.net.SocketAddress;

import com.google.inject.ImplementedBy;
import com.gorthaur.cluster.protocol.Cluster.WebServerState;

@ImplementedBy(DefaultWebServerStateManager.class)
public interface WebServerStateManager {
	void processUpdate(WebServerState parseFrom) throws Exception;
	SocketAddress getAccessableEndpointFor(String checksum) throws Exception;
	String getAccessableEndpointURLFor(String string) throws Exception;
}
