package com.example.didong_foodapp.ui.Adapters;



import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.didong_foodapp.ui.fragments.CartFragment;
import com.example.didong_foodapp.ui.fragments.FoodFragment;
import com.example.didong_foodapp.ui.fragments.LichsudathangFragment;
import com.example.didong_foodapp.ui.fragments.LocationFragment;
import com.example.didong_foodapp.ui.fragments.SavedFragment;


public class ViewPagerMain extends FragmentStateAdapter {
    FoodFragment food_fragment;
    LocationFragment location_fragment;
    SavedFragment saved_fragment;
    CartFragment cartFragment;

    LichsudathangFragment lichsudathangFragment;

    public ViewPagerMain(FragmentManager fragmentManager, Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
        food_fragment = new FoodFragment();
        location_fragment = new LocationFragment();
        saved_fragment = new SavedFragment();
        cartFragment = new CartFragment();
        lichsudathangFragment = new LichsudathangFragment();
    }

    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 0:
                return location_fragment;
            case 1:
                return cartFragment;
            case 2:
                return saved_fragment;
            case 3:
                return lichsudathangFragment;
            case 4:
                return food_fragment;
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 5; // Số lượng fragment trong viewpager
    }
}
