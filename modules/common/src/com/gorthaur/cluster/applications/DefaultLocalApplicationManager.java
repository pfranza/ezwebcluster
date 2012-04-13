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
import com.gorthaur.cluster.ClusterStateManager;
import com.gorthaur.cluster.protocol.Cluster.ClusterNode.ActiveApplications;
import com.gorthaur.cluster.protocol.Cluster.ClusterNode.ActiveApplications.ApplicationStatus;
import com.gorthaur.cluster.protocol.Cluster.ClusterNode.Builder;

@Singleton
public class DefaultLocalApplicationManager implements ApplicationManager {

	@Inject
	Injector injector;
	
	@Inject
	ClusterStateManager stateManager;
	
	private ArrayList<ApplicationWrapper> applications = new ArrayList<ApplicationWrapper>();
	
	private Executor executor;
	
	{
		executor = Executors.newCachedThreadPool();
	}
	
	@Override
	public void launchApplication(Class<? extends Application> cls, Properties configuration) {
		Application app = injector.getInstance(cls);
		ApplicationWrapper w = new ApplicationWrapper();
		w.application = app;
		w.applicationid = UUID.randomUUID().toString();
		
		applications.add(w);
		app.configure(configuration);
		executor.execute(app);
		stateManager.forceStatusSend();
	}
	
	@Override
	public void shutdownApplication(String applicationId) {
		for (Iterator<ApplicationWrapper> iterator = applications.iterator(); iterator.hasNext();) {
			ApplicationWrapper type = iterator.next();
			if(type.applicationid.equals(applicationId)) {
				type.application.stop();
				stateManager.forceStatusSend();
				iterator.remove();
				return;
			}
		}
	}
	
	private static class ApplicationWrapper {
		String applicationid;
		Application application;
	}

	@Override
	public void populateStatus(Builder builder) {
		for(ApplicationWrapper a: applications) {
			ActiveApplications app = ActiveApplications.newBuilder()
					.setApplicationId(a.applicationid)
					.setName(a.application.getClass().getSimpleName())
					.setStatus(ApplicationStatus.RUNNING)
					.build();
			builder.addApplications(app);
		}
	}
	
}
