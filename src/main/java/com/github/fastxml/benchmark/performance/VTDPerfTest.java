package com.github.fastxml.benchmark.performance;

import com.github.fastxml.benchmark.Debug;
import com.github.fastxml.benchmark.model.Person;
import com.ximpleware.NavException;
import com.ximpleware.ParseException;
import com.ximpleware.VTDGen;
import com.ximpleware.VTDNav;

/**
 * Created by weager on 2016/07/07.
 */
public class VTDPerfTest extends PerfTestSupport implements PerfTest {

    public long test(byte[] ba, int totalNumber, int fileLength) {
        try {

            VTDGen vg = new VTDGen();

            parseXml2PersionObject(ba, vg);

            long lt = 0;
            for (int j = 0;j<10;j++){
                long a = System.currentTimeMillis();
                for (int i = 0; i < totalNumber; i++)
                {
                    parseXml2PersionObject(ba, vg);
                }
                long l2 = System.currentTimeMillis();
                lt = lt + (l2 - a);
            }
            return lt;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0L;
    }

    public void parseXml2PersionObject(byte[] ba, VTDGen vg) throws NavException {
        try{
            vg.setDoc_BR(ba);
            vg.parse(true);
            VTDNav nav = vg.getNav();

            if(nav.matchElement("database")){
                nav.toElement(VTDNav.FC);
                Person p = new Person();
                do{
                    if(nav.hasAttr("id")){
                        p.setId(Long.valueOf(nav.toString(nav.getAttrVal("id"))));
                    }
                    if(nav.toElement(VTDNav.FC, "name")){
                        p.setName(nav.toString(nav.getText()));
                    }

                    if(nav.toElement(VTDNav.NEXT_SIBLING, "email")){
                        p.setEmail(nav.toString(nav.getText()));
                    }

                    if(nav.toElement(VTDNav.NEXT_SIBLING, "phone")){
                        p.setPhone(nav.toString(nav.getText()));
                    }

                    if(nav.toElement(VTDNav.NEXT_SIBLING, "address")){
                        if(nav.getAttrCount() > 0){
                            if(nav.hasAttr("city")){
                                p.setCity(nav.toString(nav.getAttrVal("city")));
                            }

                            if(nav.hasAttr("state")){
                                p.setState(Integer.valueOf(nav.toString(nav.getAttrVal("state"))));
                            }

                            if(nav.hasAttr("zip")){
                                p.setZip(nav.toString(nav.getAttrVal("zip")));
                            }

                            if(nav.hasAttr("country")){
                                p.setCountry(nav.toString(nav.getAttrVal("country")));
                            }

                        }
                        if(nav.toElement(VTDNav.FC, "line1")){
                            p.setLine1(nav.toString(nav.getText()));
                            if(nav.toElement(VTDNav.NEXT_SIBLING, "line2")){
                                p.setLine2(nav.toString(nav.getText()));
                            }
                            nav.toElement(VTDNav.P);
                        }
                    }
                    nav.toElement(VTDNav.P);
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
                }while(nav.toElement(VTDNav.NEXT_SIBLING, "person"));
            }
        }
        catch (ParseException e){
            System.out.println(" Not wellformed -->" +e);
        }
    }

    public String getCaseName() {
        return "VTD-XML";
    }

}
