package com.example.v2ex.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by 佟杨 on 2017/9/4.
 */

public class LoadImg {


    public static void LoadImage(String url, ImageView imageView, Context context) {
        Glide.with(context)
                .load(url)
                .into(imageView);


    }
}
