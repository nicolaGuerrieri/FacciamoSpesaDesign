package com.test.nicolaguerrieri.facciamospesadesign.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.test.nicolaguerrieri.facciamospesadesign.R;

/**
 * Created by nicola.guerrieri2 on 02/10/2015.
 */
public class AdapterGallery  extends BaseAdapter {
    private Context context;
    private int itemBackground;
    Integer[] imageIDs;
    public AdapterGallery(Context c, Integer[] imageIDs)
    {
        context = c;
        this.imageIDs = imageIDs;
        // sets a grey background; wraps around the images
//        TypedArray a = obtainStyledAttributes(R.styleable.MyGallery);
//        itemBackground = a.getResourceId(R.styleable.MyGallery_android_galleryItemBackground, 0);
//        a.recycle();
    }
    // returns the number of images
    public int getCount() {
        return imageIDs.length;
    }
    // returns the ID of an item
    public Object getItem(int position) {
        return position;
    }
    // returns the ID of an item
    public long getItemId(int position) {
        return position;
    }
    // returns an ImageView view
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(imageIDs[position]);
        imageView.setLayoutParams(new Gallery.LayoutParams(500, 500));
//        imageView.setBackgroundResource(itemBackground);
        return imageView;
    }
}