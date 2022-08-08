package com.bruce.java8.pass;

import java.util.Arrays;

/**
 * Pass By Value 和 Pass By Reference
 * 值传递：调用方法修改后，不影响传入的值
 * 引用传递：调用方法修改后，影响传入的值（出 String 外）
 *
 * @author rcy
 * @version 1.1.0
 * @className: Pass
 * @date 2022-08-08
 */
public class Pass {

    private int num = 10;
    private String str = "Hello";
    private StringBuilder sb = new StringBuilder("Hello");
    private int[] arr = new int[3];
    private A a = new A();

    public static void main(String[] args) {
        Pass pass = new Pass();

        // 基本类型 -> 值传递
        pass.fun(pass.num);
        System.out.println("pass.num = " + pass.num);

        // String为引用类型（特殊） -> 值传递
        pass.fun(pass.str);
        System.out.println("pass.str = " + pass.str);

        // 直接传入 StringBuilder 对象引用，引用传递（产生一个引用副本作为参数传入），修改只是修改副本引用的地址而不会修改原引用的地址
        pass.fun(pass.sb);
        System.out.println("pass.sb = " + pass.sb);

        // 此处改变引用地址所指向对象的内容
        pass.funAppend(pass.sb);
        System.out.println("pass.sbAppend = " + pass.sb);

        // 数组引用，引用传递
        pass.fun(pass.arr);
        System.out.println("pass.arr = " + Arrays.toString(pass.arr));

        pass.funChangeArr(pass.arr);
        System.out.println("pass.changeArr = " + Arrays.toString(pass.arr));

        pass.fun(pass.a);
        System.out.println("pass.a = " + pass.a);

    }

    public void fun(int num) {
        num = 22;
    }

    public void fun(String str) {
        str = "update";
    }

    public void fun(StringBuilder sb) {
        sb = new StringBuilder("update");
    }

    public void funAppend(StringBuilder sb) {
        sb.append("update");
    }

    public void fun(int[] arr) {
        arr = new int[]{1, 2, 4};
    }

    public void funChangeArr(int[] arr) {
        arr[0] = 6;
    }

    public void fun(A a) {
        a = new A();
        System.out.println(a);
    }

}


class A {

}