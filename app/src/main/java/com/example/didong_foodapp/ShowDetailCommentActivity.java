package com.example.didong_foodapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.didong_foodapp.ui.Adapters.ImageComment;
import com.example.didong_foodapp.ui.Models.CommentModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ShowDetailCommentActivity extends AppCompatActivity {
    TextView txtCommentTitle,txtCommentContent,txtScore,txtUser,likes,avatar;
    RecyclerView recyclerImageComment;
    List<Bitmap> listbitmap;
    CommentModel comModel;
    String user,likeStatus;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_layout_comment);
        txtScore=findViewById(R.id.scoreTxtComment);
        txtCommentTitle=findViewById(R.id.titleTxtComment);
        txtCommentContent=findViewById(R.id.contentTxtComment);
        txtUser=findViewById(R.id.username);
        recyclerImageComment=findViewById(R.id.recycler_imagecomment);
        avatar=findViewById(R.id.avatar);
        listbitmap= new ArrayList<>();
        comModel=getIntent().getParcelableExtra("commentmodel");
        user=getIntent().getStringExtra("commentuser");
        likes=findViewById(R.id.commentLike);
        likeStatus=getIntent().getStringExtra("commentlike");
        txtCommentTitle.setText(comModel.getTitle());
        txtCommentContent.setText(comModel.getContent());
        txtScore.setText(comModel.getScore()+"");
        txtUser.setText(user);
        likes.setText( likeStatus);
        if(likes.getText()=="Đã thích")
        {
            likes.setTextColor(Color.parseColor("#757575"));
        }
        else
        {
            likes.setTextColor(Color.parseColor("#1e81b0"));
        }
        if (Objects.equals(comModel.getUser(), FirebaseAuth.getInstance().getCurrentUser().getUid())) {
           avatar.setBackgroundResource(R.drawable.baseline_current_person_24);
        }
        for (String link:comModel.getImageList()) {
            StorageReference storageImage = FirebaseStorage.getInstance().getReference().child(link);
            long megabyte = 1024 * 1024;
            storageImage.getBytes(megabyte).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    listbitmap.add(bitmap);
                    if ( listbitmap.size()==comModel.getImageList().size()){
                        ImageComment adapterRecyclerImageComment= new ImageComment( ShowDetailCommentActivity.this,R.layout.custom_imagecomment, listbitmap,comModel,true,user,likeStatus);
                        RecyclerView.LayoutManager layoutmanager=new GridLayoutManager( ShowDetailCommentActivity.this,2);
                        recyclerImageComment.setLayoutManager(layoutmanager);
                        recyclerImageComment.setAdapter(adapterRecyclerImageComment);
                        adapterRecyclerImageComment.notifyDataSetChanged();
                    }
                }
            });
        }

    }
}
