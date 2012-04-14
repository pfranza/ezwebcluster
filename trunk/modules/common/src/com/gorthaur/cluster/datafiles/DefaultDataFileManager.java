package com.gorthaur.cluster.datafiles;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;
import java.util.zip.Adler32;

import javax.inject.Singleton;

import org.apache.commons.io.FileUtils;

import com.gorthaur.cluster.protocol.Cluster.ClusterNode.Builder;
import com.gorthaur.cluster.protocol.Cluster.ClusterNode.DataFile;
import com.gorthaur.cluster.protocol.Cluster.ReplicateFile;

@Singleton
public class DefaultDataFileManager implements DataFileManager {

	private File directory = new File(new File("data"), UUID.randomUUID().toString());
	
	public DefaultDataFileManager() throws Exception {
		directory.mkdirs();
		FileUtils.cleanDirectory(directory);
	}

	@Override
	public void createFile(ReplicateFile file) {
		System.out.println("Creating File");
		File f = new File(directory, file.getName());

		try {
			if(f.exists() && file.getChecksum().equals(createChecksum(f))) {
				System.out.println("File is uptodate");
			} else {
				FileOutputStream fos = new FileOutputStream(f);
				fos.write(file.getData().toByteArray());
				fos.close();
				
				if(!(f.exists() && file.getChecksum().equals(createChecksum(f)))) {
					f.delete();
					System.out.println("File Checksum Mismatch");
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
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
		try {
		return "" + FileUtils.checksum(f, new Adler32()).getValue();
		} catch (Exception e) {
			return "";
		}
	}
	
}
