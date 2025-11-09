package com.example.supermarketmngmntsys;

import com.example.supermarketmngmntsys.mylinkedlist.MyLinkedList;
//----------------------------------------------------------------------------------------------------------------------
public class GoodItem { //FIELDS
    private String name;
    private MyLinkedList<Shelf> shelves;
    private double price;
    private int stock;
    private double mass; // grams
    private int size; //cubic centimeters
    private String desc;
    private String imgUrl;
    //----------------------------------------------------------------------------------------------------------------------
    public GoodItem(String name, MyLinkedList<Shelf> shelves, double price, int stock, double mass, int size, String desc, String imgUrl) { //CONSTRUCTOR
        setName(name);
        setShelves(shelves);
        setPrice(price);
        setStock(stock);
        setMass(mass);
        setSize(size);
        setDesc(desc);
        setImgUrl(imgUrl);
    }
    //----------------------------------------------------------------------------------------------------------------------
    public double getMass() {
        return mass;
    }
    //----------------------------------------------------------------------------------------------------------------------
    public void setMass(double mass) {
        this.mass = mass;
    }
    //----------------------------------------------------------------------------------------------------------------------
    public int getSize() {
        return size;
    }
    //----------------------------------------------------------------------------------------------------------------------
    public void setSize(int size) {
        this.size = size;
    }
    //----------------------------------------------------------------------------------------------------------------------
    public String getName() {
        return name;
    }
    //----------------------------------------------------------------------------------------------------------------------
    public void setName(String name) {
        this.name = name;
    }
    //----------------------------------------------------------------------------------------------------------------------
    public MyLinkedList<Shelf> getShelves() {
        return shelves;
    }
    //----------------------------------------------------------------------------------------------------------------------
    public void setShelves(MyLinkedList<Shelf> shelves) {
        this.shelves = shelves;
    }
    //----------------------------------------------------------------------------------------------------------------------
    public double getPrice() {
        return price;
    }
    //----------------------------------------------------------------------------------------------------------------------
    public void setPrice(double price) {
        this.price = price;
    }
    //----------------------------------------------------------------------------------------------------------------------
    public int getStock() {
        return stock;
    }
    //----------------------------------------------------------------------------------------------------------------------
    public void setStock(int stock) {
        this.stock = stock;
    }
    //----------------------------------------------------------------------------------------------------------------------
    public String getImgUrl() {
        return imgUrl;
    }
    //----------------------------------------------------------------------------------------------------------------------
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
    //----------------------------------------------------------------------------------------------------------------------
    public String getDesc() {
        return desc;
    }
    //----------------------------------------------------------------------------------------------------------------------
    public void setDesc(String desc) {
        this.desc = desc;
    }
    //----------------------------------------------------------------------------------------------------------------------
}
