package com.bruce.jvm.jvmtuning;

/**
 * FUll GC 频繁
 * 设置对象直接进入老年代，造成老年代内存消耗严重，频繁进行 FULL GC
 *
 * @author rcy
 * @version 1.1.0
 * @className: HeapOOM
 * @date 2022-06-14
 */
public class HeapOOM {

    /**
     * 启动指令
     * java -Xms30m -Xmx30m -Xmn2m -XX:SurvivorRatio=8 -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=dump/dump.hprof dump.HeapOOM
     *
     * @param args:
     * @author: rcy
     * @date: 2022-06-16
     * @return: void
     **/
    public static void main(String[] args) throws InterruptedException {
        Object1 object1 = new Object1();
        while (true) {
            Object2 object2 = new Object2();
            Thread.sleep(100);
        }
    }

}

class Object1 {
    int size = (10 * 1024 * 1024) / 4; // 2.5m
    int[] nums = new int[size];

    public Object1() {
        for (int i = 0; i < size; i++) {
            nums[i] = i;
        }
    }

}

class Object2 {
    int size = (1 * 1024 * 1024) / 4; // 0.25m
    int[] nums = new int[size];

    public Object2() {
        for (int i = 0; i < size; i++) {
            nums[i] = i;
        }
    }
}