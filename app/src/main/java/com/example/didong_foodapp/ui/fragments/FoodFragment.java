package com.example.didong_foodapp.ui.fragments;



import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.didong_foodapp.LoginActivity;
import com.example.didong_foodapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class FoodFragment extends Fragment {
    TextView email,username;
    public static TextInputEditText name, phone, address;
    Button btUpdate;
    DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference databaseRef1 = FirebaseDatabase.getInstance().getReference("InformationUser");

    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view= inflater.inflate(R.layout.fragment_food,container,false);
        email=view.findViewById(R.id.txtEmail);
        username=view.findViewById(R.id.txtUser);
        email.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        name = view.findViewById(R.id.name);
        phone = view.findViewById(R.id.phone);
        address = view.findViewById(R.id.address);
        btUpdate = view.findViewById(R.id.btnUpdate);
        String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference childRef = databaseRef.child("users").child(uid).child("username");
        databaseRef1.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if(dataSnapshot.getKey().equals("address")){
                        address.setText(dataSnapshot.getValue().toString());
                    }
                    else if(dataSnapshot.getKey().equals("name")){
                        name.setText(dataSnapshot.getValue().toString());
                    }
                    else{
                        phone.setText(dataSnapshot.getValue().toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        childRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                username.setText(snapshot.getValue(String.class));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(address.getText().toString().isEmpty() == true ||name.getText().toString().isEmpty() == true || phone.getText().toString().isEmpty()==true)
                    Toast.makeText(getContext(), "Update Failed",
                            Toast.LENGTH_SHORT).show();
                else {
                    loadInformation(address.getText().toString(), name.getText().toString(), phone.getText().toString());
                    Toast.makeText(getContext(), "Update Successfully",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    public void loadInformation(String address, String name, String phone){
        HashMap User = new HashMap();
        User.put("address", address);
        User.put("name", name);
        User.put("phone", phone);
        String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseRef1.child(uid).updateChildren(User).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful()){

                }
            }
        });
    }
    public String getName()
    {
        return name.getText().toString();
    }
    public String getPhone()
    {
        return phone.getText().toString();
    }
    public String getAddress()
    {
        return address.getText().toString();
    }
}