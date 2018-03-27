package com.sp18.ssu370.WasteYourTime.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by andyd on 3/22/2018.
 */

public class ImgurImage {
    @SerializedName("id")
    private String id;
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("animated")
    private String animated;
    @SerializedName("link")
    private String link;
    @SerializedName("name")
    private String name;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getAnimated() {
        return animated;
    }

    public String getLink() {
        return link;
    }

    public String getName() {
        return name;
    }
}
