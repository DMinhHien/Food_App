package com.example.didong_foodapp.ui.Adapters;



import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.didong_foodapp.ui.fragments.FoodFragment;
import com.example.didong_foodapp.ui.fragments.LocationFragment;


public class ViewPagerMain extends FragmentStateAdapter {
    FoodFragment food_fragment;
    LocationFragment location_fragment;

    public ViewPagerMain(FragmentManager fragmentManager, Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
        food_fragment = new FoodFragment();
        location_fragment = new LocationFragment();
    }

    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return food_fragment;
            case 1:
                return location_fragment;
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 2; // Số lượng fragment trong viewpager
    }
}
