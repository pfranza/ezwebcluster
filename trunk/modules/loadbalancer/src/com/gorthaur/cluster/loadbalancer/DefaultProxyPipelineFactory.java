package com.gorthaur.cluster.loadbalancer;

import static org.jboss.netty.channel.Channels.pipeline;

import javax.inject.Inject;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;

public class DefaultProxyPipelineFactory implements ChannelPipelineFactory {

	@Inject
	ProxyInboundHandler handler;

	@Override
	public ChannelPipeline getPipeline() throws Exception {
		ChannelPipeline pipeline = pipeline();
        pipeline.addLast("handler", handler);
		return pipeline;
	}

}
