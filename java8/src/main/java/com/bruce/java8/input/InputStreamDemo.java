package com.bruce.java8.input;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author rcy
 * @version 1.0.0
 * @className: InputStreamDemo
 * @date 2022-04-19
 */
public class InputStreamDemo {

    public static void main(String[] args) throws Exception {
        // 第一种方式
        File file1 = new File("hello.txt");
        FileInputStream in1 = new FileInputStream(file1);

        // 第二种方式
        File file2 = new File("hello.txt");
        FileInputStream in2 = new FileInputStream(file2);
        // 用指定的编码格式读取字节流用，跟inputStream配合使用
        InputStreamReader inReader = new InputStreamReader(in2, StandardCharsets.UTF_8);
        BufferedReader bufReader2 = new BufferedReader(inReader);

        // 第三种方式
        File file3 = new File("hello.txt");
        FileReader fileReader = new FileReader(file3);
        BufferedReader bufReader3 = new BufferedReader(fileReader);

    }


}
