package com.example.proyecto_final.ui;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.proyecto_final.R;
import com.example.proyecto_final.entities.Image;

import java.util.ArrayList;
import java.util.List;

public class PhotoUriAdapter  extends RecyclerView.Adapter<PhotoUriAdapter.PhotoUriViewHolder> {

    private List<Uri> photoList = new ArrayList<>();
    private Context mContext;

    public PhotoUriAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setList(List<Uri> list){
        this.photoList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PhotoUriViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_item, parent, false);
        return new PhotoUriViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoUriViewHolder holder, int position) {
        if(photoList.get(position) != null){
            holder.imgPhoto.setImageURI(photoList.get(position));
        }else{
            holder.imgPhoto.setImageResource(R.drawable.ic_image_not_found);
        }
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    public class PhotoUriViewHolder extends RecyclerView.ViewHolder{
        ImageView imgPhoto;
        public PhotoUriViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_photo);
        }
    }
}
