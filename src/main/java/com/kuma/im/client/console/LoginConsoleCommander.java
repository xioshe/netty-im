package com.kuma.im.client.console;

import com.kuma.im.protocol.packet.LoginRequestPacket;
import com.kuma.im.util.SessionUtils;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * 登录命令行控制器
 *
 * @author kuma 2021-02-27
 */
@Slf4j
public class LoginConsoleCommander implements ConsoleCommander {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        if (SessionUtils.hasLogin(channel)) {
            log.warn("当前登录用户{}还未退出, 请退出后再重新登录", SessionUtils.getSession(channel));
            return;
        }
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        System.out.print("请登录\n输入用户名: ");
        String username = scanner.next();
        loginRequestPacket.setUsername(username);
        // 使用默认密码
        loginRequestPacket.setPassword("pwd");

        // 发送登录数据包
        channel.writeAndFlush(loginRequestPacket);
        waitForLoginResponse();
    }

    private void waitForLoginResponse() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
