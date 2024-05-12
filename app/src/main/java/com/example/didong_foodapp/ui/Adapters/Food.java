package com.example.didong_foodapp.ui.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.didong_foodapp.R;
import com.example.didong_foodapp.ui.Models.FoodModel;
import com.example.didong_foodapp.ui.Models.MenuModel;

import java.util.List;

public class Food extends RecyclerView.Adapter<Food.HolderFood>{
    Context context;
    List<FoodModel> foodModelList;

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
    }

    @Override
    public int getItemCount() {
        return foodModelList.size();
    }

    public class HolderFood extends RecyclerView.ViewHolder {
        TextView txtFoodName;
        public HolderFood(@NonNull View itemView) {
            super(itemView);
            txtFoodName=itemView.findViewById(R.id.txtFoodName);
        }
    }
}
