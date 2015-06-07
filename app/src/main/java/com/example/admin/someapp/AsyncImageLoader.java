package com.example.admin.someapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Ivo on 7.6.2015..
 */
public class AsyncImageLoader extends AsyncTask<Void, Void, Bitmap> {
    private Context context;
    private ImageView imageView;
    private int imageResId;
    private Bitmap bmp;
    private int sampleSize;

    public AsyncImageLoader(Context context, ImageView imageView, int imageResId, Bitmap bmp){
        this.context = context;
        this.imageView = imageView;
        this.imageResId = imageResId;
        this.bmp = bmp;
    }

    @Override
    protected void onPreExecute() {
        imageView.setVisibility(View.GONE);
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
        imageView.setVisibility(View.VISIBLE);
        try{
            imageView.setImageBitmap(bitmap);
        }catch(Exception e){
            imageView.setImageResource(R.mipmap.ic_launcher);
        }
        super.onPostExecute(bitmap);
    }

    private Bitmap decodeAndScale(Bitmap bmp){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = setSampleSize();

        return BitmapFactory.decodeResource(context.getResources(), imageResId, options);
    }

    private int setSampleSize(){
        if(getScreenWidth((Activity)context ) >= 320){
            sampleSize = 2;
        }
        return sampleSize;
    }

    public static int getScreenWidth(Activity activity){
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float density = activity.getResources().getDisplayMetrics().density;
        float dpWidth = outMetrics.widthPixels / density;

        return (int)dpWidth;
    }
}
