package com.example.comp2000;

public class MenuItem {
    private int id;
    private String name;
    private String description;
    private float price;
    private byte[] imageBlob;
    private int categoryId;

    public MenuItem(String name, String description, float price, byte[] imageBlob, int categoryId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageBlob = imageBlob;
        this.categoryId = categoryId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public byte[] getImageBlob() {
        return imageBlob;
    }

    public void setImageBlob(byte[] imageBlob) {
        this.imageBlob = imageBlob;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
