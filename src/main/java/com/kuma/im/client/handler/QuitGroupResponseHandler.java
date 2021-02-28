package com.kuma.im.client.handler;

import com.kuma.im.protocol.packet.QuitGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 取出群聊响应处理器
 *
 * @author kuma 2021-02-27
 */
@Slf4j
public class QuitGroupResponseHandler extends SimpleChannelInboundHandler<QuitGroupResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupResponsePacket msg) throws Exception {
        if (msg.isSuccess()) {
            log.info("退出群聊[{}]成功!", msg.getGroupId());
        } else {
            log.info("退出群聊[{}]失败, 原因: {}", msg.getGroupId(), msg.getReason());
        }
    }
}
