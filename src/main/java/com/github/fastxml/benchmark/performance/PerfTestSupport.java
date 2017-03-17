package com.github.fastxml.benchmark.performance;

import com.github.fastxml.benchmark.utils.MathUtils;

/**
 * Created by weager on 2017/02/12.
 */
public class PerfTestSupport {
    public void printStatistic(long totalTime, long totalNumber, long fileLength) {
        StringBuilder sb = new StringBuilder();
        sb.append("| " + getCaseName() + " | ");
        sb.append(MathUtils.toShortFormat((float) (totalTime) / totalNumber / 10)); // average parsing time
        sb.append(" | ");
        sb.append(MathUtils.toShortFormat(((double) fileLength * 1000 * totalNumber) / ((totalTime / 10) * (1 << 20)))); // performance
        sb.append(" |");
        System.out.println(sb.toString());
    }

    public String getCaseName() {
        return this.getClass().getSimpleName();
    }
}
