package com.example.didong_foodapp.ui.Adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import java.util.*;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    List<CartModel> list;
    int total = 0;
    public CartAdapter(List<CartModel> list)
    {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartModel cartModel=list.get(position);
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child(cartModel.getImage());
        long megabyte=1024*1024;
        storageRef.getBytes(megabyte).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                holder.imageView.setImageBitmap(bitmap);
            }
        });
        holder.name.setText(cartModel.getName());
        holder.price.setText(cartModel.getPrice());
        holder.qty.setText(cartModel.getQty());
        holder.qty.setTag(cartModel.getQty());
        holder.imgTangSoLuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int dem = Integer.parseInt(holder.qty.getTag().toString());
                dem++;
                holder.qty.setText(dem+"");
                holder.qty.setTag(dem);
                
                CartModel temp = new CartModel(cartModel.getImage(), Integer.toString(dem), cartModel.getName(), cartModel.getPrice());
                if(CartFragment.list.contains(temp)){
                    CartFragment.list.remove(temp);
                }
                CartFragment.list.add(new CartModel(cartModel.getImage(), Integer.toString(dem), cartModel.getName(), cartModel.getPrice()));
                if(CartFragment.list.isEmpty()){
                    CartFragment.totalCost.setText("0 đ");
                }
                else{
                    total = 0;
                    for(int i = 0; i < list.size(); i++){
                        total += Integer.parseInt(list.get(i).getPrice().replace(" đ","")) * Integer.parseInt(list.get(i).getQty());
                    }
                    CartFragment.totalCost.setText(total + " đ");
                }
            }
        });
//
//
        holder.imgGiamSoLuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int dem = Integer.parseInt(holder.qty.getTag().toString());
                if(dem>0){
                    CartModel temp = new CartModel(cartModel.getImage(), Integer.toString(dem), cartModel.getName(), cartModel.getPrice());
                    CartFragment.list.remove(temp);
                    dem--;
                    if(dem!=0)
                        CartFragment.list.add(new CartModel(cartModel.getImage(), Integer.toString(dem), cartModel.getName(),cartModel.getPrice()));
                }

                holder.qty.setText(dem+"");
                holder.qty.setTag(dem);

                if(CartFragment.list.isEmpty()){
                    CartFragment.totalCost.setText("0 đ");
                }
                else{
                    total = 0;
                    for(int i = 0; i < list.size(); i++){
                        total += Integer.parseInt(list.get(i).getPrice().replace(" đ","")) * Integer.parseInt(list.get(i).getQty());
                    }
                    CartFragment.totalCost.setText(total + " đ");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView;
        TextView name, price, qty;
        ImageView imgGiamSoLuong, imgTangSoLuong;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            imageView = itemView.findViewById(R.id.detail_Image);
            name = itemView.findViewById(R.id.detail_Name);
            price = itemView.findViewById(R.id.detail_Price);
            qty = itemView.findViewById(R.id.detail_Qty);
            imgGiamSoLuong= itemView.findViewById(R.id.detail_button_remove);
            imgTangSoLuong=itemView.findViewById(R.id.detail_button_add );
        }
    }
}
