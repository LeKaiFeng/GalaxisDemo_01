package com.me.util;

import lombok.Getter;
import lombok.Setter;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * 与wms交互的报文的封装类
 */
@Getter
@Setter
public class Post {
    // 起始字符 0xAA(顺丰)/0xCC(供应商) 1字节
    private byte stx;

    // 数据信息域总长度 +1(指令码) +4(SEQ) 16进制 2字节
    private short len;

    // 请求序列号(循环+1)，标识同一条请求 4字节
    private int seq;

    // 指令码 1字节
    private byte ins;

    // 数据信息域 (len-5)字节
    private byte[] data;

    // 前面所有字节的异或(包括起始字符) 16进制 1字节
    private byte xor;

    // 结束字符 1字节
    private byte etx;

    public Post() {
    }

    public Post(int seq, byte ins, byte[] data) {
        this.seq = seq;
        this.ins = ins;
        this.data = data;

        this.stx = (byte) 0xAA;
        this.len = (short) (data.length + 5);
        this.xor = xor(stx
                , toBytes(len, 2)
                , toBytes(seq, 4)
                , toBytes(ins, 1)
                , data);
        this.etx = 0x55;
    }

    public Post(String post) {
        this(ConventUtils.hexStrToBytes(post));
    }

    public Post(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);

        this.stx = buffer.get();
        this.len = buffer.getShort();
        this.seq = buffer.getInt();
        this.ins = buffer.get();
        // this.template = getData(buffer, len - 5);
        this.data = new byte[len - 5];
        buffer.get(data);

        this.xor = buffer.get();
        this.etx = buffer.get();
    }

    private byte xor(byte stx, byte[]... bytess) {
        for (byte[] bytes : bytess) {
            for (byte b : bytes) {
                stx ^= b;
            }
        }
        return stx;
    }

    // int值转为bytes
    private byte[] toBytes(int value, int len) {
        byte[] bytes = new byte[len];
        for (int i = 0; i < len; i++) {
            bytes[len - i - 1] = (byte) ((value >> (i * 8)) & 0xFF);
        }

        return bytes;
    }

    @Override
    public String toString() {
        return "Post{" +
                "stx=" + stx +
                ", len=" + len +
                ", seq=" + seq +
                ", ins=" + ins +
                ", data=" + Arrays.toString(data) +
                ", xor=" + xor +
                ", etx=" + etx +
                '}';
    }

	public byte getStx() {
		return stx;
	}

	public void setStx(byte stx) {
		this.stx = stx;
	}

	public short getLen() {
		return len;
	}

	public void setLen(short len) {
		this.len = len;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public byte getIns() {
		return ins;
	}

	public void setIns(byte ins) {
		this.ins = ins;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public byte getXor() {
		return xor;
	}

	public void setXor(byte xor) {
		this.xor = xor;
	}

	public byte getEtx() {
		return etx;
	}

	public void setEtx(byte etx) {
		this.etx = etx;
	}
    
}
