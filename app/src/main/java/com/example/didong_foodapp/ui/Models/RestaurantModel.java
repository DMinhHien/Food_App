package com.example.didong_foodapp.ui.Models;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.didong_foodapp.ui.Controller.Interface.LocationInterface;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RestaurantModel implements Parcelable {
    int order;
    String closeTime,openTime,nameR,introVid,maR;
    DatabaseReference nodeRoot;
    List<String> imageR;
    List<String> tienich;

    protected RestaurantModel(Parcel in) {
        order = in.readInt();
        closeTime = in.readString();
        openTime = in.readString();
        nameR = in.readString();
        introVid = in.readString();
        maR = in.readString();
        imageR = in.createStringArrayList();
        tienich = in.createStringArrayList();
        likes = in.readLong();
        chiNhanhModelList=new ArrayList<ChiNhanhModel>();
        in.readTypedList( chiNhanhModelList,ChiNhanhModel.CREATOR);
        comModel= new ArrayList<CommentModel>();
        in.readTypedList(comModel,CommentModel.CREATOR);
    }

    public static final Creator<RestaurantModel> CREATOR = new Creator<RestaurantModel>() {
        @Override
        public RestaurantModel createFromParcel(Parcel in) {
            return new RestaurantModel(in);
        }

        @Override
        public RestaurantModel[] newArray(int size) {
            return new RestaurantModel[size];
        }
    };

    public List<ChiNhanhModel> getChiNhanhModelList() {
        return chiNhanhModelList;
    }

    public void setChiNhanhModelList(List<ChiNhanhModel> chiNhanhModelList) {
        this.chiNhanhModelList = chiNhanhModelList;
    }

    List<ChiNhanhModel> chiNhanhModelList;
    List<CommentModel> comModel;
    public List<CommentModel> getComModel() {
        return comModel;
    }

    public void setComModel(List<CommentModel> comModel) {
        this.comModel = comModel;
    }
    long likes;

    public long getLikes() {
        return likes;
    }

    public void setLikes(long likes) {
        this.likes = likes;
    }


    public RestaurantModel(){
        nodeRoot= FirebaseDatabase.getInstance().getReference();
    }


    public List<String> getImageR() {
        return imageR;
    }

    public void setImageR(List<String> imageR) {
        this.imageR = imageR;
    }


    public int isOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getNameR() {
        return nameR;
    }

    public void setNameR(String nameR) {
        this.nameR = nameR;
    }

    public String getIntroVid() {
        return introVid;
    }

    public void setIntroVid(String introVid) {
        this.introVid = introVid;
    }

    public String getMaR() {
        return maR;
    }

    public void setMaR(String maR) {
        this.maR = maR;
    }

    public List<String> getTienich() {
        return tienich;
    }

    public void setTienich(List<String> tienich) {
        this.tienich = tienich;
    }

    public void getDanhSachQuanAn(final LocationInterface locationInterface,Location currentLocation){
        ValueEventListener valueEventListener= new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DataSnapshot datSnapshotR=snapshot.child("restaurants");
                //Lay danh sach quan an
                for (DataSnapshot dataValue:datSnapshotR.getChildren() ){
                    RestaurantModel restaurantModel= dataValue.getValue(RestaurantModel.class);
                    restaurantModel.setMaR((dataValue.getKey()));
                    DataSnapshot dataSnapShotImage= snapshot.child("imageR").child(dataValue.getKey());
                    //Lay danh sach hinhn anh
                    List <String> imageList=new ArrayList<>();
                    for (DataSnapshot valueImage : dataSnapShotImage.getChildren()){
                        imageList.add(valueImage.getValue(String.class));
                    }
                    restaurantModel.setImageR(imageList);
                    //Lay danh sach binh luan
                    DataSnapshot snapshotComment=snapshot.child("commentR").child(restaurantModel.getMaR());
                    List<CommentModel> CommentList= new ArrayList<>();
                    for (DataSnapshot CommentValue: snapshotComment.getChildren() ){
                        CommentModel commentModel= CommentValue.getValue(CommentModel.class);
                        commentModel.setMaBL(CommentValue.getKey());
                        List<String> imageCommentList=new ArrayList<>();
                        DataSnapshot snapshotNodeComment= snapshot.child("imageComment").child(commentModel.getMaBL());
                        for (DataSnapshot valueImageComment:snapshotNodeComment.getChildren())
                            imageCommentList.add(valueImageComment.getValue(String.class));
                        commentModel.setImageList(imageCommentList);
                        CommentList.add(commentModel);
                    }

                    restaurantModel.setComModel(CommentList);
                    //Lay danh sach chi nhanh
                    DataSnapshot snapshotChiNhanh=snapshot.child("chinhanhR").child(restaurantModel.getMaR());
                    List<ChiNhanhModel> chinhanhModels=new ArrayList<>();
                    for (DataSnapshot valueChinhNhanh :snapshotChiNhanh.getChildren()){
                        ChiNhanhModel chinhanhModel=valueChinhNhanh.getValue( ChiNhanhModel.class);
                        Location restuarantLocation = new Location("");
                        restuarantLocation.setLatitude(chinhanhModel.getLatitude());
                        restuarantLocation.setLongitude(chinhanhModel.getLongitude());
                        double distance =currentLocation.distanceTo( restuarantLocation)/1000;
                        chinhanhModel.setDistance(distance);
                        chinhanhModels.add(chinhanhModel);

                    }
                    restaurantModel.setChiNhanhModelList( chinhanhModels);
                    locationInterface.getListRestaurantModel(restaurantModel);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        nodeRoot.addListenerForSingleValueEvent(valueEventListener);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(order);
        dest.writeString(closeTime);
        dest.writeString(openTime);
        dest.writeString(nameR);
        dest.writeString(introVid);
        dest.writeString(maR);
        dest.writeStringList(imageR);
        dest.writeStringList(tienich);
        dest.writeLong(likes);
        dest.writeTypedList(chiNhanhModelList);
        dest.writeTypedList(comModel);
    }
}
