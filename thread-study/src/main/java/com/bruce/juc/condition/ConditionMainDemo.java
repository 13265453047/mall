package com.bruce.juc.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author rcy
 * @version 1.0.0
 * @className: ConditionDemo
 * @date 2023-03-24
 */
public class ConditionMainDemo {
    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        Condition condition1 = lock.newCondition();
        Condition condition2 = lock.newCondition();

        ConditionWaitDemo waitDemo = new ConditionWaitDemo(lock, condition1);
        ConditionNotifyDemo notifyDemo = new ConditionNotifyDemo(lock, condition1);

        Thread t1 = new Thread(waitDemo);
        Thread t2 = new Thread(notifyDemo);

        t1.start();
        t2.start();

    }

}
