package com.kuma.im.client.handler;

import com.kuma.im.protocol.packet.GroupMessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 群聊消息接收处理器
 *
 * @author kuma 2021-02-28
 */
@Slf4j
public class GroupMessageResponseHandler extends SimpleChannelInboundHandler<GroupMessageResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageResponsePacket msg) throws Exception {
        log.info("{}", isSharable());
        log.info("群聊[{}]收到消息: {}:{} >> {}",
                msg.getGroupId(), msg.getFromUserId(), msg.getFromUsername(), msg.getMessage());
    }
}
