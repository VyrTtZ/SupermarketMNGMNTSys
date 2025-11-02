package com.example.supermarketmngmntsys;

import LinkedList.LinkedList;

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
}
