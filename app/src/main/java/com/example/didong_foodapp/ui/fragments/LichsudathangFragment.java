package com.example.didong_foodapp.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.didong_foodapp.R;
import com.example.didong_foodapp.ui.Adapters.LichsuhoadonAdapter;
import com.example.didong_foodapp.ui.Models.LichsuModel;
import com.example.didong_foodapp.ui.Models.UserInformation;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class LichsudathangFragment extends Fragment {

    public RecyclerView recyclerViewDathang;

    public LichsudathangFragment(){};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lichsudathang, container, false);
        recyclerViewDathang=view.findViewById(R.id.recyclerHistory);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getContext());
        recyclerViewDathang.setLayoutManager(layoutManager);
        List<LichsuModel> list = new ArrayList<>();
        //lay du lieu cho list tu firebase
        //
        list.add(new LichsuModel(new UserInformation("Trinh Xuan Duong","0865671403","Binh Duong"), new ArrayList<>(),"100000"));
        //

        LichsuhoadonAdapter adapterDatHangHistory=new LichsuhoadonAdapter(getContext(),list,R.layout.lichsu_item);
        recyclerViewDathang.setAdapter(adapterDatHangHistory);
        return view;
    }

}
