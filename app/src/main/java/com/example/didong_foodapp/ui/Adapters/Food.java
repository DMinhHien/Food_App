package com.example.didong_foodapp.ui.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.didong_foodapp.R;
import com.example.didong_foodapp.ui.Models.CartModel;
import com.example.didong_foodapp.ui.Models.DatMonModel;
import com.example.didong_foodapp.ui.Models.FoodModel;
import com.example.didong_foodapp.ui.Models.MenuModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class Food extends RecyclerView.Adapter<Food.HolderFood>{
    Context context;
    List<FoodModel> foodModelList;
    List<CartModel> cartModelList;
    public static  List<DatMonModel> datMonList = new ArrayList<>();


    public Food(Context context, List<FoodModel> foodModelList) {
        this.context = context;
        this.foodModelList =foodModelList;

    }
    @NonNull
    @Override
    public Food.HolderFood onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout_food,parent,false);

        return new HolderFood(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Food.HolderFood holder, int position) {
        FoodModel foodModel=foodModelList.get(position);
        holder.txtFoodName.setText(foodModel.getName());
        holder.txtSoluong.setTag(0);
        holder.txtPrice.setText(Long.toString(foodModel.getPrice()));
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child(foodModel.getImage());
        long megabyte=1024*1024;
        storageRef.getBytes(megabyte).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                holder.imageFood.setImageBitmap(bitmap);
            }
        });
        holder.imgTangSoLuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int dem = Integer.parseInt(holder.txtSoluong.getTag().toString());
                dem++;
                holder.txtSoluong.setText(dem+"");
                holder.txtSoluong.setTag(dem);
                holder.txtPrice.setText(Long.toString(foodModel.getPrice()));
                DatMonModel datMonTag = (DatMonModel) holder.imgGiamSoLuong.getTag();
                if(datMonTag != null)
                {
                    Food.datMonList.remove(datMonTag);
                }

                DatMonModel datMon = new DatMonModel();
                datMon.setSoluong(dem);
                datMon.setPrice(Integer.parseInt(Long.toString(foodModel.getPrice())));
                datMon.setTenMonAn(foodModel.getName());

                holder.imgGiamSoLuong.setTag(datMon);

                Food.datMonList.add(datMon);
                for(DatMonModel datmon1 : Food.datMonList)
                {
                    Log.d("kiemtra", datmon1.getTenMonAn()+" "+datmon1.getSoluong() +  " " + datmon1.getPrice());
                }

            }
        });

        holder.imgGiamSoLuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int dem = Integer.parseInt(holder.txtSoluong.getTag().toString());
                if(dem!=0) {
                    dem--;
                    if (dem == 0) {
                        DatMonModel datMon = (DatMonModel) v.getTag();
                        Food.datMonList.remove(datMon);
                    }
                }
                holder.txtSoluong.setText(dem+"");
                holder.txtSoluong.setTag(dem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodModelList.size();
    }

    public class HolderFood extends RecyclerView.ViewHolder {
        TextView txtFoodName, txtSoluong, txtPrice;
        ImageView imageFood;
        ImageView imgGiamSoLuong, imgTangSoLuong;

        public HolderFood(@NonNull View itemView) {
            super(itemView);
            txtFoodName=itemView.findViewById(R.id.detail_Name);
            txtSoluong=itemView.findViewById(R.id.detail_Qty);
            imgGiamSoLuong= itemView.findViewById(R.id.detail_button_remove);
            imgTangSoLuong=itemView.findViewById(R.id.detail_button_add );
            txtPrice = itemView.findViewById(R.id.detail_Price);
            imageFood = itemView.findViewById(R.id.detail_Image);
        }
    }
}
