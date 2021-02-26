package com.kuma.im.server.handler;

import com.kuma.im.entity.PacketCodeC;
import com.kuma.im.entity.packet.MessageRequestPacket;
import com.kuma.im.entity.packet.MessageResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 消息请求处理
 *
 * @author kuma 2021-02-26
 */
@Slf4j
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket messageRequestPacket) {
        String message = messageRequestPacket.getMessage();
        log.info("服务端发来消息 >> {}", message);

        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setMessage("Echo [" + message + "]");
        ByteBuf respBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), messageResponsePacket);
        ctx.channel().writeAndFlush(respBuf);
    }
}
