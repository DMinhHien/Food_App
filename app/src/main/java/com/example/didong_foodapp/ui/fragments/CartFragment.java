package com.example.didong_foodapp.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.didong_foodapp.R;
import com.example.didong_foodapp.ui.Adapters.CartAdapter;
import com.example.didong_foodapp.ui.Models.CartModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;

public class CartFragment extends Fragment {
    List<CartModel> list;
    CartAdapter adapter;
    RecyclerView recyclerView;

    public CartFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        list = new ArrayList<>();
        list.add(new CartModel(R.drawable.s1, 2,"Bánh bao", "10000 đ"));
        list.add(new CartModel(R.drawable.s1, 3,"Bánh bao1", "20000 đ"));
        list.add(new CartModel(R.drawable.s1, 4,"Bánh bao2", "30000 đ"));
        list.add(new CartModel(R.drawable.s1, 5,"Bánh bao3", "40000 đ"));
        adapter = new CartAdapter(list);
        recyclerView.setAdapter(adapter);

        return view;
    }
}