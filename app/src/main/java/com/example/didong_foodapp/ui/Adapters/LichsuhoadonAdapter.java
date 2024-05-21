package com.example.didong_foodapp.ui.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.didong_foodapp.R;
import com.example.didong_foodapp.ui.Models.CartModel;
import com.example.didong_foodapp.ui.Models.LichsuModel;

import java.util.List;

public class LichsuhoadonAdapter extends RecyclerView.Adapter<LichsuhoadonAdapter.ViewHolder> {
    Context context;
    List<LichsuModel> list;
    int resources;

    public LichsuhoadonAdapter(Context context, List<LichsuModel> list, int resources) {
        this.context=context;
        this.list=list;
        this.resources=resources;
    }

    @NonNull
    @Override
    public LichsuhoadonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(resources,parent,false);
        ViewHolder viewholder=new ViewHolder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull LichsuhoadonAdapter.ViewHolder holder, int position) {
        LichsuModel lichsuModel = list.get(position);
        List<CartModel> listmonan = lichsuModel.getListdoan();
        holder.txtTenKhachHang.setText(lichsuModel.getPerson().getName());
        holder.txtdiachi.setText(lichsuModel.getPerson().getAddress());
        holder.txtsodienthoai.setText(lichsuModel.getPerson().getPhone());
        holder.txtTongTien.setText(lichsuModel.getTongtien());
        holder.recyclerViewMonan.setAdapter(new CartAdapter(listmonan));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView txtTenKhachHang, txtsodienthoai, txtdiachi, txtTongTien;

        RecyclerView recyclerViewMonan;

        public ViewHolder( View itemView) {
            super(itemView);
            txtTenKhachHang = itemView.findViewById(R.id.name);
            txtsodienthoai = itemView.findViewById(R.id.phone);
            txtdiachi = itemView.findViewById(R.id.address);
            txtTongTien = itemView.findViewById(R.id.total);
            recyclerViewMonan = itemView.findViewById(R.id.recyclerViewMonAn);
            recyclerViewMonan.setLayoutManager(new LinearLayoutManager(recyclerViewMonan.getContext()));
        }
    }

}
