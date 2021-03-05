package com.bruce.chapt03;

import java.util.concurrent.TimeUnit;

/**
 * @author rcy
 * @data 2021-03-05 12:50
 * @description 线程中断复位
 * 外面的线程调用 thread.interrupt 来设置中断标识，而在线程里面，又通过 Thread.interrupted 把线程的标识又进行了复位
 * <p>
 * 除了通过 Thread.interrupted 方法对线程中断标识进行复位以外，还有一种被动复位的场景，就是对抛出 InterruptedException 异常的方法，
 * 在InterruptedException 抛出之前，JVM 会先把线程的中断标识位清除，然后才会抛出 InterruptedException，
 * 这个时候如果调用 isInterrupted 方法，将会返回 false
 */
public class InterruptedDemo {

    /*
    线程为什么要复位？
    线程执行 interrupt() 以后的源码




    thread.cpp
    void Thread::interrupt(Thread*thread) {
        trace("interrupt", thread);
        debug_only(check_for_dangling_thread_pointer(thread);)
        os::interrupt (thread);
    }



    os_linux.cpp
    void os::interrupt(Thread*thread) {
        assert (Thread::current () == thread ||
                Threads_lock -> owned_by_self(),
                "possibility of dangling Thread pointer");
        OSThread * osthread = thread -> osthread();
        if (!osthread -> interrupted()) {
            osthread -> set_interrupted(true);
            // More than one thread can get here with the same value of
            osthread,
                    // resulting in multiple notifications. We do, however, want the
                    store
            // to interrupted() to be visible to other threads before we
            execute unpark ().
                    OrderAccess::fence ();
            ParkEvent * const slp = thread -> _SleepEvent;
            if (slp != NULL) slp -> unpark();
        }
        // For JSR166. Unpark even if interrupt status already was set
        if (thread -> is_Java_thread())
            ((JavaThread *) thread)->parker()->unpark();
        ParkEvent * ev = thread -> _ParkEvent;
        if (ev != NULL) ev -> unpark();
    }

    其实就是通过 unpark 去唤醒当前线程，并且设置一个标识位为 true。 并没有
    所谓的中断线程的操作，所以实际上，线程复位可以用来实现多个线程之间的
    通信。

*/


    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            while (true) {
                boolean interruptedFlag = Thread.currentThread().isInterrupted();
                if (interruptedFlag) {
                    System.out.println("before:" + interruptedFlag);
                    Thread.interrupted();//对线程进行复位，中断标识为false
                    System.out.println("after:" + Thread.currentThread()
                            .isInterrupted());
                }
            }
        });
        thread.start();
        TimeUnit.SECONDS.sleep(1);
        thread.interrupt();//设置中断标识,中断标识为 true
    }

}
