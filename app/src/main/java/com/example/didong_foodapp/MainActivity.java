package com.example.didong_foodapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.didong_foodapp.ui.Adapters.ViewPagerMain;
import com.example.didong_foodapp.ui.Models.RestaurantModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    ViewPager2 viewPageMain;
    RadioButton rdLocation,rdFood,rdLuuLai;
    RadioGroup rdGroup;

    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = getSharedPreferences("newComment", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("newComment","none");
        editor.commit();
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        rdLocation=(RadioButton) findViewById(R.id.group_place);
        rdFood= (RadioButton) findViewById(R.id.group_food);
        rdLuuLai =(RadioButton) findViewById(R.id.rdluulai);
        rdGroup=(RadioGroup) findViewById(R.id.group_food_place);
        viewPageMain=(ViewPager2) findViewById(R.id.viewpager_main);
        ViewPagerMain viewpagermain = new ViewPagerMain(getSupportFragmentManager(), getLifecycle());
        viewPageMain.setAdapter(viewpagermain);
        ViewPager2.OnPageChangeCallback pageChangeCallback = new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                          rdLocation.setChecked(true);
                        break;
                    case 1:
                         rdFood.setChecked(true);
                        break;
                    case 2:
                        rdLuuLai.setChecked(true);
                        break;

                }
            }

        };
        viewPageMain.registerOnPageChangeCallback(pageChangeCallback);
        rdGroup.setOnCheckedChangeListener(this);
    }



    @Override
    public void onCheckedChanged(RadioGroup group,@IdRes int checkedId) {
        if (checkedId == R.id.group_place) {
            viewPageMain.setCurrentItem(0);
        } else if (checkedId == R.id.group_food) {
            viewPageMain.setCurrentItem(1);}
            else if(checkedId==R.id.rdluulai)
                viewPageMain.setCurrentItem(2);

    }
    public void logout(View view) {
        startActivity(new Intent(MainActivity.this,WelcomeActivity.class));
        FirebaseAuth.getInstance().signOut();
    }


}