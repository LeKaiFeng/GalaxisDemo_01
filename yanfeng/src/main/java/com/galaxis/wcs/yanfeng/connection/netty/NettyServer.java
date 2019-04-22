package com.galaxis.wcs.yanfeng.connection.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.GenericFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyServer {
    private static final Logger log = LoggerFactory.getLogger(NettyServer.class);

    private int port;
    private ChannelInitializer<SocketChannel> initializer;

    public NettyServer(int port, ChannelInitializer<SocketChannel> initializer) {
        this.port = port;
        this.initializer = initializer;
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
                    .childHandler(initializer);

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

}
