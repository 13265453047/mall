package com.bruce.netty.io.nio;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @author rcy
 * @version 1.0.0
 * @className: CharBufferDemo
 * @date 2023-02-07
 */
public class CharBufferDemo {

    public static void main(String[] args) throws Exception {
        String msg = "Hello World";
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        byte[] bytes = msg.getBytes();
        for (int i = 0; i < bytes.length; i++) {
            buffer.put(bytes[i]);
        }
        buffer.flip();

        Charset latin1 = StandardCharsets.ISO_8859_1;
        // 创建一个解码器（用于读取）和一个编码器 （用于写入）：
        CharsetDecoder decoder = latin1.newDecoder();
        CharsetEncoder encoder = latin1.newEncoder();

        // 为了将字节数据解码为一组字符，我们把 ByteBuffer 传递给 CharsetDecoder，结果得到一个 CharBuffer：
        CharBuffer cb = decoder.decode(buffer);
        // 如果想要处理字符，我们可以在程序的此处进行。但是我们只想无改变地将它写回，所以没有什么要做的。
        cb.append(", I'm Tom.");

        // 要写回数据，我们必须使用 CharsetEncoder 将它转换回字节：
        ByteBuffer outputData = encoder.encode(cb);

        FileOutputStream fou = new FileOutputStream("doc/charbuffer.txt");
        FileChannel fc = fou.getChannel();

        fc.write(outputData);
    }

}
