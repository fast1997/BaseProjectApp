package com.sp18.ssu370.WasteYourTime.ui.util;

import com.google.gson.Gson;
import com.sp18.ssu370.WasteYourTime.model.Album;
import com.sp18.ssu370.WasteYourTime.model.ImgurImageList;
import com.sp18.ssu370.WasteYourTime.model.Memes;

import java.util.ArrayList;


/**
 * Created by andyd on 3/22/2018.
 */

public class ImgurImageParser {
    public static final ImgurImageList imgurImageListFromJson(String responseString){
        Gson gson = new Gson();
        ImgurImageList temp =  gson.fromJson(responseString, ImgurImageList.class);

        ArrayList<Memes> contents = new ArrayList<Memes>();
        for(int i = 0; i < temp.getData().size(); i++){
            if( temp.getData().get(i) != null){
                if(temp.getData().get(i).getImages() != null){
                    contents.add(temp.getData().get(i));
                }

            }
        }

        return new ImgurImageList(contents);
    }

    public static final Album albumFromJson(String responseString){
        Gson gson = new Gson();
        return gson.fromJson(responseString, Album.class);
    }
}
