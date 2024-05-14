package com.example.didong_foodapp.ui.Controller;

import com.example.didong_foodapp.ui.Models.UserModel;

public class LoginController {
    UserModel uModel;
    public LoginController(){
        uModel=new UserModel();
    }
    public void AddInfoController( UserModel uModelR,String Uid){
        uModel.AddInfo(uModelR,Uid);
    }

}
