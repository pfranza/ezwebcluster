package com.gorthaur.cluster.channels;

import java.io.DataInput;
import java.io.DataOutput;
import java.util.Collection;
import java.util.Properties;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.jgroups.Address;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;
import org.jgroups.blocks.RequestCorrelator.Header;
import org.jgroups.conf.ClassConfigurator;
import org.jgroups.util.Streamable;

import com.gorthaur.cluster.ClusterStateManager;
import com.gorthaur.cluster.applications.Application;
import com.gorthaur.cluster.applications.DefaultLocalApplicationManager;
import com.gorthaur.cluster.datafiles.DataFileManager;
import com.gorthaur.cluster.protocol.Cluster.ClusterNode;
import com.gorthaur.cluster.protocol.Cluster.LaunchApplication;
import com.gorthaur.cluster.protocol.Cluster.LaunchApplication.Property;
import com.gorthaur.cluster.protocol.Cluster.ReplicateFile;
import com.gorthaur.cluster.protocol.Cluster.ShutdownApplication;
import com.gorthaur.cluster.protocol.Cluster.TerminateClusterNode;
import com.gorthaur.cluster.protocol.Cluster.WebServerState;
import com.gorthaur.cluster.web.WebServerStateManager;

@Singleton
public class AdministrationChannel {

	private JChannel channel;

	private ReceiverAdapter recv = new ReceiverAdapter() {
		
		@SuppressWarnings("unchecked")
		public void receive(org.jgroups.Message msg) {
			try {
				ProtoBufHeader header = (ProtoBufHeader) msg.getHeader((short) 1900);
				if(header == null || header.className == null) {
					System.out.println("Dumping Message");
					return;
				}
				if(header.className.equals(ClusterNode.class.getName())) {
					ClusterNode node = ClusterNode.parseFrom(msg.getBuffer());
					stateManager.processNewState(node, msg.getSrc());
				} else if(header.className.equals(LaunchApplication.class.getName())) {
					LaunchApplication application = LaunchApplication.parseFrom(msg.getBuffer());
					Properties properties = new Properties();
					for(Property p: application.getPropertiesList()) {
						properties.setProperty(p.getKey(), p.getValue());
					}
					applicationManager.launchApplication((Class<? extends Application>)Class.forName(application.getClsName()), properties);
				} else if(header.className.equals(ShutdownApplication.class.getName())) {
					ShutdownApplication application = ShutdownApplication.parseFrom(msg.getBuffer());
					applicationManager.shutdownApplication(application.getApplicationId());
				} else if(header.className.equals(TerminateClusterNode.class.getName())) {
					System.out.println("Terminating Cluster Node: ");
					System.exit(0);
				} else if(header.className.equals(ReplicateFile.class.getName())) {
					ReplicateFile file = ReplicateFile.parseFrom(msg.getBuffer());
					dataFileManager.createFile(file);
				} else if(header.className.equals(WebServerState.class.getName())) {
					webStateManager.processUpdate(WebServerState.parseFrom(msg.getBuffer()));
				} else {
					System.out.println("Unknown class: " + header.className);
				}
				
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		public void viewAccepted(View newView) {
			 System.out.println("** view: " + newView);
		}
		
	};
	
	@Inject ClusterStateManager stateManager;
	@Inject DefaultLocalApplicationManager applicationManager;
	@Inject DataFileManager dataFileManager;
	@Inject WebServerStateManager webStateManager;
	
	@Inject
	AdministrationChannel() throws Exception {
		ClassConfigurator.add((short) 1900, ProtoBufHeader.class);
		channel=new JChannel();
		channel.setReceiver(recv);
		channel.connect("AdministrationChannel");
		System.out.println("Local Name: " + channel.getName() );					
	}
	
	public Collection<Address> getAddresses() {
		return channel.getView().getMembers();
	}

	public String getName() {
		return channel.getAddressAsString();
	}

	public void publishMessage(Class<?> cls, byte[] bytes) throws Exception {
		publishMessage((Address)null, cls, bytes);
	}
	
	public void publishMessage(String address, Class<?> cls, byte[] bytes) throws Exception {
		publishMessage(getAddressForString(address), cls, bytes);
	}
	
	private Address getAddressForString(String s) {
		return stateManager.forNodeName(s);
	}
	
	public void publishMessage(Address address, Class<?> cls, byte[] bytes) throws Exception {
		Message msg = new Message(address, bytes);
		msg.putHeader((short) 1900, new ProtoBufHeader(cls));
		channel.send(msg);
	}

	public static class ProtoBufHeader extends Header implements Streamable {
		
		String className;

		public ProtoBufHeader() {}

        public ProtoBufHeader(Class<?> cls) {
           className = cls.getName();
        }

        public String toString() {
            return className;
        }

        public int size() {
            return className.length();
        }

        @Override
        public void writeTo(DataOutput out) throws Exception {
        	out.writeUTF(className);
        }
        
        @Override
        public void readFrom(DataInput in) throws Exception {
        	className = in.readUTF();
        }

	}
	
}
