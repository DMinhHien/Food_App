package com.example.didong_foodapp.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.didong_foodapp.R;
import com.example.didong_foodapp.ThanhtoanActivity;
import com.example.didong_foodapp.ui.Adapters.CartAdapter;
import com.example.didong_foodapp.ui.Models.CartModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;

public class CartFragment extends Fragment implements View.OnClickListener {
    public static List<CartModel> list= new ArrayList<>();

    public static CartAdapter adapter;

    public static String CurrentRestaurant = "";
    RecyclerView recyclerView;
    public static TextView totalCost;

    public Button btnThanhToan;
    SharedPreferences sharedPreferences1;

    public static String totalDisplay;
    int total = 0;
    public CartFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        btnThanhToan = view.findViewById(R.id.button_newOrder);
        btnThanhToan.setOnClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        totalCost = view.findViewById(R.id.txtTotal);
        for(int i = 0; i < list.size(); i++){
            total += Integer.parseInt(list.get(i).getPrice().replace(" đ","")) * Integer.parseInt(list.get(i).getQty());
        }
        totalDisplay = Integer.toString(total);
        adapter = new CartAdapter(list);
        recyclerView.setAdapter(adapter);
        totalCost.setText(totalDisplay + " đ");
        sharedPreferences1 = getActivity().getSharedPreferences("Thongtinvatpham", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences1.edit();
        // lay thong tin vat pham
        editor.commit();
        return view;
    }

    @Override
    public void onStop() {

        super.onStop();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.button_newOrder)
        {
            if(list.isEmpty()){
                Toast.makeText(this.getContext(), "Giỏ hàng trống!",
                        Toast.LENGTH_SHORT).show();
            }
            else{
                Intent iThanhtoan = new Intent(this.getContext(),ThanhtoanActivity.class);
                startActivity(iThanhtoan);
            }

        }
    }
}