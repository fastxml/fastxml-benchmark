package com.github.fastxml.benchmark.performance;

import com.github.fastxml.benchmark.utils.FileLoaderUtils;
import com.github.fastxml.benchmark.utils.MathUtils;

import java.io.IOException;

/**
 * Created by weager on 2017/02/12.
 */
public class PerfTestMain {
    /**
     * VM options: -server -Xms128m
     */
    public static void main(String[] args) throws IOException {

        System.out.println("_Performance = (fileLength *1000 * totalNumber)/((totalTime/10) * (1<<20)))_\n");

        PerfTestMain perfTest = new PerfTestMain();
        PerfTest[] perfTests = perfTest.generate();
        perfTest.test("address-small.xml", perfTests);
        perfTest.test("address-middle.xml", perfTests);
        perfTest.test("address-big.xml", perfTests);
    }

    private void test(String fileName, PerfTest[] perfTests) throws IOException {
        byte[] ba = FileLoaderUtils.loadClasspathFile(fileName);
        int fileLength = ba.length;

        int totalNumber;
        if (fileLength < 1000)
            totalNumber = 80000;
        else if (fileLength < 3000)
            totalNumber = 40000;
        else if (fileLength < 6000)
            totalNumber = 8000;
        else if (fileLength < 15000)
            totalNumber = 3200;
        else if (fileLength < 30000)
            totalNumber = 2000;
        else if (fileLength < 60000)
            totalNumber = 1200;
        else if (fileLength < 120000)
            totalNumber = 300;
        else if (fileLength < 500000)
            totalNumber = 100;
        else if (fileLength < 2000000)
            totalNumber = 40;
        else
            totalNumber = 5;
        System.out.println("***Parse " + fileLength + " bytes file " + totalNumber + " times***\n");
        System.out.println("| Xml Parser | Average parsing time(ms) | Performance |");
        System.out.println("| ---------- | -----------------------: | ----------: |");
        for (PerfTest perfTest : perfTests) {
            long time = perfTest.test(ba, totalNumber, fileLength);
            printStatistic(perfTest.getCaseName(), time, totalNumber, fileLength);
        }
        System.out.println();
    }

    private PerfTest[] generate() {
        return new PerfTest[]{
                new DomPerfTest(),
                new VTDPerfTest(),
                new XmlPullPerfTest(),
                new FastXmlPerfTest4InputStream(),
                new FastXmlPerfTest()};
    }

    private void printStatistic(String caseName, long totalTime, long totalNumber, long fileLength) {
        StringBuilder sb = new StringBuilder();
        sb.append("| " + caseName + " | ");
        sb.append(MathUtils.toShortFormat((float) (totalTime) / totalNumber / 10)); // average parsing time
        sb.append(" | ");
        sb.append(MathUtils.toShortFormat(((double) fileLength * 1000 * totalNumber) / ((totalTime / 10) * (1 << 20)))); // performance
        sb.append(" |");
        System.out.println(sb.toString());
    }

}
