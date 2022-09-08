package com.bruce.java8.format;

import java.text.MessageFormat;

/**
 * @author rcy
 * @version 1.1.0
 * @className: MessageFormatDemo
 * @date 2022-09-08
 */
public class MessageFormatDemo {

    /**
     * 记一次 MessageFormat 问题
     * <p>
     * java.text.MessageFormat格式化数字时会出现逗号分隔
     * <p>
     * 总结：使用MessageFormat格式化数字、日期的时候，传入的替换参数最好先转成我们所需要格式的字符串类型变量，
     * 否则容易被MessageFormat格式化成我们预期之外的结果。
     */
    public static void main(String[] args) {
        System.out.println(MessageFormat.format("{0}   {1}", 999, 9999));
        System.out.println(MessageFormat.format("{0}   {1}", new Integer(999).toString(), new Integer(9999).toString()));
        System.out.println(MessageFormat.format("{0}   {1}   {2}", 9999.999, 99999.9999, 99999.4444444));
    }

    //输出
    //999   9,999
    //999   9999
    //9,999.999   100,000   99,999.444

}
