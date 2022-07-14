package com.bruce.jvm.command;

/**
 * PrintFlagsFinal
 * 查看JVM虚拟机的所有参数
 *
 * java -XX:+PrintFlagsFinal -version > flags.txt
 *
 * 使用该指令可查看JVM虚拟机的所有参数
 *
 * 值得注意的是"="表示默认值，":="表示被用户或JVM修改后的值
 * 要想查看某个进程具体参数的值，可以使用jinfo，这块后面聊
 * 一般要设置参数，可以先查看一下当前参数是什么，然后进行修改
 *
 */
public class PrintFlagsFinalDemo {

    public static void main(String[] args) {
        System.out.println("Hello World!");
    }

}
