package com.github.fastxml.benchmark.memory;

import com.github.fastxml.benchmark.Debug;
import com.github.fastxml.benchmark.model.Person;
import com.github.fastxml.benchmark.utils.FileLoaderUtils;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;

/**
 * Created by weager on 2016/07/07.
 */
public class DomMemoryTest{
    static Runtime rt;

    /**
     VM options:
        -server -Xms128m

     OUTPUT:
         file length: 17367391
         Memory Use: 144.77722 MB.
         Multiplying factor: 8.741089
         Time Use: 1050
     */
    public static void main(String[] args) {
        try {
            rt = Runtime.getRuntime();
            byte[] ba = FileLoaderUtils.loadClasspathFile("address-big.xml");
            int length = ba.length;
            System.out.println("file length: " + length);
            long startMem = rt.totalMemory() - rt.freeMemory();
            long startTime = System.currentTimeMillis();
            DocumentBuilderFactory factory =
                    DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(false);
            factory.setExpandEntityReferences(false);
            factory.setIgnoringComments(true);
            factory.setValidating(false);

            DocumentBuilder parser = factory.newDocumentBuilder();
            ByteArrayInputStream bais = new ByteArrayInputStream(ba);
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
                                for(int j = 0; j < len4PersonField; j++){
                                    Node personField = personFields.item(j);
                                    if("name".equals(personField.getNodeName())){
                                        p.setName(personField.getTextContent());
                                    }else if("email".equals(personField.getNodeName())){
                                        p.setEmail(personField.getTextContent());
                                    }else if("phone".equals(personField.getNodeName())){
                                        p.setPhone(personField.getTextContent());
                                    }else if("address".equals(personField.getNodeName())){
                                        NamedNodeMap addressAttrs = personField.getAttributes();
                                        if(addressAttrs != null && addressAttrs.getLength() > 0){
                                            int len4AddressAttr = addressAttrs.getLength();
                                            for(int x = 0; x < len4AddressAttr; x++){
                                                Node addressAttr = addressAttrs.item(x);
                                                if("city".equals(addressAttr.getNodeName())){
                                                    p.setCity(addressAttr.getNodeValue());
                                                }else if("state".equals(addressAttr.getNodeName())){
                                                    p.setState(Integer.valueOf(addressAttr.getNodeValue()));
                                                }else if("zip".equals(addressAttr.getNodeName())){
                                                    p.setZip(addressAttr.getNodeValue());
                                                }else if("country".equals(addressAttr.getNodeName())){
                                                    p.setCountry(addressAttr.getNodeValue());
                                                }
                                            }
                                        }
                                        if(personField.hasChildNodes()){
                                            NodeList lines = personField.getChildNodes();
                                            for(int y = 0; y < lines.getLength(); y++){
                                                Node line = lines.item(y);
                                                if("line1".equals(line.getNodeName())){
                                                    p.setLine1(line.getTextContent());
                                                }else if("line2".equals(line.getNodeName())){
                                                    p.setLine2(line.getTextContent().trim());
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
            long endMem = rt.totalMemory() - rt.freeMemory();
            System.out.println("Memory Use: " + ((float) endMem - startMem) / (1 << 20) + " MB.");
            System.out.println("Multiplying factor: " + ((float) endMem - startMem) / length);
            System.out.println("Time Use: " + (System.currentTimeMillis() - startTime));
        } catch (Exception e) {
            System.out.println("exception ==> " + e);
        }
    }
}
