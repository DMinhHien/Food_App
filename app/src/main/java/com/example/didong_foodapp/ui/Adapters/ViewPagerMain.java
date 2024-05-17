package com.example.didong_foodapp.ui.Adapters;



import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.didong_foodapp.ui.fragments.CartFragment;
import com.example.didong_foodapp.ui.fragments.FoodFragment;
import com.example.didong_foodapp.ui.fragments.LocationFragment;
import com.example.didong_foodapp.ui.fragments.SavedFragment;


public class ViewPagerMain extends FragmentStateAdapter {
    FoodFragment food_fragment;
    LocationFragment location_fragment;
    SavedFragment saved_fragment;

    public ViewPagerMain(FragmentManager fragmentManager, Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
        food_fragment = new FoodFragment();
        location_fragment = new LocationFragment();
        saved_fragment = new SavedFragment();
    }

    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return location_fragment;
            case 1:
                return food_fragment;
            case 2:
                return saved_fragment;

        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 3; // Số lượng fragment trong viewpager
    }
}
