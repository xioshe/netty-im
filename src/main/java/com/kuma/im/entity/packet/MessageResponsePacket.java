package com.kuma.im.entity.packet;

import com.kuma.im.entity.Command;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 消息回复数据包
 *
 * @author kuma 2021-02-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MessageResponsePacket extends Packet {

    private String message;

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_RESPONSE;
    }
}
