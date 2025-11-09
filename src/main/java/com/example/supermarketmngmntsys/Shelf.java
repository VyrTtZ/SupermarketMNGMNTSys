package com.example.supermarketmngmntsys;

import com.example.supermarketmngmntsys.mylinkedlist.MyLinkedList;

public class Shelf {
    private int number;
    private MyLinkedList<GoodItem> goods;
    private MyLinkedList<Integer> size;

    public Shelf(int number, MyLinkedList<GoodItem> goods, MyLinkedList<Integer> size) {
        this.number = number;
        this.goods = goods;
        this.size = size;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public MyLinkedList<GoodItem> getGoods() {
        return goods;
    }

    public void setGoods(MyLinkedList<GoodItem> goods) {
        this.goods = goods;
    }

    public MyLinkedList<Integer> getSize() {
        return size;
    }

    public void setSize(MyLinkedList<Integer> size) {
        this.size = size;
    }
}
