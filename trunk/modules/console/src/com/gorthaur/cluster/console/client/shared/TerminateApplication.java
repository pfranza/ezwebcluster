package com.gorthaur.cluster.console.client.shared;

import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.general.StringResult;

public class TerminateApplication implements Action<StringResult> {

	private String applicationProcessId;
	private String nodeAddress;
	
	public String getApplicationProcessId() {
		return applicationProcessId;
	}
	
	public void setApplicationProcessId(String applicationProcessId) {
		this.applicationProcessId = applicationProcessId;
	}
	
	public String getNodeAddress() {
		return nodeAddress;
	}
	
	public void setNodeAddress(String nodeAddress) {
		this.nodeAddress = nodeAddress;
	}
	
}
