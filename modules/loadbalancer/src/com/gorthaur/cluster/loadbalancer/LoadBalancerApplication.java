package com.gorthaur.cluster.loadbalancer;

import java.util.Properties;

import javax.inject.Inject;

import com.google.inject.Injector;
import com.gorthaur.cluster.applications.Application;

public class LoadBalancerApplication implements Application {

	@Inject 
	Injector injector;
	
	private Properties properties;

	private Boot secureBalancer;
	private Boot standardBalancer;
	
	@Override
	public void run() {
		System.out.println("Launch Secure Load Balancer");
		secureBalancer = injector.createChildInjector(new SecureApplicationModule()).getInstance(Boot.class);
		secureBalancer.setPort(Integer.valueOf(properties.getProperty("port", Integer.toString(secureBalancer.getPort()))));	
		secureBalancer.run();
		
		System.out.println("Launch Standard Load Balancer");
		standardBalancer = injector.createChildInjector(new StandardApplicationModule()).getInstance(Boot.class);
		standardBalancer.setPort(8080);	
		standardBalancer.run();
	}

	@Override
	public void configure(Properties properties) {
		this.properties = properties;
	}

	@Override
	public void stop() {
		secureBalancer.stop();
		standardBalancer.stop();
	}

}
