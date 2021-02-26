package com.kuma.im.server;

import com.kuma.im.codec.PacketDecoder;
import com.kuma.im.codec.PacketEncoder;
import com.kuma.im.filter.MagicNumberSpliter;
import com.kuma.im.server.handler.LoginRequestHandler;
import com.kuma.im.server.handler.MessageRequestHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author kuma 2021-02-26
 */
@Slf4j
public class Server {
    private final int port;

    public Server(int port) {
        this.port = port;
    }

    public void startup() {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new MagicNumberSpliter())
                                .addLast(new PacketDecoder())
                                .addLast(new LoginRequestHandler())
                                // server 处理 Message 请求更频繁
                                .addLast(new MessageRequestHandler())
                                .addLast(new PacketEncoder());
                    }
                });
        bind(bootstrap);
    }

    private void bind(final ServerBootstrap serverBootstrap) {
        serverBootstrap.bind(port).addListener(future -> {
            if (future.isSuccess()) {
                log.info("端口[" + port + "]绑定成功!");
            } else {
                log.info("端口[" + port + "]绑定失败!");
            }
        });
    }

    public static void main(String[] args) {
        new Server(8080).startup();
    }
}
