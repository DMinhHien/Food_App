package com.example.didong_foodapp.ui.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.didong_foodapp.R;
import com.example.didong_foodapp.ui.Models.RestaurantModel;

import java.util.List;

public class RecyclerLocation extends RecyclerView.Adapter<RecyclerLocation.ViewHolder> {
    List<RestaurantModel> resModelList;
    int resources;
    public RecyclerLocation( List<RestaurantModel> resModelList, int resources){
        this.resModelList= resModelList;
        this.resources=resources;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNameRLocation;
        public ViewHolder(View itemView){
            super(itemView);
            txtNameRLocation=(TextView) itemView.findViewById(R.id.txtNameRLocation);
        }
    }
    @NonNull
    @Override
    public RecyclerLocation.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(resources,parent,false);
        ViewHolder viewholder=new ViewHolder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerLocation.ViewHolder holder, int position) {
        RestaurantModel resModel=resModelList.get(position);
        holder.txtNameRLocation.setText(resModel.getNameR());
    }

    @Override
    public int getItemCount() {
        return resModelList.size();
    }


}
