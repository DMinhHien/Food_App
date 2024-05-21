package com.example.didong_foodapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.didong_foodapp.ui.Models.CartModel;
import com.example.didong_foodapp.ui.fragments.FoodFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ThanhtoanActivity extends AppCompatActivity implements View.OnClickListener  {


    private Context context;

    public List<CartModel> listsanpham = new ArrayList<>();

    public FoodFragment fm = new FoodFragment();

    SharedPreferences sharedPreferences;
    SharedPreferences sharedPreferences1;



    public FoodFragment foodFragment;

    TextView txtName, txtsdt, txtaddress;
    Button btnConfirm;
    ImageButton btnclose;
    String mk1, mk2, mk3;
    String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
    DatabaseReference databaseRef1 = FirebaseDatabase.getInstance().getReference("InformationUser");
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_thanhtoan);
        txtName = findViewById(R.id.name);
        txtsdt = findViewById(R.id.phone);
        txtaddress= findViewById(R.id.address);
        btnConfirm= (Button) findViewById(R.id.btnXacnhan);
        btnclose = findViewById(R.id.close);
        btnclose.setOnClickListener(this);
        databaseRef1.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if(dataSnapshot.getKey().equals("address")){
                        txtaddress.setText(dataSnapshot.getValue().toString());
                    }
                    else if(dataSnapshot.getKey().equals("name")){
                        txtName.setText(dataSnapshot.getValue().toString());
                    }
                    else{
                        txtsdt.setText(dataSnapshot.getValue().toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.btnXacnhan)
        {

        }
        if(id== R.id.close)
        {
            finish();
        }
    }
}
