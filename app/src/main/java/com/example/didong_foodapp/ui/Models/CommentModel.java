package com.example.didong_foodapp.ui.Models;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
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
    public void ThemBinhLuan(String maR,CommentModel comModel,final List<String> listImage){
        DatabaseReference nodeComment= FirebaseDatabase.getInstance().getReference().child("commentR");
        String key = nodeComment.child(maR).push().getKey();

        nodeComment.child(maR).child(key).setValue(comModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    if(listImage.size()>0) {
                        for (String valueImage : listImage) {
                            Uri uri = Uri.fromFile(new File(valueImage));
                            StorageReference storageRef = FirebaseStorage.getInstance().getReference().child(uri.getLastPathSegment());
                            storageRef.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                                }
                            });
                        }
                    }
                }
            }
        });
        if(listImage.size()>0) {
            for (String valueImage : listImage) {
                Uri uri = Uri.fromFile(new File(valueImage));
                FirebaseDatabase.getInstance().getReference().child("imageComment").child(key).push().setValue(uri.getLastPathSegment());
            }
        }
    }

}
