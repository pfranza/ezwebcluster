package com.gorthaur.cluster.applications;

import java.util.Properties;

public interface Application {
	
	void configure(Properties properties);
	
	void start();
	
	void stop();
	
}
