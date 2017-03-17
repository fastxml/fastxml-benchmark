package com.github.fastxml.benchmark.utils;

/**
 * Created by weager on 2017/02/12.
 */
public class MathUtils {
    public static double toShortFormat(double n) {
        long l1 = Math.round(n * 10000);
        double ret = l1 / 10000.0;
        return ret;
    }
}
