package com.example.didong_foodapp.ui.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.widget.TextView;

import com.example.didong_foodapp.R;
import com.example.didong_foodapp.ui.Models.MenuModel;

import java.util.List;

public class Menu extends RecyclerView.Adapter<Menu.HolderMenu>{
    Context context;
    List<MenuModel> menuModelList;

    public Menu(Context context, List<MenuModel> menuModelList) {
        this.context = context;
        this.menuModelList = menuModelList;
    }

    @NonNull
    @Override
    public Menu.HolderMenu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout_menu,parent,false);

        return new HolderMenu(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Menu.HolderMenu holder, int position) {
        MenuModel menuModel = menuModelList.get(position);
        holder.txtMenu.setText(menuModel.getName());
        holder.recyclerViewFood.setLayoutManager(new LinearLayoutManager(context));
        Food adapterFood = new Food(context, menuModel.getFoodList());
        holder.recyclerViewFood.setAdapter(adapterFood);
        adapterFood.notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return menuModelList.size();
    }

    public class HolderMenu extends RecyclerView.ViewHolder {
        TextView txtMenu;
        RecyclerView recyclerViewFood;

        public HolderMenu(@NonNull View itemView) {
            super(itemView);
            txtMenu=itemView.findViewById(R.id.menuName);
            recyclerViewFood=itemView.findViewById(R.id.recyclerFood);
        }
    }
}
