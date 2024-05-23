package com.example.didong_foodapp.ui.Adapters;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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
import com.example.didong_foodapp.ui.Models.UserModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Comment extends RecyclerView.Adapter<Comment.ViewHolder> {
    Context context;
    int layout;
    List<CommentModel> commentModelList;
    ImageComment adapterRecyclerImageComment;
    SharedPreferences sharedPreferences;
    HashMap<String, Boolean> userAndid = new HashMap<>();
    RestaurantModel resModel;
    private DatabaseReference mDatabase,lDatabase,uDatabase, usDatabase;
    List<String> listLike = new ArrayList<>();
    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    List<String> UserComment = new ArrayList<>();
    List<String> UserNameComment = new ArrayList<>();
    int dem;

    public Comment(Context context, int layout, List<CommentModel> commentModelListA,String maR,RestaurantModel resModel) {
        this.context = context;
        this.layout = layout;
        this.commentModelList = commentModelListA;
        this.resModel=resModel;

    }

    public class ViewHolder  extends RecyclerView.ViewHolder{
        TextView txtCommentTitle,txtCommentContent,txtScore,avatar,like,txtUser;
        RecyclerView recyclerImageComment;
        LinearLayout commentContainer;
        boolean checkLike=true;
        public ViewHolder(View itemView){

            super(itemView);
            txtUser=itemView.findViewById(R.id.username);
            txtScore=itemView.findViewById(R.id.scoreTxtComment);
            txtCommentTitle=itemView.findViewById(R.id.titleTxtComment);
            txtCommentContent=itemView.findViewById(R.id.contentTxtComment);
            like=itemView.findViewById(R.id.commentLike);
            avatar=itemView.findViewById(R.id.avatar);
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
    public void onBindViewHolder(@NonNull Comment.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final CommentModel comModel=commentModelList.get(position);
        Log.d("CommentModel", comModel.getUser());
        if (comModel.getContent()!=null) {
            mDatabase = FirebaseDatabase.getInstance().getReference("likedComments");
            lDatabase= FirebaseDatabase.getInstance().getReference().child("commentR").child(resModel.getMaR());
            uDatabase = FirebaseDatabase.getInstance().getReference("commentR");
            usDatabase = FirebaseDatabase.getInstance().getReference("users");
            uDatabase.child(resModel.getMaR()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                            if(dataSnapshot1.getKey().equals("user")){
                                userAndid.put(dataSnapshot1.getValue().toString(), true);
                            }
                        }
                    }
                    Set<Map.Entry<String, Boolean>> entrySet = userAndid.entrySet();
                    for(Map.Entry<String, Boolean> entry : entrySet){
                        if(entry.getKey().equals(comModel.getUser()) && userAndid.get(entry.getKey())){
                            usDatabase.child(entry.getKey()).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                                        if(dataSnapshot.getKey().equals("username")){
                                            holder.txtUser.setText(dataSnapshot.getValue().toString());
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            userAndid.put(entry.getKey(), false);
                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            List<Bitmap> listbitmap = new ArrayList<>();
            holder.txtCommentTitle.setText(comModel.getTitle());
            holder.txtCommentContent.setText(comModel.getContent());
            holder.txtScore.setText(comModel.getScore() + "");
            likeCheck(holder,comModel);

            for (String link : comModel.getImageList()) {
                StorageReference storageImage = FirebaseStorage.getInstance().getReference().child(link);
                long megabyte = 1024 * 1024;
                storageImage.getBytes(megabyte).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        listbitmap.add(bitmap);
                        if (listbitmap.size() == comModel.getImageList().size()) {
                            adapterRecyclerImageComment = new ImageComment(context, R.layout.custom_imagecomment,
                                    listbitmap, comModel, false, holder.txtUser.getText().toString(),holder.like.getText().toString());
                            RecyclerView.LayoutManager layoutmanager = new GridLayoutManager(context, 2);
                            holder.recyclerImageComment.setLayoutManager(layoutmanager);
                            holder.recyclerImageComment.setAdapter(adapterRecyclerImageComment);
                            adapterRecyclerImageComment.notifyDataSetChanged();
                        }
                    }
                });
            }
            holder.like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseReference likeDatabase=lDatabase.child(comModel.getMaBL());
                    if(holder.like.getText()=="Đã thích")
                    {
                        holder.like.setText("Thích");
                        holder.like.setTextColor(Color.parseColor("#757575"));
                        long currentLike= comModel.getLikes();
                        currentLike=currentLike-1;
                        comModel.setLikes( currentLike);
                        likeDatabase.child("likes").setValue(currentLike);
                        mDatabase.child(uid).child(comModel.getMaBL()).removeValue();
                    }
                    else
                    {
                        holder.like.setText("Đã thích");
                        holder.like.setTextColor(Color.parseColor("#1e81b0"));
                        long currentLike= comModel.getLikes();
                        currentLike=currentLike+1;
                        comModel.setLikes( currentLike);
                        likeDatabase.child("likes").setValue(currentLike);
                        mDatabase.child(uid).child(comModel.getMaBL()).setValue(comModel.getMaBL());
                    }
                    adapterRecyclerImageComment.setLikeStatus(holder.like.getText().toString());
                }
            });
            if (Objects.equals(comModel.getUser(), FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                holder.avatar.setBackgroundResource(R.drawable.baseline_current_person_24);

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
                                    Intent iBinhLuan = new Intent(context, BinhLuanActivity.class);
                                    iBinhLuan.putExtra("quananBinhLuan", resModel);
                                    iBinhLuan.putExtra("tenquan", resModel.getNameR());
                                    iBinhLuan.putExtra("diachi", resModel.getChiNhanhModelList().get(0).getDiachi());
                                    iBinhLuan.putExtra("maquan", resModel.getMaR());
                                    iBinhLuan.putExtra("currentComment", comModel);
                                    iBinhLuan.putExtra("isEdit", "true");
                                    context.startActivity(iBinhLuan);
                                    commentModelList.remove(position);
                                    return true;
                                } else if (item.getItemId() == R.id.menu_item_xoa) {
                                    // Xử lý hành động cho menu item 2
                                    DatabaseReference nodeComment = FirebaseDatabase.getInstance().getReference().
                                            child("commentR").child(resModel.getMaR()).child(comModel.getMaBL());
                                    holder.commentContainer.removeAllViews();
                                    nodeComment.removeValue();
                                    mDatabase.child(uid).child(comModel.getMaBL()).removeValue();
                                    comModel.setContent(null);
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
        }

    }

    @Override
    public int getItemCount() {
            return commentModelList.size();
    }
    public void likeCheck(@NonNull Comment.ViewHolder holder,CommentModel comModel){
        mDatabase.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren()){
                    String resid = snap.getKey();
                    listLike.add(resid);
                }
                if(holder.checkLike){
                    for(int i = 0; i < listLike.size(); i++){
                        if(comModel.getMaBL().equals(listLike.get(i))){
                            holder.like.setText("Đã thích");
                            holder.like.setTextColor(Color.parseColor("#1e81b0"));
                            holder.checkLike=false;
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


}
