package com.zhouzy.netty.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client channelActive..");
       // ctx.writeAndFlush(Unpooled.copiedBuffer("这是客户端!", CharsetUtil.UTF_8)); // 必须有flush
        ctx.writeAndFlush(Unpooled.copiedBuffer("这是客户端!", CharsetUtil.UTF_8)); // 必须有flush
        //ctx.write("这是客户端!");
        //ctx.flush();
        // 必须存在flush
        // ctx.write(Unpooled.copiedBuffer("Netty rocks!", CharsetUtil.UTF_8));
        // ctx.flush();
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        System.out.println("client channelRead..");
        ByteBuf buf = msg.readBytes(msg.readableBytes());
        System.out.println("Client received:" + ByteBufUtil.hexDump(buf) + "; The value is:" + buf.toString(Charset.forName("utf-8")));
        //ctx.channel().close().sync();// client关闭channel连接
    }
    
    /** 
         *此方法会在接收到服务器数据后调用  
         * */  
        public void channelRead0(ChannelHandlerContext ctx, ByteBuf in) {  
            System.out.println("Client received: " + ByteBufUtil.hexDump(in.readBytes(in.readableBytes())));  
        }  


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}