package com.github.fastxml.benchmark.performance;

import com.github.fastxml.FastXmlFactory;
import com.github.fastxml.FastXmlParser;
import com.github.fastxml.benchmark.Debug;
import com.github.fastxml.benchmark.model.Person;
import com.github.fastxml.exception.NumberFormatException;
import com.github.fastxml.exception.ParseException;

import java.io.ByteArrayInputStream;

/**
 * Created by weager on 2016/07/07.
 */
public class FastXmlPerfTest4InputStream extends PerfTestSupport implements PerfTest {

    private final byte[] database = "database".getBytes();
    private final byte[] person = "person".getBytes();
    private final byte[] id = "id".getBytes();
    private final byte[] name = "name".getBytes();
    private final byte[] email = "email".getBytes();
    private final byte[] phone = "phone".getBytes();
    private final byte[] address = "address".getBytes();
    private final byte[] city = "city".getBytes();
    private final byte[] state = "state".getBytes();
    private final byte[] zip = "zip".getBytes();
    private final byte[] country = "country".getBytes();
    private final byte[] line1 = "line1".getBytes();
    private final byte[] line2 = "line2".getBytes();


    public long test(byte[] ba, int totalNumber, int fileLength) {
        try {

            ByteArrayInputStream bais = new ByteArrayInputStream(ba);
            parseXml2PersionObject(bais);

            long lt = 0;
            for (int j = 0;j<10;j++){
                long a = System.currentTimeMillis();
                for (int i = 0; i < totalNumber; i++)
                {
                    parseXml2PersionObject(bais);
                }
                long l2 = System.currentTimeMillis();
                lt = lt + (l2 - a);
            }
            return lt;

        } catch (Exception e) {
            System.out.println("exception ==> " + e);
        }
        return 0L;
    }

    public void parseXml2PersionObject(ByteArrayInputStream bais) throws ParseException, NumberFormatException {
        bais.reset();
        FastXmlParser parser = FastXmlFactory.newInstance(bais);
        int event = 0;
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
                if(Debug.printInfo) {
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
    }

    public String getCaseName() {
        return "***FastXml***(for huge files)";
    }
}
