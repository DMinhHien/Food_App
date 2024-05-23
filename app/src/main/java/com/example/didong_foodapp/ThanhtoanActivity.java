package com.example.didong_foodapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.didong_foodapp.ui.Adapters.CartAdapter;
import com.example.didong_foodapp.ui.Models.CartModel;
import com.example.didong_foodapp.ui.Models.LichsuModel;
import com.example.didong_foodapp.ui.Models.UserInformation;
import com.example.didong_foodapp.ui.fragments.CartFragment;
import com.example.didong_foodapp.ui.fragments.FoodFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ThanhtoanActivity extends AppCompatActivity implements View.OnClickListener  {
    private Context context;
    TextView txtName, txtsdt, txtaddress;
    Button btnConfirm;
    ImageButton btnclose;
    String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
    String tongtien,nameR;

    List<CartModel> listvatpham;
    DatabaseReference databaseRef1 = FirebaseDatabase.getInstance().getReference("InformationUser");

    DatabaseReference databaseRef2 = FirebaseDatabase.getInstance().getReference("Chitiethoadon");

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_thanhtoan);
        nameR=CartFragment.CurrentRestaurant;
        tongtien = CartFragment.totalDisplay;
        listvatpham = CartFragment.list;
        txtName = findViewById(R.id.name);
        txtsdt = findViewById(R.id.phone);
        txtaddress= findViewById(R.id.address);
        btnConfirm= (Button) findViewById(R.id.btnXacnhan);
        btnclose = findViewById(R.id.close);
        btnclose.setOnClickListener(this);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtaddress.getText().toString().isEmpty()||txtName.getText().toString().isEmpty()||txtsdt.getText().toString().isEmpty()){
                    Toast.makeText(ThanhtoanActivity.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                }
                else{
                    LichsuModel data = new LichsuModel(new UserInformation(txtName.getText().toString(),txtsdt.getText().toString(),txtaddress.getText().toString()),listvatpham,tongtien);
                    Calendar calendar=Calendar.getInstance();
                    SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
                    String currentTime=dateFormat.format(calendar.getTime());
                    data.setDate(currentTime);
                    data.setNameR(nameR);
                    String key =databaseRef2.child(uid).push().getKey();
                    databaseRef2.child(uid).child(key).setValue(data);
                    Toast.makeText(ThanhtoanActivity.this, "Đặt hàng thành công!", Toast.LENGTH_SHORT).show();
                    CartFragment.list.clear();
                    CartFragment.adapter.notifyDataSetChanged();
                    CartFragment.totalCost.setText("0 đ");
                    btnclose.callOnClick();
                }

            }
        });

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

        if(id== R.id.close)
        {
            finish();
        }
    }
}
