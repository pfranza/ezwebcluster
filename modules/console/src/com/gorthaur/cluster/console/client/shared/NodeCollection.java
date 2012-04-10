package com.gorthaur.cluster.console.client.shared;

import java.util.ArrayList;

import net.customware.gwt.dispatch.shared.Result;

public class NodeCollection implements Result {

	private ArrayList<ClusterNode> nodes = new ArrayList<ClusterNode>();
	
	public ArrayList<ClusterNode> getNodes() {
		return nodes;
	}
	
	public void setNodes(ArrayList<ClusterNode> nodes) {
		this.nodes = nodes;
	}
}
