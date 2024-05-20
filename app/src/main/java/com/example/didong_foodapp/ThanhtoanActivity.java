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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.didong_foodapp.ui.fragments.FoodFragment;

public class ThanhtoanActivity extends AppCompatActivity implements View.OnClickListener  {


    private Context context;

    SharedPreferences sharedPreferences;

    public FoodFragment foodFragment;

    TextView txtName, txtsdt, txtaddress;
    Button btnConfirm;
    ImageButton btnclose;
    String mk1, mk2, mk3;
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
        sharedPreferences = getSharedPreferences("Thongtinnguoidung",Context.MODE_PRIVATE );
        SharedPreferences.Editor editor = sharedPreferences.edit();
//        txtName.setText(sharedPreferences.getString("name","12"));
//        txtaddress.setText(sharedPreferences.getString("diachi","12"));
//        txtsdt.setText(sharedPreferences.getString("phone","12"));
        mk1 = sharedPreferences.getString("name","12");
        mk2 = sharedPreferences.getString("diachi","12");
        mk3 = sharedPreferences.getString("phone","12");


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
