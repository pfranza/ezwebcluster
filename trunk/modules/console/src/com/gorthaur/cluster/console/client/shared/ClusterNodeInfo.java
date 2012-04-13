package com.gorthaur.cluster.console.client.shared;

import net.customware.gwt.dispatch.shared.Result;

public class ClusterNodeInfo implements Result {

	private String[] filenames;
	private String[] checksum;
	
	private String[] name;
	private String[] applicationId;
	private String[] status;
	
	private double memoryFreePercent;
	private double cpuUtilization;
	
	public String[] getFilenames() {
		return filenames;
	}
	public void setFilenames(String[] filenames) {
		this.filenames = filenames;
	}
	public String[] getChecksum() {
		return checksum;
	}
	public void setChecksum(String[] checksum) {
		this.checksum = checksum;
	}
	public String[] getName() {
		return name;
	}
	public void setName(String[] name) {
		this.name = name;
	}
	public String[] getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(String[] applicationId) {
		this.applicationId = applicationId;
	}
	public String[] getStatus() {
		return status;
	}
	public void setStatus(String[] status) {
		this.status = status;
	}
	public double getMemoryFreePercent() {
		return memoryFreePercent;
	}
	public void setMemoryFreePercent(double memoryFreePercent) {
		this.memoryFreePercent = memoryFreePercent;
	}
	public double getCpuUtilization() {
		return cpuUtilization;
	}
	public void setCpuUtilization(double cpuUtilization) {
		this.cpuUtilization = cpuUtilization;
	}
	
	
	
}
