package com.bruce.java8.amount;

import java.math.BigDecimal;

/**
 * @author rcy
 * @version 1.1.0
 * @className: BigDecimalDemo
 * @date 2022-06-10
 */
public class BigDecimalDemo {

    public static void main(String[] args) {
        BigDecimal b1 = new BigDecimal(88);
        BigDecimal b2 = new BigDecimal(8.8d);
        BigDecimal b3 = new BigDecimal(String.valueOf(8.8d));
        BigDecimal b4 = new BigDecimal("8.8");
        BigDecimal b5 = new BigDecimal(8.8d).setScale(2, BigDecimal.ROUND_HALF_UP);
        System.out.println(b1);
        System.out.println(b2);
        System.out.println(b3);
        System.out.println(b4);
        System.out.println(b5);
        /*
        88
        8.800000000000000710542735760100185871124267578125
        8.8
        8.8
        8.80
        针对b2这种情况会丢失精度，应使用字符串的形式
         */
    }

}
