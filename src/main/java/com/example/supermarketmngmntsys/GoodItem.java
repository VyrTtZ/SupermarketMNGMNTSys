package com.example.supermarketmngmntsys;

import LinkedList.LinkedList;

public class GoodItem {
    private String name;
    private LinkedList<Shelf> shelves;
    private double price;
    private int stock;
    private double mass; // grams
    private int size; //cubic centimeters
    private String desc;
    private String imgUrl;

    public GoodItem(String name, LinkedList<Shelf> shelves, double price, int stock, double mass, int size, String desc, String imgUrl) {
        this.name = name;
        this.shelves = shelves;
        this.price = price;
        this.stock = stock;
        this.mass = mass;
        this.size = size;
        this.desc = desc;
        this.imgUrl = imgUrl;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
