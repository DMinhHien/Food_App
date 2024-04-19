package com.example.didong_foodapp.ui.fragments;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.didong_foodapp.R;
import com.example.didong_foodapp.ui.Controller.LocationController;
import com.example.didong_foodapp.ui.Models.RestaurantModel;

public class LocationFragment extends Fragment {
    LocationController locationController;
//    RestaurantModel RModel;
    RecyclerView recyclerLocation;
    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view= inflater.inflate(R.layout.fragment_location,container,false);
        recyclerLocation =(RecyclerView) view.findViewById(R.id.recyclerLocation);
        return view;
    }
    @Override
    public void onStart(){
        super.onStart();
        locationController= new LocationController(getContext());
        locationController.getRestaurantLocationList(recyclerLocation);
    }
}
