package org.fastxml.benchmark.performance;

import org.fastxml.benchmark.Debug;
import org.fastxml.benchmark.model.Person;
import com.ximpleware.NavException;
import com.ximpleware.ParseException;
import com.ximpleware.VTDGen;
import com.ximpleware.VTDNav;
import org.fastxml.benchmark.utils.FileLoaderUtils;

import java.io.InputStream;

/**
 * Created by weager on 2016/07/07.
 */
public class VTDPerfTest{

    /**
     VM options:
        -server -Xms128m

     OUTPUT:
         total is 40000
         file length: 1515
         average parsing time ==> 0.0149875
         performance ==> 96.48190913892947


         total is 1200
         file length: 41292
         average parsing time ==> 0.38441667
         performance ==> 102.50530118797451


         total is 5
         file length: 17367391
         average parsing time ==> 171.72
         performance ==> 96.52001596553065
     */
    public static void main(String[] args) {
        test("address-small.xml");
        test("address-middle.xml");
        test("address-big.xml");
    }
    public static void test(String fileName){
        try {
            byte[] ba = FileLoaderUtils.loadClasspathFile(fileName);
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

            VTDGen vg = new VTDGen();

            parseXml2PersionObject(ba, vg);

            long lt = 0;
            for (int j = 0;j<10;j++){
                long a = System.currentTimeMillis();
                for(int i=0;i<total;i++)
                {
                    parseXml2PersionObject(ba, vg);
                }
                long l2 = System.currentTimeMillis();
                lt = lt + (l2 - a);
            }
            System.out.println(" average parsing time ==> "+
                    ((float)(lt)/total/10));
            System.out.println(" performance ==> "+
                    ( ((double)fl *1000 * total)/((lt/10)*(1<<20))) + "\n\n");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void parseXml2PersionObject(byte[] ba, VTDGen vg) throws NavException {
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

}
