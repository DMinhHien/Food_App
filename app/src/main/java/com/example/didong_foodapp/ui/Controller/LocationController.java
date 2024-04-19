package com.example.didong_foodapp.ui.Controller;

import android.content.Context;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.didong_foodapp.R;
import com.example.didong_foodapp.ui.Adapters.RecyclerLocation;
import com.example.didong_foodapp.ui.Controller.Interface.LocationInterface;
import com.example.didong_foodapp.ui.Models.RestaurantModel;

import java.util.ArrayList;
import java.util.List;

public class LocationController {
    Context context;
    RestaurantModel RModel;
    RecyclerLocation adapterRecyclerLocation;
    public LocationController(Context context){
        this.context=context;
        RModel=new RestaurantModel();
    }
    public void getRestaurantLocationList (RecyclerView recyclerLocation){
        final List<RestaurantModel> RModelList= new ArrayList<>();
        RecyclerView.LayoutManager layoutmanager= new LinearLayoutManager(context);
        recyclerLocation.setLayoutManager(layoutmanager);
        adapterRecyclerLocation= new RecyclerLocation(RModelList, R.layout.custom_recyclerview_location);
        recyclerLocation.setAdapter(adapterRecyclerLocation);
        LocationInterface locationInterface=new LocationInterface() {
            @Override
            public void getListRestaurantModel(RestaurantModel restaurantModel) {
                RModelList.add(restaurantModel);
                adapterRecyclerLocation.notifyDataSetChanged();
            }
        };
        RModel.getDanhSachQuanAn(locationInterface);
    }
}
