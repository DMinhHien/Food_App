package com.example.didong_foodapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.didong_foodapp.ui.Adapters.AdapterHienThiHinhBinhLuanDC;
import com.example.didong_foodapp.ui.Controller.CommentController;
import com.example.didong_foodapp.ui.Models.CommentModel;
import com.example.didong_foodapp.ui.Models.RestaurantModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BinhLuanActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txtTenQuanAn, txtDiaChiQuanAn,txtPost;
    Toolbar toolbar;
    ImageButton btnChonHinh;
    EditText edTitle,edComment,edScore;
    RecyclerView recyclerViewChonHinhBinhLuan;
    AdapterHienThiHinhBinhLuanDC adapterHienThiHinhBinhLuanDC;
    String maquanan;
    CommentController commentController;
    List<String> listHinhDuocChon;
    SharedPreferences sharedPreferences;
    RestaurantModel resModel;
    CommentModel editingComment;
    String isEdit;
    final int REQUEST_CHONHINHBINHLUAN = 11;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        sharedPreferences = getSharedPreferences("restaurantFromComment", MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        resModel=getIntent().getParcelableExtra("quananBinhLuan");
        setContentView(R.layout.layout_binhluan);
        maquanan=getIntent().getStringExtra("maquan");
        String tenquan =getIntent().getStringExtra("tenquan");
        String diachi = getIntent().getStringExtra("diachi");
        isEdit= getIntent().getStringExtra("isEdit");
        edTitle=findViewById(R.id.editTextTitle);
        edComment=findViewById(R.id.editTextComment);
        edScore=findViewById(R.id.editTextScore);
        txtPost=findViewById(R.id.txtDangBinhLuan);
        txtTenQuanAn= findViewById(R.id.txtTenQuan);
        txtDiaChiQuanAn=findViewById(R.id.txtDiaChi);
        toolbar =findViewById(R.id.toolbarPost);
        btnChonHinh = (ImageButton) findViewById(R.id.btnChonHinh);
        recyclerViewChonHinhBinhLuan = findViewById(R.id.recyclerChonHinhBinhLuan);
        commentController= new CommentController();
        listHinhDuocChon= new ArrayList<>();
        if (Objects.equals(isEdit, "true")){
            editingComment=getIntent().getParcelableExtra("currentComment");
            txtPost.setText("Sửa");
            edTitle.setText(editingComment.getTitle());
            edComment.setText(editingComment.getContent());
            listHinhDuocChon=editingComment.getImageList();
            adapterHienThiHinhBinhLuanDC = new AdapterHienThiHinhBinhLuanDC(this,R.layout.layout_hienthihinhduocchon,listHinhDuocChon,true);
            recyclerViewChonHinhBinhLuan.setAdapter(adapterHienThiHinhBinhLuanDC);
            adapterHienThiHinhBinhLuanDC.notifyDataSetChanged();
        }
        RecyclerView.LayoutManager layoutManager =new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        recyclerViewChonHinhBinhLuan.setLayoutManager(layoutManager);
        txtDiaChiQuanAn.setText(diachi);
        txtTenQuanAn.setText(tenquan);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        btnChonHinh.setOnClickListener(this);
        txtPost.setOnClickListener(this);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent startActivity=new Intent(BinhLuanActivity.this, ChiTietResActivity.class);
                startActivity.putExtra("quanan",resModel);
                startActivity(startActivity);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
        else if (id==R.id.txtDangBinhLuan) {
            double score=Double.parseDouble(edScore.getText().toString());
            if (!edComment.getText().toString().isEmpty() && !edScore.getText().toString().isEmpty()) {
                if ((score>10.0)) {
                    Toast.makeText(BinhLuanActivity.this, "Điểm phải từ 0-10",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    CommentModel comModel;
                    comModel = new CommentModel();
                    String title = edTitle.getText().toString();
                    String content = edComment.getText().toString();
                    comModel.setContent(content);
                    comModel.setTitle(title);
                    comModel.setScore(score);
                    comModel.setLikes(0);
                    comModel.setUser(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    commentController.ThemBinhLuan(maquanan, comModel, listHinhDuocChon);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("newComment", "true");
//                editor.putString("newMaComment", maBL);
                    editor.putString("previousMaR", resModel.getMaR());
                    editor.commit();
                    Intent startActivity = new Intent(BinhLuanActivity.this, MainActivity.class);
                    BinhLuanActivity.this.startActivity(startActivity);
                    finish();
                }
            }
            else if(edComment.getText().toString().isEmpty() && edScore.getText().toString().isEmpty()){
                Toast.makeText(BinhLuanActivity.this, "Hãy nhập đầy đủ",
                        Toast.LENGTH_SHORT).show();
            }
            else if(edComment.getText().toString().isEmpty()){
                Toast.makeText(BinhLuanActivity.this, "Hãy nhập nội dung",
                        Toast.LENGTH_SHORT).show();

            }
            else if(edScore.getText().toString().isEmpty()){
                Toast.makeText(BinhLuanActivity.this, "Hãy nhập điểm",
                        Toast.LENGTH_SHORT).show();
            }

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CHONHINHBINHLUAN)
        {
            if(resultCode == RESULT_OK)
            {
                listHinhDuocChon = new ArrayList<>();
                listHinhDuocChon = data.getStringArrayListExtra("listHinhDuocChon");
                adapterHienThiHinhBinhLuanDC = new AdapterHienThiHinhBinhLuanDC(this,R.layout.layout_hienthihinhduocchon,listHinhDuocChon,false);
                recyclerViewChonHinhBinhLuan.setAdapter(adapterHienThiHinhBinhLuanDC);
                adapterHienThiHinhBinhLuanDC.notifyDataSetChanged();
            }
        }
    }
}
