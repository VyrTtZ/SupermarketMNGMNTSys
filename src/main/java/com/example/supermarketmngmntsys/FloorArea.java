package com.example.supermarketmngmntsys;

import LinkedList.LinkedList;

public class FloorArea {
    private String name;
    private LinkedList<Aisle> aisles;
    private LinkedList<Integer> size;

    public FloorArea(String name, LinkedList<Aisle> aisles, LinkedList<Integer> size) {
        this.name = name;
        this.aisles = aisles;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LinkedList<Aisle> getAisles() {
        return aisles;
    }

    public void setAisles(LinkedList<Aisle> aisles) {
        this.aisles = aisles;
    }

    public LinkedList<Integer> getSize() {
        return size;
    }

    public void setSize(LinkedList<Integer> size) {
        this.size = size;
    }
}
