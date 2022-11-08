package com.bruce.java8.regex;

import java.util.regex.Pattern;

/**
 * 正则表达式
 * https://www.jb51.net/article/257697.htm
 *
 * @author rcy
 * @version 1.1.0
 * @className: PasswordRegex
 * @date 2022-10-18
 */
public class PasswordRegex {

    /**
     * 密码长度应为6-16位且同时包含字母和数字
     * <p>
     * 分开来注释一下：
     * ^ 匹配一行的开头位置
     * (?![0-9]+$) 预测该位置后面不全是数字
     * (?![a-zA-Z]+$) 预测该位置后面不全是字母
     * [0-9A-Za-z] {8,16} 由8-16位数字或这字母组成
     * $ 匹配行结尾位置
     * <p>
     * 注：(?!xxxx) 是正则表达式的负向零宽断言一种形式，标识预该位置后不是xxxx字符。
     */
    String PASSWORD_REGEX = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";


    /**
     * 假定设置密码时，密码规则为：  字母、数字、特殊符号，至少匹配2种
     * 则密码可能出现的情况有：
     * 1、数字+特殊符号
     * 2、字母+特殊符号
     * 3、字母+数字
     * 4、字母+数字+特殊符号
     * (组合与顺序无关)
     * 解决思路：
     * 1、遍历字符串的字符数组，查看是否包含目标特殊字符，若包含，则标记字符串
     * 包含特殊字符，并替换当前特殊字符为''。
     * 2、判断剩下的字符组成的字符串，是否匹配以下情况
     * - 纯字母
     * - 纯数字
     * - 字母+数字
     * 3、字符串匹配规则
     * 纯字母+包含特殊字符  ---- 匹配通过
     * 纯数字+包含特殊字符 ---- 匹配通过
     * 字母+数字+包含个数字符 ---- 匹配通过
     */
    //特殊字符
    public static final String SPEC_CHARACTERS = "!\"#$%&'()*+,-./:;<=>?@\\]\\[^_`{|}~";
    // 纯字母
    public static final String character = "[a-zA-Z]{1,}$";
    // 纯数字
    public static final String numberic = "[0-9]{1,}$";
    // 字母和数字
    public static final String number_and_character = "((^[a-zA-Z]{1,}[0-9]{1,}[a-zA-Z0-9]*)+)" +
            "|((^[0-9]{1,}[a-zA-Z]{1,}[a-zA-Z0-9]*)+)$";
    // 字母或数字
    public static final String number_or_character = "[a-zA-Z0-9]+$";
    // 字母数字下划线
    public static final String ncw = "\\w+$";

    public static boolean checkPassword(String targetString) {
        String opStr = targetString;
        boolean isLegal = false;
        boolean hasSpecChar = false;
        char[] charArray = opStr.toCharArray();
        for (char c : charArray) {
            if (SPEC_CHARACTERS.contains(String.valueOf(c))) {
                hasSpecChar = true;
                // 替换此字符串
                opStr = opStr.replace(c, ' ');
            }
        }
        String excSpecCharStr = opStr.replace(" ", "");
        boolean isPureNum = Pattern.compile(numberic).matcher(excSpecCharStr).matches();
        boolean isPureChar = Pattern.compile(character).matcher(excSpecCharStr).matches();
        boolean isNumAndChar = Pattern.compile(number_and_character).matcher(excSpecCharStr).matches();
        isLegal = ((isPureNum && hasSpecChar)
                || (isPureChar && hasSpecChar) || isNumAndChar && hasSpecChar) || isNumAndChar;
        System.out.println("字符串：" + targetString + ",是否符合规则：" + isLegal);
        System.out.println("---------------");
        return isLegal;
    }

    public static void main(String[] args) {
        checkPassword("fasdagd");
        checkPassword("41234123");
        checkPassword("#$%^&&*(");
        checkPassword("fasd$$");
        checkPassword("41234%%%");
        checkPassword("fasd41^(324");
        checkPassword("fa413%^&*");
        checkPassword("&%fa413%^&*");
    }

}
