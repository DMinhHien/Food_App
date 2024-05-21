package com.example.didong_foodapp.ui.Adapters;

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
import com.example.didong_foodapp.ui.Models.MonAnTrongHoaDonModel;

import java.util.List;

public class LichsuhoadonAdapter extends RecyclerView.Adapter<LichsuhoadonAdapter.ViewHolder> {

    List<LichsuModel> list;

    public LichsuhoadonAdapter(List<LichsuModel> list) {this.list=list; }

    @NonNull
    @Override
    public LichsuhoadonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.lichsu_item,parent,false));
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenKhachHang = itemView.findViewById(R.id.txttenkhachhang);
            txtsodienthoai = itemView.findViewById(R.id.txtsodienthoai);
            txtdiachi = itemView.findViewById(R.id.txtdiachigiaohang);
            txtTongTien = itemView.findViewById(R.id.txtTongTien);
            recyclerViewMonan = itemView.findViewById(R.id.recyclerViewMonAn);
            recyclerViewMonan.setLayoutManager(new LinearLayoutManager(recyclerViewMonan.getContext()));
        }
    }

}
