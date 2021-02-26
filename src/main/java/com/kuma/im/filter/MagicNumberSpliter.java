package com.kuma.im.filter;

import com.kuma.im.entity.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * 拆包器和魔数校验
 *
 * @author kuma 2021-02-26
 */
public class MagicNumberSpliter extends LengthFieldBasedFrameDecoder {

    public MagicNumberSpliter() {
        super(Integer.MAX_VALUE, 7, 4);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        // fail fast
        if (in.getInt(in.readerIndex()) != PacketCodeC.MAGIC_NUMBER) {
            ctx.channel().close();
            return null;
        }
        return super.decode(ctx, in);
    }
}
