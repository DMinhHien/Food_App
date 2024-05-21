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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("Chitiethoadon");
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
        //add len Firebase
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference1.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //listsanpham
        sharedPreferences = getSharedPreferences("Thongtinnguoidung",Context.MODE_PRIVATE );
        SharedPreferences.Editor editor = sharedPreferences.edit();
        txtName.setText(sharedPreferences.getString("name","12"));
        txtaddress.setText(sharedPreferences.getString("diachi","12"));
        txtsdt.setText(sharedPreferences.getString("phone","12"));



    }
    public void loaddata(String uid, String tongtien, String tenkhachhang, String tendiachi, String phone, List<CartModel> listvatpham, String madon)
    {
        DatabaseReference nodeHoadon= FirebaseDatabase.getInstance().getReference().child(uid);


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
