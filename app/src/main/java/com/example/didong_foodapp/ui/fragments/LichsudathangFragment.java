package com.example.didong_foodapp.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.didong_foodapp.R;
import com.example.didong_foodapp.ui.Adapters.LichsuhoadonAdapter;
import com.example.didong_foodapp.ui.Models.LichsuModel;

import java.util.ArrayList;
import java.util.List;

public class LichsudathangFragment extends Fragment {

    public RecyclerView recyclerViewDathang;

    public LichsudathangFragment(){};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lichsudathang, container, false);
        List<LichsuModel> list = new ArrayList<>();

        recyclerViewDathang.setAdapter(new LichsuhoadonAdapter(list));
        return view;
    }
}