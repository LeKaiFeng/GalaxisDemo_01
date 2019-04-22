package com.galaxis.wcs.yanfeng.connection.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.GenericFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class NettyClient {
    private static final Logger log = LoggerFactory.getLogger(NettyClient.class);

    private String host;
    private int port;
    /**
     * 只可添加线程安全的 @Sharable的handler
     */
    private LinkedHashMap<String, ChannelHandler> handlers;
    /**
     * 可添加任意类型的 handler, 优先选择
     */
    private ChannelInitializer<SocketChannel> initializer;
    private boolean reConn;
    private final long RE_CONN_DELAY;

    private volatile Channel channel;

    public NettyClient(String host, int port, LinkedHashMap<String, ChannelHandler> handlers) {
        this(host, port, handlers, false, 0);
    }

    public NettyClient(String host, int port, LinkedHashMap<String, ChannelHandler> handlers, boolean reConn, long RE_CONN_DELAY) {
        this.host = host;
        this.port = port;
        this.handlers = handlers;
        this.reConn = reConn;
        this.RE_CONN_DELAY = RE_CONN_DELAY;
    }

    public NettyClient(String host, int port, ChannelInitializer<SocketChannel> initializer) {
        this(host, port, initializer, false, 0);
    }

    public NettyClient(String host, int port, ChannelInitializer<SocketChannel> initializer, boolean reConn, long RE_CONN_DELAY) {
        this.host = host;
        this.port = port;
        this.initializer = initializer;
        this.reConn = reConn;
        this.RE_CONN_DELAY = RE_CONN_DELAY;
    }

    public void init() {
        do {
            NioEventLoopGroup group = new NioEventLoopGroup();

            Bootstrap bootstrap = new Bootstrap();

            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true);

            if (Objects.nonNull(initializer)) {
                bootstrap.handler(initializer);
            } else {
                bootstrap.handler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ChannelPipeline pipeline = ch.pipeline();
                        for (Map.Entry<String, ChannelHandler> entry : handlers.entrySet()) {
                            pipeline.addLast(entry.getKey(), entry.getValue());
                        }
                    }
                });
            }

            try {
                ChannelFuture future = bootstrap.connect(host, port).sync();

                future.addListener((GenericFutureListener<ChannelFuture>) f -> {
                    log.info("Netty Client 连接成功, result:{}, {}:{}", f.isSuccess(), host, port);
                    this.channel = f.channel();
                });

                future.channel().closeFuture().sync();
                log.info("本地Netty Client 关闭, {}:{}", host, port);

            } catch (Exception e) {
                log.warn("连接远端server失败, {}:{}, e: {}", host, port, e.getMessage());
            } finally {
                group.shutdownGracefully();
                log.info("Netty Client资源已释放, {}:{}", host, port);

                if (reConn) {
                    // 如果需要重连, 所有资源释放完成之后, 再次发起重连操作
                    try {
                        Thread.sleep(RE_CONN_DELAY);
                        log.info("开始尝试重连, {}:{}", host, port);
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                    }
                }
            }
        } while (reConn);
    }

    public boolean write(Object message) {
        if (Objects.isNull(channel)) {
            log.info("连接尚未建立, 请稍后...");
            return false;
        }

        if (!channel.isWritable()) {
            log.info("channel暂不可执行发送, Registered: {}, Active: {}, Open: {}, Writable: {}"
                    , channel.isRegistered(), channel.isActive(), channel.isOpen(), channel.isWritable());
            return false;
        }

        channel.writeAndFlush(message);
        return true;
    }

}
