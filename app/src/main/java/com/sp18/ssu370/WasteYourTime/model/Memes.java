package com.sp18.ssu370.WasteYourTime.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Memes implements Parcelable, Comparable<Memes>{

    private boolean newest, oldest, viral = false;

    public Memes(ArrayList<ImgurImage> images) {
        this.images = images;
    }

    public Memes(ArrayList<ImgurImage> images, String title) {
        this.title = title;
        this.images = images;
    }

    public void setNewest(boolean newest) {
        this.newest = newest;
    }

    public void setOldest(boolean oldest) {
        this.oldest = oldest;
    }

    public void setViral(boolean viral) {
        this.viral = viral;
    }

    @SerializedName("id")
    private String id;
    @SerializedName("title")
    private String title;
    @SerializedName("images")
    private ArrayList<ImgurImage> images;
    @SerializedName("link")
    private String link;
    @SerializedName("ups")
    private int ups;
    @SerializedName("datetime")
    private int datetime;

    public int getUps() {
        return ups;
    }


    public int getDatetime() {
        return datetime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @SuppressWarnings("unchecked")
    private Memes(Parcel in) {
        id = in.readString();
        title = in.readString();
        images = new ArrayList<ImgurImage>();
        images = in.readArrayList(ImgurImage.class.getClassLoader());
    }

    public static final Parcelable.Creator<Memes> CREATOR
            = new Parcelable.Creator<Memes>() {
        public Memes createFromParcel(Parcel in) {
            return new Memes(in);
        }

        public Memes[] newArray(int size) {
            return new Memes[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeList(images);
    }

    public ArrayList<ImgurImage> getImages() {
        return images;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    @Override
    public int compareTo(@NonNull Memes memes) {
            if(viral){
                return memes.ups - ups;
            }
            else if(newest){
                return datetime - memes.datetime;
            }
            else if(oldest){
                return memes.datetime - datetime;
            }
            return 0;
    }
}
