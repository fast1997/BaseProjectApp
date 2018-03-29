package com.sp18.ssu370.WasteYourTime.ui.view;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sp18.ssu370.WasteYourTime.model.ImgurImage;
import com.sp18.ssu370.WasteYourTime.model.ImgurImageList;
import com.sp18.ssu370.WasteYourTime.model.Memes;
import com.sp18.ssu370.WasteYourTime.ui.activities.ImageActivity;
import com.sp18.ssu370.WasteYourTime.ui.util.AllImagesAsyncTask;
import com.sp18.ssu370.baseprojectapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private ImgurImageList imgList;
    private ArrayList<ImgurImage> allImages;
    private int imgListIdx = 1;
    private AllImagesAsyncTask task;

    public RecyclerViewAdapter(Context mContext, ImgurImageList imgList) {
        this.mContext = mContext;
        this.imgList = imgList;

        task = new AllImagesAsyncTask();
        task.setListener(new AllImagesAsyncTask.OnAllImageFetchResponse() {
            @Override
            public void onCallback(ArrayList<ImgurImage> imgurImages) {
                allImages = imgurImages;
            }
        });
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.image_view, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.imageTitle.setText(imgList.getData().get(imgListIdx).getImages().get(position).getName());
        //if(imgList.getData().get(imgListIdx).getImages().get(position).isAnimated())
        Picasso.get().load(imgList.getData().get(imgListIdx).getImages().get(position).getUrl())
                .fit()
                .into(holder.img_thumbnail);

        //Set click listener
        holder.imgurImageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, ImageActivity.class);
                //pass data to image activity
                intent.putExtra("Title",imgList.getData().get(imgListIdx).getImages().get(position).getName() );
                intent.putExtra("Thumbnail",imgList.getData().get(imgListIdx).getImages().get(position).getUrl());
                intent.putExtra("Description",imgList.getData().get(imgListIdx).getImages().get(position).getDescription());
                intent.putExtra("Animated", imgList.getData().get(imgListIdx).getImages().get(position).isAnimated());

                //start the activity
                mContext.startActivity(intent);

            }
        });

        //imgListIdx++;
    }

    @Override
    public int getItemCount() {
        return imgList.getData().get(imgListIdx).getImages().size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView imageTitle;
        ImageView img_thumbnail;
        View imgurImageView;

        public MyViewHolder(View itemView){
            super(itemView);

            imageTitle = itemView.findViewById(R.id.meme_title_id);
            img_thumbnail = itemView.findViewById(R.id.meme_image_id);
            imgurImageView = itemView.findViewById(R.id.image_view_id);
        }
    }
}
