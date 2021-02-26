package com.kuma.im.server;

import com.kuma.im.entity.PacketCodeC;
import com.kuma.im.entity.packet.LoginRequestPacket;
import com.kuma.im.entity.packet.LoginResponsePacket;
import com.kuma.im.entity.packet.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * 服务端登录处理
 *
 * @author kuma 2021-02-26
 */
@Slf4j
public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        Packet packet = PacketCodeC.INSTANCE.decode(byteBuf);

        if (packet instanceof LoginRequestPacket) {
            LoginRequestPacket requestPacket = (LoginRequestPacket) packet;

            LoginResponsePacket responsePacket = new LoginResponsePacket();
            responsePacket.setVersion(packet.getVersion());

            if (valid(requestPacket)) {
                log.info("客户端登录验证成功");
                responsePacket.setReason("账号密码校验失败");
                responsePacket.setSuccess(false);
            } else {
                log.info("客户端登录验证失败");
                responsePacket.setSuccess(true);
            }
            ByteBuf response = PacketCodeC.INSTANCE.encode(ctx.alloc(), responsePacket);
            ctx.writeAndFlush(response);
        }
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }
}
