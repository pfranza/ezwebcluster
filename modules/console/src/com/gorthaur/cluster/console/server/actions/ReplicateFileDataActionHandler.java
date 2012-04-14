package com.gorthaur.cluster.console.server.actions;

import java.io.ByteArrayOutputStream;
import java.util.zip.Adler32;

import javax.inject.Inject;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;
import net.customware.gwt.dispatch.shared.general.StringResult;

import com.google.protobuf.ByteString;
import com.gorthaur.cluster.channels.AdministrationChannel;
import com.gorthaur.cluster.console.client.shared.ReplicateFileDataAction;
import com.gorthaur.cluster.console.server.util.Base64;
import com.gorthaur.cluster.protocol.Cluster.ReplicateFile;

public class ReplicateFileDataActionHandler implements ActionHandler<ReplicateFileDataAction, StringResult>{

	@Inject
	AdministrationChannel channel;
	
	@Override
	public StringResult execute(ReplicateFileDataAction arg0,
			ExecutionContext arg1) throws DispatchException {
		try {
				
			byte[] data = Base64.decode(arg0.getData().split("base64,")[1]);
			Adler32 c = new Adler32();
			c.update(data);
			long checksum = c.getValue();

			ReplicateFile file = ReplicateFile.newBuilder()
					.setData(ByteString.copyFrom(data))
					.setChecksum("" + checksum)
					.setName(arg0.getName())
					.build();

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			file.writeTo(out);

			channel.publishMessage(file.getClass(), out.toByteArray());	

			return new StringResult("OK:" + checksum);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Class<ReplicateFileDataAction> getActionType() {
		return ReplicateFileDataAction.class;
	}

	@Override
	public void rollback(ReplicateFileDataAction arg0, StringResult arg1,
			ExecutionContext arg2) throws DispatchException {}

}
