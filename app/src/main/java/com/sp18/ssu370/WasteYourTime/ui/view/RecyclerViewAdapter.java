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
import com.sp18.ssu370.WasteYourTime.ui.activities.ImageActivity;
import com.sp18.ssu370.baseprojectapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private ImgurImageList imgList;

    public RecyclerViewAdapter(Context mContext, ImgurImageList imgList) {
        this.mContext = mContext;
        this.imgList = imgList;
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

        holder.imageTitle.setText(imgList.getImgurImage().getMemes().get(position).getName());
        Picasso.get().load(imgList.getImgurImage().getMemes().get(position).getUrl()).into(holder.img_thumbnail);

        //Set click listener
        holder.imgurImageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, ImageActivity.class);
                //pass data to image activity
                intent.putExtra("Title",imgList.getImgurImage().getMemes().get(position).getName() );
                intent.putExtra("Thumbnail",imgList.getImgurImage().getMemes().get(position).getUrl() );
                intent.putExtra("Description","Killer whales are actually dolphins." );

                //start the activity
                mContext.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return imgList.getImgurImage().getMemes().size();
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
