package com.gorthaur.cluster.console.server.actions;

import com.gorthaur.cluster.console.client.shared.ListNodes;

import net.customware.gwt.dispatch.server.guice.ActionHandlerModule;

public class ActionsModule extends ActionHandlerModule {

	@Override
	protected void configureHandlers() {
		bindHandler(ListNodes.class, ListNodesHandler.class);
	}

}
