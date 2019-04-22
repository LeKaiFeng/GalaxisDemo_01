package com.me.netty.inbound;

import com.me.util.Constance;
import com.me.util.Post;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.java.Log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class WmsInboundHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private static final Logger log = LoggerFactory.getLogger(WmsInboundHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {

        int msgLen = msg.readableBytes();
        byte[] bytes = new byte[msgLen];
        msg.readBytes(bytes);
        Post post = new Post(bytes);

        byte ins = post.getIns();

        switch (ins) {
            case (byte) 0x83: {
                deal83(ctx, post);
                break;
            }
            case (byte) 0x84: {
                deal84(ctx, post);
                break;
            }
            default:
                log.info("未知的ins: {}", ins);
        }

    }

    AtomicInteger atomic = new AtomicInteger(1000);

    private void deal84(ChannelHandlerContext ctx, Post post) {
    	
    	
//    	byte[] data = post.getData();
//    	System.out.println();
//    	for (byte b : data) {
//			System.out.print(b+" ");
//		}
//    	System.out.println();
//    	System.out.println("格口号:"+data[1]);
////    	System.out.println("运单长度:"+data[2]);
//    	log.info("84 -运单长度:{ }",data[2]);
//    	byte[] barcode=new byte[data.length-4];
//    	for (int i = 3; i < data.length-1; i++) {
//			barcode[i-3]=data[i];
//		}
////    	System.out.println("运单号:"+new String(barcode));
//    	log.info("84 - 收到条码: {}",new String(barcode));
//    	System.out.println("异常码:"+data[data.length-1]);
    	
    	
        ByteBuffer buffer = ByteBuffer.allocate(5);
        buffer.put((byte) 0x00)
                .putInt(post.getSeq());
        byte[] array = buffer.array();
        Post post84 = new Post(atomic.getAndIncrement(), (byte) 0x84, array);
        byte[] bytes = postToBytes(post84);
        ctx.writeAndFlush(Unpooled.copiedBuffer(bytes));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.warn("处理{}的消息出现了异常, {}", ctx.channel().remoteAddress(), cause.getMessage(), cause);
    }
    int port=0;
    int num =0;
    private void deal83(ChannelHandlerContext ctx, Post post) {
    	try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	log.info("开始处理83指令--------------------------------------");
    	long start = System.currentTimeMillis();
        byte[] data = post.getData();
        String code = new String(data);
        log.info("收到条码: {}", code);

        String[] split = code.split("&");
        String barCode = null;
        String qrCode = null;
        for (String c : split) {
            // noread
            if (Constance.CODE_NOREAD.equals(c)) {
                barCode = c;
                continue;
            }

            // 12位条形码
            if (c.length() == 12) {
                barCode = c;
                continue;
            }

            // 二维码长度大于12
            if (c.length() > 12 && c.contains("M=")) {
                qrCode = c;
            }

        }

        log.info("barCode: {}, qrCode: {}", barCode, qrCode);

        // 计算格口
        
        if (Constance.CODE_NOREAD.equals(barCode) || Objects.isNull(barCode)) {
            barCode = Constance.CODE_NOREAD;
            port = Constance.PORT_ERROR;
        } else {
        	/**
        	 * 按条码最后一位进行分拣
        	 */
//            long l = Long.parseLong(barCode);
//            long ws = l % 10;
//            if (ws >= Constance.PORT_MIN && ws <= Constance.PORT_MAX) {
//               port = (int) ws;
//            } else {
//                port = Constance.PORT_MAX;
//            }
//            if(port%2==0) {
//            	port=4;
//            }else {
//            	port=6;
//            }
        	/**
        	  *  生成随机数
        	 */
            Random ran = new Random();
            port = ran.nextInt(18)+1;
        	
//            port++;
//            if(port>=19) {
//            	port=1;
//            }
            
        }

        log.info("port: {}", port);

        // 反馈响应
        ByteBuffer buffer = ByteBuffer.allocate(9 + barCode.length());
        buffer
                .put((byte) 0x00)
                .putInt(post.getSeq())
                .put((byte) 0x02)
                .putShort((short) port)
                .put((byte) barCode.length())
                .put(barCode.getBytes());
        byte[] data83 = buffer.array();
        Post post83 = new Post(post.getSeq(), post.getIns(), data83);
        byte[] bytes = postToBytes(post83);

        ctx.writeAndFlush(Unpooled.copiedBuffer(bytes));
        log.info("send: {}", Arrays.toString(bytes));
    	long end = System.currentTimeMillis();
    	num++;
    	log.info("条码:"+code+",耗时："+(end-start)+",	第"+num+"件完成-------------------------------------");
    }

    private byte[] postToBytes(Post post) {
        ByteBuffer buffer = ByteBuffer.allocate(post.getLen() + 5);
        buffer
                .put(post.getStx())
                .putShort(post.getLen())
                .putInt(post.getSeq())
                .put(post.getIns())
                .put(post.getData())
                .put(post.getXor())
                .put(post.getEtx());

        return buffer.array();
    }

}
