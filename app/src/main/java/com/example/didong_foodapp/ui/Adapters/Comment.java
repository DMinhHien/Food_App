package com.example.didong_foodapp.ui.Adapters;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.didong_foodapp.BinhLuanActivity;
import com.example.didong_foodapp.ChiTietResActivity;
import com.example.didong_foodapp.R;
import com.example.didong_foodapp.ui.Models.CommentModel;
import com.example.didong_foodapp.ui.Models.RestaurantModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Comment extends RecyclerView.Adapter<Comment.ViewHolder> {
    Context context;
    int layout;
    List<CommentModel> commentModelList;
    SharedPreferences sharedPreferences;
    RestaurantModel resModel;



    public Comment(Context context, int layout, List<CommentModel> commentModelListA,String maR,RestaurantModel resModel) {
        sharedPreferences = context.getSharedPreferences("newComment", MODE_PRIVATE);
        String NewComment=sharedPreferences.getString("newComment","0");
        if(NewComment.equals("isNewComment")){
            String newComment=sharedPreferences.getString("newMaComment","0");
            DatabaseReference NewCommentData= FirebaseDatabase.getInstance().getReference();
            NewCommentData.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Log.d("Kiemtra","oke");
                    DataSnapshot snapshotComment=snapshot.child("commentR").child(maR).child(newComment);
                    CommentModel commentModel= snapshotComment.getValue(CommentModel.class);
                    commentModel.setMaBL(snapshotComment.getKey());
                    List<String> imageCommentList=new ArrayList<>();
                    DataSnapshot snapshotNodeComment= snapshot.child("imageComment").child(newComment);
                    for (DataSnapshot valueImageComment:snapshotNodeComment.getChildren())
                        imageCommentList.add(valueImageComment.getValue(String.class));
                    commentModel.setImageList(imageCommentList);
                    commentModelListA.add(commentModel);
                    SharedPreferences.Editor editor =  sharedPreferences.edit();
                    editor.putString("restaurantFromComment", "none");
                    editor.apply();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
        this.context = context;
        this.layout = layout;
        this.commentModelList = commentModelListA;
        this.resModel=resModel;

    }

    public class ViewHolder  extends RecyclerView.ViewHolder{
        TextView txtCommentTitle,txtCommentContent,txtScore;
        RecyclerView recyclerImageComment;
        LinearLayout commentContainer;
        public ViewHolder(View itemView){

            super(itemView);
            txtScore=itemView.findViewById(R.id.scoreTxtComment);
            txtCommentTitle=itemView.findViewById(R.id.titleTxtComment);
            txtCommentContent=itemView.findViewById(R.id.contentTxtComment);
            recyclerImageComment=itemView.findViewById(R.id.recycler_imagecomment);
            commentContainer=itemView.findViewById(R.id.linearComment);

        }
    }

    @NonNull
    @Override
    public Comment.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(layout,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Comment.ViewHolder holder, int position) {
        final CommentModel comModel=commentModelList.get(position);
        List<Bitmap> listbitmap= new ArrayList<>();
        holder.txtCommentTitle.setText(comModel.getTitle());
        holder.txtCommentContent.setText(comModel.getContent());
        holder.txtScore.setText(comModel.getScore()+"");
        for (String link:comModel.getImageList()) {
            StorageReference storageImage = FirebaseStorage.getInstance().getReference().child(link);
            long megabyte = 1024 * 1024;
            storageImage.getBytes(megabyte).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    listbitmap.add(bitmap);
                    if ( listbitmap.size()==comModel.getImageList().size()){
                        ImageComment adapterRecyclerImageComment= new ImageComment(context,R.layout.custom_imagecomment, listbitmap,comModel,false);
                        RecyclerView.LayoutManager layoutmanager=new GridLayoutManager(context,2);
                        holder.recyclerImageComment.setLayoutManager(layoutmanager);
                        holder.recyclerImageComment.setAdapter(adapterRecyclerImageComment);
                        adapterRecyclerImageComment.notifyDataSetChanged();
                    }
                }
            });
        }
        holder.commentContainer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                PopupMenu popupMenu = new PopupMenu(context, v);
                popupMenu.getMenuInflater().inflate(R.menu.option_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        // Xử lý các hành động khi mục menu được chọn
                        if (item.getItemId() == R.id.menu_item_sua) {
                            // Xử lý hành động cho menu item 1
                            Intent iBinhLuan =new Intent (context, BinhLuanActivity.class);
                            iBinhLuan.putExtra("quananBinhLuan",resModel);
                            iBinhLuan.putExtra("tenquan",resModel.getNameR());
                            iBinhLuan.putExtra("diachi",resModel.getChiNhanhModelList().get(0).getDiachi());
                            iBinhLuan.putExtra("maquan",resModel.getMaR());
                            iBinhLuan.putExtra("currentComment",comModel);
                            iBinhLuan.putExtra("isEdit","true");
                            context.startActivity(iBinhLuan);
                            holder.commentContainer.removeAllViews();
                            return true;
                        } else if (item.getItemId() == R.id.menu_item_xoa) {
                            // Xử lý hành động cho menu item 2
                            holder.commentContainer.removeAllViews();
                            return true;
                        }
                        return false;
                    }
                });
                popupMenu.show();
                return true;
            }
        });


    }

    @Override
    public int getItemCount() {
        int comments=commentModelList.size();
        if (comments>5)
            return 5;
        else
            return commentModelList.size();
    }


}
