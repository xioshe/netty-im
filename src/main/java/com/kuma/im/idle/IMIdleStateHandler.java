package com.kuma.im.idle;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author kuma 2021-02-28
 */
@Slf4j
public class IMIdleStateHandler extends IdleStateHandler {

    private static final int READER_IDLE_SECONDS = 15;

    public IMIdleStateHandler() {
        // 0 代表不关心的条件
        super(READER_IDLE_SECONDS, 0, 0);
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) {
        log.info("{} 秒内未接受到数据, 关闭连接", READER_IDLE_SECONDS);
        ctx.channel().close();
    }
}
