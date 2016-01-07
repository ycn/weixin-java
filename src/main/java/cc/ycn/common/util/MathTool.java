package cc.ycn.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by andy on 11/13/15.
 */
public final class MathTool {

    // 高精度加法
    public static BigDecimal add(double a, double b) {
        BigDecimal bA = new BigDecimal(String.valueOf(a));
        BigDecimal bB = new BigDecimal(String.valueOf(b));
        return bA.add(bB);
    }

    // 高精度减法
    public static BigDecimal subtract(double a, double b) {
        BigDecimal bA = new BigDecimal(String.valueOf(a));
        BigDecimal bB = new BigDecimal(String.valueOf(b));
        return bA.subtract(bB);
    }

    // 高精度乘法
    public static BigDecimal multiply(double a, double b) {
        BigDecimal bA = new BigDecimal(String.valueOf(a));
        BigDecimal bB = new BigDecimal(String.valueOf(b));
        return bA.multiply(bB);
    }

    // 高精度除法 (保留2位小数)
    public static BigDecimal divide(double a, double b) {
        BigDecimal bA = new BigDecimal(String.valueOf(a));
        BigDecimal bB = new BigDecimal(String.valueOf(b));
        return bA.divide(bB, 2, RoundingMode.DOWN);
    }

    // 高精度加法
    public static BigDecimal add(String a, String b) {
        BigDecimal bA = new BigDecimal(a);
        BigDecimal bB = new BigDecimal(b);
        return bA.add(bB);
    }

    // 高精度减法
    public static BigDecimal subtract(String a, String b) {
        BigDecimal bA = new BigDecimal(a);
        BigDecimal bB = new BigDecimal(b);
        return bA.subtract(bB);
    }

    // 高精度乘法
    public static BigDecimal multiply(String a, String b) {
        BigDecimal bA = new BigDecimal(a);
        BigDecimal bB = new BigDecimal(b);
        return bA.multiply(bB);
    }

    // 高精度除法 (保留2位小数)
    public static BigDecimal divide(String a, String b) {
        BigDecimal bA = new BigDecimal(a);
        BigDecimal bB = new BigDecimal(b);
        return bA.divide(bB, 2, RoundingMode.DOWN);
    }

    public static BigDecimal formattedByScale(int wei,BigDecimal bigDecimal) {
        return bigDecimal.setScale(wei, BigDecimal.ROUND_UP);
    }
}
