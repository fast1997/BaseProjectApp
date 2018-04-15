package com.sp18.ssu370.WasteYourTime.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * Created by andyd on 3/22/2018.
 */

public class ImgurImage implements Parcelable{

    @SerializedName("id")
    private String id;
    @SerializedName("title")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("animated")
    private boolean animated;
    @SerializedName("width")
    private int width;
    @SerializedName("height")
    private int height;
    @SerializedName("link")
    private String url;


    private ImgurImage(Parcel in) {
        id = in.readString();
        name = in.readString();
        description = in.readString();
        animated = in.readByte() != 0;
        width = in.readInt();
        height = in.readInt();
        url = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<ImgurImage> CREATOR
            = new Parcelable.Creator<ImgurImage>() {
        public ImgurImage createFromParcel(Parcel in) {
            return new ImgurImage(in);
        }

        public ImgurImage[] newArray(int size) {
            return new ImgurImage[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeByte((byte) (animated ? 1 : 0));
        dest.writeInt(width);
        dest.writeInt(height);
        dest.writeString(url);
    }



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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }



}
