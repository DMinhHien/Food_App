package com.example.didong_foodapp.ui.Models;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.didong_foodapp.ui.Controller.Interface.LocationInterface;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RestaurantModel {
    int order;
    String closeTime,openTime,nameR,introVid,maR;
    DatabaseReference nodeRoot;

    public long getLikes() {
        return likes;
    }

    public void setLikes(long likes) {
        this.likes = likes;
    }

    long likes;
    public RestaurantModel(){
        nodeRoot= FirebaseDatabase.getInstance().getReference();
    }
    List<String> tienich;
    public int isOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getNameR() {
        return nameR;
    }

    public void setNameR(String nameR) {
        this.nameR = nameR;
    }

    public String getIntroVid() {
        return introVid;
    }

    public void setIntroVid(String introVid) {
        this.introVid = introVid;
    }

    public String getMaR() {
        return maR;
    }

    public void setMaR(String maR) {
        this.maR = maR;
    }

    public List<String> getTienich() {
        return tienich;
    }

    public void setTienich(List<String> tienich) {
        this.tienich = tienich;
    }

    public void getDanhSachQuanAn(final LocationInterface locationInterface){
        ValueEventListener valueEventListener= new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DataSnapshot datSnapshotR=snapshot.child("restaurants");
                for (DataSnapshot dataValue:datSnapshotR.getChildren() ){
                    RestaurantModel restaurantModel= dataValue.getValue(RestaurantModel.class);
                    locationInterface.getListRestaurantModel(restaurantModel);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        nodeRoot.addListenerForSingleValueEvent(valueEventListener);
    }


}
