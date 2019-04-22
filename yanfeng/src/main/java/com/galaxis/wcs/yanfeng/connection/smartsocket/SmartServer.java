package com.galaxis.wcs.yanfeng.connection.smartsocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartboot.socket.MessageProcessor;
import org.smartboot.socket.Protocol;
import org.smartboot.socket.transport.AioQuickServer;

import java.io.IOException;
import java.util.Objects;

public class SmartServer {
    private static final Logger log = LoggerFactory.getLogger(SmartServer.class);

    private int port;
    private MessageProcessor<String> processor;
    private Protocol<String> protocol;
    private AioQuickServer<String> server;

    public SmartServer(int port, MessageProcessor<String> processor, Protocol<String> protocol) {
        this.port = port;
        this.processor = processor;
        this.protocol = protocol;
    }

    public void init() {
        server = new AioQuickServer<>(port, protocol, processor);
        server.setBannerEnabled(false);
        try {
            server.start();
            log.info("server启动成功, port: {}", port);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    public void cls() {
        log.warn("关闭server!");
        if (Objects.nonNull(server)) {
            server.shutdown();
        }
    }

}
