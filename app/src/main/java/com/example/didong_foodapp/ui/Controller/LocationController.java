package com.example.didong_foodapp.ui.Controller;

import android.content.Context;
import android.location.Location;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.didong_foodapp.BinhLuanActivity;
import com.example.didong_foodapp.R;
import com.example.didong_foodapp.ui.Adapters.AdapterSaveRestaurant;
import com.example.didong_foodapp.ui.Adapters.RecyclerLocation;
import com.example.didong_foodapp.ui.Controller.Interface.LocationInterface;
import com.example.didong_foodapp.ui.Models.FoodModel;
import com.example.didong_foodapp.ui.Models.MenuModel;
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
    public void getRestaurantLocationList (Context contextL, RecyclerView recyclerLocation, ProgressBar pBar, Location currentLocation, SearchView searchRes){
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
        searchRes.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterlist(newText,RModelList,adapterRecyclerLocation);
                return true;
            }
        });
    }
    private void filterlist(String text,List<RestaurantModel> RModelList,RecyclerLocation adapterRecyclerLocation){
        List<RestaurantModel> filterlist=new ArrayList<>();
        for (RestaurantModel rModel:RModelList ){
            if(rModel.getNameR().toLowerCase().contains(text.toLowerCase())){
                filterlist.add(rModel);
            }
            if (rModel.getSignature().toLowerCase().contains(text.toLowerCase()) && (!filterlist.contains(rModel))) {
                filterlist.add(rModel);
            }
        }

        if (filterlist.isEmpty()){
            Toast.makeText(context, "Không tìm thấy",
                    Toast.LENGTH_SHORT).show();
        }else{
            adapterRecyclerLocation.setFiler(filterlist);
        }
    }

}
