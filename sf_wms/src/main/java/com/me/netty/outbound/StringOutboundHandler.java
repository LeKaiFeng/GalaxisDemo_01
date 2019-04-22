package com.me.netty.outbound;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ChannelHandler.Sharable
public class StringOutboundHandler extends ChannelOutboundHandlerAdapter {
    private static final Logger log = LoggerFactory.getLogger(StringOutboundHandler.class);

    public static final String LINE_SEPARATOR = System.lineSeparator();

    private boolean readLine;

    public StringOutboundHandler() {
        this(false);
    }

    public StringOutboundHandler(boolean readLine) {
        this.readLine = readLine;
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        String message = (String) msg;
        if (readLine) {
            message += LINE_SEPARATOR;
        }
        super.write(ctx, message, promise);
    }
}
