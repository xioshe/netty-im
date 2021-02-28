package com.kuma.im.server.handler;

import com.kuma.im.protocol.packet.QuitGroupRequestPacket;
import com.kuma.im.protocol.packet.QuitGroupResponsePacket;
import com.kuma.im.util.SessionUtils;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

/**
 * 退出群聊请求处理器
 *
 * @author kuma 2021-02-27
 */
@ChannelHandler.Sharable
public class QuitGroupRequestHandler extends SimpleChannelInboundHandler<QuitGroupRequestPacket> {

    public static final QuitGroupRequestHandler INSTANCE = new QuitGroupRequestHandler();

    protected QuitGroupRequestHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupRequestPacket msg) throws Exception {
        QuitGroupResponsePacket quitGroupResponsePacket = new QuitGroupResponsePacket();

        String groupId = msg.getGroupId();
        ChannelGroup channelGroup = SessionUtils.getChannelGroup(groupId);
        if (channelGroup != null) {
            channelGroup.remove(ctx.channel());

            quitGroupResponsePacket.setSuccess(true);
            quitGroupResponsePacket.setGroupId(groupId);
        } else {
            quitGroupResponsePacket.setSuccess(false);
            quitGroupResponsePacket.setReason("群聊不存在!");
        }

        ctx.channel().writeAndFlush(quitGroupResponsePacket);
    }
}
