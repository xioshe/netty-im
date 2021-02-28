package com.kuma.im.client.handler;

import com.kuma.im.protocol.packet.LoginResponsePacket;
import com.kuma.im.session.Session;
import com.kuma.im.util.SessionUtils;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 登录响应处理
 *
 * @author kuma 2021-02-26
 */
@Slf4j
@ChannelHandler.Sharable
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    public static final LoginResponseHandler INSTANCE = new LoginResponseHandler();

    protected LoginResponseHandler() {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket msg) {
        String username = msg.getUsername();
        log.info("{}", isSharable());
        if (msg.isSuccess()) {
            // 客户端记录 session
            String userId = msg.getUserId();
            SessionUtils.bindSession(new Session(userId, username), ctx.channel());
            log.info("用户{}登录成功, userId 为 [{}]", username, userId);
        } else {
            log.error("{}登录失败, 原因: {}", username, msg.getReason());
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("客户端连接被关闭!");
    }
}
