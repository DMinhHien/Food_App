package com.example.didong_foodapp.ui.Models;

import java.util.List;

public class HoaDonModel {
    List<CartModel> listdanhmuc;
    public List<CartModel> getListdanhmuc() {
        return listdanhmuc;
    }

    public void setListdanhmuc(List<CartModel> listdanhmuc) {
        this.listdanhmuc = listdanhmuc;
    }


    public HoaDonModel(){};
    public HoaDonModel(List<CartModel> list)
    {
        this.listdanhmuc=list;
    }
}
