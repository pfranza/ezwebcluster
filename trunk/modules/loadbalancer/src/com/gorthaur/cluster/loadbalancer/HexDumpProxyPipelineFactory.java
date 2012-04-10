package com.gorthaur.cluster.loadbalancer;

import static org.jboss.netty.channel.Channels.pipeline;

import javax.inject.Inject;
import javax.net.ssl.SSLEngine;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.handler.ssl.SslHandler;

import com.atlt.loadbalancer.ssl.SecureChatSslContextFactory;

public class HexDumpProxyPipelineFactory implements ChannelPipelineFactory {

	@Inject
	HexDumpProxyInboundHandler handler;

	@Override
	public ChannelPipeline getPipeline() throws Exception {
		ChannelPipeline pipeline = pipeline();
				
		SSLEngine engine = SecureChatSslContextFactory.getServerContext().createSSLEngine();
		engine.setUseClientMode(false);
		pipeline.addLast("ssl", new SslHandler(engine));
		
        pipeline.addLast("handler", handler);
		return pipeline;
	}

}
