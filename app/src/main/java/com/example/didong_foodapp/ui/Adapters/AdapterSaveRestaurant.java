package com.example.didong_foodapp.ui.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.example.didong_foodapp.ui.Models.SaveRestaurantModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class AdapterSaveRestaurant extends RecyclerView.Adapter<AdapterSaveRestaurant.ViewHolder>  {
    List<RestaurantModel> resModelList;
    int resources;
    Context context;
    SharedPreferences sharedPreferences;

    public AdapterSaveRestaurant(Context context, List<RestaurantModel> resModelList, int resources){
        this.resModelList= resModelList;
        this.resources=resources;
        this.context=context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNameRLocation,txtTitle1,txtTitle2,txtContent1,txtContent2,
                txtScore1,txtScore2,txtTotalComment,txtTotalImage,txtAverage,
                txtDistance,txtAddress;
        ImageView imageLocationR;
        CardView cardView;
        public ViewHolder(View itemView){
            super(itemView);
            txtNameRLocation=(TextView) itemView.findViewById(R.id.txtNameRLocation);
            imageLocationR=(ImageView) itemView.findViewById((R.id.imageLocation));
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
    public AdapterSaveRestaurant.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(resources,parent,false);
        ViewHolder viewholder=new ViewHolder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSaveRestaurant.ViewHolder holder, int position) {
        RestaurantModel resModel = resModelList.get(position);
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
            holder.txtTotalComment.setText(resModel.getComModel().size()+"");
            int totalComment=0;
            int sumScore=0;
            for (CommentModel commentModel1:resModel.getComModel()){
                totalComment+=commentModel1.getImageList().size();
                sumScore+=commentModel1.getScore();
            }
            double average=sumScore/resModel.getComModel().size();
            holder.txtAverage.setText(String.format("%.1f",average));
            if (totalComment>0)
                holder.txtTotalImage.setText(totalComment+"");

        }

        //Load address and distance

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startActivity=new Intent(context, ChiTietResActivity.class);
                startActivity.putExtra("quanan",resModel);
                context.startActivity(startActivity);
            }
        });
//        sharedPreferences= context.getSharedPreferences("restaurantFromComment", Context.MODE_PRIVATE);
//        String previousResComment=sharedPreferences.getString("restaurantFromComment","0");
//        if (previousResComment.equals(resModel.getMaR())){
//            Intent startActivity=new Intent(context, ChiTietResActivity.class);
//            startActivity.putExtra("quanan",resModel);
//            context.startActivity(startActivity);
//        }
    }

    @Override
    public int getItemCount() {
        return resModelList.size();
    }


}
