package com.kuma.im.client.handler;

import com.kuma.im.entity.PacketCodeC;
import com.kuma.im.entity.packet.LoginRequestPacket;
import com.kuma.im.entity.packet.LoginResponsePacket;
import com.kuma.im.util.LoginUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * 登录响应处理
 *
 * @author kuma 2021-02-26
 */
@Slf4j
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        log.info("客户端开始登录");

        LoginRequestPacket requestPacket = new LoginRequestPacket();
        requestPacket.setUserId(UUID.randomUUID().toString());
        requestPacket.setUsername("kuma");
        requestPacket.setPassword("honey");

        ByteBuf buf = PacketCodeC.INSTANCE.encode(ctx.alloc(), requestPacket);
        ctx.channel().writeAndFlush(buf);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket msg) {
        if (msg.isSuccess()) {
            // 标记子通道为已登录
            LoginUtils.markAsLogin(ctx.channel());
            log.info("客户端登录成功");
        } else {
            log.error("客户端登录失败, 原因: {}", msg.getReason());
        }
    }
}
