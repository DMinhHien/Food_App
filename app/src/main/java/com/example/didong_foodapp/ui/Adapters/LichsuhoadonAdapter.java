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


    public LichsuhoadonAdapter(Context context, List<LichsuModel> list) {
        this.context=context;
        this.list=list;
    }

    @NonNull
    @Override
    public LichsuhoadonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.lichsu_item,parent,false);
        ViewHolder viewholder=new ViewHolder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull LichsuhoadonAdapter.ViewHolder holder, int position) {
        LichsuModel lichsuModel = list.get(position);
        holder.txtTenKhachHang.setText(lichsuModel.getPerson().getName());
        holder.txtdiachi.setText(lichsuModel.getPerson().getAddress());
        holder.txtsodienthoai.setText(lichsuModel.getPerson().getPhone());
        holder.txtTongTien.setText(lichsuModel.getTongtien());
        RecyclerView.LayoutManager layoutmanager= new LinearLayoutManager(context);
        holder.recyclerViewMonan.setLayoutManager(layoutmanager);
        CartAdapter adapter = new CartAdapter(lichsuModel.getListdoan());
        holder.recyclerViewMonan.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView txtTenKhachHang, txtsodienthoai, txtdiachi, txtTongTien;

        RecyclerView recyclerViewMonan;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenKhachHang = itemView.findViewById(R.id.name);
            txtsodienthoai = itemView.findViewById(R.id.phone);
            txtdiachi = itemView.findViewById(R.id.address);
            txtTongTien = itemView.findViewById(R.id.total);
            recyclerViewMonan = itemView.findViewById(R.id.recyclerViewMonAn);
        }
    }

}
