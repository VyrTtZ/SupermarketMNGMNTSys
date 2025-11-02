package com.example.supermarketmngmntsys;

import LinkedList.LinkedList;

public class Shelf {
    private int number;
    private LinkedList<GoodItem> goods;
    private LinkedList<Integer> size;

    public Shelf(int number, LinkedList<GoodItem> goods, LinkedList<Integer> size) {
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

    public LinkedList<GoodItem> getGoods() {
        return goods;
    }

    public void setGoods(LinkedList<GoodItem> goods) {
        this.goods = goods;
    }

    public LinkedList<Integer> getSize() {
        return size;
    }

    public void setSize(LinkedList<Integer> size) {
        this.size = size;
    }
}
