package com.sp18.ssu370.WasteYourTime.network;

import android.os.AsyncTask;

import com.sp18.ssu370.WasteYourTime.model.Album;
import com.sp18.ssu370.WasteYourTime.ui.util.ImgurImageParser;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class AlbumAsyncTask extends AsyncTask<String,String,Album>{

    private String albumUrl ="https://api.imgur.com/3/gallery/album/";
    protected OnAlbumFetchResponse listener;

    public void setListener(OnAlbumFetchResponse listener){this.listener = listener;}

    @Override
    protected Album doInBackground(String... strings) {
        String albumID = strings[1];
        albumUrl += albumID;

        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(albumUrl).newBuilder();

        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "Client-ID 9fca26f6c035661")
                .build();//new attemp for imgur

        Response response = null;
        try {
            response = client.newCall(request).execute();
            if (response != null) {
                return ImgurImageParser.albumFromJson(response.body().string()); }
        } catch (IOException e) {
            // do something with exception
        }
        return null;
    }

    @Override
    protected void onPostExecute(Album album) {
        super.onPostExecute(album);
        listener.onCallback(album);
    }

    public interface OnAlbumFetchResponse {
        void onCallback(Album album);
    }
}
