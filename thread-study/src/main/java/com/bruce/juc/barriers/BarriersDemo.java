package com.bruce.juc.barriers;

/**
 * @author rcy
 * @data 2021-01-30 10:26
 * @description 为保证内存的可见性，java编译器在生成指令序列的适当位置会插入内存屏障指令来禁止特定类型的处理器重排序。
 * <p>
 * JMM把内存屏障指令分为4类
 * LoadLoad Barriers
 * LoadStore Barriers
 * StoreStore Barriers
 * StoreLoad Barriers
 * 其中StoreLoad Barriers是一个“全能型”的屏障，它同时具有其他三个屏障的效果。
 * 执行该屏障开销会很昂贵，因为当前处理器通常要把些缓存区中的数据全部刷新到内存中（Buffer Fully Flush）
 */
public class BarriersDemo {

    public static void main(String[] args) {
        double pi = 3.14;
        double r = 1.0;
        double area = pi * r * r;
    }

    int a;
    volatile int v1 = 1;
    volatile int v2 = 2;

    void readAndWrite() {
        // volatile 读
        int i = v1;
        // volatile 读
        int j = v2;
        // 普通写
        a = i + j;

        // volatile 写
        v1 = i + 1;

        // volatile 写
        v2 = j * 2;
    }


}
