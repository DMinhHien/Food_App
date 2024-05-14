package com.example.didong_foodapp.ui.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.didong_foodapp.R;
import com.example.didong_foodapp.ui.Models.DatMonModel;
import com.example.didong_foodapp.ui.Models.FoodModel;
import com.example.didong_foodapp.ui.Models.MenuModel;

import java.util.ArrayList;
import java.util.List;

public class Food extends RecyclerView.Adapter<Food.HolderFood>{
    Context context;
    List<FoodModel> foodModelList;

    public static  List<DatMonModel> datMonList = new ArrayList<>();


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
        holder.txtSoluong.setTag(0);

        holder.imgTangSoLuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int dem = Integer.parseInt(holder.txtSoluong.getTag().toString());
                dem++;
                holder.txtSoluong.setText(dem+"");
                holder.txtSoluong.setTag(dem);

                DatMonModel datMonTag = (DatMonModel) holder.imgGiamSoLuong.getTag();
                if(datMonTag != null)
                {
                    Food.datMonList.remove(datMonTag);
                }

                DatMonModel datMon = new DatMonModel();
                datMon.setSoluong(dem);
                datMon.setTenMonAn(foodModel.getName());
                holder.imgGiamSoLuong.setTag(datMon);


                Food.datMonList.add(datMon);
                for(DatMonModel datmon1 : Food.datMonList)
                {
                    Log.d("kiemtra", datmon1.getTenMonAn()+" "+datmon1.getSoluong());
                }
            }
        });

        holder.imgGiamSoLuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int dem = Integer.parseInt(holder.txtSoluong.getTag().toString());
                if(dem!=0) {
                    dem--;
                    if (dem == 0) {
                        DatMonModel datMon = (DatMonModel) v.getTag();
                        Food.datMonList.remove(datMon);
                    }
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
        TextView txtFoodName, txtSoluong;
        ImageView imgGiamSoLuong, imgTangSoLuong;
        public HolderFood(@NonNull View itemView) {
            super(itemView);
            txtFoodName=itemView.findViewById(R.id.txtFoodName);
            txtSoluong=itemView.findViewById(R.id.txtSoLuong);
            imgGiamSoLuong= itemView.findViewById(R.id.imgGiamSoLuong);
            imgTangSoLuong=itemView.findViewById(R.id.imgTangSoLuong);
        }
    }
}
