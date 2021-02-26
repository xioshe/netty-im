package com.kuma.im.server.handler;

import com.kuma.im.entity.PacketCodeC;
import com.kuma.im.entity.packet.LoginRequestPacket;
import com.kuma.im.entity.packet.LoginResponsePacket;
import com.kuma.im.util.LoginUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 登录请求处理
 *
 * @author kuma 2021-02-26
 */
@Slf4j
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket msg) {

        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(msg.getVersion());

        if (!valid(msg)) {
            log.info("客户端登录验证失败");
            loginResponsePacket.setReason("账号密码校验失败");
            loginResponsePacket.setSuccess(false);
        } else {
            log.info("客户端登录验证成功");
            loginResponsePacket.setSuccess(true);
            LoginUtils.markAsLogin(ctx.channel());
        }
        ByteBuf response = PacketCodeC.INSTANCE.encode(ctx.alloc(), loginResponsePacket);
        ctx.channel().writeAndFlush(response);
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }
}
