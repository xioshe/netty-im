package com.kuma.im.client.handler;

import com.kuma.im.entity.packet.JoinGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 加入群聊响应处理器
 *
 * @author kuma 2021-02-27
 */
@Slf4j
public class JoinGroupResponseHandler extends SimpleChannelInboundHandler<JoinGroupResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupResponsePacket msg) {
        if (msg.isSuccess()) {
            log.info("加入群聊[{}]成功", msg.getGroupId());
        } else {
            log.info("加入群聊[{}]失败, 原因: {}", msg.getGroupId(), msg.getReason());
        }
    }
}
