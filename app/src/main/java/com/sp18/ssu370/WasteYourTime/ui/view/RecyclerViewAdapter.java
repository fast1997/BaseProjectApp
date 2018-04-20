package com.sp18.ssu370.WasteYourTime.ui.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.sp18.ssu370.WasteYourTime.model.ImgurImage;
import com.sp18.ssu370.WasteYourTime.model.ImgurImageList;
import com.sp18.ssu370.WasteYourTime.ui.activities.ImageActivity;
import com.sp18.ssu370.baseprojectapp.R;

import java.io.Serializable;
import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private ImgurImageList imgList;

    public int getCurrentPosition() {
        return currentPosition;
    }

    private int currentPosition;

    public RecyclerViewAdapter(Context mContext, ImgurImageList imgList) {
        this.mContext = mContext;
        this.imgList = imgList;
        currentPosition = 0;
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
        currentPosition = position;
        //if( imgList.getData().get(position) != null ) {
         //   if( imgList.getData().get(position).getImages() != null) {
                final ImgurImage img = imgList.getData().get(position).getImages().get(0);

                if(imgList.getData().get(position).getTitle().length() < 18 ) {
                    holder.imageTitle.setText(imgList.getData().get(position).getTitle());
                }
                else{
                    String cutName = imgList.getData().get(position).getTitle().substring(0,17) + "...";
                    holder.imageTitle.setText(cutName);
                }
                //holder.imageTitle.setText(imgList.getData().get(position).getTitle());

                if(img.isAnimated()){
                    GlideDrawableImageViewTarget gifLoad = new GlideDrawableImageViewTarget(holder.img_thumbnail);

                    Glide.with(mContext)
                            .load(img.getUrl())
                            .into(gifLoad);
                }
                else{
                    Glide.with(mContext)
                            .load(img.getUrl())
                            .into(holder.img_thumbnail);
                }


                //Set click listener
                holder.imgurImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Bundle args = new Bundle();
                        args.putSerializable("ImageArrayList", imgList.getData().get(position).getImages());
                        Intent intent = new Intent(mContext, ImageActivity.class);
                        //pass data to image activity
                        intent.putExtra("GalleryTitle", imgList.getData().get(position).getTitle());
                        intent.putExtra("AllImg", args);
                        intent.putExtra("GalleryID",imgList.getData().get(position).getId());
                        //start the activity
                        mContext.startActivity(intent);

                    }
                });
         //   }
           // else{
           //     return;
           // }
       /* }
        else{
            return;
        }*/
        //imgListIdx++;
    }

    @Override
    public int getItemCount() {
        if(imgList == null)
            return 0;
        return imgList.getData().size();
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
