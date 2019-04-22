package com.galaxis.wcs.yanfeng.connection.smartsocket.protocol;

import com.galaxis.wcs.yanfeng.util.Constance;
import org.smartboot.socket.Protocol;
import org.smartboot.socket.transport.AioSession;

import java.nio.ByteBuffer;

public class StringProtocol implements Protocol<String> {

    private static final int MIN_SIZE = 2;

    @Override
    public String decode(ByteBuffer byteBuffer, AioSession<String> aioSession, boolean b) {
        int remaining = byteBuffer.remaining();
        if (remaining < MIN_SIZE) {
            return null;
        }

        byte[] bytes = new byte[remaining];
        byteBuffer.get(bytes);
        return new String(bytes, Constance.CHARSET);
    }

    @Override
    public ByteBuffer encode(String s, AioSession<String> aioSession) {
        byte[] bytes = s.getBytes(Constance.CHARSET);
        ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
        buffer.put(bytes);
        buffer.flip();
        return buffer;

    }

}
