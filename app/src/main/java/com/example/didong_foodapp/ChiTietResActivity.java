package com.example.didong_foodapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.didong_foodapp.ui.Adapters.Comment;
import com.example.didong_foodapp.ui.Controller.MenuController;
import com.example.didong_foodapp.ui.Models.CommentModel;
import com.example.didong_foodapp.ui.Models.RestaurantModel;
import com.example.didong_foodapp.ui.Models.UserModel;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.android.gms.maps.OnMapReadyCallback;


import java.sql.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ChiTietResActivity extends AppCompatActivity implements OnMapReadyCallback{
    TextView txtName,txtAddress,txtTime,txtStatus,txtTotalImage,
            txtTotalComment,txtTotalSave,txtTotalCheckIn,txtTitleToolbar,txtLikes;
    ImageView ImageR;
    Button btnBinhLuan,btnLike;
    RestaurantModel resModel;
    Toolbar toolbar;
    Comment adapterComment;
    RecyclerView recyclerComment;
    String email;
    RecyclerView recyclerMenu;
    GoogleMap googleMap;
    SupportMapFragment mapFragment;
    MenuController menuController;
    Button buttonSave;
    UserModel userModel;
    String uid;
    private DatabaseReference mDatabase,lDatabase,likedDatabase;
    boolean check=true;
    boolean checkLike=true;
    List<String> list = new ArrayList<>();
    List<String> listLike = new ArrayList<>();

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
        txtLikes=findViewById(R.id.txtTotalLike);
        ImageR=findViewById(R.id.imageChiTiet);
        txtTitleToolbar=findViewById(R.id.titleToolbar);
        toolbar=findViewById(R.id.toolbar_layout);
        mapFragment= (SupportMapFragment) getSupportFragmentManager() .findFragmentById(R.id.mapFragmentDetail);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        recyclerComment=findViewById(R.id.recycler_comment);
        recyclerMenu=findViewById(R.id.recyclerMenu);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mapFragment.getMapAsync(this);
        menuController= new MenuController();
        btnBinhLuan = (Button) findViewById(R.id.btnBinhLuan);
        btnLike=findViewById(R.id.btnLike);
        buttonSave = (Button) findViewById(R.id.btSave);
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        userModel = new UserModel();
        mDatabase = FirebaseDatabase.getInstance().getReference("LikeRestaurant");
        lDatabase= FirebaseDatabase.getInstance().getReference().child("restaurants").child(resModel.getMaR());
        likedDatabase=FirebaseDatabase.getInstance().getReference("likedRes");

        checkSave1(resModel);
        checkSave2(resModel);
        btnBinhLuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent iBinhLuan =new Intent (ChiTietResActivity.this, BinhLuanActivity.class);
                iBinhLuan.putExtra("quananBinhLuan",resModel);
                iBinhLuan.putExtra("tenquan",resModel.getNameR());
                iBinhLuan.putExtra("diachi",resModel.getChiNhanhModelList().get(0).getDiachi());
                iBinhLuan.putExtra("maquan",resModel.getMaR());
                iBinhLuan.putExtra("isEdit", "false");
                ChiTietResActivity.this.startActivity(iBinhLuan);
                Log.d("Kiemtra","ok");
                finish();
            }
        });
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(buttonSave.getText()=="Đã lưu")
                {
                    buttonSave.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.baseline_bookmark_24_white,0,0);
                    buttonSave.setText("Lưu");
                    mDatabase.child(uid).child(resModel.getMaR()).removeValue();
                }
                else
                {
                    buttonSave.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.baseline_bookmark_24,0,0);
                    buttonSave.setText("Đã lưu");
                    mDatabase.child(uid).child(resModel.getMaR()).setValue(resModel);
                }
            }
        });
        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnLike.getText()=="Đã thích")
                {
                    btnLike.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.baseline_check_24_white,0,0);
                    btnLike.setText("Thích");
                    int currentLike= Integer.valueOf(txtLikes.getText().toString());
                    currentLike=currentLike-1;
                    txtLikes.setText(currentLike+"");
                    resModel.setLikes(currentLike);
                    lDatabase.child("likes").setValue(currentLike);
                    likedDatabase.child(uid).child(resModel.getMaR()).removeValue();
                }
                else
                {
                    btnLike.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.baseline_check_24,0,0);
                    btnLike.setText("Đã thích");
                    int currentLike= Integer.valueOf(txtLikes.getText().toString());
                    currentLike=currentLike+1;
                    txtLikes.setText(currentLike+"");
                    resModel.setLikes(currentLike);
                    lDatabase.child("likes").setValue(currentLike);
                    likedDatabase.child(uid).child(resModel.getMaR()).setValue(resModel);
                }
            }
        });
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
        txtLikes.setText(resModel.getLikes()+"");
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
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerComment.setLayoutManager(layoutManager);
        adapterComment = new Comment(this,R.layout.custom_layout_comment,resModel.getComModel(),resModel.getMaR(),resModel);
        recyclerComment.setAdapter(adapterComment);
        adapterComment.notifyDataSetChanged();
        NestedScrollView nestedChiTiet=findViewById(R.id.NestedChiTiet);
        nestedChiTiet.smoothScrollTo(0,0);
        menuController.GetRestaurentMenuList(this,resModel.getMaR(),recyclerMenu, resModel.getNameR());

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap=googleMap;
        double latitude=resModel.getChiNhanhModelList().get(0).getLatitude();
        double longitude=resModel.getChiNhanhModelList().get(0).getLongitude();
        LatLng latlng=new LatLng(latitude,longitude);
        MarkerOptions markerOptions =new MarkerOptions();
        markerOptions.position(latlng);
        markerOptions.title(resModel.getNameR());
        googleMap.addMarker(markerOptions);
        CameraUpdate cameraUpdate= CameraUpdateFactory.newLatLngZoom(latlng,14);
        googleMap.moveCamera(cameraUpdate);
    }
    public void checkSave1(RestaurantModel restaurantModel){
       mDatabase.child(uid).addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               for(DataSnapshot snap : snapshot.getChildren()){
                   String resid = snap.getKey();
                   list.add(resid);
               }
               if(check){
                   for(int i = 0; i < list.size(); i++){
                       if(restaurantModel.getMaR().equals(list.get(i))){
                           buttonSave.setText("Đã lưu");
                           buttonSave.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.baseline_bookmark_24,0,0);
                           check=false;
                           return;
                       }
                   }
               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });

    }
    public void checkSave2(RestaurantModel restaurantModel){
        likedDatabase.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren()){
                    String resid = snap.getKey();
                    listLike.add(resid);
                }
                if(checkLike){
                    for(int i = 0; i < listLike.size(); i++){
                        if(restaurantModel.getMaR().equals(listLike.get(i))){
                            btnLike.setText("Đã thích");
                            btnLike.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.baseline_check_24,0,0);
                            checkLike=false;
                            return;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();

    }
}