package com.bruce.netty.io.nio;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 从文件中读取
 *
 * @author rcy
 * @version 1.0.0
 * @className: ReadFile
 * @date 2023-02-07
 */
public class ReadFile {

    /**
     * 因此读取文件涉及三个步骤：
     * (1) 从 FileInputStream 获取 Channel，
     * (2) 创建 Buffer，
     * (3) 将数据从 Channel 读到 Buffer 中。
     */

    public static void main(String[] args) throws Exception {
        // 第一步是获取通道。我们从 FileInputStream 获取通道：
        FileInputStream fin = new FileInputStream("doc/readandshow.txt");
        FileChannel fc = fin.getChannel();

        // 下一步是创建缓冲区：
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        // 最后，需要将数据从通道读到缓冲区中，如下所示：
        fc.read(buffer);

        /*
            我们不需要告诉通道要读 多少数据 到缓冲区中。
            每一个缓冲区都有复杂的内部统计机制，
            它会跟踪已经读了多少数据以及还有多少空间可以容纳更多的数据。
         */
    }


}
