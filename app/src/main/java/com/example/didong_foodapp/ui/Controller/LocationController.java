package com.example.didong_foodapp.ui.Controller;

import android.content.Context;
import android.location.Location;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

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
    public void getRestaurantLocationList (Context contextL,RecyclerView recyclerLocation, ProgressBar pBar, Location currentLocation){
        final List<RestaurantModel> RModelList= new ArrayList<>();
        RecyclerView.LayoutManager layoutmanager= new LinearLayoutManager(context);
        recyclerLocation.setLayoutManager(layoutmanager);
        adapterRecyclerLocation= new RecyclerLocation(contextL,RModelList, R.layout.custom_recyclerview_location);
        recyclerLocation.setAdapter(adapterRecyclerLocation);
        LocationInterface locationInterface=new LocationInterface() {
            @Override
            public void getListRestaurantModel(RestaurantModel restaurantModel) {
                RModelList.add(restaurantModel);
                adapterRecyclerLocation.notifyDataSetChanged();
                pBar.setVisibility(View.GONE);
            }
        };
        RModel.getDanhSachQuanAn(locationInterface,currentLocation);
    }
}
