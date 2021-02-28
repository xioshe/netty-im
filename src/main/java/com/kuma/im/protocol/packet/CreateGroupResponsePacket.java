package com.kuma.im.protocol.packet;

import com.kuma.im.protocol.Command;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 创建群聊的响应
 *
 * @author kuma 2021-02-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CreateGroupResponsePacket extends BaseResponsePacket {

    private String groupId;

    private List<String> usernameList;

    @Override
    public Byte getCommand() {
        return Command.CREATE_GROUP_RESPONSE;
    }
}
