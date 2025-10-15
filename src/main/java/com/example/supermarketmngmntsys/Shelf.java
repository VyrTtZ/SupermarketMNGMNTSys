package com.example.supermarketmngmntsys;

import LinkedList.LinkedList;

public class Shelf {
    private String name;
    private LinkedList<GoodItem> goods;
    private LinkedList<Integer> size;

    public Shelf(String name, LinkedList<GoodItem> goods, LinkedList<Integer> size) {
        this.name = name;
        this.goods = goods;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
