package com.kuma.im.client.console;

import com.kuma.im.protocol.packet.ListGroupMembersRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * 获取群聊成员管理
 *
 * @author kuma 2021-02-27
 */
public class ListGroupMembersConsoleCommander implements ConsoleCommander {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.print("输入 groupId, 获取成员列表: ");
        String groupId = scanner.next();
        ListGroupMembersRequestPacket listGroupMembersRequestPacket = new ListGroupMembersRequestPacket(groupId);
        channel.writeAndFlush(listGroupMembersRequestPacket);
    }
}
