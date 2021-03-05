package com.bruce.chapt02;

import java.util.concurrent.TimeUnit;

/**
 * @author rcy
 * @data 2021-03-05 11:51
 * @description java线程6种状态
 * 1.NEW:初始状态，线程被构建，但是还没有调用 start 方法
 * 2.RUNNABLE:运行状态，JAVA 线程把操作系统中的就绪和运行两种状态统一称为“运行中”
 * 3.WAITING
 * 4.TIMED_WAITING:超时等待状态，超时以后自动返回
 * 5.BLOCKED:阻塞状态，表示线程进入等待状态,也就是线程因为某种原因放弃了 CPU 使用权，阻塞也分为几种情况
 *  Ø 等待阻塞：运行的线程执行 wait 方法，jvm 会把当前线程放入到等待队列
 *  Ø 同步阻塞：运行的线程在获取对象的同步锁时，若该同步锁被其他线程锁占用了，那么 jvm 会把当前的线程放入到锁池中
 *  Ø 其他阻塞：运行的线程执行 Thread.sleep 或者 t.join 方法，或者发出了 I/O请求时，JVM 会把当前线程设置为阻塞状态，当 sleep 结束、join 线程终止、io 处理完毕则线程恢复
 * 6.TERMINATED:终止状态，表示当前线程执行完毕
 *
 * 查看当前程序执行的线程栈：
 * class文件，键入“jps”，（JDK1.5 提供的一个显示当前所有 java进程 pid 的命令），可以获得相应进程的 pid
 * 根据上一步骤获得的 pid，继续输入 jstack pid（jstack 是 java 虚拟机自带的一种堆栈跟踪工具。jstack 用于打印出给定的 java 进程 ID 或 core file 或远程调试服务的 Java 堆栈信息）
 *
 */
public class ThreadState {

    public static void main(String[] args) {

        // TIMED_WAITING
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "timed_waiting").start();

        // WAITING
        new Thread(() -> {
            synchronized (ThreadState.class) {
                try {
//                    Thread.currentThread().wait(); // 由此获取当前线程会出现异常
                    ThreadState.class.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "waiting").start();

        new Thread(new BlockDemo(), "BlockDemo_01").start();
        new Thread(new BlockDemo(), "BlockDemo_02").start();

    }

    static class BlockDemo implements Runnable {
        @Override
        public void run() {
            synchronized (ThreadState.class) {
                try {
                    TimeUnit.SECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
