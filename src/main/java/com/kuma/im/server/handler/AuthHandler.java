package com.kuma.im.server.handler;

import com.kuma.im.util.SessionUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * 身份验证处理
 * 热拔插
 *
 * @author kuma 2021-02-27
 */
@Slf4j
public class AuthHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!SessionUtils.hasLogin(ctx.channel())) {
            ctx.channel().close();
        } else {
            // 动态删除处理器
            ctx.pipeline().remove(this);
            super.channelRead(ctx, msg);
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        if (SessionUtils.hasLogin(ctx.channel())) {
            log.info("当前连接登录验证完毕，无需再次验证, AuthHandler 被移除");
        } else {
            log.info("无登录凭证, 强制关闭连接!");
        }
    }
}
