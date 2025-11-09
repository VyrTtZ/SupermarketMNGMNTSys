package com.example.supermarketmngmntsys;

import com.example.supermarketmngmntsys.mylinkedlist.MyLinkedList;
import java.io.FileReader;
import java.io.FileWriter;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;
//----------------------------------------------------------------------------------------------------------------------
public class Parent {
    static MyLinkedList<Supermarket> markets = new MyLinkedList<Supermarket>();
    //----------------------------------------------------------------------------------------------------------------------
    public Parent(MyLinkedList<Supermarket> m) { //CONSTRUCTOR
        this.markets = m;
    }
    //----------------------------------------------------------------------------------------------------------------------
    public static MyLinkedList<Supermarket> getMarkets() {
        return markets;
    }
    //----------------------------------------------------------------------------------------------------------------------
    public static void setMarkets(MyLinkedList<Supermarket> m) {
        markets = m;
    }
    //----------------------------------------------------------------------------------------------------------------------
    private static XStream configureXStream() { //ATTEMPTED CONFIGURATION FO XSTREAM FOR PERSISTANCE
        XStream xstream = new XStream(new DomDriver());
        xstream.addPermission(AnyTypePermission.ANY);
        xstream.allowTypesByWildcard(new String[] {
                "com.example.supermarketmngmntsys.**"
        });
        xstream.ignoreUnknownElements();

        return xstream;
    }
    //----------------------------------------------------------------------------------------------------------------------
    public static void save() throws Exception { //ATTEMPTED SAVE METHOD
        XStream xstream = configureXStream();

        try (FileWriter writer = new FileWriter("supermarketmngmntSys.xml")) {
            xstream.toXML(markets, writer);
        }
    }
    //----------------------------------------------------------------------------------------------------------------------
    public static void load() throws Exception { //ATTEMPTED LOAD METHOD
        XStream xstream = configureXStream();

        try (FileReader reader = new FileReader("supermarketmngmntSys.xml")) {
            markets = (MyLinkedList<Supermarket>) xstream.fromXML(reader);
        }
    }
    //----------------------------------------------------------------------------------------------------------------------
}