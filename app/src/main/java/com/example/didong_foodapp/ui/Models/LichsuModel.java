package com.example.didong_foodapp.ui.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.didong_foodapp.ui.Controller.Interface.LichSuInterface;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LichsuModel implements Parcelable {
    String nameR;

    String maLichsu;
    String tongtien;
    UserInformation person;
    List<CartModel> listdoan;
    protected LichsuModel(Parcel in) {
        nameR = in.readString();
        maLichsu = in.readString();
        tongtien = in.readString();
        date = in.readString();
        listdoan = new ArrayList<CartModel>();
        in.readTypedList(listdoan,CartModel.CREATOR);
        person = in.readParcelable(UserInformation.class.getClassLoader());
    }

    public static final Creator<LichsuModel> CREATOR = new Creator<LichsuModel>() {
        @Override
        public LichsuModel createFromParcel(Parcel in) {
            return new LichsuModel(in);
        }

        @Override
        public LichsuModel[] newArray(int size) {
            return new LichsuModel[size];
        }
    };

    public String getMaLichsu() {
        return maLichsu;
    }

    public void setMaLichsu(String maLichsu) {
        this.maLichsu = maLichsu;
    }


    public String getNameR() {
        return nameR;
    }

    public void setNameR(String nameR) {
        this.nameR = nameR;
    }



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    String date;

    List<LichsuModel> lichsuModelList;
    public String getTongtien() {
        return tongtien;
    }

    public void setTongtien(String tongtien) {
        this.tongtien = tongtien;
    }
    public UserInformation getPerson() {
        return person;
    }
    public void setPerson(UserInformation person) {
        this.person = person;
    }



    public List<CartModel> getListdoan() {
        return listdoan;
    }

    public void setListdoan(List<CartModel> listdoan) {
        this.listdoan = listdoan;
    }

    public LichsuModel(){};

    public LichsuModel(UserInformation person, List<CartModel> listdoan, String tongtien)
    {
        this.person=person;
        this.listdoan=listdoan;
        this.tongtien=tongtien;
    }
    public void setLichsuList(List<LichsuModel> lichsuModelList){this.lichsuModelList = lichsuModelList;}
    public void GetLichSuList(String maid, LichSuInterface lichSuInterface)
    {
        DatabaseReference nodeLichsu = FirebaseDatabase.getInstance().getReference().child("Chitiethoadon").child(maid);
        nodeLichsu.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final List<LichsuModel> lichsuModels = new ArrayList<>();
                for(DataSnapshot value:snapshot.getChildren())
                {
                    LichsuModel lichsuModel = new LichsuModel();
                    lichsuModel = value.getValue(LichsuModel.class);
                    lichsuModels.add(lichsuModel);
                    lichSuInterface.getLichSuSuccess(lichsuModels);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(nameR);
        dest.writeString(maLichsu);
        dest.writeString(tongtien);
        dest.writeString(date);
        dest.writeTypedList(listdoan);
        dest.writeParcelable(person,flags);
    }
}
