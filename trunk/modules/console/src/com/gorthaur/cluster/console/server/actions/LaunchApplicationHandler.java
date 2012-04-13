package com.gorthaur.cluster.console.server.actions;

import java.io.ByteArrayOutputStream;

import javax.inject.Inject;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;
import net.customware.gwt.dispatch.shared.general.StringResult;

import com.gorthaur.cluster.channels.AdministrationChannel;
import com.gorthaur.cluster.console.client.shared.LaunchApplication;

public class LaunchApplicationHandler implements ActionHandler<LaunchApplication, StringResult>{

	@Inject
	AdministrationChannel channel;
	
	@Override
	public StringResult execute(LaunchApplication arg0, ExecutionContext arg1)
			throws DispatchException {
		try {
			com.gorthaur.cluster.protocol.Cluster.LaunchApplication msg = com.gorthaur.cluster.protocol.Cluster.LaunchApplication.newBuilder()
					.setClsName(arg0.getApplicationClassName())
					.build();

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			msg.writeTo(out);

			channel.publishMessage(arg0.getNodeAddress(), com.gorthaur.cluster.protocol.Cluster.LaunchApplication.class, out.toByteArray());

			return new StringResult("OK");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Class<LaunchApplication> getActionType() {
		return LaunchApplication.class;
	}

	@Override
	public void rollback(LaunchApplication arg0, StringResult arg1,
			ExecutionContext arg2) throws DispatchException {}

}
