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
import com.example.didong_foodapp.ui.fragments.CartFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class FoodHistoryAdapter extends RecyclerView.Adapter<FoodHistoryAdapter.ViewHolder> {
    List<CartModel> list;
    int total = 0;
    public FoodHistoryAdapter(List<CartModel> list)
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
        holder.price.setText(cartModel.getPrice()+ " đ");
        holder.qty.setText(cartModel.getQty());
        holder.qty.setTag(cartModel.getQty());

//
//

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView;
        TextView name, price, qty;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            imageView = itemView.findViewById(R.id.detail_Image);
            name = itemView.findViewById(R.id.detail_Name);
            price = itemView.findViewById(R.id.detail_Price);
            qty = itemView.findViewById(R.id.detail_Qty);
        }
    }
}
