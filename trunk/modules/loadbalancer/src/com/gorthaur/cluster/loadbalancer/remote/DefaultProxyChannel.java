package com.gorthaur.cluster.loadbalancer.remote;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;

public class DefaultProxyChannel implements ProxyChannel {

	private ChannelFuture f;
	private boolean writable = false;

	@Override
	public ProxyChannel bind(ChannelFuture f, final Channel origionator) {
		this.f = f;
		f.addListener(new ChannelFutureListener() {		
			@Override
			public void operationComplete(ChannelFuture arg0) throws Exception {
				if(arg0.isSuccess()) {
					origionator.setReadable(true);
					writable = true;
				} else {
					origionator.close();
				}
			}
		});
		return this;
	}
	
	@Override
	public boolean write(ChannelBuffer msg) {
		if (writable) {
			getChannel().write(msg);
			return true;
		}
		return false;
	}

	@Override
	public org.jboss.netty.channel.Channel getChannel() {
		return f.getChannel();
	}

	@Override
	public void close() {
		
	}


}
