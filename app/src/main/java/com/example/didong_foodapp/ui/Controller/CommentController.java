package com.example.didong_foodapp.ui.Controller;

import com.example.didong_foodapp.ui.Models.CommentModel;

import java.util.List;

public class CommentController {
    CommentModel comModel;
    public CommentController(){
        comModel=new CommentModel();
    }
    public String ThemBinhLuan(String maR,CommentModel comModel,final List<String> listImage){
        return comModel.ThemBinhLuan(maR,comModel,listImage);
    }
}
