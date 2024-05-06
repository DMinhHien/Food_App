package com.example.didong_foodapp.ui.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserModel implements Parcelable {
    String email;
    private DatabaseReference dataNodeUser;
    public UserModel(){
        dataNodeUser= FirebaseDatabase.getInstance().getReference().child("users");
    }

    protected UserModel(Parcel in) {
        email = in.readString();
        username = in.readString();
        uid = in.readString();
    }

    public static final Creator<UserModel> CREATOR = new Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel in) {
            return new UserModel(in);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(email);
        dest.writeString(username);
        dest.writeString(uid);
    }
}
