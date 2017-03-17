package com.github.fastxml.benchmark.memory;

import com.github.fastxml.FastXmlFactory;
import com.github.fastxml.FastXmlParser;
import com.github.fastxml.benchmark.Debug;
import com.github.fastxml.benchmark.model.Person;
import com.github.fastxml.benchmark.utils.FileLoaderUtils;

import java.io.InputStream;

/**
 * Created by weager on 2016/07/07.
 */
public class FastXmlMemoryTest4InputStream {
    private final static byte[] database = "database".getBytes();
    private final static byte[] person = "person".getBytes();
    private final static byte[] id = "id".getBytes();
    private final static byte[] name = "name".getBytes();
    private final static byte[] email = "email".getBytes();
    private final static byte[] phone = "phone".getBytes();
    private final static byte[] address = "address".getBytes();
    private final static byte[] city = "city".getBytes();
    private final static byte[] state = "state".getBytes();
    private final static byte[] zip = "zip".getBytes();
    private final static byte[] country = "country".getBytes();
    private final static byte[] line1 = "line1".getBytes();
    private final static byte[] line2 = "line2".getBytes();
    static Runtime rt;

    /**
     * VM options:
     * -server -Xms128m
     * <p>
     * OUTPUT:
     * file length: 17880651
     * Memory Use: 17.001877 MB.
     * Multiplying factor: 0.99704194
     * Time Use: 333
     */
    public static void main(String[] args) {
        try {
            rt = Runtime.getRuntime();
            InputStream inputStream = FileLoaderUtils.getInputStream("address-big.xml");
            int length = inputStream.available();
            System.out.println("file length: " + length);
            long startMem = rt.totalMemory() - rt.freeMemory();
            long startTime = System.currentTimeMillis();
            FastXmlParser parser = FastXmlFactory.newInstance(inputStream);
            if (parser.next() == FastXmlParser.START_DOCUMENT && parser.next() == FastXmlParser.START_TAG && parser.isMatch(database)) {
                Person p = new Person();
                while (parser.next() == FastXmlParser.START_TAG && parser.isMatch(person)) {
                    if (parser.next() == FastXmlParser.ATTRIBUTE_NAME && parser.isMatch(id) && parser.next() == FastXmlParser.ATTRIBUTE_VALUE) {
                        p.setId(parser.getLong());
                    }
                    if (parser.next() == FastXmlParser.START_TAG && parser.isMatch(name) && parser.next() == FastXmlParser.TEXT) {
                        p.setName(parser.getStringWithDecoding());
                        parser.next();
                    }
                    if (parser.next() == FastXmlParser.START_TAG && parser.isMatch(email) && parser.next() == FastXmlParser.TEXT) {
                        p.setEmail(parser.getString());
                        parser.next();
                    }
                    if (parser.next() == FastXmlParser.START_TAG && parser.isMatch(phone) && parser.next() == FastXmlParser.TEXT) {
                        p.setPhone(parser.getString());
                        parser.next();
                    }
                    if (parser.next() == FastXmlParser.START_TAG && parser.isMatch(address)) {
                        if (parser.next() == FastXmlParser.ATTRIBUTE_NAME && parser.isMatch(city)) {
                            parser.next();
                            p.setCity(parser.getString());
                        }
                        if (parser.next() == FastXmlParser.ATTRIBUTE_NAME && parser.isMatch(state)) {
                            parser.next();
                            p.setState(parser.getInt());
                        }
                        if (parser.next() == FastXmlParser.ATTRIBUTE_NAME && parser.isMatch(zip)) {
                            parser.next();
                            p.setZip(parser.getString());
                        }
                        if (parser.next() == FastXmlParser.ATTRIBUTE_NAME && parser.isMatch(country)) {
                            parser.next();
                            p.setCountry(parser.getString());
                        }
                        if (parser.next() == FastXmlParser.START_TAG && parser.isMatch(line1)) {
                            parser.next();
                            p.setLine1(parser.getStringWithDecoding());
                            parser.next();
                        }
                        if (parser.next() == FastXmlParser.START_TAG && parser.isMatch(line2)) {
                            parser.next();
                            p.setLine2(parser.getString());
                            parser.next();
                        }
                        parser.next();
                    }
                    parser.next();
                    if (Debug.printInfo) {
                        System.out.println("id: " + p.getId());
                        System.out.println("name: " + p.getName());
                        System.out.println("email: " + p.getEmail());
                        System.out.println("phone: " + p.getPhone());
                        System.out.println("city: " + p.getCity());
                        System.out.println("state: " + p.getState());
                        System.out.println("zip: " + p.getZip());
                        System.out.println("country: " + p.getCountry());
                        System.out.println("line1: " + p.getLine1());
                        System.out.println("line2: " + p.getLine2());
                    }
                }
            }

            // do nothing!
            long endMem = rt.totalMemory() - rt.freeMemory();
            System.out.println("Memory Use: " + ((float) endMem - startMem) / (1 << 20) + " MB.");
            System.out.println("Multiplying factor: " + ((float) endMem - startMem) / length);
            System.out.println("Time Use: " + (System.currentTimeMillis() - startTime));
        } catch (Exception e) {
            System.out.println("exception ==> " + e);
        }
    }
}
