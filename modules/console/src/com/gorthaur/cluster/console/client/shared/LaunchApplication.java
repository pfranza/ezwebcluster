package com.gorthaur.cluster.console.client.shared;

import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.general.StringResult;

public class LaunchApplication implements Action<StringResult> {

	private String applicationClassName;
	private String nodeAddress;
	
	LaunchApplication(){}
	
	public LaunchApplication(String applicationClassName, String nodeAddress) {
		super();
		this.applicationClassName = applicationClassName;
		this.nodeAddress = nodeAddress;
	}

	public String getApplicationClassName() {
		return applicationClassName;
	}
	
	public void setApplicationClassName(String applicationClassName) {
		this.applicationClassName = applicationClassName;
	}
	
	public String getNodeAddress() {
		return nodeAddress;
	}
	
	public void setNodeAddress(String nodeAddress) {
		this.nodeAddress = nodeAddress;
	}
	
}
