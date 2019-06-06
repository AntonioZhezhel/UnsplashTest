package com.example.unsplashtest;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private final List<Photo> photoList;
    private Context mContrxt;


    public Adapter(List<Photo> photos, Context context) {
        photoList = photos;
        mContrxt = context;

    }

    @Override
    public Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        Photo photo = photoList.get(position);


        Picasso.get()
                .load(photo.getUrls().getRegular())
                .resize(300, 300)
                .centerCrop()
                .into(holder.imageView);

    }

    public void addPhotos(List<Photo> photos) {
        int lastCount = getItemCount();
        photoList.addAll(photos);
        notifyItemRangeInserted(lastCount, photos.size());
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.imgview);
        }
    }


}
