package com.kuma.im.client;

import com.kuma.im.client.console.ConsoleCommandManager;
import com.kuma.im.client.console.ConsoleCommander;
import com.kuma.im.client.console.LoginConsoleCommander;
import com.kuma.im.client.handler.CreateGroupResponseHandler;
import com.kuma.im.client.handler.LoginResponseHandler;
import com.kuma.im.client.handler.LogoutResponseHandler;
import com.kuma.im.client.handler.MessageResponseHandler;
import com.kuma.im.codec.PacketDecoder;
import com.kuma.im.codec.PacketEncoder;
import com.kuma.im.filter.MagicNumberSpliter;
import com.kuma.im.util.SessionUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Netty 客户端
 *
 * @author kuma 2021-02-25
 */
@Slf4j
public class Client {
    private static final int MAX_RETRY = 5;
    private final String host;
    private final int port;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void startClient() {
        NioEventLoopGroup loopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(loopGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new MagicNumberSpliter())
                                .addLast(new PacketDecoder())
                                .addLast(new MessageResponseHandler())
                                .addLast(new CreateGroupResponseHandler())
                                .addLast(new LoginResponseHandler())
                                .addLast(new LogoutResponseHandler())
                                .addLast(new PacketEncoder());
                    }
                });
        retryConnect(bootstrap, MAX_RETRY);
    }

    private void retryConnect(Bootstrap bootstrap, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                log.info("连接成功!");
                // 连接成功之后启动控制台监听线程获取用户输入信息
                ChannelFuture connectFuture = (ChannelFuture) future;
                startConsoleThread(connectFuture.channel());
            } else if (retry == 0) {
                log.error("重试次数已用完, 放弃连接!");
            } else {
                // 第几次重连
                int order = (MAX_RETRY - retry) + 1;
                // 本次重连的间隔
                int delay = 1 << order;
                log.error("连接失败, 第" + order + "次重连...");
                bootstrap.config().group().schedule(() -> retryConnect(bootstrap, retry - 1),
                        delay, TimeUnit.SECONDS);
            }
        });
    }

    private void startConsoleThread(Channel channel) {
        ConsoleCommander manager = new ConsoleCommandManager();
        ConsoleCommander loginConsoleCommander = new LoginConsoleCommander();
        Scanner scanner = new Scanner(System.in);

        new Thread(() -> {
            while (!Thread.interrupted()) {
                if (!SessionUtils.hasLogin(channel)) {
                    loginConsoleCommander.exec(scanner, channel);
                } else {
                    manager.exec(scanner, channel);
                }
            }
        }).start();
    }

    public static void main(String[] args) {
        new Client("localhost", 8080).startClient();
    }
}
