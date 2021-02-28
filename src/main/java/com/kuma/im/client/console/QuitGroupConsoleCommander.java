package com.kuma.im.client.console;

import com.kuma.im.protocol.packet.QuitGroupRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * 命令行控制器, 退出群聊
 *
 * @author kuma 2021-02-27
 */
public class QuitGroupConsoleCommander implements ConsoleCommander {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.print("输入 groupId, 退出群聊: ");
        String groupId = scanner.next();
        QuitGroupRequestPacket quitGroupRequestPacket = new QuitGroupRequestPacket(groupId);
        channel.writeAndFlush(quitGroupRequestPacket);
    }
}
