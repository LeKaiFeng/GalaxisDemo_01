package com.galaxis.wcs.yanfeng.device.line;

import com.galaxis.wcs.yanfeng.connection.netty.inbound.AbstractTimeOutInboundHandler;
import com.galaxis.wcs.yanfeng.util.Constance;
import com.galaxis.wcs.yanfeng.util.OesUtil;
import com.galaxis.wcs.yanfeng.util.template.LinePost;
import com.galaxis.wcs.yanfeng.util.template.LineRequest;
import com.galaxis.wcs.yanfeng.util.template.LineResponse;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 *
 */
@Component
@Scope("prototype")
@ChannelHandler.Sharable
public class YanFengLineInboundHandler extends AbstractTimeOutInboundHandler<ByteBuf> {
    private static final Logger log = LoggerFactory.getLogger(YanFengLineInboundHandler.class);
    public static final int MESSAGE_LENGTH_INFO = 8;
    public static final int MESSAGE_LENGTH_INTERACTION = 64;

    public static final int BYTES_LENGTH_BOX_NUMBER = 20;
    public static final int BYTES_LENGTH_PRODUCTION_DATE = 15;

    public static final byte DEFAULT = 0;

    @Autowired
    private LineDispatcher dispatcher;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    protected void channelRead0(ChannelHandlerContext context, ByteBuf buffer) throws Exception {
        int length = buffer.readableBytes();
        if (length != MESSAGE_LENGTH_INFO && length != MESSAGE_LENGTH_INTERACTION) {
            log.warn("接收到的字节长度异常, length: {}, bytes: {}", length, Arrays.toString(getBytes(buffer, length)));
            return;
        }

        // 解码request
        LineRequest request = decodeRequest(buffer);
        request.setComeFrom(context.channel().remoteAddress().toString());
        Integer type = request.getType();

        // 判断是否在缓存内
        String key = getKey(request);
        LineResponse response;
        if (Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
            // 在缓存内就不需要做处理
            response = (LineResponse) redisTemplate.opsForValue().get(key);
            log.info("find response on cache, request: {}, response: {}", request, response);
        } else {
            // 处理请求
            response = new LineResponse();
            log.debug("do get...");
            dispatcher.doGet(request, response);
        }

        // 缓存请求与响应
        // 心跳不缓存
        if (!Arrays.asList(LinePost.TYPE_HEART).contains(type)) {
            cacheRequest(request, response);
        }

        if (Arrays.asList(LinePost.TYPE_GET, LinePost.TYPE_OVER, LinePost.TYPE_BOX_CLEAN, LinePost.TYPE_HEART).contains(type)) {
            // 如果类型是请求终点, 到达终点反馈, 就需要给PLC反馈信息
            byte[] bytes = encodeResponse(response);
            context.channel().writeAndFlush(Unpooled.copiedBuffer(bytes));
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.warn("处理{}的消息出现了异常, {}", ctx.channel().remoteAddress(), cause.getMessage(), cause);
    }

    public LineRequest decodeRequest(ByteBuf buffer) {
        LineRequest request = new LineRequest();
        int length = buffer.readableBytes();

        if (log.isDebugEnabled()) {
            byte[] bytes = new byte[length];
            buffer.readBytes(bytes);
            log.debug("收到request, length: {}, bytes: {}", length, Arrays.toString(bytes));
            buffer.resetReaderIndex();
        }

        if (length == MESSAGE_LENGTH_INFO) {
            // 组装停线/停线恢复接口的请求
            int type;
            int lineNo;
            int errorCode;
            int verify;

            type = buffer.readByte();
            buffer.readByte();
            lineNo = buffer.readByte();
            buffer.readByte();
            errorCode = buffer.readShort();
            verify = buffer.getUnsignedByte(MESSAGE_LENGTH_INFO - 1);

            request.setType(type);
            request.setLocation(lineNo);
            request.setErrorCode(errorCode);
            request.setVerify(verify);
        } else if (length == MESSAGE_LENGTH_INTERACTION) {
            // 组装请求/响应接口的请求
            int plcSeq;
            String boxNumber;
            int location;
            int type;
            int check;
            int occupyInbound1;
            int occupyInbound2;
            int boxWeight;
            int boxLength;
            String productionDate;
            int verify;

            plcSeq = buffer.readInt();
            byte[] barCode = getBytes(buffer, BYTES_LENGTH_BOX_NUMBER);
            boxNumber = OesUtil.decodeBytes(barCode);
            location = buffer.readInt();
            type = buffer.readByte();
            buffer.readByte();
            check = buffer.readByte();
            buffer.readByte();
            occupyInbound1 = buffer.readByte();
            occupyInbound2 = buffer.readByte();
            buffer.readShort();
            boxWeight = buffer.readShort();
            boxLength = buffer.readShort();
            byte[] dateCode = getBytes(buffer, BYTES_LENGTH_PRODUCTION_DATE);
            productionDate = OesUtil.decodeBytes(dateCode);
            verify = buffer.getUnsignedByte(MESSAGE_LENGTH_INTERACTION - 1);

            request.setSeq(plcSeq);
            request.setCartonNo(boxNumber);
            request.setLocation(location);
            request.setType(type);
            request.setCheck(check);
            request.setOccupy(Arrays.asList(occupyInbound1, occupyInbound2));
            request.setWeight(BigDecimal.valueOf(boxWeight));
            request.setLength(BigDecimal.valueOf(boxLength));
            request.setProductionDate(productionDate);
            request.setVerify(verify);
        }
        log.debug("解码request, type: {}, request: {}", request.getType(), request);
        return request;
    }

    public byte[] encodeResponse(LineResponse response) {
        log.debug("编码response: {}", response);
        ByteBuffer buffer;
        byte type = response.getType().byteValue();
        if (Arrays.asList(LinePost.TYPE_STOP, LinePost.TYPE_STOP_RECOVER).contains(Integer.valueOf(type))) {
            buffer = ByteBuffer.allocate(MESSAGE_LENGTH_INFO);

            byte location;
            short errorCode;

            location = response.getLocation().byteValue();
            errorCode = response.getErrorCode().shortValue();

            buffer.put(type);
            buffer.put(DEFAULT);
            buffer.put(location);
            buffer.put(DEFAULT);
            buffer.putShort(errorCode);

        } else if (Arrays.asList(LinePost.TYPE_GET, LinePost.TYPE_MIDDLE, LinePost.TYPE_OVER, LinePost.TYPE_HEART, LinePost.TYPE_BOX_CLEAN).contains(Integer.valueOf(type))) {
            buffer = ByteBuffer.allocate(MESSAGE_LENGTH_INTERACTION);

            int seq;
            byte[] boxNumber = new byte[BYTES_LENGTH_BOX_NUMBER];
            int location;
            byte stop;
            byte errorCode;
            byte verify;

            stop = response.getWarn().byteValue();
            seq = response.getSeq();
            byte[] boxBytes = response.getCartonNo().getBytes();
            System.arraycopy(boxBytes, 0, boxNumber, 0, Math.min(boxBytes.length, boxNumber.length));
            verify = response.getVerify().byteValue();

            if (stop == 1) {
                // 停线
                errorCode = response.getErrorCode().byteValue();
                location = 0;
            } else {
                // 不停线
                errorCode = 0;
                location = response.getLocation();
            }

            buffer.putInt(seq);
            buffer.put(boxNumber);
            buffer.putInt(location);
            buffer.put(type);
            buffer.put(DEFAULT);
            buffer.put(stop);
            buffer.put(errorCode);
            buffer.put(63, verify);

        } else {
            buffer = ByteBuffer.allocate(0);
        }
        byte[] bytes = buffer.array();
        log.debug("编码后的response, length: {}, bytes: {}", bytes.length, Arrays.toString(bytes));
        return bytes;
    }

    private byte[] getBytes(ByteBuf buffer, int length) {
        byte[] bytes = new byte[length];
        buffer.readBytes(bytes);
        return bytes;
    }

    private void cacheRequest(LineRequest request, LineResponse response) {
        Integer type = request.getType();
        if (Arrays.asList(LinePost.TYPE_GET, LinePost.TYPE_MIDDLE, LinePost.TYPE_OVER).contains(type)) {
            // 请求/响应接口
            String key = getKey(request);
            redisTemplate.opsForValue().set(key, response, Constance.CACHE_TIME, TimeUnit.MILLISECONDS);
            log.debug("缓存response成功, request: {}, response: {}", request, response);
        }
    }

    private String getKey(LineRequest request) {
        return Constance.KEY_PRE_PLC_REQUEST + request.getType() + request.getSeq() + request.getCartonNo() + request.hashCode();
    }

}
