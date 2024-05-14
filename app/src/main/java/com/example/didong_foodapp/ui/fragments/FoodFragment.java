package com.example.didong_foodapp.ui.fragments;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.didong_foodapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FoodFragment extends Fragment {
    TextView email,username;
    DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();

    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view= inflater.inflate(R.layout.fragment_food,container,false);
        email=view.findViewById(R.id.txtEmail);
        username=view.findViewById(R.id.txtUser);
        email.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference childRef = databaseRef.child("users").child(uid).child("username");
        childRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                username.setText(snapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;


    }
}