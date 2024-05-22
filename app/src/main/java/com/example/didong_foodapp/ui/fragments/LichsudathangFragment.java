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
import com.example.didong_foodapp.ui.Controller.LichsuController;
import com.example.didong_foodapp.ui.Models.LichsuModel;
import com.example.didong_foodapp.ui.Models.UserInformation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LichsudathangFragment extends Fragment {

    public RecyclerView recyclerViewDathang;

    LichsuController lichsuController;

    public LichsudathangFragment(){};

    DatabaseReference databaseRef2 = FirebaseDatabase.getInstance().getReference("Chitiethoadon");
    String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lichsudathang, container, false);
        recyclerViewDathang=view.findViewById(R.id.recyclerHistory);
        lichsuController = new LichsuController();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        lichsuController.GetLichSuList(this.getContext(),uid,recyclerViewDathang);
    }
}
