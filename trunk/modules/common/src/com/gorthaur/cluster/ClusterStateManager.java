package com.gorthaur.cluster;

import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import com.gorthaur.cluster.channels.AdministrationChannel;

public class ClusterStateManager {

	@Inject
	AdministrationChannel channel;
	
	private Timer t = new Timer();
	
	private TimerTask task = new TimerTask() {		
		@Override
		public void run() {
			//ClusterNode 
		}
	};
	
	{
		t.scheduleAtFixedRate(task, 2000, 5000);
	}
	
}
