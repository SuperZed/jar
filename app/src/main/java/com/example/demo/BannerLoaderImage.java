package com.example.demo;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.plugin.library.banner.loader.ImageLoader;

/**
 * Created by xiezh on 2017/1/20.
 */

public class BannerLoaderImage extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context).load(path.toString()).into(imageView);
    }
}
