package com.kuma.im.protocol.packet;

import com.kuma.im.protocol.Command;
import com.kuma.im.session.Session;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 获取群聊成员响应
 *
 * @author kuma 2021-02-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ListGroupMembersResponsePacket extends BaseResponsePacket {

    private String groupId;
    private List<Session> sessionList;

    @Override
    public Byte getCommand() {
        return Command.LIST_GROUP_MEMBERS_RESPONSE;
    }
}
