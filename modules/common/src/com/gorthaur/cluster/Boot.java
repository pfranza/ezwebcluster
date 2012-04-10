package com.gorthaur.cluster;

import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import org.jgroups.Address;

import com.google.inject.Guice;
import com.gorthaur.cluster.channels.AdministrationChannel;

public class Boot {

	@Inject
	AdministrationChannel channel;
	
	void start() {
		Timer t = new Timer();
		t.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				for(Address a: channel.getAddresses()) {
					System.out.println(a);
				}
			}
		}, 2000, 10000);
	}
	
	public static void main(String[] args) {
		Guice.createInjector().getInstance(Boot.class).start();		
	}
	
}
