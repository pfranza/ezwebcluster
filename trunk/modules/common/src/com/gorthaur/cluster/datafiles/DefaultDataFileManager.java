package com.gorthaur.cluster.datafiles;

import java.io.File;

import javax.inject.Singleton;

import com.gorthaur.cluster.protocol.Cluster.ClusterNode.Builder;
import com.gorthaur.cluster.protocol.Cluster.ClusterNode.DataFile;
import com.gorthaur.cluster.protocol.Cluster.ReplicateFile;

@Singleton
public class DefaultDataFileManager implements DataFileManager {

	private File directory = new File("data");
	
	public DefaultDataFileManager() {
		if(directory.exists()) {
			directory.delete();
		}
		
		directory.mkdirs();
	}

	@Override
	public void createFile(ReplicateFile file) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void populateStatus(Builder builder) {
		for(File f: directory.listFiles()) {
			DataFile file = DataFile.newBuilder()
					.setFilename(f.getName())
					.setChecksum(createChecksum(f))
					.build();
			
			builder.addFiles(file);
		}
	}

	private String createChecksum(File f) {
		// TODO Auto-generated method stub
		return "";
	}
	
}
