package com.kuma.im.client.console;

import com.kuma.im.protocol.packet.JoinGroupRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * 加入群聊命令行控制器
 *
 * @author kuma 2021-02-27
 */
public class JoinGroupConsoleCommander implements ConsoleCommander {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.print("输入 groupId, 加入群聊: ");
        String groupId = scanner.next();
        JoinGroupRequestPacket joinGroupRequestPacket = new JoinGroupRequestPacket(groupId);
        channel.writeAndFlush(joinGroupRequestPacket);
    }
}
