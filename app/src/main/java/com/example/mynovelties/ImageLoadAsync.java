package com.example.mynovelties;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

public class ImageLoadAsync extends AsyncTask<String, Void, Bitmap>  {
    ImageView imgV;

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        imgV.setImageBitmap(bitmap);
    }

    public ImageLoadAsync(ImageView imgV) {
        this.imgV = imgV;
    }

    @Override
    protected Bitmap doInBackground(String... urls) {
        String url = urls[0];
        Bitmap myBitmap = null;
        try {
            InputStream in = new java.net.URL(url).openStream();
            myBitmap = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        return myBitmap;
    }
}
