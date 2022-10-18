package com.bruce.java8.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author rcy
 * @version 1.1.0
 * @className: NumberRegex
 * @date 2022-10-18
 */
public class NumberRegex {

    public static void main(String[] args) {
        // \A-匹配字符串的开头
        // (?=.*\d)检查是否至少有一位数字（因为基本上所有的数字都是可选的）
        // (?!(?:\D*\d){3,})检查不超过2位数字
        // \d{0,1}匹配最多1位整数部分
        // (?:\.\d{1,1})?最多匹配小数点后1位
        // \z匹配字符串的结尾
        String regex = "\\A(?=.*\\d)(?!(?:\\D*\\d){3,})\\d{0,1}(?:\\.\\d{1,1})?\\z";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher_0 = pattern.matcher(String.valueOf(0));
        Matcher matcher_01 = pattern.matcher(String.valueOf(0.1));
        Matcher matcher_1 = pattern.matcher(String.valueOf(1));
        Matcher matcher_9 = pattern.matcher(String.valueOf(9));
        Matcher matcher_10 = pattern.matcher(String.valueOf(10));
        Matcher matcher_95 = pattern.matcher(String.valueOf(9.5));
        Matcher matcher_955 = pattern.matcher(String.valueOf(9.55));

        boolean matcher_0_b = matcher_0.find();
        boolean matcher_01_b = matcher_01.find();
        boolean matcher_1_b = matcher_1.find();
        boolean matcher_9_b = matcher_9.find();
        boolean matcher_10_b = matcher_10.find();
        boolean matcher_95_b = matcher_95.find();
        boolean matcher_955_b = matcher_955.find();

        System.out.println("matcher_0_b = " + matcher_0_b);
        System.out.println("matcher_01_b = " + matcher_01_b);
        System.out.println("matcher_1_b = " + matcher_1_b);
        System.out.println("matcher_9_b = " + matcher_9_b);
        System.out.println("matcher_10_b = " + matcher_10_b);
        System.out.println("matcher_95_b = " + matcher_95_b);
        System.out.println("matcher_955_b = " + matcher_955_b);
    }

}
