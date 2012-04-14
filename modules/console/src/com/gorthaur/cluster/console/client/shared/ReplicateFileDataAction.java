package com.gorthaur.cluster.console.client.shared;

import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.general.StringResult;

public class ReplicateFileDataAction implements Action<StringResult> {

	private String data;
	private String name;

	ReplicateFileDataAction(){}
	
	public ReplicateFileDataAction(String name, String data) {
		super();
		this.name = name;
		this.data = data;
	}
	
	public String getData() {
		return data;
	}
	
	public String getName() {
		return name;
	}
	
	
}
