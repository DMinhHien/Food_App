package com.example.didong_foodapp.ui.Controller;

import com.example.didong_foodapp.ui.Models.CommentModel;

import java.util.List;

public class CommentController {
    CommentModel comModel;
    public CommentController(){
        comModel=new CommentModel();
    }
    public void ThemBinhLuan(String maR,CommentModel comModel,final List<String> listImage){
        comModel.ThemBinhLuan(maR,comModel,listImage);
    }
    public void SuaBinhLuan(String maR,String maBl,CommentModel comModel,final List<String> listImage){
        comModel.SuaBinhLuan(maR,maBl,comModel,listImage);
    }
}
