package com.gorthaur.cluster.console.server.actions;

import com.gorthaur.cluster.console.client.shared.LaunchApplication;
import com.gorthaur.cluster.console.client.shared.ListNodeInfo;
import com.gorthaur.cluster.console.client.shared.ListNodes;
import com.gorthaur.cluster.console.client.shared.ReplicateFileDataAction;
import com.gorthaur.cluster.console.client.shared.TerminateApplication;
import com.gorthaur.cluster.console.client.shared.TerminateNode;

import net.customware.gwt.dispatch.server.guice.ActionHandlerModule;

public class ActionsModule extends ActionHandlerModule {

	@Override
	protected void configureHandlers() {
		bindHandler(ListNodes.class, ListNodesHandler.class);
		bindHandler(LaunchApplication.class, LaunchApplicationHandler.class);
		bindHandler(TerminateApplication.class, TerminateApplicationHandler.class);
		bindHandler(ListNodeInfo.class, ListNodeInfoHandler.class);
		bindHandler(TerminateNode.class, TerminateNodeHandler.class);
		bindHandler(ReplicateFileDataAction.class, ReplicateFileDataActionHandler.class);
	}

}
