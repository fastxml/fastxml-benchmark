package com.github.fastxml.benchmark.memory;

import com.github.fastxml.benchmark.Debug;
import com.github.fastxml.benchmark.model.Person;
import com.ximpleware.NavException;
import com.ximpleware.ParseException;
import com.ximpleware.VTDGen;
import com.ximpleware.VTDNav;
import com.github.fastxml.benchmark.utils.FileLoaderUtils;

import java.io.IOException;

/**
 * Created by weager on 2016/07/07.
 */
public class VTDMemoryTest {
    static Runtime rt;

    /**
     VM options:
        -server -Xms128m

     OUTPUT:
         file length: 17367391
         Memory Use: 32.230522 MB.
         Multiplying factor: 1.9459543
         Time Use: 651
     */
    public static void main(String[] args) throws NavException {
        try{
            rt = Runtime.getRuntime();
            byte[] ba = FileLoaderUtils.loadClasspathFile("address-big.xml");
            int length = ba.length;
            System.out.println("file length: " + length);
            long startMem = rt.totalMemory() - rt.freeMemory();
            long startTime = System.currentTimeMillis();
            VTDGen vg = new VTDGen();
            vg.setDoc(ba);
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
                                p.setLine2(nav.toString(nav.getText()).trim());
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

            long endMem = rt.totalMemory() - rt.freeMemory();
            System.out.println("Memory Use: " + ((float)endMem - startMem)/(1<<20) + " MB.");
            System.out.println("Multiplying factor: " + ((float) endMem - startMem)/length );
            System.out.println("Time Use: " + (System.currentTimeMillis() - startTime));

        }
        catch (ParseException e){
            System.out.println(" Not wellformed -->" +e);
        }
        catch (IOException e){
            System.out.println(" io exception ");
        }
    }

}
