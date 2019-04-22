package com.galaxis.wcs.yanfeng.connection.netty.inbound;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 超时断开的抽象InboundHandler
 * 一般给不主动发消息的客户端用
 * 一段时间没有收/发消息, 就断开重连, 配合 IdleStateHandler, ReadTimeoutHandler, WriteTimeoutHandler等使用
 *
 * @param <T>
 */
public abstract class AbstractTimeOutInboundHandler<T> extends SimpleChannelInboundHandler<T> {
    private static final Logger log = LoggerFactory.getLogger(AbstractTimeOutInboundHandler.class);

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        log.info("channel: {} 触发了event: {}", ctx.channel(), evt);
        if (evt instanceof IdleStateEvent) {
            IdleState state = ((IdleStateEvent) evt).state();
            switch (state) {
                case READER_IDLE: {
                    log.info("{} read timeout, and channel will close...", ctx.channel().remoteAddress());
                    ctx.channel().close().addListener(future -> {
                        log.info("{} read timeout, and channel is close...", ctx.channel().remoteAddress());
                    });
                    break;
                }
                case WRITER_IDLE: {
                    log.info("{} write timeout, and channel will close...", ctx.channel().remoteAddress());
                    ctx.channel().close().addListener(future -> {
                        log.info("{} write timeout, and channel is close...", ctx.channel().remoteAddress());
                    });
                    break;
                }
                case ALL_IDLE: {
                    log.info("{} read/write timeout, and channel will close...", ctx.channel().remoteAddress());
                    ctx.channel().close().addListener(future -> {
                        log.info("{} read/write timeout, and channel is close...", ctx.channel().remoteAddress());
                    });
                    break;
                }
                default: {
                    super.userEventTriggered(ctx, evt);
                }
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
