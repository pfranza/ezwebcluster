package com.gorthaur.cluster;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.gorthaur.cluster.channels.AdministrationChannel;
import com.gorthaur.cluster.protocol.Cluster.ClusterNode;

@Singleton
public class ClusterStateManager {

	@Inject
	AdministrationChannel channel;
	
	private Cache<String, ClusterNode> cache = CacheBuilder.newBuilder()
		    .expireAfterWrite(10, TimeUnit.SECONDS)
		    .build(); 
	
	private Timer t = new Timer();
	
	private TimerTask task = new TimerTask() {		
		@Override
		public void run() {
			try {
				ClusterNode node = ClusterNode.newBuilder()
						.setName(channel.getName()).build();
				
				channel.publishMessage(node);
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
