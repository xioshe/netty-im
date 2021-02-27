package com.kuma.im.server.handler;

import com.kuma.im.entity.packet.JoinGroupRequestPacket;
import com.kuma.im.entity.packet.JoinGroupResponsePacket;
import com.kuma.im.util.SessionUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

/**
 * 加入群聊请求
 *
 * @author kuma 2021-02-27
 */
public class JoinGroupRequestHandler extends SimpleChannelInboundHandler<JoinGroupRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupRequestPacket msg) throws Exception {
        String groupId = msg.getGroupId();
        ChannelGroup channelGroup = SessionUtils.getChannelGroup(groupId);
        channelGroup.add(ctx.channel());

        JoinGroupResponsePacket joinGroupResponsePacket = new JoinGroupResponsePacket();
        joinGroupResponsePacket.setGroupId(groupId);
        joinGroupResponsePacket.setSuccess(true);
        ctx.channel().writeAndFlush(joinGroupResponsePacket);
    }
}
