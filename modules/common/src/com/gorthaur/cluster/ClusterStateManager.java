package com.gorthaur.cluster;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.gorthaur.cluster.applications.LocalApplicationManager;
import com.gorthaur.cluster.channels.AdministrationChannel;
import com.gorthaur.cluster.protocol.Cluster.ClusterNode;
import com.jezhumble.javasysmon.CpuTimes;
import com.jezhumble.javasysmon.JavaSysMon;

@Singleton
public class ClusterStateManager {

	@Inject
	AdministrationChannel channel;
	
	@Inject LocalApplicationManager applicationManager;
	
	private JavaSysMon monitor = new JavaSysMon();
	private CpuTimes prevTimes;
	
	private Cache<String, ClusterNode> cache = CacheBuilder.newBuilder()
		    .expireAfterWrite(10, TimeUnit.SECONDS)
		    .build(); 
	
	private Timer t = new Timer();
	
	private TimerTask task = new TimerTask() {		
		@Override
		public void run() {
			try {
				ClusterNode node = ClusterNode.newBuilder()
						.setName(channel.getName())
						.setCpuFrequency(monitor.cpuFrequencyInHz())
						.setCpuUtilization(prevTimes != null ? monitor.cpuTimes().getCpuUsage(prevTimes) : 0 )
						.setMemoryFreeBytes(monitor.physical().getFreeBytes())
						.setMemoryTotalBytes(monitor.physical().getTotalBytes())
						.build();
	
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				node.writeTo(out);
				
				channel.publishMessage(ClusterNode.class, out.toByteArray());
				cache.cleanUp();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}			
		}
	};
	
	{
		t.scheduleAtFixedRate(task, 10000, 5000);
	}

	public void processNewState(ClusterNode node) {
		cache.put(node.getName(), node);
	}
	
	public Collection<ClusterNode> getAllNodes() {
		ArrayList<ClusterNode> list = new ArrayList<ClusterNode>();
		list.addAll(cache.asMap().values());
		return list;
	}
	
}
