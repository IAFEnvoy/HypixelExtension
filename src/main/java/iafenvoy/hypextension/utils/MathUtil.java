package iafenvoy.hypextension.utils;

public class MathUtil {
    public static double round(double x, int digit) {
        if (digit < 0) return x;
        double n = Math.pow(10, digit);
        return (double) Math.round(x * n) / n;
    }
}
