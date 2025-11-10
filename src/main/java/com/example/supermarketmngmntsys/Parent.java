package com.example.supermarketmngmntsys;

import com.example.supermarketmngmntsys.mylinkedlist.MyLinkedList;

import java.io.*;

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

    //----------------------------------------------------------------------------------------------------------------------
    public static void load() throws Exception { //ATTEMPTED LOAD METHOD

    }
    //----------------------------------------------------------------------------------------------------------------------
}