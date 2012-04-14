package com.gorthaur.cluster.loadbalancer.remote;

import java.net.SocketAddress;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.channel.socket.ClientSocketChannelFactory;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.google.inject.Provider;
import com.gorthaur.cluster.loadbalancer.EndpointFactory;


@Singleton
public class DefaultSocketCollection implements SocketCollection {

	@Inject
	ClientSocketChannelFactory cf;	
	
	@Inject 
	EndpointFactory endpointFactory;
	
	@Inject
	Provider<ProxyChannel> channelProvider;
	
	private LoadingCache<Channel, ProxyChannel> channels = CacheBuilder.newBuilder()
		       .expireAfterWrite(1, TimeUnit.MINUTES)
		       .removalListener(new RemovalListener<Channel, ProxyChannel>() {
		    	   @Override
		    	   public void onRemoval(RemovalNotification<Channel, ProxyChannel> arg0) {
		    		  closeOnFlush(arg0.getValue().getChannel());
		    		  arg0.getValue().close();
		    	   }
		        } ).build(new CacheLoader<Channel, ProxyChannel>() {
		             public ProxyChannel load(final Channel origionator) throws Exception {	            	 
		            	 ClientBootstrap cb = new ClientBootstrap(cf);
		        		 cb.getPipeline().addLast("handler", new OutboundHandler(origionator));
		        		 SocketAddress endPoint = endpointFactory.getNextEndpoint();
//		        		 System.out.println("Using: " + endPoint);
		        		 ChannelFuture f = cb.connect(endPoint);
		        		 return channelProvider.get().bind(f, origionator);
		            	 
		             }
		        });
	
	@Override
	public void create(final Channel origionator) throws Exception {
		channels.get(origionator);
	}

	@Override
	public void destroy(final Channel origionator) throws Exception {
		channels.invalidate(origionator);
	}

	@Override
	public ProxyChannel get(final Channel origionator) throws Exception {
		return channels.get(origionator);
	}

	private class OutboundHandler extends SimpleChannelUpstreamHandler {

		private final Channel inboundChannel;

		OutboundHandler(Channel inboundChannel) {
			this.inboundChannel = inboundChannel;
		}

		@Override
		public void messageReceived(ChannelHandlerContext ctx, final MessageEvent e)
				throws Exception {
			ChannelBuffer msg = (ChannelBuffer) e.getMessage();
			inboundChannel.write(msg);
		}

		@Override
		public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e)
				throws Exception {
			closeOnFlush(inboundChannel);
		}

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
				throws Exception {
			e.getCause().printStackTrace();
			closeOnFlush(e.getChannel());
		}
	}
		 
	static void closeOnFlush(Channel ch) {
		if (ch.isConnected()) {
			ch.write(ChannelBuffers.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
		}
	}
	
}
