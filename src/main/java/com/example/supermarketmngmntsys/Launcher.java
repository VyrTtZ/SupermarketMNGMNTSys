package com.example.supermarketmngmntsys;

import LinkedList.LinkedList;
import javafx.application.Application;

public class Launcher {
    LinkedList<Supermarket> supermarkets;
    public static void main(String[] args) {
        Application.launch(UI.class, args);
    }
}
