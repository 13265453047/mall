package com.bruce.chapt03;

import java.util.concurrent.TimeUnit;

/**
 * @author rcy
 * @data 2021-03-05 12:33
 * @description 线程的优雅中断
 *
 * interrupt()相当于设置了 boolean volatile interrupt 属性（中断标识位）
 *
 * 这种通过标识位或者中断操作的方式能够使线程在终止时有机会去清理资源，
 * 而不是武断地将线程停止，因此这种终止线程的做法显得更加安全和优雅
 *
 *
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
