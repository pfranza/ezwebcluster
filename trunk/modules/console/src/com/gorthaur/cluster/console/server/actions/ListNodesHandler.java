package com.gorthaur.cluster.console.server.actions;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.gorthaur.cluster.ClusterStateManager;
import com.gorthaur.cluster.console.client.shared.ListNodes;
import com.gorthaur.cluster.console.client.shared.NodeCollection;
import com.gorthaur.cluster.protocol.Cluster.ClusterNode;

public class ListNodesHandler implements ActionHandler<ListNodes, NodeCollection>{

	@Inject
	ClusterStateManager channel;
	
	@Override
	public NodeCollection execute(ListNodes arg0, ExecutionContext arg1)
			throws DispatchException {
		NodeCollection c = new NodeCollection();
		c.setNodes(transform(channel.getAllNodes()));
		return c;
	}

	private ArrayList<com.gorthaur.cluster.console.client.shared.ClusterNode> transform(
			Collection<ClusterNode> allNodes) {
		ArrayList<com.gorthaur.cluster.console.client.shared.ClusterNode> list = new ArrayList<com.gorthaur.cluster.console.client.shared.ClusterNode>();
		for(ClusterNode c: allNodes) {
			list.add(new com.gorthaur.cluster.console.client.shared.ClusterNode(c.getName()));
		}
		return list;
	}

	@Override
	public Class<ListNodes> getActionType() {
		return ListNodes.class;
	}

	@Override
	public void rollback(ListNodes arg0, NodeCollection arg1,
			ExecutionContext arg2) throws DispatchException {}

}
