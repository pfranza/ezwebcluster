package com.gorthaur.cluster.loadbalancer;

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


public class HexDumpProxyInboundHandler extends SimpleChannelUpstreamHandler {

	private SocketCollection sockets;
	
    @Inject
    public HexDumpProxyInboundHandler(SocketCollection sockets) {
        this.sockets = sockets;
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
            throws Exception {
    	
    	ChannelBuffer msg = (ChannelBuffer) e.getMessage();
    	
    	Channel proxyChannel = sockets.get(e.getChannel().getId());
    	proxyChannel.write(msg);
    }
    
    @Override
    public void channelOpen(ChannelHandlerContext ctx, ChannelStateEvent e)
    		throws Exception {
    	sockets.create(e.getChannel().getId(), e.getChannel());
    }
    
    @Override
    public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e)
    		throws Exception {
    	sockets.destroy(e.getChannel().getId());
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