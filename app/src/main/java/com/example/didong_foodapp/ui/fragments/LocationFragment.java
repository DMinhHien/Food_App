package com.example.didong_foodapp.ui.fragments;



import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.didong_foodapp.R;
import com.example.didong_foodapp.ui.Controller.LocationController;
import com.example.didong_foodapp.ui.Models.RestaurantModel;

public class LocationFragment extends Fragment {
    LocationController locationController;
    SharedPreferences sharedPreferences;
    //    RestaurantModel RModel;
    RecyclerView recyclerLocation;
    ProgressBar pBar;
    SearchView searchRes;
    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view= inflater.inflate(R.layout.fragment_location,container,false);
        recyclerLocation =(RecyclerView) view.findViewById(R.id.recyclerLocation);
        pBar=view.findViewById(R.id.progressLocation);
        searchRes=view.findViewById(R.id.searchView);
        searchRes.clearFocus();
        return view;
    }
    @Override
    public void onStart(){
        super.onStart();
        sharedPreferences= getContext().getSharedPreferences("location", Context.MODE_PRIVATE);
        Location currentLocation = new Location("");
        currentLocation.setLatitude(Double.parseDouble(sharedPreferences.getString("latitude","0")));
        currentLocation.setLongitude(Double.parseDouble(sharedPreferences.getString("longitude","0")));
        locationController= new LocationController(getContext());
        locationController.getRestaurantLocationList(getContext(),recyclerLocation,pBar,currentLocation,searchRes);
    }
}
