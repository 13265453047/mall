package com.bruce.jvm.jvmtuning;

/**
 * CPU占用高
 *
 * @author rcy
 * @version 1.1.0
 * @className: InfiniteLoop
 * @date 2022-06-14
 */
public class InfiniteLoop {

    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            long i = 0;
            while (true) {
                i++;
            }
        });
        thread.start();
    }

}
