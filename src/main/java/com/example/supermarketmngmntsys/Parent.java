package com.example.supermarketmngmntsys;

import LinkedList.LinkedList;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class Parent {
    static LinkedList<Supermarket> markets = new LinkedList<Supermarket>();

    public Parent(LinkedList<Supermarket> m) {
        this.markets = m;
    }

    public static LinkedList<Supermarket> getMarkets() {
        return markets;
    }

    public static void setMarkets(LinkedList<Supermarket> m) {
        markets = m;
    }

    public static void save() throws Exception {
        XStream xstream = new XStream(new DomDriver());
        XStream.setupDefaultSecurity(xstream);
        xstream.allowTypes(new Class[]{Parent.class, LinkedList.class, Supermarket.class}); // fixed
        ObjectOutputStream out = xstream.createObjectOutputStream(new FileWriter("supermarketmngmntSys.xml"));
        out.writeObject(markets);
        out.close();
    }

    @SuppressWarnings("unchecked")
    public void load() throws Exception {
        XStream xstream = new XStream(new DomDriver());
        XStream.setupDefaultSecurity(xstream);
        xstream.allowTypes(new Class[]{Parent.class, LinkedList.class, Supermarket.class}); // fixed
        ObjectInputStream is = xstream.createObjectInputStream(new FileReader("supermarketmngmntSys.xml")); // fixed file name
        markets = (LinkedList<Supermarket>) is.readObject();
        is.close();
    }
}
