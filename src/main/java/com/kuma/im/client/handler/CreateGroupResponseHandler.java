package com.kuma.im.client.handler;

import com.kuma.im.entity.packet.CreateGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 创建群聊响应处理器
 *
 * @author kuma 2021-02-27
 */
@Slf4j
public class CreateGroupResponseHandler extends SimpleChannelInboundHandler<CreateGroupResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupResponsePacket createGroupResponsePacket) {
        log.info("群创建成功, id 为 [{}]", createGroupResponsePacket.getGroupId());
        log.info("群成员列表: {}", createGroupResponsePacket.getUsernameList());
    }
}
