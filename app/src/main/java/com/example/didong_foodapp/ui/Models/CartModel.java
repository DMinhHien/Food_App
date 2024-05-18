package com.example.didong_foodapp.ui.Models;

import java.util.Objects;

public class CartModel {
    String image;
    String name,price, qty;

    public CartModel(){};
    public CartModel(String image,String qty, String name,String price)
    {
        this.qty = qty;
        this.image = image;
        this.name = name;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartModel cartModel = (CartModel) o;
        return Objects.equals(image, cartModel.image) &&
                Objects.equals(name, cartModel.name) &&
                Objects.equals(price, cartModel.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(image, qty, name, price);
    }
}
