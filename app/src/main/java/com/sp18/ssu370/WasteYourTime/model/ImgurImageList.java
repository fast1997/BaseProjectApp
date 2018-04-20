package com.sp18.ssu370.WasteYourTime.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by andyd on 3/22/2018.
 */

public class ImgurImageList implements Parcelable{


    @SerializedName("data")
    private ArrayList<Memes> data;

    @SuppressWarnings("unchecked")
    private ImgurImageList(Parcel in) {
        data = new ArrayList<Memes>();
        data = in.readArrayList(Memes.class.getClassLoader());
    }

    public ImgurImageList(ImgurImageList i){
        data = i.data;
    }

    public ImgurImageList(ArrayList<Memes> data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<ImgurImageList> CREATOR
            = new Parcelable.Creator<ImgurImageList>() {
        public ImgurImageList createFromParcel(Parcel in) {
            return new ImgurImageList(in);
        }

        public ImgurImageList[] newArray(int size) {
            return new ImgurImageList[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(data);
    }

    public ArrayList<Memes> getData() {
        return data;
    }

    public void setData(ArrayList<Memes> data) {
        this.data = data;
    }
}
