package com.example.didong_foodapp.ui.Models;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserModel {
    String email;
    private DatabaseReference dataNodeUser;
    public UserModel(){
        dataNodeUser= FirebaseDatabase.getInstance().getReference().child("users");
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public void AddInfo(UserModel uModel,String Uid){

        dataNodeUser.child(Uid).setValue(uModel);
    }
    String username;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    String uid;
}
