package com.example.admin.someapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.someapp.model.Country;

import java.util.List;


public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder>{

    private List<Country> countries;
    private int rowLayout;
    private Context mContext;
    private Bitmap bmp;
    private LruCache<String, Bitmap> mMemoryCache;

    public CountryAdapter(List<Country> countries, int rowLayout, Context mContext, LruCache<String, Bitmap> mMemoryCache){
        this.countries = countries;
        this.rowLayout = rowLayout;
        this.mContext = mContext;
        this.mMemoryCache = mMemoryCache;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ViewHolder(view, new CountryAdapter.ViewHolder.MyClickListener() {
            @Override
            public void itemClicked(View view, int position) {
                countries.remove(position);
                notifyItemRemoved(position);
            }
        });
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Country country = countries.get(position);

        final String imageKey = String.valueOf(country.getImageResourceId(mContext));
        final Bitmap bitmap = mMemoryCache.get(imageKey);
        if(bitmap != null){
            holder.countryImage.setImageBitmap(bitmap);
            holder.countryName.setText(country.name);
        }else {
            holder.mImageLoader = new AsyncImageLoader(mContext, holder, country.name, country.getImageResourceId(mContext), bmp, mMemoryCache);
            holder.mImageLoader.execute();
        }
    }

    @Override
    public int getItemCount() {
        return countries == null ? 0 : countries.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView countryName;
        public ImageView countryImage;
        public ProgressBar progressBar;
        public AsyncImageLoader mImageLoader;
        public MyClickListener myClickListener;

        public ViewHolder(View itemView, MyClickListener clickListener) {
            super(itemView);

            this.myClickListener = clickListener;
            countryName = (TextView)itemView.findViewById(R.id.countryName);
            countryImage = (ImageView)itemView.findViewById(R.id.countryImage);
            progressBar = (ProgressBar)itemView.findViewById(R.id.progress_bar);

            countryName.setOnClickListener(this);
            //itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.itemClicked(v, getAdapterPosition());
        }

        public interface MyClickListener{
            void itemClicked(View view, int position);
        }
    }
}
