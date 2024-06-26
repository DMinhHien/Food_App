package com.example.didong_foodapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.didong_foodapp.ui.Adapters.AdapterChonHinhBinhLuan;
import com.example.didong_foodapp.ui.Models.ChonHinhBinhLuanModel;
import com.example.didong_foodapp.ui.Models.CommentModel;

import java.util.ArrayList;
import java.util.List;

public class ChonHinhBinhLuanActivity extends AppCompatActivity implements View.OnClickListener {

    List<ChonHinhBinhLuanModel> listDuongDan;
    List<String> listHinhDuocChon;
    List<ChonHinhBinhLuanModel> listPickedImage;
    RecyclerView recyclerChonHinhBinhLuan;
    AdapterChonHinhBinhLuan adapterChonHinhBinhLuan;
    TextView txtDone;
    CommentModel editingComment;
    Toolbar toolbar;
    private static final int REQUEST_PERMISSION_CODE = 1;
    private Context context;

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_chonhinh_binhluan);
        listDuongDan = new ArrayList<>();
        listPickedImage= new ArrayList<>();
        editingComment=getIntent().getParcelableExtra("currentComment");
        if (editingComment!=null){
            listHinhDuocChon= editingComment.getImageList();
        }
        else {
            listHinhDuocChon = new ArrayList<>();
        }
        recyclerChonHinhBinhLuan = (RecyclerView) findViewById(R.id.recyclerChonHinhBinhLuan);
        toolbar=findViewById(R.id.pickedToolbar);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        adapterChonHinhBinhLuan = new AdapterChonHinhBinhLuan(this, R.layout.custom_layout_chonhinhbinhluan, listDuongDan);
        recyclerChonHinhBinhLuan.setLayoutManager(layoutManager);
        recyclerChonHinhBinhLuan.setAdapter(adapterChonHinhBinhLuan);
        txtDone=findViewById(R.id.txtDone);
        int checkReadExStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES);
        if(checkReadExStorage != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, REQUEST_PERMISSION_CODE);
        }
        else
        {
            getTatCaHinhAnhTrongTheNho();
        }

        txtDone.setOnClickListener(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1)
        {
            if(grantResults.length > 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                getTatCaHinhAnhTrongTheNho();
            }
        }

    }


    public void getTatCaHinhAnhTrongTheNho()
    {
        String [] projection = {MediaStore.Images.Media.DATA};
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String sortOrder = MediaStore.Images.Media.DATE_TAKEN + " DESC";
        Cursor cursor = this.getContentResolver().query(uri,projection,null,null,sortOrder);
        cursor.moveToFirst();

        while(!cursor.isAfterLast())
        {
            @SuppressLint("Range") String duongdan = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            ChonHinhBinhLuanModel chonHinhModel= new ChonHinhBinhLuanModel(duongdan,false);
            listDuongDan.add(chonHinhModel);
            adapterChonHinhBinhLuan.notifyDataSetChanged();
            Log.d("kiemtra1",duongdan);
            cursor.moveToNext();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.txtDone)
        {
            for(ChonHinhBinhLuanModel value : listDuongDan)
            {
                if(value.isCheck())
                    listHinhDuocChon.add(value.getLink());
            }
            Intent data= getIntent();
            data.putStringArrayListExtra("listHinhDuocChon",(ArrayList<String>) listHinhDuocChon);
            setResult(RESULT_OK,data);
            finish();
            return;
        }

    }
}
