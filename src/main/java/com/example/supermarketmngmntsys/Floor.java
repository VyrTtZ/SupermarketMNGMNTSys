package com.example.supermarketmngmntsys;

import com.example.supermarketmngmntsys.mylinkedlist.MyLinkedList;
//----------------------------------------------------------------------------------------------------------------------
public class Floor {
    private int level;
    private MyLinkedList<FloorArea> floorAreas;
    private MyLinkedList<Integer> size;
    //----------------------------------------------------------------------------------------------------------------------
    public Floor(int level, MyLinkedList<FloorArea> floorAreas, MyLinkedList<Integer> size) {
        setLevel(level);
        setFloorAreas(floorAreas);
        setSize(size);
    }
    //----------------------------------------------------------------------------------------------------------------------
    public int getLevel() {
        return level;
    }
    //----------------------------------------------------------------------------------------------------------------------
    public void setLevel(int level) {
        this.level = level;
    }
    //----------------------------------------------------------------------------------------------------------------------
    public MyLinkedList<FloorArea> getFloorAreas() {
        return floorAreas;
    }
    //----------------------------------------------------------------------------------------------------------------------
    public void setFloorAreas(MyLinkedList<FloorArea> floorAreas) {
        this.floorAreas = floorAreas;
    }
    //----------------------------------------------------------------------------------------------------------------------
    public MyLinkedList<Integer> getSize() {
        return size;
    }
    //----------------------------------------------------------------------------------------------------------------------
    public void setSize(MyLinkedList<Integer> size) {
        this.size = size;
    }
    //----------------------------------------------------------------------------------------------------------------------
}
