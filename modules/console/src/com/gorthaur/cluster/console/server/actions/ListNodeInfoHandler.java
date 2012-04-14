package com.gorthaur.cluster.console.server.actions;

import java.util.ArrayList;

import javax.inject.Inject;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.gorthaur.cluster.ClusterStateManager;
import com.gorthaur.cluster.console.client.shared.ClusterNodeInfo;
import com.gorthaur.cluster.console.client.shared.ListNodeInfo;
import com.gorthaur.cluster.protocol.Cluster.ClusterNode;
import com.gorthaur.cluster.protocol.Cluster.ClusterNode.ActiveApplications;
import com.gorthaur.cluster.protocol.Cluster.ClusterNode.DataFile;

public class ListNodeInfoHandler implements ActionHandler<ListNodeInfo, ClusterNodeInfo>{

	@Inject
	ClusterStateManager channel;
	
	@Override
	public ClusterNodeInfo execute(ListNodeInfo arg0, ExecutionContext arg1)
			throws DispatchException {

		for(ClusterNode c: channel.getAllNodes()) {
			if(c.getName().equalsIgnoreCase(arg0.getName())) {
				ClusterNodeInfo info = new ClusterNodeInfo();
				
				ArrayList<String>  filenames = new ArrayList<String>();
				ArrayList<String>  checksum = new ArrayList<String>();
				
				ArrayList<String>  name = new ArrayList<String>();
				ArrayList<String>  applicationId = new ArrayList<String>();
				ArrayList<String>  status = new ArrayList<String>();
				
				info.setMemoryFreePercent(
						((double)c.getMemoryFreeBytes() / (double)c.getMemoryTotalBytes()) * 100.0);
				
				info.setCpuUtilization(c.getCpuUtilization());
				
//				System.out.println("Free: " + c.getMemoryFreeBytes());
//				System.out.println("Total: " + c.getMemoryTotalBytes());
//				
				for(ActiveApplications a: c.getApplicationsList()) {
					name.add(a.getName());
					applicationId.add(a.getApplicationId());
					status.add(a.getStatus().name());
				}
				
				for(DataFile f: c.getFilesList()) {
					filenames.add(f.getFilename());
					checksum.add(f.getChecksum());
				}
				
				info.setFilenames(filenames.toArray(new String[0]));
				info.setChecksum(checksum.toArray(new String[0]));
				
				info.setName(name.toArray(new String[0]));
				info.setApplicationId(applicationId.toArray(new String[0]));
				info.setStatus(status.toArray(new String[0]));
				
				return info;
			}
		}
		
		return null;
	}

	@Override
	public Class<ListNodeInfo> getActionType() {
		return ListNodeInfo.class;
	}

	@Override
	public void rollback(ListNodeInfo arg0, ClusterNodeInfo arg1,
			ExecutionContext arg2) throws DispatchException {}

}
