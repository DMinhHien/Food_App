package com.example.didong_foodapp.ui.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.didong_foodapp.R;
import com.example.didong_foodapp.ui.Controller.LocationController;
import com.example.didong_foodapp.ui.Controller.SaveController;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class SavedFragment extends Fragment {
    SaveController saveController;
    SharedPreferences sharedPreferences;
    //    RestaurantModel RModel;
    RecyclerView AdapterSaveRestaurant;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_saved,container,false);
        AdapterSaveRestaurant =(RecyclerView) view.findViewById(R.id.recyclerSaved);
        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        sharedPreferences= getContext().getSharedPreferences("location", Context.MODE_PRIVATE);
        Location currentLocation = new Location("");
        currentLocation.setLatitude(Double.parseDouble(sharedPreferences.getString("latitude","0")));
        currentLocation.setLongitude(Double.parseDouble(sharedPreferences.getString("longitude","0")));
        saveController= new SaveController(getContext());
        saveController.getRestaurantLocationList(getContext(),AdapterSaveRestaurant,currentLocation);
    }
}
