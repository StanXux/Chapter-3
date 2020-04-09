package com.example.chapter3.homework;

import java.util.ArrayList;

public class Item {
    private String name;

    public Item(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static ArrayList<Item> getItems() {
        ArrayList<Item> items = new ArrayList<Item>();
        for(int i = 0; i < 7; i++) {
            items.add(new Item("Item"+String.valueOf(i)));
        }
        return items;
    }
}
