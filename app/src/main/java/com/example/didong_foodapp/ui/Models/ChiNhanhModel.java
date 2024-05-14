package com.example.didong_foodapp.ui.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ChiNhanhModel implements Parcelable {
    String diachi;
    Double latitude;
    public ChiNhanhModel(){}

    protected ChiNhanhModel(Parcel in) {
        diachi = in.readString();
        if (in.readByte() == 0) {
            latitude = null;
        } else {
            latitude = in.readDouble();
        }
        if (in.readByte() == 0) {
            longitude = null;
        } else {
            longitude = in.readDouble();
        }
        if (in.readByte() == 0) {
            distance = null;
        } else {
            distance = in.readDouble();
        }
    }

    public static final Creator<ChiNhanhModel> CREATOR = new Creator<ChiNhanhModel>() {
        @Override
        public ChiNhanhModel createFromParcel(Parcel in) {
            return new ChiNhanhModel(in);
        }

        @Override
        public ChiNhanhModel[] newArray(int size) {
            return new ChiNhanhModel[size];
        }
    };

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    Double longitude;
    Double distance;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(diachi);
        if (latitude == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(latitude);
        }
        if (longitude == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(longitude);
        }
        if (distance == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(distance);
        }
    }
}
