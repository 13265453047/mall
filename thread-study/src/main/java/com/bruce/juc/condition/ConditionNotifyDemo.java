package com.bruce.juc.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author rcy
 * @version 1.0.0
 * @className: ConditionDemo
 * @date 2023-03-24
 */
public class ConditionNotifyDemo implements Runnable {

    private Lock lock;

    private Condition oneCondition;

    public ConditionNotifyDemo(Lock lock, Condition oneCondition) {
        this.lock = lock;
        this.oneCondition = oneCondition;
    }

    @Override
    public void run() {
        System.out.println("begin - ConditionNotifyDemo");

        lock.lock();
        try {
            oneCondition.signal();
            System.out.println("end - ConditionNotifyDemo");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}
