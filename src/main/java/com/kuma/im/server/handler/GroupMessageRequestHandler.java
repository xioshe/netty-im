package com.kuma.im.server.handler;

import com.kuma.im.protocol.packet.GroupMessageRequestPacket;
import com.kuma.im.protocol.packet.GroupMessageResponsePacket;
import com.kuma.im.session.Session;
import com.kuma.im.util.SessionUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import lombok.extern.slf4j.Slf4j;

/**
 * 发送消息处理器
 *
 * @author kuma 2021-02-28
 */
@Slf4j
public class GroupMessageRequestHandler extends SimpleChannelInboundHandler<GroupMessageRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageRequestPacket msg) throws Exception {
        String groupId = msg.getGroupId();
        ChannelGroup channelGroup = SessionUtils.getChannelGroup(groupId);
        if (channelGroup == null) {
            log.info("群聊[{}]不存在, 发送终止!", groupId);
            return;
        }
        GroupMessageResponsePacket groupMessageResponsePacket = new GroupMessageResponsePacket();
        groupMessageResponsePacket.setGroupId(groupId);
        groupMessageResponsePacket.setMessage(msg.getMessage());
        Session session = SessionUtils.getSession(ctx.channel());
        groupMessageResponsePacket.setFromUserId(session.getUserId());
        groupMessageResponsePacket.setFromUsername(session.getUsername());
        channelGroup.writeAndFlush(groupMessageResponsePacket);
    }
}
