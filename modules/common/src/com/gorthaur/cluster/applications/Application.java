package com.gorthaur.cluster.applications;

import java.util.Properties;

public interface Application extends Runnable {
	void configure(Properties properties);
	void stop();	
}
