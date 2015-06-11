package com.example.admin.someapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.admin.someapp.model.Country;

import java.util.List;

/**
 * Created by ADMIN on 5.6.2015..
 */
public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder>{

    private List<Country> countries;
    private int rowLayout;
    private Context mContext;
    private Bitmap bmp;
    private LruCache<String, Bitmap> mMemoryCache;
    private ProgressBar progBar;

    public CountryAdapter(List<Country> countries, int rowLayout, Context mContext, LruCache<String, Bitmap> mMemoryCache){
        this.countries = countries;
        this.rowLayout = rowLayout;
        this.mContext = mContext;
        this.mMemoryCache = mMemoryCache;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Country country = countries.get(position);

        final String imageKey = String.valueOf(country.getImageResourceId(mContext));
        final Bitmap bitmap = mMemoryCache.get(imageKey);
        if(bitmap != null){
            holder.countryImage.setImageBitmap(bitmap);
        }else {
            holder.mImageLoader = new AsyncImageLoader(mContext, holder, country.name, country.getImageResourceId(mContext), bmp, mMemoryCache);
            holder.mImageLoader.execute();
        }
    }

    @Override
    public int getItemCount() {
        return countries == null ? 0 : countries.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView countryName;
        public ImageView countryImage;
        public ProgressBar progressBar;
        public AsyncImageLoader mImageLoader;

        public ViewHolder(View itemView) {
            super(itemView);
            countryName = (TextView)itemView.findViewById(R.id.countryName);
            countryImage = (ImageView)itemView.findViewById(R.id.countryImage);
            progressBar = (ProgressBar)itemView.findViewById(R.id.progress_bar);
        }
    }
}
