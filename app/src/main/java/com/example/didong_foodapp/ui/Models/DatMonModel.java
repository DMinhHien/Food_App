package com.example.didong_foodapp.ui.Models;

public class DatMonModel {
    String tenMonAn;
    int soluong;
    int price;

    int img;
    public int getSoluong() {
        return soluong;
    }

    public String getTenMonAn() {
        return tenMonAn;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public void setTenMonAn(String tenMonAn) {
        this.tenMonAn = tenMonAn;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
