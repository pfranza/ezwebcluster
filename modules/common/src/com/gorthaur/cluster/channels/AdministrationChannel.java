package com.gorthaur.cluster.channels;

import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.jgroups.Address;
import org.jgroups.JChannel;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;

@Singleton
public class AdministrationChannel {

	private JChannel channel;

	private ReceiverAdapter recv = new ReceiverAdapter() {
		
		public void receive(org.jgroups.Message msg) {
			
		}
		
		public void viewAccepted(View newView) {
			 System.out.println("** view: " + newView);
		}
		
	};
	
	@Inject
	AdministrationChannel() throws Exception {
		channel=new JChannel();
		channel.setReceiver(recv);
		channel.connect("AdministrationChannel");
		System.out.println("Local Name: " + channel.getName() );	
	}
	
	public Collection<Address> getAddresses() {
		return channel.getView().getMembers();
	}
	
//	private static class ClusterNode implements Serializable {
//
//		/**
//		 * 
//		 */
//		private static final long serialVersionUID = 3849098560672418456L;
//		
//		private Address address;
//		
//	}
//	
	
}
