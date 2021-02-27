package com.kuma.im.entity.packet;

import com.kuma.im.entity.Command;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 加入群聊响应
 *
 * @author kuma 2021-02-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class JoinGroupResponsePacket extends Packet {

    private boolean success;
    private String groupId;
    private String reason;

    @Override
    public Byte getCommand() {
        return Command.JOIN_GROUP_RESPONSE;
    }
}
