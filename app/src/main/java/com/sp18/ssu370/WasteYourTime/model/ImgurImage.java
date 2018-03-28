package com.sp18.ssu370.WasteYourTime.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by andyd on 3/22/2018.
 */

public class ImgurImage {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("url")
    private String url;


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
