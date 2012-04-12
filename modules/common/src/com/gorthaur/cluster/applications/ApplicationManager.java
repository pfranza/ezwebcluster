package com.gorthaur.cluster.applications;

import java.util.Properties;

import com.google.inject.ImplementedBy;
import com.gorthaur.cluster.protocol.Cluster.ClusterNode.Builder;

@ImplementedBy(DefaultLocalApplicationManager.class)
public interface ApplicationManager {

	public abstract void populateStatus(Builder builder);

	public abstract void shutdownApplication(String applicationId);

	public abstract void launchApplication(Class<? extends Application> cls, Properties configuration);

}