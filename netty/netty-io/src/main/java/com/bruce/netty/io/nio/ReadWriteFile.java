package com.bruce.netty.io.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 读写结合，copy文件
 *
 * @author rcy
 * @version 1.0.0
 * @className: ReadWriteFile
 * @date 2023-02-07
 */
public class ReadWriteFile {

    /**
     * 执行三个基本操作：
     * 首先创建一个 Buffer，
     * 然后从源文件中将数据读到这个缓冲区中，
     * 然后将缓冲区写入目标文件。
     * 这个程序不断重复 ― 读、写、读、写 ― 直到源文件结束。
     * <p>
     * 该程序让您看到我们如何检查操作的状态，
     * 以及如何使用 clear() 和 flip() 方法重设缓冲区，
     * 并准备缓冲区以便将新读取的数据写到另一个通道中。
     */

    public static void main(String[] args) throws Exception {
        // 第一步是获取通道。我们从 FileInputStream 获取通道：
        FileInputStream fin = new FileInputStream("doc/readandshow.txt");
        FileChannel fcin = fin.getChannel();

        // 首先从 FileOutputStream 获取一个通道
        FileOutputStream fou = new FileOutputStream("doc/copy.txt");
        FileChannel fcout = fou.getChannel();

        // 下一步是创建缓冲区：
        ByteBuffer buffer = ByteBuffer.allocate(1024);

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
