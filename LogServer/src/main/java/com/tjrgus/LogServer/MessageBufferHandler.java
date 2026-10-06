package com.tjrgus.LogServer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;

public class MessageBufferHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRegistered(ChannelHandlerContext ctx){
        System.out.println("channel Registered");
    }
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx){
        System.out.println("channel Un-Registered");
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg){
        ByteBuf in = (ByteBuf) msg;
        System.out.println("서버가 받은 : " + in.toString(StandardCharsets.UTF_8).trim());

        in.readerIndex(0);

        ctx.write("Success" + in);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx){
        ctx.flush();
        System.out.println("Read End");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}