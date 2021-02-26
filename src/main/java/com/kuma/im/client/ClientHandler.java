package com.kuma.im.client;

import com.kuma.im.entity.PacketCodeC;
import com.kuma.im.entity.packet.LoginRequestPacket;
import com.kuma.im.entity.packet.LoginResponsePacket;
import com.kuma.im.entity.packet.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * 客户端
 *
 * @author kuma 2021-02-25
 */
@Slf4j
public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("客户端开始登录");

        LoginRequestPacket requestPacket = new LoginRequestPacket();
        requestPacket.setUserId(UUID.randomUUID().toString());
        requestPacket.setUsername("kuma");
        requestPacket.setPassword("honey");

        ByteBuf buf = PacketCodeC.INSTANCE.encode(ctx.alloc(), requestPacket);
        ctx.writeAndFlush(buf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;

        Packet response = PacketCodeC.INSTANCE.decode(byteBuf);

        if (response instanceof LoginResponsePacket) {
            LoginResponsePacket responsePacket = (LoginResponsePacket) response;
            if (responsePacket.isSuccess()) {
                log.info("客户端登录成功");
            } else {
                log.error("客户端登录失败, 原因: {}", responsePacket.getReason());
            }
        }
    }
}
