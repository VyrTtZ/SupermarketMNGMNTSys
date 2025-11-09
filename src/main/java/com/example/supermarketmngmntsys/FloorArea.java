package com.example.supermarketmngmntsys;

import com.example.supermarketmngmntsys.mylinkedlist.MyLinkedList;

public class FloorArea {
    private String name;
    private MyLinkedList<Aisle> aisles;
    private MyLinkedList<Integer> size;

    public FloorArea(String name, MyLinkedList<Aisle> aisles, MyLinkedList<Integer> size) {
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

    public MyLinkedList<Aisle> getAisles() {
        return aisles;
    }

    public void setAisles(MyLinkedList<Aisle> aisles) {
        this.aisles = aisles;
    }

    public MyLinkedList<Integer> getSize() {
        return size;
    }

    public void setSize(MyLinkedList<Integer> size) {
        this.size = size;
    }
}
