package com.example.didong_foodapp.ui.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.didong_foodapp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class AdapterHienThiHinhBinhLuanDC extends RecyclerView.Adapter<AdapterHienThiHinhBinhLuanDC.ViewHolderHinhBinhLuan> {
    Context context;
    int resource;
    List<String> list;
    int previousImage;
    public AdapterHienThiHinhBinhLuanDC(Context context,int resource, List<String> list,int previousImage)
    {
        this.context=context;
        this.resource=resource;
        this.list=list;
        this.previousImage=previousImage;
    }
    @NonNull
    @Override
    public AdapterHienThiHinhBinhLuanDC.ViewHolderHinhBinhLuan onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
        ViewHolderHinhBinhLuan viewHolderHinhBinhLuan = new ViewHolderHinhBinhLuan(view);

        return viewHolderHinhBinhLuan;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHienThiHinhBinhLuanDC.ViewHolderHinhBinhLuan holder, int position) {
        if (position>=previousImage) {
            Uri uri = Uri.parse(list.get(position));
            Bitmap bit = BitmapFactory.decodeFile(String.valueOf(uri));
            holder.imageView.setImageBitmap(bit);
        }
        else {
            StorageReference storageImage = FirebaseStorage.getInstance().getReference().child(list.get(position));
            long megabyte = 1024 * 1024;
            storageImage.getBytes(megabyte).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                    holder.imageView.setImageBitmap(bitmap);
                }
            });
        }
        holder.imgXoa.setTag(position);
        holder.imgXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int vitri = (int)v.getTag();
                list.remove(vitri);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolderHinhBinhLuan extends RecyclerView.ViewHolder {
        ImageView imageView,imgXoa;
        public ViewHolderHinhBinhLuan(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgChonHinhBinhLuan);
            imgXoa = itemView.findViewById(R.id.imgDelete);

        }
    }
}
