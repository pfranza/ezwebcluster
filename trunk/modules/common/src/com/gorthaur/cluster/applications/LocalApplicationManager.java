package com.gorthaur.cluster.applications;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import com.google.inject.Inject;
import com.google.inject.Injector;

@Singleton
public class LocalApplicationManager {

	@Inject
	Injector injector;
	
	private ArrayList<ApplicationWrapper> applications = new ArrayList<ApplicationWrapper>();
	
	private Executor executor;
	
	{
		executor = Executors.newCachedThreadPool();
	}
	
	public void launchApplication(Class<? extends Application> cls, Properties configuration) {
		Application app = injector.getInstance(cls);
		ApplicationWrapper w = new ApplicationWrapper();
		w.application = app;
		w.applicationid = UUID.randomUUID().toString();
		
		applications.add(w);
		app.configure(configuration);
		executor.execute(app);
	}
	
	public void shutdownApplication(String applicationId) {
		for (Iterator<ApplicationWrapper> iterator = applications.iterator(); iterator.hasNext();) {
			ApplicationWrapper type = iterator.next();
			if(type.applicationid.equals(applicationId)) {
				type.application.stop();
				iterator.remove();
				return;
			}
		}
	}
	
	private static class ApplicationWrapper {
		String applicationid;
		Application application;
	}
	
}
