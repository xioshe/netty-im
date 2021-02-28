package com.kuma.im.protocol.packet;

import com.kuma.im.protocol.Command;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 发送群聊消息请求
 *
 * @author kuma 2021-02-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GroupMessageRequestPacket extends Packet {

    private String groupId;
    private String message;

    @Override
    public Byte getCommand() {
        return Command.GROUP_MESSAGE_REQUEST;
    }
}
