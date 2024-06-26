package com.example.didong_foodapp.ui.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.didong_foodapp.R;
import com.example.didong_foodapp.ShowDetailCommentActivity;
import com.example.didong_foodapp.ui.Models.CommentModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ImageComment extends RecyclerView.Adapter<ImageComment.ViewHolder>{
    Context context;
    int resources;
    List<Bitmap> listimage;
    CommentModel comModel;
    boolean isDetail;
    String user,likeStatus;



    public ImageComment(Context context, int resources, List<Bitmap> listimage,CommentModel comModel,  boolean isDetail,String user,String likeStatus) {
        this.context = context;
        this.resources = resources;
        this.listimage = listimage;
        this.comModel=comModel;
        this.isDetail=isDetail;
        this.user=user;
        this.likeStatus=likeStatus;

    }
    @SuppressLint("NotifyDataSetChanged")
    public void setLikeStatus(String likeStatus){
        this.likeStatus=likeStatus;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageComment;
        TextView txtImageNumber;
        FrameLayout framelayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageComment=itemView.findViewById(R.id.FirstImage);
            txtImageNumber=itemView.findViewById(R.id.imageNumber);
            framelayout=itemView.findViewById(R.id.frame_number);

        }
    }

    @NonNull
    @Override
    public ImageComment.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(resources,parent,false);
        ViewHolder viewholderComment=new ViewHolder(view);
        return viewholderComment;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageComment.ViewHolder holder, int position) {
      holder.imageComment.setImageBitmap(listimage.get(position));
      if(!isDetail) {
          if (position == 3) {
              int remain = listimage.size() - 4;
              if (remain > 0) {
                  holder.framelayout.setVisibility(View.VISIBLE);
                  holder.txtImageNumber.setText("+" + remain);
                  holder.imageComment.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          Log.d("Clicked", "Success");
                          Intent ChiTietComment = new Intent(context, ShowDetailCommentActivity.class);
                          ChiTietComment.putExtra("commentmodel", comModel);
                          ChiTietComment.putExtra("commentuser",user);
                          ChiTietComment.putExtra("commentlike",likeStatus);
                          context.startActivity(ChiTietComment);

                      }
                  });
              }
          }
      }
    }

    @Override
    public int getItemCount() {
        if(!isDetail) {
            if (listimage.size() < 4) {
                return listimage.size();
            }
            else return 4;
        }
        else
            return listimage.size();
    }


}
