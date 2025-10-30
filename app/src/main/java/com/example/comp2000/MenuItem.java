package com.example.comp2000;

import android.graphics.drawable.Drawable;

public class MenuItem {
    private String name;
    private float price;

    private Drawable image;

    public MenuItem(String name, float price, Drawable image) {
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public Drawable getImage() {
        return image;
    }
}
