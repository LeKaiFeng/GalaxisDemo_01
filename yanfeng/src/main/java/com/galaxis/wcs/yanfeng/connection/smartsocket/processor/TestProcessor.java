package com.galaxis.wcs.yanfeng.connection.smartsocket.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartboot.socket.MessageProcessor;
import org.smartboot.socket.StateMachineEnum;
import org.smartboot.socket.transport.AioSession;

public class TestProcessor implements MessageProcessor<String> {
    private static final Logger log = LoggerFactory.getLogger(TestProcessor.class);

    @Override
    public void process(AioSession<String> session, String msg) {
        log.info("server 收到: {}", msg);
    }

    @Override
    public void stateEvent(AioSession<String> session, StateMachineEnum stateMachineEnum, Throwable throwable) {
        log.info("event: {}, e: {}", stateMachineEnum, throwable);

    }
}
