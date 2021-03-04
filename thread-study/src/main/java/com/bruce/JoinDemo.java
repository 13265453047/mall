package com.bruce;

/**
 * @author rcy
 * @data 2021-03-04 21:12
 * @description join()会使主线程一直处于WAITING状态，join()中会循环调用wait()方法，知道isAlive()为false，
 * 当前线程结束时在会JVM源码中Thread.cpp中可知线程会调用ensure_join,其中有lock.notify_all(thread)会唤醒所有wait在该锁下的线程。
 * main线程则会继续执行
 */
public class JoinDemo {

    public static void main(String[] args) throws InterruptedException {

        /**
         * 线程 t1、t2、t3会按照顺执行
         */

        Thread t1 = new Thread(()->{
            System.out.println("t1");
        });

        Thread t2 = new Thread(()->{
            System.out.println("t2");
        });

        Thread t3 = new Thread(()->{
            System.out.println("t3");
        });

        t1.start();
        t1.join();

        t2.start();
        t2.join();

        t3.start();
        t3.join();
    }



}
