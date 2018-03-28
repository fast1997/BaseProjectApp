package com.sp18.ssu370.WasteYourTime.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Memes {
    @SerializedName("memes")
    private ArrayList<ImgurImage> memes;


    public ArrayList<ImgurImage> getMemes() {
        return memes;
    }
}
