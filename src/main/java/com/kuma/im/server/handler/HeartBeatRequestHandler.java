package com.kuma.im.server.handler;

import com.kuma.im.protocol.packet.HeartBeatRequestPacket;
import com.kuma.im.protocol.packet.HeartBeatResponsePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 心跳检测处理
 *
 * @author kuma 2021-02-28
 */
@Slf4j
@ChannelHandler.Sharable
public class HeartBeatRequestHandler extends SimpleChannelInboundHandler<HeartBeatRequestPacket> {

    public static final HeartBeatRequestHandler INSTANCE = new HeartBeatRequestHandler();

    private HeartBeatRequestHandler() {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HeartBeatRequestPacket msg) throws Exception {
        log.info("Heartbeat!");
        ctx.writeAndFlush(new HeartBeatResponsePacket());
    }
}
