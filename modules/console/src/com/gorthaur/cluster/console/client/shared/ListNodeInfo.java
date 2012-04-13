package com.gorthaur.cluster.console.client.shared;

import net.customware.gwt.dispatch.shared.Action;

public class ListNodeInfo implements Action<ClusterNodeInfo> {

	private String name;
	
	ListNodeInfo(){}
	
	public ListNodeInfo(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
}
