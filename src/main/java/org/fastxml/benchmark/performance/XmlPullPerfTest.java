package org.fastxml.benchmark.performance;

import org.fastxml.benchmark.Debug;
import org.fastxml.benchmark.model.Person;
import org.fastxml.benchmark.utils.FileLoaderUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by weager on 2016/07/09.
 */
public class XmlPullPerfTest {

    /**
     VM options:
        -server -Xms128m

     OUTPUT:
         total is 40000
         file length: 1515
         average parsing time ==> 0.0125675
         performance ==> 115.12482783708914


         total is 1200
         file length: 41292
         average parsing time ==> 0.31741667
         performance ==> 124.35511538856908


         total is 5
         file length: 17367391
         average parsing time ==> 143.95999
         performance ==> 115.17965743869999
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

            ByteArrayInputStream bais = new ByteArrayInputStream(ba);

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance(
                    System.getProperty(XmlPullParserFactory.PROPERTY_NAME), null);
            factory.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            factory.setFeature(XmlPullParser.FEATURE_VALIDATION, false);
            factory.setFeature(XmlPullParser.FEATURE_PROCESS_DOCDECL, false);
            XmlPullParser xpp = factory.newPullParser();

            parseXml2PersionObject(xpp, bais);

            long lt = 0;
            for (int j = 0; j < 10; j++) {
                long a = System.currentTimeMillis();
                for (int i = 0; i < total; i++) {
                    parseXml2PersionObject(xpp, bais);
                }
                long l2 = System.currentTimeMillis();
                lt = lt + (l2 - a);
            }
            System.out.println(" average parsing time ==> " +
                    ((float) (lt) / total / 10));
            System.out.println(" performance ==> " +
                    (((double) fl * 1000 * total) / ((lt / 10) * (1 << 20))) + "\n\n");

        } catch (Exception e) {
            System.out.println("exception ==> " + e);
        }
    }

    public static void parseXml2PersionObject(XmlPullParser xpp, ByteArrayInputStream bais) throws XmlPullParserException, IOException {
        bais.reset();
        xpp.setInput(bais, null);
        int eventType = xpp.nextTag();
        if (eventType == XmlPullParser.START_TAG && "database".equals(xpp.getName())) {
            Person p = new Person();
            do {
                eventType = xpp.nextTag();
                if (eventType == XmlPullParser.START_TAG && "person".equals(xpp.getName())) {
                    if (xpp.getAttributeCount() > 0 && "id".equals(xpp.getAttributeName(0))) {
                        p.setId(Long.valueOf(xpp.getAttributeValue(0)));
                    }
                    if (xpp.nextTag() == XmlPullParser.START_TAG && "name".equals(xpp.getName()) && xpp.nextToken() == XmlPullParser.TEXT) {
                        p.setName(xpp.getText());
                        xpp.nextTag();
                    }
                    if (xpp.nextTag() == XmlPullParser.START_TAG && "email".equals(xpp.getName()) && xpp.nextToken() == XmlPullParser.TEXT) {
                        p.setEmail(xpp.getText());
                        xpp.nextTag();
                    }
                    if (xpp.nextTag() == XmlPullParser.START_TAG && "phone".equals(xpp.getName()) && xpp.nextToken() == XmlPullParser.TEXT) {
                        p.setPhone(xpp.getText());
                        xpp.nextTag();
                    }
                    if (xpp.nextTag() == XmlPullParser.START_TAG && "address".equals(xpp.getName())) {
                        if (xpp.getAttributeCount() > 0) {
                            if ("city".equals(xpp.getAttributeName(0))) {
                                p.setCity(xpp.getAttributeValue(0));
                            }
                            if ("state".equals(xpp.getAttributeName(1))) {
                                p.setState(Integer.valueOf(xpp.getAttributeValue(1)));
                            }
                            if ("zip".equals(xpp.getAttributeName(2))) {
                                p.setZip(xpp.getAttributeValue(2));
                            }
                            if ("country".equals(xpp.getAttributeName(3))) {
                                p.setCountry(xpp.getAttributeValue(3));
                            }
                        }
                        if (xpp.nextTag() == XmlPullParser.START_TAG && "line1".equals(xpp.getName()) && xpp.nextToken() == XmlPullParser.CDSECT) {
                            p.setLine1(xpp.getText());
                            xpp.nextTag();
                        }
                        if (xpp.nextTag() == XmlPullParser.START_TAG && "line2".equals(xpp.getName()) && xpp.nextToken() == XmlPullParser.TEXT) {
                            p.setLine2(xpp.getText());
                            xpp.nextTag();
                        }
                        xpp.nextTag();
                    }
                    xpp.nextTag();
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
            } while (xpp.getEventType() == XmlPullParser.END_TAG && "person".equals(xpp.getName()));
        }

    }
}
