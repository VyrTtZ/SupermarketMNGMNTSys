package com.example.supermarketmngmntsys;

import com.example.supermarketmngmntsys.mylinkedlist.MyLinkedList;
import java.io.FileReader;
import java.io.FileWriter;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;

public class Parent {
    static MyLinkedList<Supermarket> markets = new MyLinkedList<Supermarket>();

    public Parent(MyLinkedList<Supermarket> m) {
        this.markets = m;
    }

    public static MyLinkedList<Supermarket> getMarkets() {
        return markets;
    }

    public static void setMarkets(MyLinkedList<Supermarket> m) {
        markets = m;
    }

    private static XStream configureXStream() {
        XStream xstream = new XStream(new DomDriver());

        // Allow all types (use with caution in production)
        xstream.addPermission(AnyTypePermission.ANY);

        // Allow types by wildcard
        xstream.allowTypesByWildcard(new String[] {
                "com.example.supermarketmngmntsys.**"
        });

        // Ignore unknown elements
        xstream.ignoreUnknownElements();

        return xstream;
    }

    public static void save() throws Exception {
        XStream xstream = configureXStream();

        try (FileWriter writer = new FileWriter("supermarketmngmntSys.xml")) {
            xstream.toXML(markets, writer);
        }
    }

    @SuppressWarnings("unchecked")
    public static void load() throws Exception {
        XStream xstream = configureXStream();

        try (FileReader reader = new FileReader("supermarketmngmntSys.xml")) {
            markets = (MyLinkedList<Supermarket>) xstream.fromXML(reader);
        }
    }
}