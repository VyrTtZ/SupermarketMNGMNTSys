package com.example.supermarketmngmntsys;

import com.example.supermarketmngmntsys.mylinkedlist.MyLinkedList;

public class Aisle {

    private String name;
    private MyLinkedList<Shelf> shelves;
    private MyLinkedList<Integer> size;
    private int refrigerated;

    public Aisle(String name, MyLinkedList<Shelf> shelves, MyLinkedList<Integer> size, int refrigerated) {
        this.name = name;
        this.shelves = shelves;
        this.size = size;
        this.refrigerated = refrigerated;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MyLinkedList<Shelf> getShelves() {
        return shelves;
    }

    public void setShelves(MyLinkedList<Shelf> shelves) {
        this.shelves = shelves;
    }

    public MyLinkedList<Integer> getSize() {
        return size;
    }

    public void setSize(MyLinkedList<Integer> size) {
        this.size = size;
    }

    public int getRefrigerated() {
        return refrigerated;
    }

    public void setRefrigerated(int refrigerated) {
        this.refrigerated = refrigerated;
    }
}
