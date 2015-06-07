package com.example.admin.someapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

    public CountryAdapter(List<Country> countries, int rowLayout, Context mContext){
        this.countries = countries;
        this.rowLayout = rowLayout;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Country country = countries.get(position);

        //holder.countryName.setText(country.name);
        //holder.countryImage.setImageDrawable(mContext.getDrawable(country.getImageResourceId(mContext)));

        holder.mImageLoader = new AsyncImageLoader(mContext, holder.countryImage, country.getImageResourceId(mContext), bmp);
        holder.mImageLoader.execute();
        holder.countryName.setText(country.name);
    }

    @Override
    public int getItemCount() {
        return countries == null ? 0 : countries.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView countryName;
        public ImageView countryImage;
        public AsyncImageLoader mImageLoader;

        public ViewHolder(View itemView) {
            super(itemView);
            countryName = (TextView)itemView.findViewById(R.id.countryName);
            countryImage = (ImageView)itemView.findViewById(R.id.countryImage);
        }
    }
}
