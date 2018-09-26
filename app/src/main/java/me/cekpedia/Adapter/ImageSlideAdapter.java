package me.cekpedia.Adapter;

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import me.cekpedia.Fragment.HomeFragment;
import me.cekpedia.R;

/**
 * Created by rezadwihendarno on 18/04/2018.
 */

public class ImageSlideAdapter extends PagerAdapter {

    private int icons[];
    private LayoutInflater inflater;
    private Context context;

    public ImageSlideAdapter(Context context, int[] icons) {
        this.context = context;
        this.icons = icons;

    }

    @Override
    public int getCount() {
        return icons.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.sliding_images, null);

        ImageView img = (ImageView) view.findViewById(R.id.slider);
        img.setImageResource(icons[position]);

        ViewPager vp = (ViewPager) container;
        vp.addView(view,0);
        return  view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);
    }
}