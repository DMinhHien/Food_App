package com.example.didong_foodapp.ui.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.didong_foodapp.R;
import com.example.didong_foodapp.ui.Models.ChonHinhBinhLuanModel;

import java.util.List;

public class AdapterChonHinhBinhLuan extends RecyclerView.Adapter<AdapterChonHinhBinhLuan.ViewHolderChonHinh> {
    Context context;
    int resource;
    List<ChonHinhBinhLuanModel> listDuongDan;
    public AdapterChonHinhBinhLuan(Context context, int resource, List<ChonHinhBinhLuanModel> listDuongDan)
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
    public void onBindViewHolder(@NonNull AdapterChonHinhBinhLuan.ViewHolderChonHinh holder, @SuppressLint("RecyclerView") int position) {
        ChonHinhBinhLuanModel duongdan = listDuongDan.get(position);
        Uri uri =Uri.parse(duongdan.getLink());
        Bitmap bit = BitmapFactory.decodeFile(String.valueOf(uri));
        holder.imageView.setImageBitmap(bit);
        holder.checkBox.setChecked(duongdan.isCheck());
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkbox=(CheckBox) v;
                listDuongDan.get(position).setCheck(checkbox.isChecked());
            }
        });
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
