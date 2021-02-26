package com.kuma.im.client.handler;

import com.kuma.im.entity.packet.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 消息请求响应处理
 *
 * @author kuma 2021-02-26
 */
@Slf4j
public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket msg) {
        String message = msg.getMessage();
        log.info("服务端发来消息 >> {}", message);
    }
}
