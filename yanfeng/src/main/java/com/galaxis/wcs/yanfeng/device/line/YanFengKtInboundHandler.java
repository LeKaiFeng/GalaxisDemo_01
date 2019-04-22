package com.galaxis.wcs.yanfeng.device.line;

import com.galaxis.wcs.yanfeng.connection.netty.inbound.AbstractTimeOutInboundHandler;
import com.galaxis.wcs.yanfeng.util.OesUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Scope("prototype")
@ChannelHandler.Sharable
public class YanFengKtInboundHandler extends AbstractTimeOutInboundHandler<ByteBuf> {
    private static final Logger log = LoggerFactory.getLogger(YanFengKtInboundHandler.class);

    public static final int MESSAGE_LENGTH_KT = 25;

    public static final byte DEFAULT = 0;

    public static volatile byte[] ktStatuses;

    static {
        ktStatuses = new byte[MESSAGE_LENGTH_KT * Byte.SIZE];
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf buffer) {
        int len = buffer.readableBytes();
        if (len != MESSAGE_LENGTH_KT) {
            log.info("接收到的字节长度不正确, len: {}", len);
            return;
        }

        byte[] bytes = new byte[MESSAGE_LENGTH_KT];
        buffer.readBytes(bytes);

        // 将位图转为字节数组
        byte[] ktBits = new byte[bytes.length * Byte.SIZE];
        int index = 0;
        for (byte b : bytes) {
            byte[] bits = OesUtil.toBits(b);
            for (byte bit : bits) {
                ktBits[index++] = bit;
            }
        }

        log.debug("{} 收到kt状态数组: {}", channelHandlerContext.channel().remoteAddress(), Arrays.toString(bytes));
        log.debug("{} 对应kt状态位图: {}", channelHandlerContext.channel().remoteAddress(), Arrays.toString(ktBits));

        // 把收到的kt状态赋值到ktStatuses
        ktStatuses = ktBits;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.warn("处理{}的消息出现了异常, {}", ctx.channel().remoteAddress(), cause.getMessage(), cause);
    }
}
