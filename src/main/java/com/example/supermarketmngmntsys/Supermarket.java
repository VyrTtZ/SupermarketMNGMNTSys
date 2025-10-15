package com.example.supermarketmngmntsys;

import LinkedList.LinkedList;

public class Supermarket {
    private String name = "";
    private LinkedList<Floor> floors;

    public Supermarket(String name, LinkedList<Floor> floors) {
        this.name = name;
        this.floors = floors;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LinkedList<Floor> getFloors() {
        return floors;
    }

    public void setFloors(LinkedList<Floor> floors) {
        this.floors = floors;
    }
}
