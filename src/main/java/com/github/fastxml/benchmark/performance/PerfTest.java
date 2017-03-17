package com.github.fastxml.benchmark.performance;

/**
 * test the performance for a file
 * Created by weager on 2017/02/12.
 */
public interface PerfTest {
    /**
     * performance test
     *
     * @param bytes       file content
     * @param totalNumber total number of times
     * @param fileLength
     * @return total time
     */
    long test(byte[] bytes, int totalNumber, int fileLength);

    String getCaseName();
}
