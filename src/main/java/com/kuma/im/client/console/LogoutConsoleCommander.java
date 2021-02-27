package com.kuma.im.client.console;

import com.kuma.im.entity.packet.LogoutRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * 登出命令行控制器
 *
 * @author kuma 2021-02-27
 */
public class LogoutConsoleCommander implements ConsoleCommander {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        LogoutRequestPacket logoutRequestPacket = new LogoutRequestPacket();
        channel.writeAndFlush(logoutRequestPacket);
    }
}
