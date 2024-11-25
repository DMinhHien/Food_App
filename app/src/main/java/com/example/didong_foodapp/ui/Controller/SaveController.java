package com.example.didong_foodapp.ui.Controller;

import android.content.Context;
import android.location.Location;
import android.view.View;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.didong_foodapp.R;
import com.example.didong_foodapp.ui.Adapters.AdapterSaveRestaurant;
import com.example.didong_foodapp.ui.Adapters.RecyclerLocation;
import com.example.didong_foodapp.ui.Controller.Interface.LocationInterface;
import com.example.didong_foodapp.ui.Models.RestaurantModel;
import com.example.didong_foodapp.ui.Models.SaveRestaurantModel;

import java.util.ArrayList;
import java.util.List;

public class SaveController {
    Context context;
    SaveRestaurantModel RModel;
    AdapterSaveRestaurant adapterSaveRestaurant;

    public SaveController(Context context){
        this.context=context;
        RModel=new SaveRestaurantModel();
    }

    public void getRestaurantLocationList (Context contextL, RecyclerView recyclerLocation, Location currentLocation) {
        final List<RestaurantModel> RModelList = new ArrayList<>();
        RecyclerView.LayoutManager layoutmanager = new LinearLayoutManager(context);
        recyclerLocation.setLayoutManager(layoutmanager);
        adapterSaveRestaurant = new AdapterSaveRestaurant(contextL, RModelList, R.layout.custom_recycleview_savelocation);
        recyclerLocation.setAdapter(adapterSaveRestaurant);
        LocationInterface locationInterface = new LocationInterface() {
            @Override
            public void getListRestaurantModel(RestaurantModel restaurantModel) {
                RModelList.add(restaurantModel);
                adapterSaveRestaurant.notifyDataSetChanged();
            }
        };
        RModel.getDanhSachQuanAn(locationInterface, currentLocation);
    }
}
