package com.example.didong_foodapp.ui.Models;

import java.util.List;

public class LichsuModel {



    String tongtien;
    UserInformation person;
    List<CartModel> listdoan;
    public String getTongtien() {
        return tongtien;
    }

    public void setTongtien(String tongtien) {
        this.tongtien = tongtien;
    }
    public UserInformation getPerson() {
        return person;
    }

    public void setPerson(UserInformation person) {
        this.person = person;
    }



    public List<CartModel> getListdoan() {
        return listdoan;
    }

    public void setListdoan(List<CartModel> listdoan) {
        this.listdoan = listdoan;
    }



    public LichsuModel(UserInformation person, List<CartModel> listdoan, String tongtien)
    {
        this.person=person;
        this.listdoan=listdoan;
        this.tongtien=tongtien;
    }


}
