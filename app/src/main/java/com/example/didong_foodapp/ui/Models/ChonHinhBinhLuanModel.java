package com.example.didong_foodapp.ui.Models;

public class ChonHinhBinhLuanModel {
    String link;
    boolean isCheck;

    public ChonHinhBinhLuanModel(String link, boolean isCheck) {
        this.link = link;
        this.isCheck = isCheck;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
