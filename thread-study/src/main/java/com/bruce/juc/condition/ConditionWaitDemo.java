package com.bruce.juc.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author rcy
 * @version 1.0.0
 * @className: ConditionDemo
 * @date 2023-03-24
 */
public class ConditionWaitDemo implements Runnable {

    private Lock lock;

    private Condition oneCondition;

    public ConditionWaitDemo(Lock lock, Condition oneCondition) {
        this.lock = lock;
        this.oneCondition = oneCondition;
    }

    @Override
    public void run() {
        System.out.println("begin - ConditionWaitDemo");

        lock.lock();
        try {
            oneCondition.await();
            System.out.println("end - ConditionWaitDemo");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}
