package com.galaxis.wcs.yanfeng.connection.socket;

import com.galaxis.wcs.yanfeng.util.Constance;
import com.galaxis.wcs.yanfeng.util.ThreadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;

public class SocketClient {
    private static final Logger log = LoggerFactory.getLogger(SocketClient.class);

    private String readLine;
    private InetSocketAddress socketAddress;

    private BufferedWriter writer = null;
    private BufferedReader reader = null;
    private DataInputStream dis = null;
    private Socket socket = null;
    public volatile boolean inited;
    private Object reader_lock = new Object();
    private Object writer_lock = new Object();
    private int timeout;

    public SocketClient(String ip, int port, String readLine) {
        this(ip, port, readLine, 0);
    }

    public SocketClient(String ip, int port, String readLine, int timeout) {
        this.readLine = readLine;
        this.socketAddress = new InetSocketAddress(ip, port);
        this.timeout = timeout;
    }

    /**
     * 初始化
     */
    public synchronized void init() {
        while (!inited) {
            try {
                socket = new Socket();
                socket.setSoTimeout(timeout);
                socket.connect(socketAddress);
                log.info("TCPclient启动成功！");
                writer = new BufferedWriter(new OutputStreamWriter(
                        socket.getOutputStream(), StandardCharsets.UTF_8));
                InputStream is = socket.getInputStream();
                dis = new DataInputStream(is);
                reader = new BufferedReader(new InputStreamReader(
                        dis, StandardCharsets.UTF_8));

                inited = true;
                log.info("TCPclient初始化！");

            } catch (Exception e) {
                try {
                    Thread.sleep(4000);
                    log.error("TCPclient连接失败，继续连接！{}", e.getMessage());
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }

    }

    /**
     * 发送信息
     */
    public void send(String message) throws IOException {
        synchronized (writer_lock) {
            checkInit();

            writer.write(message);
            if (Constance.Y.equalsIgnoreCase(readLine)) {
                writer.newLine();
            }
            writer.flush();
        }

    }

    /**
     * 接收反馈
     */
    public String receive() throws IOException {
        checkInit();

        String message = null;
        try {
            if (Constance.Y.equalsIgnoreCase(readLine)) {
                message = reader.readLine();

            } else {
                byte[] bytes = new byte[1024];
                int len = dis.read(bytes);
                if (len > 0) {
                    message = new String(bytes, 0, len);
                }

            }

        } catch (SocketTimeoutException e) {
            throw e;
        } catch (IOException e) {
            log.error("获取远端server反馈异常: {}", e.getMessage(), e);
            throw e;
        }

        return message;
    }

    private void checkInit() {
        while (!inited) {
            log.warn("本地client尚未初始化，请稍后...");
            init();
            try {
                Thread.sleep(ThreadUtil.SLEEP_MIDDLE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void close() {
        try {
            socket.close();
            log.info("socket client close!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        inited = false;
    }

}
