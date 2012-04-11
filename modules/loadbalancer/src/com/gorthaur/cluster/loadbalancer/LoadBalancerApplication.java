package com.gorthaur.cluster.loadbalancer;

import java.util.Properties;

import javax.inject.Inject;

import com.google.inject.Injector;
import com.gorthaur.cluster.applications.Application;

public class LoadBalancerApplication implements Application {

	@Inject 
	Injector injector;
	
	private Properties properties;

	private Boot b;
	
	@Override
	public void run() {
		System.out.println("Launch Load Balancer");
		b = injector.createChildInjector(new ApplicationModule()).getInstance(Boot.class);
		b.setPort(Integer.valueOf(properties.getProperty("port", Integer.toString(b.getPort()))));	
		b.run();
	}

	@Override
	public void configure(Properties properties) {
		this.properties = properties;
	}

	@Override
	public void stop() {
		b.stop();
	}

}
