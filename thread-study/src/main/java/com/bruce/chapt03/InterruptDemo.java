package com.bruce.chapt03;

import java.util.concurrent.TimeUnit;

/**
 * @author rcy
 * @data 2021-03-05 12:33
 * @description 线程的优雅中断
 */
public class InterruptDemo {

    private static int i;

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                i++;
            }
            System.out.println(i);
        });
        thread.start();
        TimeUnit.SECONDS.sleep(1);
        thread.interrupt();
    }


}
