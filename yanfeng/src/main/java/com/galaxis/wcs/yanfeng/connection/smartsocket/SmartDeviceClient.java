package com.galaxis.wcs.yanfeng.connection.smartsocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartboot.socket.MessageProcessor;
import org.smartboot.socket.Protocol;
import org.smartboot.socket.transport.AioQuickClient;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * 与设备交互的tcp client
 */
public class SmartDeviceClient {
    private static final Logger log = LoggerFactory.getLogger(SmartDeviceClient.class);

    private String ip;
    private int port;
    private MessageProcessor<byte[]> processor;
    private Protocol<byte[]> protocol;
    private AioQuickClient<byte[]> client;

    public SmartDeviceClient(String ip, int port, MessageProcessor<byte[]> processor, Protocol<byte[]> protocol) {
        this.ip = ip;
        this.port = port;
        this.processor = processor;
        this.protocol = protocol;
    }

    public void init() {
        client = new AioQuickClient<>(ip, port, protocol, processor);
        try {
            client.start();
            log.info("device client 启动成功, ip: {}, port: {}", ip, port);
        } catch (IOException | InterruptedException e) {
            log.warn("device client 启动失败, {}", e.getMessage(), e);
        } catch (ExecutionException e) {
            log.info("device client 连接失败... ip: {}, port: {}", ip, port);
        }
    }
}
