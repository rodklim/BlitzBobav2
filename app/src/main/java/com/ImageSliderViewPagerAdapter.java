package com;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import blitzboba.blitzbobav2.R;

/**
 * Created by Rodrigo on 10/14/2017.
 */

public class ImageSliderViewPagerAdapter extends PagerAdapter {


    private Context context;
    private LayoutInflater layoutInflater;
    private Integer [] images = {R.drawable.bettyandthecrew, R.drawable.assorteddrinks, R.drawable.bettyandcustomer};

    public ImageSliderViewPagerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        //TODO host images on firebase
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.image_slider_item, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.image_slider_imageView);
//        imageView.setImageResource(images[position]);
        Glide.with(context).load(images[position]).into(imageView);
        ViewPager viewPager = (ViewPager) container;
        viewPager.addView(view, 0);


        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager viewPager = (ViewPager) container;
        View view = (View) object;

        viewPager.removeView(view);
    }
}
