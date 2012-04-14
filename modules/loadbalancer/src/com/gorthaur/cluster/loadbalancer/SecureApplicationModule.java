package com.gorthaur.cluster.loadbalancer;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.socket.ClientSocketChannelFactory;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import com.google.inject.AbstractModule;

public class SecureApplicationModule extends AbstractModule {

	@Override
	protected void configure() {
		Executor executor = Executors.newCachedThreadPool();
		bind(Executor.class).toInstance(executor);
		bind(ClientSocketChannelFactory.class).toInstance(new NioClientSocketChannelFactory(executor, executor));
		bind(ServerBootstrap.class).toInstance(new ServerBootstrap(new NioServerSocketChannelFactory(executor, executor)));
		bind(ChannelPipelineFactory.class).to(SSLProxyPipelineFactory.class);
	}

}
