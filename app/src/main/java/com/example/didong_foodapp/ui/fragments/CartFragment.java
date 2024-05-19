package com.example.didong_foodapp.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.didong_foodapp.R;
import com.example.didong_foodapp.ui.Adapters.CartAdapter;
import com.example.didong_foodapp.ui.Models.CartModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;

public class CartFragment extends Fragment {
    public static List<CartModel> list= new ArrayList<>();;
    static CartAdapter adapter;
    RecyclerView recyclerView;
    public static TextView totalCost;

    String totalDisplay;
    int total = 0;
    public CartFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        totalCost = view.findViewById(R.id.txtTotal);
        for(int i = 0; i < list.size(); i++){
            total += Integer.parseInt(list.get(i).getPrice().replace(" đ","")) * Integer.parseInt(list.get(i).getQty());
        }
        totalDisplay = Integer.toString(total);
        adapter = new CartAdapter(list);
        recyclerView.setAdapter(adapter);
        totalCost.setText(totalDisplay + " đ");
        return view;
    }

    @Override
    public void onStop() {

        super.onStop();
    }

    public static void UpdateCart(List<CartModel> items)
    {
        if (items != null && !items.isEmpty())
        {
            list.addAll(items);
            adapter.notifyDataSetChanged();
        }
    }
}