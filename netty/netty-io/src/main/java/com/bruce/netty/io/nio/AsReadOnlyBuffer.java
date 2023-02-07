package com.bruce.netty.io.nio;

import java.nio.ByteBuffer;

/**
 * 只读缓冲区
 *
 * @author rcy
 * @version 1.0.0
 * @className: AsReadOnlyBuffer
 * @date 2023-02-07
 */
public class AsReadOnlyBuffer {

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);

        for (int i = 0; i < buffer.capacity(); ++i) {
            buffer.put((byte) i);
        }

        buffer.clear();

        ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();

        buffer.put(0, (byte) 111);

        System.out.println("readOnlyBuffer.position() = " + readOnlyBuffer.position());
        System.out.println("readOnlyBuffer.get() = " + readOnlyBuffer.get());

    }

}
