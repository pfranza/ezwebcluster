package com.gorthaur.cluster.console.client.shared;

import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.general.StringResult;

public class ReplicateFileDataAction implements Action<StringResult> {

	private String data;

	ReplicateFileDataAction(){}
	public ReplicateFileDataAction(String data) {
		super();
		this.data = data;
	}
	
	public String getData() {
		return data;
	}
	
	
	
}
