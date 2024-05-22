package com.example.didong_foodapp.ui.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Objects;

public class CartModel implements Parcelable {
    String image;
    String name,price, qty;

    public CartModel(){};
    public CartModel(String image,String qty, String name,String price)
    {
        this.qty = qty;
        this.image = image;
        this.name = name;
        this.price = price;
    }

    protected CartModel(Parcel in) {
        image = in.readString();
        name = in.readString();
        price = in.readString();
        qty = in.readString();
    }

    public static final Creator<CartModel> CREATOR = new Creator<CartModel>() {
        @Override
        public CartModel createFromParcel(Parcel in) {
            return new CartModel(in);
        }

        @Override
        public CartModel[] newArray(int size) {
            return new CartModel[size];
        }
    };

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartModel cartModel = (CartModel) o;
        return Objects.equals(image, cartModel.image) &&
                Objects.equals(name, cartModel.name) &&
                Objects.equals(price, cartModel.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(image, qty, name, price);
    }


    public void ThemCartModel(){};

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(image);
        dest.writeString(name);
        dest.writeString(price);
        dest.writeString(qty);
    }
}
