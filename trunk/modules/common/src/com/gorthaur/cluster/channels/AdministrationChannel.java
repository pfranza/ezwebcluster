package com.gorthaur.cluster.channels;

import java.io.ByteArrayOutputStream;
import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.jgroups.Address;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;

import com.gorthaur.cluster.ClusterStateManager;
import com.gorthaur.cluster.protocol.Cluster.ClusterNode;

@Singleton
public class AdministrationChannel {

	private JChannel channel;

	private ReceiverAdapter recv = new ReceiverAdapter() {
		
		public void receive(org.jgroups.Message msg) {
			try {
				ClusterNode node = ClusterNode.parseFrom(msg.getBuffer());
				stateManager.processNewState(node);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		public void viewAccepted(View newView) {
			 System.out.println("** view: " + newView);
		}
		
	};
	
	@Inject ClusterStateManager stateManager;
	
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

	public String getName() {
		return channel.getName();
	}

	public void publishMessage(ClusterNode node) throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		node.writeTo(out);
		channel.send(new Message(null, out.toByteArray()));
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
