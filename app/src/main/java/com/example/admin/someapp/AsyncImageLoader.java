package com.example.admin.someapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.LruCache;
import android.view.View;


/**
 * Created by Ivo on 7.6.2015..
 */
public class AsyncImageLoader extends AsyncTask<Void, Void, Bitmap> {
    private Context context;
    private CountryAdapter.ViewHolder viewHolder;
    private String countryName;
    private int imageResId;
    private Bitmap bmp;
    private LruCache<String, Bitmap> mMemoryCache;
    public static final int REQ_WIDTH = 500;
    public static final int REQ_HEIGHT = 200;

    public AsyncImageLoader(Context context, CountryAdapter.ViewHolder viewHolder,String countryName, int imageResId, Bitmap bmp, LruCache<String, Bitmap> mMemoryCache){
        this.context = context;
        this.imageResId = imageResId;
        this.bmp = bmp;
        this.mMemoryCache = mMemoryCache;
        this.viewHolder = viewHolder;
        this.countryName = countryName;
    }

    @Override
    protected void onPreExecute() {

        viewHolder.countryImage.setVisibility(View.GONE);
        viewHolder.progressBar.setVisibility(View.VISIBLE);

        super.onPreExecute();
    }

    @Override
    protected Bitmap doInBackground(Void... params) {

        try{
            return decodeAndScale(bmp);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        viewHolder.progressBar.setVisibility(View.GONE);
        viewHolder.countryImage.setVisibility(View.VISIBLE);
        try{
            viewHolder.countryImage.setImageBitmap(bitmap);
            viewHolder.countryName.setText(countryName);
        }catch(Exception e){
            viewHolder.countryImage.setImageResource(R.mipmap.ic_launcher);
        }
        super.onPostExecute(bitmap);
    }

    private Bitmap decodeAndScale(Bitmap bmp){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), imageResId, options);

        options.inSampleSize = calculateInSampleSize(options, REQ_WIDTH, REQ_HEIGHT);

        options.inJustDecodeBounds = false;
        final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), imageResId, options);
        if(mMemoryCache.get(String.valueOf(imageResId)) == null){
            mMemoryCache.put(String.valueOf(imageResId), bitmap);
        }
        return  bitmap;
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight){
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            //Calculate the largest inSampleSize value that is a power of 2 and keeps both
            //height and width larger than requested height and width
            while((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth){
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}
