package com.kuma.im.client.console;

import com.kuma.im.protocol.packet.GroupMessageRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * 发送群聊消息控制台
 *
 * @author kuma 2021-02-28
 */
public class GroupMessageConsoleCommander implements ConsoleCommander {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.print("发送消息给指定群组 (格式: groupId[空格]message): ");
        String groupId = scanner.next();
        String message = scanner.next();

        GroupMessageRequestPacket groupMessageRequestPacket = new GroupMessageRequestPacket();
        groupMessageRequestPacket.setGroupId(groupId);
        groupMessageRequestPacket.setMessage(message);
        channel.writeAndFlush(groupMessageRequestPacket);
    }
}
