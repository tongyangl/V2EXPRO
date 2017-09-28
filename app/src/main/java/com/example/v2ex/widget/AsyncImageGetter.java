package com.example.v2ex.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.v2ex.R;
import com.example.v2ex.utils.ScreenUtils;


/**
 * Created by yw on 2015/4/25.
 */
public class AsyncImageGetter implements Html.ImageGetter {

    private Context mContext;
    private TextView mContainer;
    private Drawable mDefaultDrawable;
    private int mMaxWidth;

    public AsyncImageGetter(Context context, TextView container) {
        mContext = context;
        mContainer = container;
         mMaxWidth = ScreenUtils.getDisplayWidth(mContext) -  ScreenUtils.dp(mContext, 100);
         mDefaultDrawable = context.getResources().getDrawable(R.mipmap.ic_launcher);
    }

    @Override
    public Drawable getDrawable(String source) {
        final URLDrawable urlDrawable = new URLDrawable();

        Glide.with(mContext).load(source).asBitmap().error(R.drawable.ic_error).placeholder(R.drawable.ic_loading).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {

                if (bitmap != null) {
                    int width;
                    int height;
                    if (bitmap.getWidth() > mMaxWidth) {
                        width = mMaxWidth;
                        height = mMaxWidth * bitmap.getHeight() / bitmap.getWidth();
                    } else {
                        width = bitmap.getWidth();
                        height = bitmap.getHeight();
                    }
                    Drawable drawable = new BitmapDrawable(mContext.getResources(), bitmap);
                    drawable.setBounds(0, 0, width, height);
                    urlDrawable.setBounds(0, 0, width, height);
                    urlDrawable.mDrawable = drawable;
                    //reset text to invalidate.
                    mContainer.setText(mContainer.getText());
                }
            }
        }); //方法


        return  urlDrawable;
    }
    public class URLDrawable extends BitmapDrawable {

        protected Drawable mDrawable;

        @Override
        public void draw(Canvas canvas) {
            if(mDrawable != null){
                mDrawable.draw(canvas);
            }else{
                mDefaultDrawable.draw(canvas);
            }
        }
    }
}
