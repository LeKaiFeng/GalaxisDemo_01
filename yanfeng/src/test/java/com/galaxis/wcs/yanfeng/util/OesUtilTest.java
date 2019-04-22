package com.galaxis.wcs.yanfeng.util;

import com.galaxis.wcs.yanfeng.connection.netty.NettyClient;
import com.galaxis.wcs.yanfeng.device.line.YanFengLineInboundHandler;
import com.galaxis.wcs.yanfeng.util.template.LineResponse;
import io.netty.channel.ChannelHandler;
import org.junit.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

public class OesUtilTest {

    @Test
    public void encodeLevelLocation() {
        Integer level = 102;
        Integer location = 111881;
        Integer en = LocationUtil.encodeLevelLocation(level, location);
        System.out.println("en = " + en);

        List<Integer> list = LocationUtil.decodeLevelLocation(en);
        System.out.println("list = " + list);

    }

    @Test
    public void testDecodeCode() {
        // [104, 101, 108, 108, 111, 32, 119, 111, 114, 108, 100]
        byte[] helloBytes = "hello world".getBytes();
        // print(bytes);
        byte[] boxCode = new byte[YanFengLineInboundHandler.BYTES_LENGTH_BOX_NUMBER];
        // [104, 101, 108, 108, 111, 32, 119, 111, 114, 108, 100, 0, 0, 0, 0, 0, 0, 0, 0, 0]
        System.arraycopy(helloBytes, 0, boxCode, 0, helloBytes.length);
        // print(boxCode);

        String box;
        box = OesUtil.decodeBytes(boxCode);
        System.out.println("box = " + box);

    }

    @Test
    public void testServer() throws IOException {
        ServerSocket server = new ServerSocket(1300);
        while (true) {
            Socket client = server.accept();
            ThreadUtil.execute(() -> {
                OutputStream os = null;
                try {
                    os = client.getOutputStream();
                    byte[] bs = testEn();
                    os.write(bs);
                    os.flush();
                    System.out.printf("send , length: %s, array: %s\n", bs.length, Arrays.toString(bs));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    @Test
    public void testOs() {
        LinkedHashMap<String, ChannelHandler> handlers = new LinkedHashMap<>();
        handlers.put("handlers", new YanFengLineInboundHandler());

        NettyClient client = new NettyClient("127.0.0.1", 1300, handlers);
        client.init();
    }

    public byte[] testEn() {
        YanFengLineInboundHandler handler = new YanFengLineInboundHandler();

        ByteBuffer buffer = ByteBuffer.allocate(YanFengLineInboundHandler.MESSAGE_LENGTH_INTERACTION);
        buffer.putInt(12345);
        byte[] bytes = "carton no 12345".getBytes();
        byte[] cos = new byte[20];
        System.arraycopy(bytes, 0, cos, 0, bytes.length);
        buffer.put(cos);
        buffer.putInt(110112);
        buffer.put((byte) 1);
        buffer.put(YanFengLineInboundHandler.DEFAULT);
        buffer.put((byte) 1);
        buffer.put(YanFengLineInboundHandler.DEFAULT);
        buffer.put((byte) 1);
        buffer.put((byte) 1);
        buffer.putShort((short) 0);
        buffer.putShort((short) 1234);
        buffer.putShort((short) 4321);
        buffer.put("sheng chan ri qi".getBytes());

        return buffer.array();

    }

    @Test
    public void testDe() {
        YanFengLineInboundHandler handler = new YanFengLineInboundHandler();

        LineResponse response = new LineResponse();
        response.setSeq(123);
        response.setCartonNo("hello carton no");
        response.setLocation(110112);
        response.setType(1);
        response.setWarn(0);
        response.setErrorCode(110);

        byte[] bytes = handler.encodeResponse(response);

        for (int i = 0; i < bytes.length; i++) {
            System.out.print(bytes[i]);
            System.out.print(", ");
            if (i % 4 == 3) {
                System.out.println();
            }
        }

    }

    @Test
    public void testBs() {
        // [0, 0, 48, 57, 99, 97, 114, 116, 111, 110, 32, 110, 111, 32, 49, 50, 51, 52, 53, 0, 1, -82, 32, 1, 0, 1, 0, 1, 1, 0, 0, 4, -46, 16, -31, 115, 104, 101, 110, 103, 32, 99, 104, 97, 110, 32, 114, 105, 32, 113, 105, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
        byte[] bytes = new byte[]{0, 0, 48, 57, 99, 97, 114, 116, 111, 110, 32, 110, 111, 32, 49, 50, 51, 52, 53, 0, 1, -82, 32, 1, 0, 1, 0, 1, 1, 0, 0, 4, -46, 16, -31, 115, 104, 101, 110, 103, 32, 99, 104, 97, 110, 32, 114, 105, 32, 113, 105, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        for (int i = 0; i < bytes.length; i++) {
            System.out.print(bytes[i]);
            System.out.print("\t, ");
            if (i % 4 == 3) {
                System.out.println();
            }
        }

    }

}