package com.gorthaur.cluster.loadbalancer;

import java.net.InetSocketAddress;
import java.util.HashMap;

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

@Singleton
public class DefaultSocketCollection implements SocketCollection {

	@Inject
	ClientSocketChannelFactory cf;
	
	private HashMap<Integer, ChannelFuture> channels = new HashMap<Integer, ChannelFuture>();
	
	@Override
	public void create(int id, final Channel origionator) throws Exception {
		 ClientBootstrap cb = new ClientBootstrap(cf);
		 cb.getPipeline().addLast("handler", new OutboundHandler(origionator));
		 ChannelFuture f = cb.connect(new InetSocketAddress("72.215.147.158", 80));
		 channels.put(id, f);
		 f.addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture arg0) throws Exception {
				if(arg0.isSuccess()) {
					origionator.setReadable(true);
				} else {
					origionator.close();
				}
			}
		});
	}

	@Override
	public void destroy(int id) throws Exception {
		ChannelFuture f = channels.remove(id);
		if(f != null) {
			closeOnFlush(f.getChannel());
		}
	}

	@Override
	public Channel get(int id) {
		ChannelFuture f = channels.get(id);
		if(f != null) {
			return f.getChannel();
		}
		return null;
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
