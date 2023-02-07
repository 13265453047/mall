package com.bruce.netty.io.nio;

import java.nio.ByteBuffer;

/**
 * 缓存分片
 * @author rcy
 * @version 1.0.0
 * @className: Sliec
 * @date 2023-02-07
 */
public class Sliec {

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);

        for (int i = 0; i < buffer.capacity(); ++i) {
            buffer.put((byte) i);
        }

        buffer.position(3);
        buffer.limit(7);
        ByteBuffer slice = buffer.slice();


        buffer.clear();

        buffer.put(4,(byte)12);
        slice.put(0,(byte)33);

        System.out.println("buffer.remaining() = " + buffer.remaining());

        System.out.println(buffer.get());
        System.out.println(slice.get());
        System.out.println(slice.get());
        System.out.println(slice.get());
        System.out.println(slice.get());
        System.out.println(buffer.get());
        System.out.println(buffer.get());
        System.out.println(buffer.get());
        System.out.println(buffer.get());
        System.out.println(buffer.get());
        System.out.println(buffer.get());
        System.out.println(buffer.get());
        System.out.println(buffer.get());
        System.out.println(buffer.get());

    }

}
