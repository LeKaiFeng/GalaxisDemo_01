package com.me.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.GenericFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NettyServer {
    private static final Logger log = LoggerFactory.getLogger(NettyServer.class);

    private int port;
    private LinkedHashMap<String, ChannelHandler> handlers;
    private Map<String, Channel> channels = new ConcurrentHashMap<>();

    public NettyServer(int port, LinkedHashMap<String, ChannelHandler> handlers) {
        this.port = port;
        this.handlers = handlers;
    }

    public void init() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap sBootstrap = new ServerBootstrap();
            sBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) {
                            ChannelPipeline pipeline = ch.pipeline();
                            for (Map.Entry<String, ChannelHandler> entry : handlers.entrySet()) {
                                pipeline.addLast(entry.getKey(), entry.getValue());
                            }

                            InetSocketAddress address = ch.remoteAddress();
                            log.info("远端Client:" + address + "连接上服务器");
                            channels.put(address.toString(), pipeline.channel());
                        }
                    });

            //绑定端口,开始接收进来的连接
            ChannelFuture future;
            try {
                future = sBootstrap.bind(port).sync();
                future.addListener((GenericFutureListener<ChannelFuture>) f -> log.info("Netty Server init over, result:{}, port: {}", f.isSuccess(), port));
                future.channel().closeFuture().sync();
            } catch (InterruptedException e) {
                log.error("{}", e.getMessage(), e);
            }

        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();

            log.warn("Netty Server 关闭了");
        }
    }

    public boolean write(Object message) {

        if (channels.isEmpty()) {
            log.debug("暂无客户端连接...");
            return false;
        }

        for (Map.Entry<String, Channel> entry : channels.entrySet()) {
            String key = entry.getKey();
            Channel channel = entry.getValue();
            channel.writeAndFlush(message).addListener(future -> {
                if (future.isSuccess()) {
                    log.debug("发送消息到 {}, message: {}", key, message);
                } else {
                    log.warn("发送消息到 {}异常, message: {}", key, message);
                    channel.closeFuture().addListener(f -> {
                        log.debug("channel {}已关闭", key);
                        channels.remove(key);
                    });
                }
            });
        }

        return true;
    }

}
