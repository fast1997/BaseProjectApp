package com.sp18.ssu370.WasteYourTime.ui.util;

import com.google.gson.Gson;
import com.sp18.ssu370.WasteYourTime.model.Album;
import com.sp18.ssu370.WasteYourTime.model.ImgurImageList;


/**
 * Created by andyd on 3/22/2018.
 */

public class ImgurImageParser {
    public static final ImgurImageList imgurImageListFromJson(String responseString){
        Gson gson = new Gson();
        return gson.fromJson(responseString, ImgurImageList.class);
    }

    public static final Album albumFromJson(String responseString){
        Gson gson = new Gson();
        return gson.fromJson(responseString, Album.class);
    }
}
