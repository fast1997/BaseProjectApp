package com.sp18.ssu370.WasteYourTime.network;

import android.os.AsyncTask;

import com.sp18.ssu370.WasteYourTime.model.ImgurImageList;
import com.sp18.ssu370.WasteYourTime.ui.util.ImgurImageParser;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;


public  class ImgurImageAsyncTask  extends AsyncTask<String,String,ImgurImageList> {

    private String baseApiUrl = "https://api.imgur.com";
    private String apiKey = "f04b6cbae2ef173f97c38ca71f2bb09d7b3e2150";
    private String appId = "0d38af84e4e2e36";

    protected OnImgurImageFetchResponse listener;

    public void setListener(OnImgurImageFetchResponse listener) {
        this.listener = listener;
    }

    @Override
    protected ImgurImageList doInBackground(String... strings) {
        String searchParams = strings[0];
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(baseApiUrl).newBuilder();
        urlBuilder.addQueryParameter("_app_key", apiKey);
        urlBuilder.addQueryParameter("_app_id", appId);
        urlBuilder.addQueryParameter("your_search_parameters", searchParams);
        String url = urlBuilder.build().toString();
        Request request = new Request.Builder().url(url).build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            if (response != null) {
                return ImgurImageParser.imgurImageListFromJson(response.body().string()); }
        } catch (IOException e) {
            // do something with exception
        }
        return null;
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
