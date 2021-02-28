package com.kuma.im.client.console;

import com.kuma.im.protocol.packet.MessageRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * 私聊命令行
 *
 * @author kuma 2021-02-27
 */
public class SendToUserConsoleCommander implements ConsoleCommander {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.print("目标用户：");

        String toUserId = scanner.next();
        String message = scanner.next();
        channel.writeAndFlush(new MessageRequestPacket(toUserId, message));
    }
}
