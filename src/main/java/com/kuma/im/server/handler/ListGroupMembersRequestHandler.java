package com.kuma.im.server.handler;

import com.kuma.im.protocol.packet.ListGroupMembersRequestPacket;
import com.kuma.im.protocol.packet.ListGroupMembersResponsePacket;
import com.kuma.im.session.Session;
import com.kuma.im.util.SessionUtils;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 查询群聊成员列表请求处理器
 *
 * @author kuma 2021-02-27
 */
@ChannelHandler.Sharable
public class ListGroupMembersRequestHandler extends SimpleChannelInboundHandler<ListGroupMembersRequestPacket> {

    public static final ListGroupMembersRequestHandler INSTANCE = new ListGroupMembersRequestHandler();

    protected ListGroupMembersRequestHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListGroupMembersRequestPacket msg) throws Exception {
        ListGroupMembersResponsePacket listGroupMembersResponsePacket = new ListGroupMembersResponsePacket();

        String groupId = msg.getGroupId();
        ChannelGroup channelGroup = SessionUtils.getChannelGroup(groupId);
        if (channelGroup != null) {
            listGroupMembersResponsePacket.setSuccess(true);
            List<Session> sessions = channelGroup.stream()
                    .map(SessionUtils::getSession).collect(Collectors.toList());
            listGroupMembersResponsePacket.setGroupId(groupId);
            listGroupMembersResponsePacket.setSessionList(sessions);
        } else {
            listGroupMembersResponsePacket.setSuccess(false);
            listGroupMembersResponsePacket.setReason("群聊不存在!");
        }
        ctx.channel().writeAndFlush(listGroupMembersResponsePacket);
    }
}
