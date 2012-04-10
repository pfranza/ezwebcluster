package com.gorthaur.cluster.console.client.shared;

import java.io.Serializable;

public class ClusterNode implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1695829783378807932L;
	
	private String name;
	
	ClusterNode(){}
	public ClusterNode(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
}
