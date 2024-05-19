package com.example.didong_foodapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.example.didong_foodapp.ui.Adapters.ViewPagerMain;
import com.example.didong_foodapp.ui.Models.RestaurantModel;
import com.example.didong_foodapp.ui.Models.UserInformation;
import com.example.didong_foodapp.ui.fragments.LoadingFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    ViewPager2 viewPageMain;
    RadioButton rdLocation,rdFood,rdLuuLai,rdCart;
    RadioGroup rdGroup;
    SharedPreferences sharedPreferences;
    ConstraintLayout constraintMain;
    DatabaseReference databaseRef1;
    List<String> listUser = new ArrayList<>();
    boolean checkExist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        databaseRef1 = FirebaseDatabase.getInstance().getReference("InformationUser");
        checkExist = false;
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        sharedPreferences= getSharedPreferences("restaurantFromComment", Context.MODE_PRIVATE);
        String check=sharedPreferences.getString("newComment","0");
        setContentView(R.layout.activity_main2);
        constraintMain=findViewById(R.id.main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        rdLocation=(RadioButton) findViewById(R.id.group_place);
        rdFood= (RadioButton) findViewById(R.id.group_food);
        rdLuuLai =(RadioButton) findViewById(R.id.rdluulai);
        rdCart=(RadioButton) findViewById(R.id.rdgiohang);
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
                         rdCart.setChecked(true);
                        break;
                    case 2:
                        rdLuuLai.setChecked(true);
                        break;
                    case 3:
                        rdFood.setChecked(true);
                        break;

                }
            }

        };
        viewPageMain.registerOnPageChangeCallback(pageChangeCallback);
        rdGroup.setOnCheckedChangeListener(this);
        if(check.equals("true")){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            LoadingFragment fragment = new LoadingFragment();
            fragmentTransaction.replace(R.id.main, fragment); // R.id.fragment_container là ID của ViewGroup nơi bạn muốn chèn Fragment
            fragmentTransaction.addToBackStack(null); // Optional: để thêm vào stack
            fragmentTransaction.commit();
        }
        databaseRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String UserID = dataSnapshot.getKey();
                    listUser.add(UserID);
                }
                for(int i = 0; i < listUser.size(); i++){
                    if(listUser.get(i).equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                        checkExist = true;
                        return;
                    }
                }
                if(checkExist == false){
                    String name = "";
                    String phone = "";
                    String address = "";
                    UserInformation userInformation = new UserInformation(name, phone, address);
                    databaseRef1.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(userInformation);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }



    @Override
    public void onCheckedChanged(RadioGroup group,@IdRes int checkedId) {
        if (checkedId == R.id.group_place)
        {
            viewPageMain.setCurrentItem(0);
        }
        else if (checkedId == R.id.rdgiohang)
        {
            viewPageMain.setCurrentItem(1);
        }
        else if(checkedId==R.id.rdluulai)
        {
            viewPageMain.setCurrentItem(2);
        }
        else if(checkedId==R.id.group_food)
        {
            viewPageMain.setCurrentItem(3);
        }

    }
    public void logout(View view) {
        startActivity(new Intent(MainActivity.this,WelcomeActivity.class));
        FirebaseAuth.getInstance().signOut();
    }
}