package com.me.netty.inbound;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.ReadTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

// @ChannelHandler.Sharable
public class BufferInboundHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private static final Logger log = LoggerFactory.getLogger(BufferInboundHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf buffer) throws Exception {
        int len = buffer.readableBytes();
        log.info("len: {}", len);

        byte[] bytes = new byte[len];
        buffer.readBytes(bytes);

        String bytesStr = Arrays.toString(bytes);
        System.out.println("bytesStr = " + bytesStr);

        ctx.channel().writeAndFlush(Unpooled.copiedBuffer(bytes));

    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        log.debug("触发了event: {}", evt);
        if (evt instanceof IdleStateEvent) {
            IdleState state = ((IdleStateEvent) evt).state();
            switch (state) {
                case READER_IDLE: {
                    ctx.channel().close().addListener(future -> {
                        log.info("read timeout, and channel is close...");
                    });
                    break;
                }
                case WRITER_IDLE: {
                    ctx.channel().close().addListener(future -> {
                        log.info("write timeout, and channel is close...");
                    });
                    break;
                }
                case ALL_IDLE: {
                    ctx.channel().close().addListener(future -> {
                        log.info("read/write timeout, and channel is close...");
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

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause instanceof ReadTimeoutException) {
            log.info("读超时...");
        } else {
            log.error("catch e, e: {}", cause.getMessage(), cause);
        }

    }
}
