package com.example.didong_foodapp.ui.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.didong_foodapp.ChiTietResActivity;
import com.example.didong_foodapp.R;
import com.example.didong_foodapp.ui.Models.ChiNhanhModel;
import com.example.didong_foodapp.ui.Models.CommentModel;
import com.example.didong_foodapp.ui.Models.RestaurantModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class RecyclerLocation extends RecyclerView.Adapter<RecyclerLocation.ViewHolder>  {
    List<RestaurantModel> resModelList;
    int resources;
    Context context;
    SharedPreferences sharedPreferences;

    public RecyclerLocation(Context context, List<RestaurantModel> resModelList, int resources){
        this.resModelList= resModelList;
        this.resources=resources;
        this.context=context;
    }
    @SuppressLint("NotifyDataSetChanged")
    public void setFiler(List<RestaurantModel> filterList){
        this.resModelList= filterList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNameRLocation,txtTitle1,txtTitle2,txtContent1,txtContent2,
                txtScore1,txtScore2,txtTotalComment,txtTotalImage,txtAverage,
                txtDistance,txtAddress;
        Button btnOrder;
        ImageView imageLocationR;
        LinearLayout commentContainer,commentContainer2;
        CardView cardView;
        public ViewHolder(View itemView){
            super(itemView);
            txtNameRLocation=(TextView) itemView.findViewById(R.id.txtNameRLocation);
            imageLocationR=(ImageView) itemView.findViewById((R.id.imageLocation));
            txtTitle1=itemView.findViewById(R.id.titleTxt1);
            txtTitle2=itemView.findViewById(R.id.titleTxt2);
            txtContent1=itemView.findViewById(R.id.contentTxt1);
            txtContent2=itemView.findViewById(R.id.contentTxt2);
            txtScore1=itemView.findViewById(R.id.scoreTxt1);
            txtScore2=itemView.findViewById(R.id.scoreTxt2);
            commentContainer=itemView.findViewById(R.id.commentContainer);
            commentContainer2=itemView.findViewById(R.id.commentContainer2);
            txtTotalComment=itemView.findViewById(R.id.black_comment);
            txtTotalImage=itemView.findViewById(R.id.camera_black);
            txtAverage=itemView.findViewById(R.id.averageScore);
            txtDistance=itemView.findViewById(R.id.txtDistance);
            txtAddress=itemView.findViewById(R.id.txtAddress);
            cardView=itemView.findViewById(R.id.CardView);
        }
    }
    @NonNull
    @Override
    public RecyclerLocation.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(resources,parent,false);
        ViewHolder viewholder=new ViewHolder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerLocation.ViewHolder holder, int position) {
        RestaurantModel resModel=resModelList.get(position);
        holder.txtNameRLocation.setText(resModel.getNameR());
        if(!resModel.getImageR().isEmpty()){
            StorageReference storageRef = FirebaseStorage.getInstance().getReference().child(resModel.getImageR().get(0));
            long megabyte=1024*1024;
            storageRef.getBytes(megabyte).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                    holder.imageLocationR.setImageBitmap(bitmap);
                }
            });
        }
        if (resModel.getComModel().size()>0){
            CommentModel comModel =resModel.getComModel().get(0);
            holder.txtTitle1.setText( comModel.getTitle());
            holder.txtContent1.setText( comModel.getContent());
            holder.txtScore1.setText(comModel.getScore()+"");
            if(resModel.getComModel().size()>1){
                CommentModel comModel2 =resModel.getComModel().get(1);
                holder.txtTitle2.setText(comModel2.getTitle());
                holder.txtContent2.setText( comModel2.getContent());
                holder.txtScore2.setText(comModel2.getScore()+"");
            }
            else
                holder.commentContainer2.setVisibility(View.GONE);
            holder.txtTotalComment.setText(resModel.getComModel().size()+"");
            int totalComment=0;
            float sumScore=0;
            for (CommentModel commentModel1:resModel.getComModel()){
                totalComment+=commentModel1.getImageList().size();
                sumScore+=commentModel1.getScore();
            }
            double average=sumScore/resModel.getComModel().size();
            holder.txtAverage.setText(String.format("%.1f",average));
            if(average<2.5)
                holder.txtAverage.setBackgroundResource(R.drawable.background_cycle_red);
            else if (average>=2.5 &&average<5 )
                holder.txtAverage.setBackgroundResource(R.drawable.background_cycle_yellow);
            else if (average>=5 &&average<7.5 )
                holder.txtAverage.setBackgroundResource(R.drawable.background_cycle_green);
            else if (average>=7.5 )
                holder.txtAverage.setBackgroundResource(R.drawable.background_cycle_blue);

            if (totalComment>0)
                holder.txtTotalImage.setText(totalComment+"");

        }
        else{
            holder.commentContainer.setVisibility(View.GONE);
            holder.commentContainer2.setVisibility(View.GONE);
        }
        //Load address and distance
        if (resModel.getChiNhanhModelList().size()>0){
            ChiNhanhModel chiNhanhGan= resModel.getChiNhanhModelList().get(0);
            for (ChiNhanhModel chiNhanhModel: resModel.getChiNhanhModelList()){
                if (chiNhanhGan.getDistance()>chiNhanhModel.getDistance())
                    chiNhanhGan=chiNhanhModel;
            }
            holder.txtAddress.setText(chiNhanhGan.getDiachi());
            holder.txtDistance.setText(String.format("%.1f",chiNhanhGan.getDistance())+" km");
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startActivity=new Intent(context, ChiTietResActivity.class);
                startActivity.putExtra("quanan",resModel);
                context.startActivity(startActivity);
            }
        });
        sharedPreferences= context.getSharedPreferences("restaurantFromComment", Context.MODE_PRIVATE);
       String previousResComment=sharedPreferences.getString("previousMaR","0");
       String check=sharedPreferences.getString("newComment","0");
       if (previousResComment.equals(resModel.getMaR())&&(check.equals("true"))){
           Intent startActivity=new Intent(context, ChiTietResActivity.class);
           startActivity.putExtra("quanan",resModel);
           context.startActivity(startActivity);
           SharedPreferences.Editor editor =  sharedPreferences.edit();
           editor.putString("newComment", "none");
           editor.apply();
        }
    }

    @Override
    public int getItemCount() {
        return resModelList.size();
    }


}
