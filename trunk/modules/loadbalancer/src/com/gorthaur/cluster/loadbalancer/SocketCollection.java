package com.gorthaur.cluster.loadbalancer;

import org.jboss.netty.channel.Channel;

import com.google.inject.ImplementedBy;

@ImplementedBy(DefaultSocketCollection.class)
public interface SocketCollection {

	void create(int id, Channel origionator) throws Exception ;

	void destroy(int id) throws Exception ;

	Channel get(int id);

}
