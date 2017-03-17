package com.github.fastxml.benchmark.performance;

import com.github.fastxml.benchmark.Debug;
import com.github.fastxml.benchmark.model.Person;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Created by weager on 2016/07/09.
 */
public class XmlPullPerfTest extends PerfTestSupport implements PerfTest {

    public long test(byte[] ba, int totalNumber, int fileLength) {
        try {

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
                for (int i = 0; i < totalNumber; i++) {
                    parseXml2PersionObject(xpp, bais);
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

    public void parseXml2PersionObject(XmlPullParser xpp, ByteArrayInputStream bais) throws XmlPullParserException, IOException {
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

    public String getCaseName() {
        return "XmlPull";
    }
}
