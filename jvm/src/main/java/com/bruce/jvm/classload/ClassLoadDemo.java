package com.bruce.jvm.classload;

/**
 * 类加载器
 *
 * @author rcy
 * @version 1.1.0
 * @className: ClassLoadDemo
 * @date 2022-06-13
 */
public class ClassLoadDemo {

    public static void main(String[] args) {
//        ClassLoader applicationClassLoad = ClassLoader.getSystemClassLoader();
//        ClassLoader extClassLoad = applicationClassLoad.getParent();
//        ClassLoader bootClassLoad = extClassLoad.getParent();
//
//        System.out.println("applicationClassLoad.getClass() = " + applicationClassLoad.getClass());
//        System.out.println("extClassLoad.getClass() = " + extClassLoad.getClass());
//        System.out.println("bootClassLoad.getClass() = " + bootClassLoad.getClass());

        String s = "";

        // 双亲委派
        // 类的全限定名+类加载器 -> 确定类的唯一性

        // 打破双亲委派
        // TODO

    }

}
