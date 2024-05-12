package com.example.didong_foodapp.ui.Models;

import androidx.annotation.NonNull;

import com.example.didong_foodapp.ui.Controller.Interface.MenuInterface;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MenuModel {
    public String getMamenu() {
        return mamenu;
    }

    public void setMamenu(String mamenu) {
        this.mamenu = mamenu;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String mamenu;
    String name;

    public List<FoodModel> getFoodList() {
        return foodModelList;
    }

    public void setFoodList(List<FoodModel> foodModelList) {
        this.foodModelList = foodModelList;
    }
    public void GetRestaurentMenuList(String maR, MenuInterface menuInterface){
        DatabaseReference nodeThucDonR= FirebaseDatabase.getInstance().getReference().child("menudetailR").child(maR);
        nodeThucDonR.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final List<MenuModel> menuModelModels = new ArrayList<>();
                for (DataSnapshot value:snapshot.getChildren()){
                    final MenuModel menuModel=new MenuModel();
                    DatabaseReference nodeMenu=FirebaseDatabase.getInstance().getReference().child("menusR").child(value.getKey());
                    nodeMenu.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshotMenu) {
                            String maMenu=snapshotMenu.getKey();
                            menuModel.setMamenu(maMenu);
                            menuModel.setName(snapshotMenu.getValue(String.class));
                            List<FoodModel> foodModelModels = new ArrayList<>();
                            for (DataSnapshot valueFood:snapshot.child(maMenu).getChildren()){
                                FoodModel foodModel= valueFood.getValue(FoodModel.class);
                                foodModel.setMafood(valueFood.getKey());
                                foodModelModels.add(foodModel);
                            }
                            menuModel.setFoodList(foodModelModels);
                            menuModelModels.add(menuModel);
                            menuInterface.getMenuSuccess(menuModelModels);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    List<FoodModel> foodModelList;

}
