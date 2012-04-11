package com.gorthaur.cluster.loadbalancer.remote;

import org.jboss.netty.channel.Channel;

import com.google.inject.ImplementedBy;

@ImplementedBy(DefaultSocketCollection.class)
public interface SocketCollection {

	void create(Channel origionator) throws Exception ;

	void destroy(Channel origionator) throws Exception ;

	ProxyChannel get(Channel origionator) throws Exception;

}
