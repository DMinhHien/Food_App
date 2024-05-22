package com.example.didong_foodapp.ui.Controller;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.didong_foodapp.ui.Adapters.LichsuhoadonAdapter;
import com.example.didong_foodapp.ui.Controller.Interface.LichSuInterface;
import com.example.didong_foodapp.ui.Models.LichsuModel;

import java.util.List;

public class LichsuController {

    LichsuModel lichsuModel;
    public LichsuController(){lichsuModel = new LichsuModel();}

    public void GetLichSuList(final Context context, String maid, final RecyclerView recyclerView)
    {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        LichSuInterface lichSuInterface = new LichSuInterface() {
            @Override
            public void getLichSuSuccess(List<LichsuModel> lichsuModelList) {
                for(LichsuModel lichsuModel:lichsuModelList)
                {
                    LichsuhoadonAdapter adapterLichSu = new LichsuhoadonAdapter(context,lichsuModelList);
                    recyclerView.setAdapter(adapterLichSu);
                    adapterLichSu.notifyDataSetChanged();
                }
            }
        };
        lichsuModel.GetLichSuList(maid,lichSuInterface);
    }
}
