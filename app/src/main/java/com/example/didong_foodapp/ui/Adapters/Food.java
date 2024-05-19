package com.example.didong_foodapp.ui.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.example.didong_foodapp.ui.Models.FoodModel;
import com.example.didong_foodapp.ui.fragments.CartFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class Food extends RecyclerView.Adapter<Food.HolderFood>{
    Context context;
    List<FoodModel> foodModelList;




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
        holder.txtPrice.setText((foodModel.getPrice()) + " đ");

        if(!CartFragment.list.isEmpty()) {
            for(int i=0;i<CartFragment.list.size();i++) {
                if (CartFragment.list.get(i).getName().equals(foodModel.getName())) {
                    holder.txtSoluong.setTag(CartFragment.list.get(i).getQty());
                    holder.txtSoluong.setText(CartFragment.list.get(i).getQty());
                    break;
                }
                else
                {
                    holder.txtSoluong.setTag(0);
                }
            }
        }
        else{
            holder.txtSoluong.setTag(0);
        }

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

                if(!CartFragment.list.isEmpty()) {
                    for(int i=0;i<CartFragment.list.size();i++) {
                        if (CartFragment.list.get(i).getName().equals(foodModel.getName())) {
                            CartFragment.list.remove(CartFragment.list.get(i));
                            break;
                        }
                    }
                }
                CartFragment.list.add(new CartModel(foodModel.getImage(), Integer.toString(dem), foodModel.getName(), Long.toString(foodModel.getPrice())));
            }
        });

        holder.imgGiamSoLuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int dem = Integer.parseInt(holder.txtSoluong.getTag().toString());
                if(dem>0){
                    for(int i=0;i<CartFragment.list.size();i++) {
                        if (CartFragment.list.get(i).getName().equals(foodModel.getName())) {
                            CartFragment.list.remove(CartFragment.list.get(i));
                            break;
                        }
                    }
                    dem--;
                    if(dem!=0)
                        CartFragment.list.add(new CartModel(foodModel.getImage(), Integer.toString(dem), foodModel.getName(),Long.toString(foodModel.getPrice())));
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
