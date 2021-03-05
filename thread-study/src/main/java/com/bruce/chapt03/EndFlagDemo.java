package com.bruce.chapt03;

import java.util.concurrent.TimeUnit;

/**
 * @author rcy
 * @data 2021-03-05 12:40
 * @description 使用中断标识位结束线程执行
 */
public class EndFlagDemo {

    private static volatile boolean flag;

    private static int i;

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            while (!flag) {
                i++;
            }
            System.out.println(i);
        });

        thread.start();
        TimeUnit.SECONDS.sleep(1);
        flag = true;
    }

}
