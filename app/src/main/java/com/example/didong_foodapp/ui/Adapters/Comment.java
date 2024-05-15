package com.example.didong_foodapp.ui.Adapters;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.didong_foodapp.R;
import com.example.didong_foodapp.ui.Models.CommentModel;
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



    public Comment(Context context, int layout, List<CommentModel> commentModelListA,String maR) {
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

    }

    public class ViewHolder  extends RecyclerView.ViewHolder{
        TextView txtCommentTitle,txtCommentContent,txtScore;
        RecyclerView recyclerImageComment;
        public ViewHolder(View itemView){

            super(itemView);
            txtScore=itemView.findViewById(R.id.scoreTxtComment);
            txtCommentTitle=itemView.findViewById(R.id.titleTxtComment);
            txtCommentContent=itemView.findViewById(R.id.contentTxtComment);
            recyclerImageComment=itemView.findViewById(R.id.recycler_imagecomment);


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
