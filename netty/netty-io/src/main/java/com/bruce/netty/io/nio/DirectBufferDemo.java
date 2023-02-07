package com.bruce.netty.io.nio;

import sun.nio.ch.DirectBuffer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 直接缓冲区
 *
 * @author rcy
 * @version 1.0.0
 * @className: DirectBufferDemo
 * @date 2023-02-07
 */
public class DirectBufferDemo {

    public static void main(String[] args) throws Exception {
        // 第一步是获取通道。我们从 FileInputStream 获取通道：
        FileInputStream fin = new FileInputStream("doc/readandshow.txt");
        FileChannel fcin = fin.getChannel();

        // 首先从 FileOutputStream 获取一个通道
        FileOutputStream fou = new FileOutputStream("doc/direct_copy.txt");
        FileChannel fcout = fou.getChannel();

        // 下一步是创建缓冲区：
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);

        // 最后，需要将数据从通道读到缓冲区中，然后写入
        while (true) {
            // 重设缓冲区
            // 最后，在从输入通道读入缓冲区之前，我们调用 clear() 方法。同样，在将缓冲区写入输出通道之前，我们调用 flip() 方法，如下所示：
            // clear() 方法重设缓冲区，使它可以接受读入的数据。 flip() 方法让缓冲区可以将新读入的数据写入另一个通道。
            buffer.clear();

            int r = fcin.read(buffer);

            // 检查状态
            if (r == -1) {
                break;
            }

            buffer.flip();
            // 最后一步是写入缓冲区中：
            fcout.write(buffer);
        }
    }

}
