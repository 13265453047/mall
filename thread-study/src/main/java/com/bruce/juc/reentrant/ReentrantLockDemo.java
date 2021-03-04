package com.bruce.juc.reentrant;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author rcy
 * @data 2021-02-02 11:56
 * @description TODO
 */
public class ReentrantLockDemo {

    private volatile int a;

    private static AtomicInteger atomic = new AtomicInteger(0);

    private static final ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        ReentrantLockDemo demo = new ReentrantLockDemo();
        ExecutorService service = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 100; i++) {
            service.execute(new ReentrantLockTask(demo));
        }

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        service.shutdown();
        System.out.println("demo.a = " + demo.a);
        System.out.println("atomic.get() = " + atomic.get());
    }

    static class ReentrantLockTask implements Runnable {

        private ReentrantLockDemo demo;

        public ReentrantLockTask(ReentrantLockDemo demo) {
            this.demo = demo;
        }

        @Override
        public void run() {
            try {
                lock.lock();
                long id = Thread.currentThread().getId();
                if (id % 2 == 0) {
                    atomic.getAndIncrement();
                    demo.write();
                } else {
                    demo.read();
                }
            } finally {
                lock.unlock();
            }
        }
    }

    private void write() {
        a += 1;
    }

    private int read() {
        return a;
    }
}
