package com.example.didong_foodapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.didong_foodapp.ui.Adapters.ImageComment;
import com.example.didong_foodapp.ui.Models.CommentModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ShowDetailCommentActivity extends AppCompatActivity {
    TextView txtCommentTitle,txtCommentContent,txtScore;
    RecyclerView recyclerImageComment;
    List<Bitmap> listbitmap;
    CommentModel comModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_layout_comment);
        txtScore=findViewById(R.id.scoreTxtComment);
        txtCommentTitle=findViewById(R.id.titleTxtComment);
        txtCommentContent=findViewById(R.id.contentTxtComment);
        recyclerImageComment=findViewById(R.id.recycler_imagecomment);
        listbitmap= new ArrayList<>();
        comModel=getIntent().getParcelableExtra("commentmodel");
        txtCommentTitle.setText(comModel.getTitle());
        txtCommentContent.setText(comModel.getContent());
        txtScore.setText(comModel.getScore()+"");
        for (String link:comModel.getImageList()) {
            StorageReference storageImage = FirebaseStorage.getInstance().getReference().child(link);
            long megabyte = 1024 * 1024;
            storageImage.getBytes(megabyte).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    listbitmap.add(bitmap);
                    if ( listbitmap.size()==comModel.getImageList().size()){
                        ImageComment adapterRecyclerImageComment= new ImageComment( ShowDetailCommentActivity.this,R.layout.custom_imagecomment, listbitmap,comModel,true);
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
