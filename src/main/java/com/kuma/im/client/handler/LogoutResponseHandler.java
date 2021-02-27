package com.kuma.im.client.handler;

import com.kuma.im.entity.packet.LogoutResponsePacket;
import com.kuma.im.util.SessionUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 退出登录响应处理器
 *
 * @author kuma 2021-02-27
 */
@Slf4j
public class LogoutResponseHandler extends SimpleChannelInboundHandler<LogoutResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogoutResponsePacket msg) throws Exception {
        if (!msg.isSuccess()) {
            log.info("登出失败, 原因: {}", msg.getReason());
            return;
        }
        SessionUtils.unBindSession(ctx.channel());
    }
}
