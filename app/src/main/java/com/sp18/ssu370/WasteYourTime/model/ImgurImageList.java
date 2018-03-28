package com.sp18.ssu370.WasteYourTime.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by andyd on 3/22/2018.
 */

public class ImgurImageList {


    @SerializedName("data")
    private ArrayList<Memes> data;

    public String getId() {
        return id;
    }

    @SerializedName("id")
    private String id;


    public ArrayList<Memes> getData() {
        return data;
    }


}
