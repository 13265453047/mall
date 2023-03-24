package com.bruce.juc.util.threadlocal;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
public class ThreadLocalExample {
    //希望每一个线程获得的num都是0
//    private static int num=0;

    /*
    正常情况下是不会有内存溢出的
    但是如果我们没有调用get 和set 的时候就会可能面临着内存溢出，
    养成好习惯不再使用的时候调remove(),加快垃圾回收，避免内存溢出退一步说，
    就算我们没有调用get 和set 和remove 方法,线程结束的时候，
    也就没有强引用再指向ThreadLocal 中的ThreadLocalMap了，
    这ThreadLocalMap 和里面的元素也会被回收掉，但是有一种危险是，
    如果线程是线程池的， 在线程执行完代码的时候并没有结束，只是归还给线程池，
    这个时候ThreadLocalMap 和里面的元素是不会回收掉的
     */

    //全局用户信息的存储
    // 一个线程可以设置多个副本
    private static ThreadLocal<Integer> local_int = ThreadLocal.withInitial(() -> 0);
    private static ThreadLocal<String> local_string = ThreadLocal.withInitial(() -> "0");

    private static void addInt() {
        Integer num = local_int.get();
//        if (num == null) {
//            num = 0;
//            local_int.set(num); //要在当前线程的范围内设置一个num对象.
//        }
        local_int.set(num + 5);
    }

    private static void addStr() {
        String str = local_string.get();
//        if (str == null) {
//            str = "0";
//            local_string.set(str); //要在当前线程的范围内设置一个str对象.
//        }

        local_string.set(str + "5");
    }

    public static void main(String[] args) {
        Thread[] thread = new Thread[5];
        for (int i = 0; i < 5; i++) {
            thread[i] = new Thread(() -> {
                addInt();
                addStr();
                System.out.println(Thread.currentThread().getName() + " " + local_int.get());
                System.out.println(Thread.currentThread().getName() + " " + local_string.get());
            });
        }
//        local_int = null;
//        local_string = null;
        for (int i = 0; i < 5; i++) {
            thread[i].start();
            try {
                thread[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
