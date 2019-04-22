package com.galaxis.wcs.yanfeng.config;

import com.galaxis.wcs.yanfeng.connection.netty.NettyServer;
import com.galaxis.wcs.yanfeng.connection.netty.outbound.StringOutboundHandler;
import com.galaxis.wcs.yanfeng.connection.socket.SocketClient;
import com.galaxis.wcs.yanfeng.device.library.YanFengInboundHandler;
import com.galaxis.wcs.yanfeng.util.Constance;
import com.galaxis.wcs.yanfeng.util.SpringBeanFactoryTool;
import com.galaxis.wcs.yanfeng.util.ThreadUtil;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TcpConfig {
    @Value("${gess.client.ip}")
    private String clientIp;
    @Value("${gess.client.port}")
    private int clientPort;
    @Value("${gess.server.port}")
    private int serverPort;
    @Value("${gess.readLine}")
    private String readLine;
    @Value("${gess.timeout}")
    private int timeout;

    @Bean
    public SocketClient gessClient() {
        SocketClient client = new SocketClient(clientIp, clientPort, readLine, timeout);
        ThreadUtil.createThread("init-tcp-client", client::init).start();
        return client;
    }

    @Bean
    public NettyServer nettyServer() {
        ChannelInitializer<SocketChannel> initializer = new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                ChannelPipeline pipeline = socketChannel.pipeline();
                pipeline.addLast("stringEncoder", new StringEncoder());
                if (Constance.Y.equalsIgnoreCase(readLine)) {
                    pipeline.addLast("addLine", new StringOutboundHandler(true));
                    pipeline.addLast("readLine", new LineBasedFrameDecoder(1024 * 8));
                }
                pipeline.addLast("stringDecoder", new StringDecoder());
                pipeline.addLast("handler", SpringBeanFactoryTool.getBean("yanFengInboundHandler", YanFengInboundHandler.class));
            }
        };
        NettyServer server = new NettyServer(serverPort, initializer);
        ThreadUtil.createThread("init-tcp-server", server::init).start();
        return server;
    }

}
