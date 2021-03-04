package com.bruce.juc.cas;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author rcy
 * @data 2021-01-29 17:49
 * @description:计数器（CAS线程安全计数器和非线程安全计数器） JVM中的CAS操作正式利用了处理器提供的CMPXCHG指令实现的。
 * 自旋CAS实现的基本思路就是循环进行CAS操作指导成功为止。
 */
public class Counter {

    private AtomicInteger atomicI = new AtomicInteger(0);
    private int i = 0;

    public static void main(String[] args) {
        Counter counter = new Counter();

        List<Thread> threads = new ArrayList<>();
        long start = System.currentTimeMillis();

        for (int i = 0; i < 100; i++) {
            Thread t = new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    counter.count();
                    counter.safeCount();
                }
            });
            threads.add(t);
        }

        for (Thread t : threads) {
            t.start();
        }

        // 等待所有线程执行完成
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("counter.i = " + counter.i);
        System.out.println("counter.atomicI.get() = " + counter.atomicI.get());
        System.out.println("counter.stampedReference.getReference() = " + counter.stampedReference.getReference());
        System.out.println("System.currentTimeMillis() - start = " + (System.currentTimeMillis() - start));
    }

    private AtomicStampedReference<Integer> stampedReference = new AtomicStampedReference<>(0, 0);

    /**
     * 使用CAS实现线程安全计数器
     */
    private void safeCount() {
//        atomicI.incrementAndGet();
//        atomicI.getAndIncrement();
//        atomicI.addAndGet(1);
//        atomicI.getAndAdd(1);


//        for (; ; ) {
//            int i = atomicI.get();
//            boolean suc = atomicI.compareAndSet(i, ++i);
//            if (suc) {
//                break;
//            }
//        }

            // 待校验，无法保证计数器并行执行
//        for (; ; ) {
//            int stamp = stampedReference.getStamp();
//            int reference = stampedReference.getReference();
//            boolean suc = stampedReference.compareAndSet(reference, ++reference, stamp, ++stamp);
//            if (suc) {
//                break;
//            }
//        }
    }

    /**
     * 非线程安全的计数器
     */
    private void count() {
        i++;
    }

}
