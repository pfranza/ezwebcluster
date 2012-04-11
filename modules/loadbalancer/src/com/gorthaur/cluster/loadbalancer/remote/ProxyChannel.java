package com.gorthaur.cluster.loadbalancer.remote;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;

import com.google.inject.ImplementedBy;

@ImplementedBy(DefaultProxyChannel.class)
public interface ProxyChannel {

	boolean write(ChannelBuffer msg);

	ProxyChannel bind(ChannelFuture f, Channel origionator);

	Channel getChannel();

	void close();

}
