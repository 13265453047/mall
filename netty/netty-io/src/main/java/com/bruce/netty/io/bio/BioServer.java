package com.bruce.netty.io.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author rcy
 * @data 2022-02-06 12:54
 * @description TODO
 */
public class BioServer {

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            Socket socket = serverSocket.accept();

            InputStream is = socket.getInputStream();

            byte[] buff = new byte[1024];
            int len = is.read(buff);
            if (len > 0) {
                String msg = new String(buff, 0, len);
                System.out.println("收到" + msg);
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
