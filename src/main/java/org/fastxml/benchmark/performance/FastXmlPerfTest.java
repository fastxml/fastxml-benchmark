package org.fastxml.benchmark.performance;

import org.fastxml.FastXmlFactory;
import org.fastxml.FastXmlParser;
import org.fastxml.benchmark.Debug;
import org.fastxml.benchmark.model.Person;
import org.fastxml.benchmark.utils.FileLoaderUtils;
import org.fastxml.exception.NumberFormatException;
import org.fastxml.exception.ParseException;

import java.io.InputStream;

/**
 * Created by weager on 2016/07/07.
 */
public class FastXmlPerfTest {

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

    /**
     VM options:
        -server -Xms128m

     OUTPUT:
         total is 40000
         file length: 1515
         average parsing time ==> 0.009535
         performance ==> 151.6867810346949


         total is 1200
         file length: 41292
         average parsing time ==> 0.24883333
         performance ==> 158.57363707267197


         total is 5
         file length: 17367391
         average parsing time ==> 111.1
         performance ==> 149.2147273845501
     */
    public static void main(String[] args) {
        test("../../../../address-small.xml");
        test("../../../../address-middle.xml");
        test("../../../../address-big.xml");
    }
    public static void test(String fileName){
        try {
            byte[] ba = FileLoaderUtils.loadClasspathFile("address-big.xml");
            int fl = ba.length;

            int total;
            if (fl < 1000)
                total = 80000;
            else if (fl < 3000)
                total = 40000;
            else if (fl < 6000)
                total = 8000;
            else if (fl < 15000)
                total = 3200;
            else if (fl < 30000)
                total = 2000;
            else if (fl < 60000)
                total = 1200;
            else if (fl < 120000)
                total = 300;
            else if (fl < 500000)
                total = 100;
            else if (fl < 2000000)
                total = 40;
            else
                total = 5;
            System.out.println("total is " + total);
            System.out.println("file length: " + fl);

            parseXml2PersionObject(ba);

            long lt = 0;
            for (int j = 0;j<10;j++){
                long a = System.currentTimeMillis();
                for(int i=0;i<total;i++)
                {
                    parseXml2PersionObject(ba);
                }
                long l2 = System.currentTimeMillis();
                lt = lt + (l2 - a);
            }
            System.out.println(" average parsing time ==> "+
                    ((float)(lt)/total/10));
            System.out.println(" performance ==> "+
                    ( ((double)fl *1000 * total)/((lt/10)*(1<<20))) + "\n\n");

        } catch (Exception e) {
            System.out.println("exception ==> " + e);
        }
    }

    public static void parseXml2PersionObject(byte[] ba) throws ParseException, NumberFormatException {
        FastXmlParser parser = FastXmlFactory.newInstance(ba);
        int event = 0;
        if (parser.next() == FastXmlParser.START_DOCUMENT && parser.next() == FastXmlParser.START_TAG && parser.isMatch(database)) {
            Person p = new Person();
            while (parser.next() == FastXmlParser.START_TAG && parser.isMatch(person)) {
                if (parser.next() == FastXmlParser.ATTRIBUTE_NAME && parser.isMatch(id) && parser.next() == FastXmlParser.ATTRIBUTE_VALUE) {
                    p.setId(parser.getLong());
                }
                if (parser.next() == FastXmlParser.START_TAG && parser.isMatch(name) && parser.next() == FastXmlParser.TEXT) {
                    p.setName(parser.getString(true));
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
                        p.setLine1(parser.getString(true));
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


}
