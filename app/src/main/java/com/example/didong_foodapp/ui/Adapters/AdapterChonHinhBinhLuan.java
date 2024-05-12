package com.example.didong_foodapp.ui.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.didong_foodapp.R;

import java.util.List;

public class AdapterChonHinhBinhLuan extends RecyclerView.Adapter<AdapterChonHinhBinhLuan.ViewHolderChonHinh> {
    Context context;
    int resource;
    List<String> listDuongDan;
    public AdapterChonHinhBinhLuan(Context context, int resource, List<String> listDuongDan)
    {
        this.context=context;
        this.resource=resource;
        this.listDuongDan=listDuongDan;
    }
    @NonNull
    @Override
    public AdapterChonHinhBinhLuan.ViewHolderChonHinh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
        ViewHolderChonHinh viewHolderChonHinh = new ViewHolderChonHinh(view);

        return viewHolderChonHinh;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterChonHinhBinhLuan.ViewHolderChonHinh holder, int position) {
        String duongdan = listDuongDan.get(position);
        Uri uri =Uri.parse(duongdan);
        holder.imageView.setImageURI(uri);
    }

    @Override
    public int getItemCount() {
        return listDuongDan.size();
    }

    public class ViewHolderChonHinh extends RecyclerView.ViewHolder {
        ImageView imageView;
        CheckBox checkBox;
        public ViewHolderChonHinh(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imgChonHinhBinhLuan);
            checkBox= (CheckBox) itemView.findViewById(R.id.checkboxChonHinh);
        }
    }
}
