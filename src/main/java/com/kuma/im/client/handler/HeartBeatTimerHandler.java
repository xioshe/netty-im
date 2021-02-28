package com.kuma.im.client.handler;

import com.kuma.im.protocol.packet.HeartBeatRequestPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.TimeUnit;

/**
 * 定时发送心跳数据包给服务器
 * 内有线程, 无法共享
 *
 * @author kuma 2021-02-28
 */
public class HeartBeatTimerHandler extends ChannelInboundHandlerAdapter {

    // 客户端心跳时间为服务端空闲检测时间的三分之一, 是为了排除公网偶发的秒级抖动
    private static final int HEARTBEAT_INTERVAL = 5;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        scheduleSendHeartBeat(ctx);

        super.channelActive(ctx);
    }

    private void scheduleSendHeartBeat(ChannelHandlerContext ctx) {
        ctx.executor().schedule(() -> {

            if (ctx.channel().isActive()) {
                ctx.writeAndFlush(new HeartBeatRequestPacket());
                // 递归安排下一次心跳检测
                scheduleSendHeartBeat(ctx);
            }
        }, HEARTBEAT_INTERVAL, TimeUnit.SECONDS);
    }
}
