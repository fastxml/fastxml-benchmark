package org.fastxml.benchmark.utils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by weager on 2016/07/12.
 */
public class FileLoaderUtils {

    public static byte[] loadClasspathFile(String fileName) throws IOException {
        return loadClasspathFile(fileName, null);
    }

    public static byte[] loadClasspathFile(String fileName, ClassLoader classLoader) throws IOException {
        if(classLoader == null){
            classLoader = Thread.currentThread().getContextClassLoader();
            if(classLoader == null){
                classLoader = FileLoaderUtils.class.getClassLoader();
            }
        }
        InputStream inputStream = classLoader.getResourceAsStream(fileName);
        int available = inputStream.available();
        byte[] doc = new byte[available];
        inputStream.read(doc);
        inputStream.close();
        return doc;
    }
}
