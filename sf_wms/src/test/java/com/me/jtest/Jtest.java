package com.me.jtest;

import com.me.netty.NettyServer;

import com.me.util.Post;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class Jtest {
    @Test
    public void test() throws IOException {
        ServerSocket server = new ServerSocket(1200);
        while (true) {
            Socket client = server.accept();
            SendAndRec sendAndRec = new SendAndRec(client);
            new Thread(sendAndRec).start();
        }
    }
    
    @Test
    public void testPost() {
    	byte[] bytes = {-86, 0, 26, 0, 0, 40, -53, -125, 0, 0, 0, 40, -53, 2, 0, 11, 12, 48, 55, 56, 57, 48, 50, 52, 57, 49, 54, 50, 52, 62, 85};
    	Post post = new Post(bytes);
    	System.out.println(post);
    }

    private class SendAndRec implements Runnable {
        private Socket client;

        public SendAndRec(Socket client) {
            this.client = client;
        }

        @Override
        public void run() {
            OutputStream os = null;
            try {
                byte[] bytes = {(byte) 204, 0, 22, 0, 0, 5, 82, (byte) 132, 0, 12, 12, 49, 53, 51, 55, 52, 54, 56, 52, 49, 54, 52, 57, 0, 2, 15, 85};
                os = client.getOutputStream();
                os.write(bytes);
                System.out.println(String.format("发送: %s", Arrays.toString(bytes)));
                InputStream is = client.getInputStream();
                byte[] r = new byte[1024];
                int l = is.read(r);
                byte[] rc = Arrays.copyOfRange(r, 0, l);
                Post post = new Post(rc);
                System.out.println("post = " + post);

                // client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
