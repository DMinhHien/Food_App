package com.example.didong_foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BinhLuanActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txtTenQuanAn, txtDiaChiQuanAn;
    Toolbar toolbar;
    ImageButton btnChonHinh;
    final int REQUEST_CHONHINHBINHLUAN = 11;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_binhluan);

        String tenquan =getIntent().getStringExtra("tenquan");
        String diachi = getIntent().getStringExtra("diachi");

        txtTenQuanAn= findViewById(R.id.txtTenQuan);
        txtDiaChiQuanAn=findViewById(R.id.txtDiaChi);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        btnChonHinh = (ImageButton) findViewById(R.id.btnChonHinh);

        txtDiaChiQuanAn.setText(diachi);
        txtTenQuanAn.setText(tenquan);
        btnChonHinh.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btnChonHinh)
        {
            Intent iChonHinhBinhLuan = new Intent(this,ChonHinhBinhLuanActivity.class);
            startActivityForResult(iChonHinhBinhLuan,REQUEST_CHONHINHBINHLUAN);
            return;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
