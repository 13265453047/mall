package com.bruce.netty.io.nio;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 写入文件
 *
 * @author rcy
 * @version 1.0.0
 * @className: WriteFile
 * @date 2023-02-07
 */
public class WriteFile {

    /**
     * 因此读取文件涉及三个步骤：
     * (1) 从 FileInputStream 获取 Channel，
     * (2) 创建 Buffer，
     * (3) 将数据从 Channel 读到 Buffer 中。
     */

    public static void main(String[] args) throws Exception {
        // 首先从 FileOutputStream 获取一个通道
        FileOutputStream fou = new FileOutputStream("doc/writesomebytes.txt");
        FileChannel fc = fou.getChannel();

        // 下一步是创建一个缓冲区并在其中放入一些数据 - 在这里，数据将从一个名为 message 的数组中取出，
        // 这个数组包含字符串 "Some bytes" 的 ASCII 字节(本教程后面将会解释 buffer.flip() 和 buffer.put() 调用)。
        String msg = "Some bytes";
        byte[] message = msg.getBytes();

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        for (int ii = 0; ii < message.length; ++ii) {
            buffer.put(message[ii]);
        }
        buffer.flip();

        // 最后一步是写入缓冲区中：
        fc.write(buffer);

//        byte[] array = buffer.array();
//        String msgStr = new String(array);
//        System.out.println(msgStr);
//        System.out.println(Integer.valueOf(msgStr.trim()));

        /*
            注意在这里同样不需要告诉通道要写入多数据。缓冲区的内部统计机制会跟踪它包含多少数据以及还有多少数据要写入。
         */
    }

}
