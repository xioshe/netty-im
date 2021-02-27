package com.kuma.im.client.console;

import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * 控制台交互接口
 * 负责处理 IO 请求
 *
 * @author kuma 2021-02-27
 */
public interface ConsoleCommander {
    void exec(Scanner scanner, Channel channel);
}
