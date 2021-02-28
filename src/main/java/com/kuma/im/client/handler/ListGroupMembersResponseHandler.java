package com.kuma.im.client.handler;

import com.kuma.im.protocol.packet.ListGroupMembersResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 查询群聊成员处理器
 *
 * @author kuma 2021-02-27
 */
@Slf4j
public class ListGroupMembersResponseHandler extends SimpleChannelInboundHandler<ListGroupMembersResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListGroupMembersResponsePacket msg) throws Exception {
        if (msg.isSuccess()) {
            log.info("群聊[{}]中的人包括: {}", msg.getGroupId(), msg.getSessionList());
        } else {
            log.info("查询群聊成员失败, 原因: {}", msg.getReason());
        }
    }
}
