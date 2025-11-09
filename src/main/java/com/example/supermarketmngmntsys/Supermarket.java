package com.example.supermarketmngmntsys;

import com.example.supermarketmngmntsys.mylinkedlist.MyLinkedList;

public class Supermarket {
    private String name = "";
    private MyLinkedList<Floor> floors;

    public Supermarket(String name, MyLinkedList<Floor> floors) {
        this.name = name;
        this.floors = floors;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MyLinkedList<Floor> getFloors() {
        return floors;
    }

    public void setFloors(MyLinkedList<Floor> floors) {
        this.floors = floors;
    }
}
