package com.sp18.ssu370.WasteYourTime.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class Album implements Parcelable {
    @SerializedName("data")
    private Memes singleData;

    @SuppressWarnings("unchecked")
    private Album(Parcel in) {
        singleData = in.readParcelable(Memes.class.getClassLoader());
    }


    public Memes getSingleData() {
        return singleData;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Album> CREATOR
            = new Parcelable.Creator<Album>() {
        public Album createFromParcel(Parcel in) {
            return new Album(in);
        }

        public Album[] newArray(int size) {
            return new Album[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(singleData,flags);
    }
}
