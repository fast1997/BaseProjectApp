package com.sp18.ssu370.WasteYourTime.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by andyd on 3/22/2018.
 */

public class ImgurImage {

    @SerializedName("id")
    private String id;
    @SerializedName("title")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("link")
    private String url;
    @SerializedName("animated")
    private boolean animated;





    public boolean isAnimated() {
        return animated;
    }


    public String getDescription() {
        return description;
    }

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
