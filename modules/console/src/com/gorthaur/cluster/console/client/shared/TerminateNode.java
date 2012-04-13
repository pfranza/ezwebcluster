package com.gorthaur.cluster.console.client.shared;

import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.general.StringResult;

public class TerminateNode implements Action<StringResult> {

	private String name;
	
	TerminateNode(){}

	public TerminateNode(String name) {
		super();
		this.name = name;
	}
	
	public String getNodeAddress() {
		return name;
	}
	
}
