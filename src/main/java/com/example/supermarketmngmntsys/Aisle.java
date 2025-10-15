package com.example.supermarketmngmntsys;

import LinkedList.LinkedList;

public class Aisle {

    private String name;
    private LinkedList<Shelf> shelves;
    private LinkedList<Integer> size;

    public Aisle(String name, LinkedList<Shelf> shelves, LinkedList<Integer> size) {
        this.name = name;
        this.shelves = shelves;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LinkedList<Shelf> getShelves() {
        return shelves;
    }

    public void setShelves(LinkedList<Shelf> shelves) {
        this.shelves = shelves;
    }

    public LinkedList<Integer> getSize() {
        return size;
    }

    public void setSize(LinkedList<Integer> size) {
        this.size = size;
    }
}
