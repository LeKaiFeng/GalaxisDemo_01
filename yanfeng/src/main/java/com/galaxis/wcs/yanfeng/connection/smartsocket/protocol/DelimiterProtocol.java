package com.galaxis.wcs.yanfeng.connection.smartsocket.protocol;

import com.galaxis.wcs.yanfeng.connection.smartsocket.protocol.decoder.DelimiterFrameDecoder;
import org.smartboot.socket.Protocol;
import org.smartboot.socket.transport.AioSession;

import java.nio.ByteBuffer;

/**
 * @author 三刀
 * @version V1.0 , 2018/4/26
 */
public class DelimiterProtocol implements Protocol<String> {

    /**
     * 结束符
     */
    private final byte[] DELIMITER_BYTES;

    public DelimiterProtocol(byte[] DELIMITER_BYTES) {
        this.DELIMITER_BYTES = DELIMITER_BYTES;
    }

    @Override
    public String decode(ByteBuffer buffer, AioSession<String> session, boolean b) {
        DelimiterFrameDecoder delimiterFrameDecoder;
        //构造指定结束符的临时缓冲区
        if (session.getAttachment() == null) {
            delimiterFrameDecoder = new DelimiterFrameDecoder(DELIMITER_BYTES, 64);
            //缓存解码器已应对半包情况
            session.setAttachment(delimiterFrameDecoder);
        } else {
            delimiterFrameDecoder = session.getAttachment();
        }

        //未解析到DELIMITER_BYTES则返回null
        if (!delimiterFrameDecoder.decode(buffer)) {
            return null;
        }
        //解码成功
        ByteBuffer byteBuffer = delimiterFrameDecoder.getBuffer();
        byte[] bytes = new byte[byteBuffer.remaining()];
        byteBuffer.get(bytes);
        //释放临时缓冲区
        session.setAttachment(null);
        return new String(bytes);
    }

    @Override
    public ByteBuffer encode(String msg, AioSession<String> session) {
        byte[] bytes = msg.getBytes();
        ByteBuffer buffer = ByteBuffer.allocate(bytes.length + DELIMITER_BYTES.length);
        buffer.put(bytes).put(DELIMITER_BYTES);
        buffer.flip();
        return buffer;
    }
}
