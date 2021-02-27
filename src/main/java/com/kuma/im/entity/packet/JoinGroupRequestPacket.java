package com.kuma.im.entity.packet;

import com.kuma.im.entity.Command;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 加入群聊请求
 *
 * @author kuma 2021-02-27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class JoinGroupRequestPacket extends Packet {

    private String groupId;

    @Override
    public Byte getCommand() {
        return Command.JOIN_GROUP_REQUEST;
    }
}
