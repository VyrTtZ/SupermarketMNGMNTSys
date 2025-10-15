package com.example.supermarketmngmntsys;

import LinkedList.LinkedList;

public class GoodItem {
    private String name;
    private LinkedList<Shelf> shelves;
    private double price;
    private int stock;

    public GoodItem(LinkedList<Shelf> shelves, String name, double price, int stock) {
        this.shelves = shelves;
        this.name = name;
        this.price = price;
        this.stock = stock;
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
}
