package com.example.didong_foodapp.ui.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.didong_foodapp.ChiTietResActivity;
import com.example.didong_foodapp.ChitietHoadonActivity;
import com.example.didong_foodapp.R;
import com.example.didong_foodapp.ui.Models.CartModel;
import com.example.didong_foodapp.ui.Models.LichsuModel;

import java.util.List;

public class HistoryAdapter  extends RecyclerView.Adapter<HistoryAdapter.ViewHolder>{
    Context context;
    List<LichsuModel> list;
    SharedPreferences sharedPreferences;

    public HistoryAdapter(Context context, List<LichsuModel> list) {
        this.context=context;
        this.list=list;
    }
    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.receipt_item,parent,false);
       ViewHolder viewholder=new ViewHolder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.ViewHolder holder, int position) {
        LichsuModel lichsuModel = list.get(position);
        holder.txtPrice.setText(lichsuModel.getTongtien()+" đ");
        int total= lichsuModel.getListdoan().size();
        holder.txtTotal.setText(total+"");
        holder.txtDate.setText(lichsuModel.getDate());
        holder.txtName.setText(lichsuModel.getNameR());
//        holder.hisCardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent startActivity=new Intent(context, HoaDonActivity.class);
//                startActivity.putExtra("hoadon",l);
//                context.startActivity(startActivity);
//            }
//        });
        holder.hisCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startActivity = new Intent(context, ChitietHoadonActivity.class);
                startActivity.putExtra("lichsu",lichsuModel);
                context.startActivity(startActivity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView txtName, txtPrice, txtTotal, txtDate;
        CardView hisCardView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.receipt_Name);
            txtPrice = itemView.findViewById(R.id.receipt_Price);
            txtTotal = itemView.findViewById(R.id.receipt_qty);
            txtDate= itemView.findViewById(R.id.receipt_date);
            hisCardView=itemView.findViewById(R.id.hoadonCardView);
        }
    }
}
