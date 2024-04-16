package com.example.didong_foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.didong_foodapp.ui.Adapters.ViewPagerMain;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity2 extends AppCompatActivity {
    ViewPager2 viewPageMain;
    RadioButton rdLocation,rdFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
                }
            }

        };
        viewPageMain.registerOnPageChangeCallback(pageChangeCallback);
    }



    public void logout(View view) {
        startActivity(new Intent(MainActivity2.this,WelcomeActivity.class));
        FirebaseAuth.getInstance().signOut();
    }


}