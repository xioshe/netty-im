package com.kuma.im.server.handler;

import com.kuma.im.entity.packet.LoginRequestPacket;
import com.kuma.im.entity.packet.LoginResponsePacket;
import com.kuma.im.session.Session;
import com.kuma.im.util.SessionUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

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
            // 记录 Session
            String userId = randomUserId();
            loginResponsePacket.setUserId(userId);
            loginResponsePacket.setUsername(msg.getUsername());
            log.info("[{}}]登录成功", msg.getUsername());
            SessionUtils.bindSession(new Session(userId, msg.getUsername()), ctx.channel());
        }
        ctx.channel().writeAndFlush(loginResponsePacket);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        SessionUtils.unBindSession(ctx.channel());
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }

    private static String randomUserId() {
        return UUID.randomUUID().toString().split("-")[0];
    }
}
