package com.example.didong_foodapp.ui.Models;

public class FoodModel {
    String mafood;
    long price;

    public String getMafood() {
        return mafood;
    }

    public void setMafood(String mafood) {
        this.mafood = mafood;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String image;
    String name;
}
