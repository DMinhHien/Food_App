package com.example.didong_foodapp.ui.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.List;

public class CommentModel implements Parcelable {
    long likes;
    double score;
    UserModel uModel;
    String content;
    public CommentModel(){}

    protected CommentModel(Parcel in) {
        likes = in.readLong();
        score = in.readDouble();
        content = in.readString();
        maBL = in.readString();
        imageList = in.createStringArrayList();
        user = in.readString();
        title = in.readString();
        uModel=in.readParcelable(UserModel.class.getClassLoader());
    }

    public static final Creator<CommentModel> CREATOR = new Creator<CommentModel>() {
        @Override
        public CommentModel createFromParcel(Parcel in) {
            return new CommentModel(in);
        }

        @Override
        public CommentModel[] newArray(int size) {
            return new CommentModel[size];
        }
    };

    public String getMaBL() {
        return maBL;
    }

    public void setMaBL(String maBL) {
        this.maBL = maBL;
    }

    String maBL;

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }

    List<String> imageList;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    String user;

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public long getLikes() {
        return likes;
    }

    public void setLikes(long likes) {
        this.likes = likes;
    }

    public UserModel getuModel() {
        return uModel;
    }

    public void setuModel(UserModel uModel) {
        this.uModel = uModel;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    String title;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeLong(likes);
        dest.writeDouble(score);
        dest.writeString(content);
        dest.writeString(maBL);
        dest.writeStringList(imageList);
        dest.writeString(user);
        dest.writeString(title);
        dest.writeParcelable(uModel, flags);
    }
}
