package utils;

import java.util.Objects;

/**
 * @author rcy
 * @data 2022-01-04 22:09
 * @description TODO
 */
public class MoneyCalculate {

    public static void main(String[] args) {
        System.out.println("相等：" + calculate(100000001, 100000000));
        System.out.println("大于1：" + calculate(1000000010, 100000000));
        System.out.println("大于2：" + calculate(1000000001, 100000000));
        System.out.println("小于1：" + calculate(10000001, 100000000));
        System.out.println("小于1：" + calculate(10000010, 100000000));
    }

    /**
     * 24÷8=3，其中24是被除数，公式是被除数÷除数=商……余数。
     *
     * @param dividend ： 被除数（分子）
     * @param divisor  ：除数（分母）100000000
     * @return
     */
    public static double calculate(long dividend, long divisor) {
        if (dividend % divisor == 0) {
            return dividend / divisor;
        }
        // 被乘数
        int multiplicand = dividend < 0 ? -1 : 1;

        // 被除数（分子）
        String dividendStr = String.valueOf(dividend);
        // 除数（分母）
        String divisorStr = String.valueOf(divisor);

        String str;
        if (dividendStr.length() >= divisorStr.length()) {
            int middleIndex = dividendStr.length() - divisorStr.length() + 1;
            str = dividendStr.substring(0, middleIndex) + "." + dividendStr.substring(middleIndex);
        } else {
            StringBuilder sb = new StringBuilder("0.");
            for (int i = 0; i < (divisorStr.length() - dividendStr.length()); i++) {
                sb.append("0");
            }
            sb.append(dividendStr);
            str = sb.toString();
        }

        char[] chars = str.toCharArray();
        int nonzeroLastIndex = 0;
        for (int i = chars.length - 1; i > 0; i--) {
            if (!Objects.equals("0", chars[i])) {
                nonzeroLastIndex = i;
                break;
            }
        }
        
        if (nonzeroLastIndex == chars.length - 1) {
            return multiplicand * Double.parseDouble(str);
        } else {
            return multiplicand * Double.parseDouble(str.substring(0, nonzeroLastIndex + 1));
        }
    }

}
