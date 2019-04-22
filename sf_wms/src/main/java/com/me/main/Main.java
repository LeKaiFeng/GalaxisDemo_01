package com.me.main;

import com.me.netty.NettyClient;
import com.me.netty.inbound.WmsInboundHandler;
import com.me.util.ConfigureManager;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class Main {
    public static void main(String[] args) {
        String ip = ConfigureManager.get("ip");
        int port = ConfigureManager.getNumber("port");

        ChannelInitializer<SocketChannel> initializer = new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline()
                        .addLast("inbound", new WmsInboundHandler());
            }
        };
        NettyClient client = new NettyClient(ip, port, initializer, true, 3000);
        client.init();
    }
}
