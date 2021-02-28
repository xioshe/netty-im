package com.kuma.im.protocol.packet;

import com.kuma.im.protocol.Command;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 发送群聊消息响应
 *
 * @author kuma 2021-02-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GroupMessageResponsePacket extends Packet {

    private String fromUserId;
    private String fromUsername;
    private String message;
    private String groupId;

    @Override
    public Byte getCommand() {
        return Command.GROUP_MESSAGE_RESPONSE;
    }
}
