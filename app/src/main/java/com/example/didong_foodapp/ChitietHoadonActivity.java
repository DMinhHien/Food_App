package com.example.didong_foodapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.didong_foodapp.ui.Adapters.CartAdapter;
import com.example.didong_foodapp.ui.Adapters.FoodHistoryAdapter;
import com.example.didong_foodapp.ui.Adapters.LichsuhoadonAdapter;
import com.example.didong_foodapp.ui.Models.LichsuModel;

public class ChitietHoadonActivity extends AppCompatActivity  {

    TextView txtname, txtphone, txtaddress, txttongtien;
    RecyclerView recyclerviewvatpham;
    ImageButton close;
    LichsuModel lichsuModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lichsu_item);
        lichsuModel = getIntent().getParcelableExtra("lichsu");
        txtname = findViewById(R.id.name);
        txtaddress = findViewById(R.id.address);
        txtphone = findViewById(R.id.phone);
        txttongtien = findViewById(R.id.total);
        recyclerviewvatpham = findViewById(R.id.recyclerViewMonAn);
        close=findViewById((R.id.close_receipt));

    }

    @Override
    public void onStart() {
        super.onStart();
        RecyclerView.LayoutManager layoutmanager= new LinearLayoutManager(ChitietHoadonActivity.this);
        recyclerviewvatpham.setLayoutManager(layoutmanager);
        FoodHistoryAdapter cartAdapter =  new FoodHistoryAdapter(lichsuModel.getListdoan());
        recyclerviewvatpham.setAdapter(cartAdapter);
        txtname.setText(lichsuModel.getPerson().getName());
        txtphone.setText(lichsuModel.getPerson().getPhone());
        txtaddress.setText(lichsuModel.getPerson().getAddress());
        txttongtien.setText(lichsuModel.getTongtien());

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();

                if(id== R.id.close_receipt)
                {
                    finish();
                }
            }
        });
    }
}
