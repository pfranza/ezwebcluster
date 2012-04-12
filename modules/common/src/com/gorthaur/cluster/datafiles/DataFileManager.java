package com.gorthaur.cluster.datafiles;

import com.google.inject.ImplementedBy;
import com.gorthaur.cluster.protocol.Cluster.ClusterNode.Builder;
import com.gorthaur.cluster.protocol.Cluster.ReplicateFile;

@ImplementedBy(DefaultDataFileManager.class)
public interface DataFileManager {

	void createFile(ReplicateFile file);

	void populateStatus(Builder builder);

}
