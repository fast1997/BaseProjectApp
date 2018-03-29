package com.sp18.ssu370.WasteYourTime.ui.util;

import android.os.AsyncTask;
import android.provider.ContactsContract;

import com.sp18.ssu370.WasteYourTime.model.ImgurImage;
import com.sp18.ssu370.WasteYourTime.model.ImgurImageList;
import com.sp18.ssu370.WasteYourTime.model.Memes;

import java.io.IOException;
import java.util.ArrayList;

public class AllImagesAsyncTask extends AsyncTask<ImgurImageList,String,ArrayList<ImgurImage>>{

    protected OnAllImageFetchResponse listener;

    public void setListener(OnAllImageFetchResponse listener) {
        this.listener = listener;
    }

    @Override
    protected ArrayList<ImgurImage> doInBackground(ImgurImageList... imgurImageLists) {
        ImgurImageList gallery = imgurImageLists[0];

        ArrayList<ImgurImage> allPics = new ArrayList<ImgurImage>();
        for(int i = 0; i < 6; i++)
        {
            allPics.addAll(gallery.getData().get(i).getImages());
        }

        return allPics;
    }

    @Override
    protected void onPostExecute(ArrayList<ImgurImage> imgurImages) {
        super.onPostExecute(imgurImages);
        listener.onCallback(imgurImages);
    }


    public interface OnAllImageFetchResponse {
        void onCallback(ArrayList<ImgurImage> imgurImages);
    }
}
