package com.example.kunshswastika.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.kunshswastika.Model.Slider_Model;
import com.example.kunshswastika.R;

import java.util.List;

public class AutoImageSliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;
    List<Slider_Model> slider_models;

    public AutoImageSliderAdapter(Context context, List<Slider_Model> slider_models) {
        this.context = context;
        this.slider_models = slider_models;
    }



    @Override
    public int getCount() {
        return slider_models.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout)object;
    }

    public void setItems(List<Slider_Model> slider_models) {
        this.slider_models = slider_models;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.banner_auto_slider_layout,container,false);

        ImageView image = view.findViewById(R.id.image);

        displayImageOriginal(context, image, slider_models, position);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((RelativeLayout)object);
    }


    private static void displayImageOriginal(Context context, ImageView img, List<Slider_Model> slider_models, int position) {
        try {
            Glide.with(context).load(slider_models.get(position))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(img);
        } catch (Exception e) {
            e.getMessage();
        }
    }



}
