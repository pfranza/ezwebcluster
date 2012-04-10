package com.gorthaur.cluster;

import javax.inject.Inject;

import com.google.inject.Guice;

public class Boot {

	@Inject
	ClusterStateManager channelManager;
	
	void start() {
		
	}
	
	public static void main(String[] args) {
		Guice.createInjector().getInstance(Boot.class).start();		
	}
	
}
