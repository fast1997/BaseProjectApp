package com.sp18.ssu370.WasteYourTime.network;

import android.os.AsyncTask;
import android.util.Log;

import com.sp18.ssu370.WasteYourTime.model.ImgurImageList;
import com.sp18.ssu370.WasteYourTime.model.Memes;
import com.sp18.ssu370.WasteYourTime.ui.util.ImgurImageParser;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


public  class ImgurImageAsyncTask  extends AsyncTask<String,String,ImgurImageList> {

    //private String baseApiUrl = "https://api.imgflip.com/get_memes"; //For base imgflip
    private String baseApiUrl = "https://api.imgur.com/3/gallery/search/";
    private String homeApiUrl = "https://api.imgur.com/3/gallery/user/time/";


    protected OnImgurImageFetchResponse listener;

    public void setListener(OnImgurImageFetchResponse listener) {
        this.listener = listener;
    }

    @Override
    protected ImgurImageList doInBackground(String... strings) {
        Random random = new Random();
        int randPage = random.nextInt(10) + 5;
        homeApiUrl += randPage + "day/2?showViral=true&mature=true&album_previews=true";

        boolean searching = false;
        String searchParams = "";
        String urlToBuild = "";

        if(strings[0].equals("home") ) {
            urlToBuild = homeApiUrl;

        }
        else {
            urlToBuild = baseApiUrl;
            searchParams = strings[0];
            searching = true;
        }
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(urlToBuild).newBuilder();

        if(searching) {
            urlBuilder.addQueryParameter("q", searchParams);
        }
        String url = urlBuilder.build().toString();

        //Request request = new Request.Builder().url(url).build();//base
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "Client-ID 9fca26f6c035661")
                .build();//new attemp for imgur


        Response response = null;
        try {
            response = client.newCall(request).execute();
            if (response != null) {
                return ImgurImageParser.imgurImageListFromJson(response.body().string()); }
        } catch (IOException e) {
            // do something with exception
            e.printStackTrace();
        }


        return new ImgurImageList(new ArrayList<Memes>());
    }

    @Override
    protected void onPostExecute(ImgurImageList imgurImageList) {
        super.onPostExecute(imgurImageList);
        listener.onCallback(imgurImageList);
    }

    public interface OnImgurImageFetchResponse {
        void onCallback(ImgurImageList imgurImageList);
    }
}
