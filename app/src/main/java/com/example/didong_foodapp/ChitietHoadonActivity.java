package com.example.didong_foodapp;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
    Toolbar toolbar;
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
        toolbar=findViewById(R.id.toolbarReceipt);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

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

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
