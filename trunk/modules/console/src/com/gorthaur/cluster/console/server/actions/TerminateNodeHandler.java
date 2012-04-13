package com.gorthaur.cluster.console.server.actions;

import java.io.ByteArrayOutputStream;

import javax.inject.Inject;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;
import net.customware.gwt.dispatch.shared.general.StringResult;

import com.gorthaur.cluster.channels.AdministrationChannel;
import com.gorthaur.cluster.console.client.shared.TerminateNode;

public class TerminateNodeHandler implements ActionHandler<TerminateNode, StringResult> {

	@Inject
	AdministrationChannel channel;
	
	@Override
	public StringResult execute(TerminateNode arg0, ExecutionContext arg1)
			throws DispatchException {
		try { 
			com.gorthaur.cluster.protocol.Cluster.TerminateClusterNode msg = com.gorthaur.cluster.protocol.Cluster.TerminateClusterNode.newBuilder()
					.build();

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			msg.writeTo(out);

			channel.publishMessage(arg0.getNodeAddress(), msg.getClass(), out.toByteArray());

			return new StringResult("");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Class<TerminateNode> getActionType() {
		return TerminateNode.class;
	}

	@Override
	public void rollback(TerminateNode arg0, StringResult arg1,
			ExecutionContext arg2) throws DispatchException {}

}
