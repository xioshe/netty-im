package com.kuma.im.client.handler;

import com.kuma.im.protocol.packet.HeartBeatResponsePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 心跳检测返回值
 *
 * @author kuma 2021-02-28
 */
@Slf4j
@ChannelHandler.Sharable
public class HeartBeatResponseHandler extends SimpleChannelInboundHandler<HeartBeatResponsePacket> {

    public static final HeartBeatResponseHandler INSTANCE = new HeartBeatResponseHandler();

    private HeartBeatResponseHandler() {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HeartBeatResponsePacket msg) throws Exception {
        log.info("Received a heartbeatPacket from server");
    }
}
