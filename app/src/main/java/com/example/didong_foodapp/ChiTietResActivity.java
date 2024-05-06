package com.example.didong_foodapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.didong_foodapp.ui.Models.RestaurantModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ChiTietResActivity extends AppCompatActivity {
    TextView txtName,txtAddress,txtTime,txtStatus,txtTotalImage,
            txtTotalComment,txtTotalSave,txtTotalCheckIn,txtTitleToolbar;
    ImageView ImageR;
    RestaurantModel resModel;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_chi_tiet);
        resModel=getIntent().getParcelableExtra("quanan");
        txtName=findViewById(R.id.nameR);
        txtAddress=findViewById(R.id.addressR);
        txtTime=findViewById(R.id.OpenTime);
        txtStatus=findViewById(R.id.Status);
        txtTotalImage=findViewById(R.id.txtTotalImage);
        txtTotalComment=findViewById(R.id.txtTotalComment);
        txtTotalSave=findViewById(R.id.txtTotalSave);
        txtTotalCheckIn=findViewById(R.id.txtTotalCheckIn);
        ImageR=findViewById(R.id.imageChiTiet);
        txtTitleToolbar=findViewById(R.id.titleToolbar);
        toolbar=findViewById(R.id.toolbar_layout);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }
    @Override
    public boolean onSupportNavigateUp(){
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Thực hiện các xử lý bạn muốn trước khi đóng Activity
                // Ví dụ: lưu dữ liệu, hiển thị thông báo, vv.

                // Gọi finish() để đóng Activity
                finish();
            }
        };
        // Gắn hành động cho dispatcher
        getOnBackPressedDispatcher().addCallback(this, callback);
        return true;

    }
    @Override
    public void onStart() {

    super.onStart();
        Calendar calendar=Calendar.getInstance();

        SimpleDateFormat dateFormat=new SimpleDateFormat("HH:mm");
        String currentTime=dateFormat.format(calendar.getTime());
        String openTime=resModel.getOpenTime();
        String closeTime=resModel.getCloseTime();
        try {
            Date currentDate=dateFormat.parse(currentTime);
            Date openDate=dateFormat.parse(openTime);
            Date closeDate=dateFormat.parse(closeTime);
            if (currentDate.after(openDate) && currentDate.before(closeDate)){
                txtStatus.setText("Open");
            }
            else
                txtStatus.setText("Closed");

        } catch (ParseException e) {
            e.printStackTrace();
        }
        txtTitleToolbar.setText(resModel.getNameR());
        txtName.setText(resModel.getNameR());
        txtAddress.setText(resModel.getChiNhanhModelList().get(0).getDiachi());
        txtTime.setText(resModel.getOpenTime()+" - "+resModel.getCloseTime());
        txtTotalComment.setText(resModel.getComModel().size()+"");
        txtTotalImage.setText(resModel.getImageR().size()+"");
        txtTime.setText(openTime+ " - "+closeTime );
        StorageReference storageImage= FirebaseStorage.getInstance().getReference().child(resModel.getImageR().get(0));
        long megabyte=1024*1024;
        storageImage.getBytes(megabyte).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                ImageR.setImageBitmap(bitmap);
            }
        });


    }

}