package com.gorthaur.cluster.loadbalancer;

import java.util.concurrent.Executor;

import javax.inject.Inject;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

import com.gorthaur.cluster.loadbalancer.remote.ProxyChannel;
import com.gorthaur.cluster.loadbalancer.remote.SocketCollection;

public class ProxyInboundHandler extends SimpleChannelUpstreamHandler {

	private SocketCollection sockets;
	//private Executor executor;
	
    @Inject
    public ProxyInboundHandler(SocketCollection sockets, Executor executor) {
        this.sockets = sockets;
//        this.executor = executor;
    }

    @Override
    public void messageReceived(final ChannelHandlerContext ctx, final MessageEvent e)
            throws Exception {
    	ChannelBuffer msg = (ChannelBuffer) e.getMessage();    	
    	ProxyChannel proxyChannel = sockets.get(e.getChannel());
    	if(!proxyChannel.write(msg)) {
    		sockets.destroy(e.getChannel());
//    		executor.execute(new Runnable() {			
//				@Override
//				public void run() {
//					try {
//						System.out.println("Resend");
//						messageReceived(ctx, e);
//					} catch (Exception e) {
//						throw new RuntimeException(e);
//					}
//				}
//			});
    	}
    }
    
    @Override
    public void channelOpen(ChannelHandlerContext ctx, ChannelStateEvent e)
    		throws Exception {
    	sockets.create(e.getChannel());
    }
    
    @Override
    public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e)
    		throws Exception {
    	sockets.destroy(e.getChannel());
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
    		throws Exception {
    	e.getChannel().close();
    }
  

    static void closeOnFlush(Channel ch) {
    	if (ch.isConnected()) {
    		ch.write(ChannelBuffers.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    	}
    }

}