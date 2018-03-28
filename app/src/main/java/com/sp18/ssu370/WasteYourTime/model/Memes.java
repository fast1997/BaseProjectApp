package com.sp18.ssu370.WasteYourTime.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Memes {
    @SerializedName("id")
    private String id;
    @SerializedName("images")
    private ArrayList<ImgurImage> images;



    public ArrayList<ImgurImage> getImages() {
        return images;
    }

    public String getId() {
        return id;
    }
}
