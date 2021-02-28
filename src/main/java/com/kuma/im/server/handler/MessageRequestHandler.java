package com.kuma.im.server.handler;

import com.kuma.im.protocol.packet.MessageRequestPacket;
import com.kuma.im.protocol.packet.MessageResponsePacket;
import com.kuma.im.session.Session;
import com.kuma.im.util.SessionUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 消息请求处理
 *
 * @author kuma 2021-02-26
 */
@Slf4j
@ChannelHandler.Sharable
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    public static final MessageRequestHandler INSTANCE = new MessageRequestHandler();

    protected MessageRequestHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket messageRequestPacket) {
        Channel toUserChannel = SessionUtils.getChannel(messageRequestPacket.getToUserId());
        // 如果消息接收者不在线，fail fast
        if (toUserChannel == null || !SessionUtils.hasLogin(toUserChannel)) {
            log.info("[{}] 不在线，消息发送失败!", messageRequestPacket.getToUserId());
            return;
        }
        // 将消息内容从发送人转交给接收人
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setMessage(messageRequestPacket.getMessage());

        // 取出消息发送人的 Session
        Session session = SessionUtils.getSession(ctx.channel());
        // 从 session 中取出发送人的信息
        messageResponsePacket.setFromUserId(session.getUserId());
        messageResponsePacket.setFromUsername(session.getUsername());

        toUserChannel.writeAndFlush(messageResponsePacket);
    }
}
