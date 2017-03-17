package com.github.fastxml.benchmark.performance;

import com.github.fastxml.benchmark.Debug;
import com.github.fastxml.benchmark.model.Person;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Created by weager on 2016/07/07.
 */
public class DomPerfTest extends PerfTestSupport implements PerfTest {

    public long test(byte[] ba, int totalNumber, int fileLength) {
        try {
            DocumentBuilderFactory factory =
                    DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(false);
            factory.setExpandEntityReferences(false);
            factory.setIgnoringComments(true);
            factory.setValidating(false);

            DocumentBuilder parser = factory.newDocumentBuilder();
            ByteArrayInputStream bais = new ByteArrayInputStream(ba);
            parseXml2PersionObject(parser, bais);

            long lt = 0;
            for (int j = 0;j<10;j++){
                long a = System.currentTimeMillis();
                for (int i = 0; i < totalNumber; i++)
                {
                    parseXml2PersionObject(parser, bais);
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

    public void parseXml2PersionObject(DocumentBuilder parser, ByteArrayInputStream bais) throws IOException, SAXException {
        bais.reset();
        Document doc = parser.parse(bais);

        Element documentElement = doc.getDocumentElement();
        if("database".equals(documentElement.getTagName())){
            NodeList childNodes = documentElement.getChildNodes();
            if(childNodes != null && childNodes.getLength() > 0){
                int len = childNodes.getLength();
                Person p = new Person();
                for(int i = 0; i < len; i++){
                    Node item = childNodes.item(i);
                    if("person".equals(item.getNodeName())){
                        NamedNodeMap attributes = item.getAttributes();
                        if(attributes != null && attributes.getLength() > 0){
                            Node id = attributes.getNamedItem("id");
                            if(id != null){
                                p.setId(Long.valueOf(id.getNodeValue()));
                            }
                        }
                        NodeList personFields = item.getChildNodes();
                        if(personFields != null && personFields.getLength() > 0){
                            int len4PersonField = personFields.getLength();
                            boolean nameParsed = false;
                            boolean emailParsed = false;
                            boolean phoneParsed = false;
                            for(int j = 0; j < len4PersonField; j++){
                                Node personField = personFields.item(j);
                                if(!nameParsed && "name".equals(personField.getNodeName())){
                                    p.setName(personField.getTextContent());
                                    nameParsed = true;
                                }else if(!emailParsed && "email".equals(personField.getNodeName())){
                                    p.setEmail(personField.getTextContent());
                                    emailParsed = true;
                                }else if(!phoneParsed && "phone".equals(personField.getNodeName())){
                                    p.setPhone(personField.getTextContent());
                                    phoneParsed = true;
                                }else if("address".equals(personField.getNodeName())){
                                    NamedNodeMap addressAttrs = personField.getAttributes();
                                    if(addressAttrs != null && addressAttrs.getLength() > 0){
                                        int len4AddressAttr = addressAttrs.getLength();
                                        boolean cityParsed = false;
                                        boolean stateParsed = false;
                                        boolean zipParsed = false;
                                        for(int x = 0; x < len4AddressAttr; x++){
                                            Node addressAttr = addressAttrs.item(x);
                                            if(!cityParsed && "city".equals(addressAttr.getNodeName())){
                                                p.setCity(addressAttr.getNodeValue());
                                                cityParsed = true;
                                            }else if(!stateParsed && "state".equals(addressAttr.getNodeName())){
                                                p.setState(Integer.valueOf(addressAttr.getNodeValue()));
                                                stateParsed = true;
                                            }else if(!zipParsed && "zip".equals(addressAttr.getNodeName())){
                                                p.setZip(addressAttr.getNodeValue());
                                                zipParsed = true;
                                            }else if("country".equals(addressAttr.getNodeName())){
                                                p.setCountry(addressAttr.getNodeValue());
                                            }
                                        }
                                    }
                                    if(personField.hasChildNodes()){
                                        NodeList lines = personField.getChildNodes();
                                        boolean line1Parsed = false;
                                        for(int y = 0; y < lines.getLength(); y++){
                                            Node line = lines.item(y);
                                            if(!line1Parsed && "line1".equals(line.getNodeName())){
                                                p.setLine1(line.getTextContent());
                                                line1Parsed = true;
                                            }else if("line2".equals(line.getNodeName())){
                                                p.setLine2(line.getTextContent());
                                            }
                                        }
                                    }
                                }
                            }
                        }
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
    }

    public String getCaseName() {
        return "javax.xml.parsers.DocumentBuilder";
    }
}
