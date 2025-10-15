package com.example.supermarketmngmntsys;

import LinkedList.LinkedList;

public class Floor {
    private int level;
    private LinkedList<FloorArea> floorAreas;
    private LinkedList<Integer> size;

    public Floor(int level, LinkedList<FloorArea> floorAreas, LinkedList<Integer> size) {
        this.level = level;
        this.floorAreas = floorAreas;
        this.size = size;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public LinkedList<FloorArea> getFloorAreas() {
        return floorAreas;
    }

    public void setFloorAreas(LinkedList<FloorArea> floorAreas) {
        this.floorAreas = floorAreas;
    }

    public LinkedList<Integer> getSize() {
        return size;
    }

    public void setSize(LinkedList<Integer> size) {
        this.size = size;
    }
}
