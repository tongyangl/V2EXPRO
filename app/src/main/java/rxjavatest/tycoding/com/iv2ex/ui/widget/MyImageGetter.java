package rxjavatest.tycoding.com.iv2ex.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import rxjavatest.tycoding.com.iv2ex.R;
import rxjavatest.tycoding.com.iv2ex.utils.MyImageLoader;
import rxjavatest.tycoding.com.iv2ex.utils.ScreenUtils;

/**
 * Created by 佟杨 on 2017/5/7.
 */

public class MyImageGetter implements Html.ImageGetter {
    private Context mContext;
    private TextView mContainer;
    private Drawable mDefaultDrawable;
    private int mMaxWidth;

    public MyImageGetter(Context context, TextView container) {
        mContext = context;
        mContainer = container;
        mMaxWidth = ScreenUtils.getDisplayWidth(mContext) - ScreenUtils.dp(mContext, 100);
        mDefaultDrawable = context.getResources().getDrawable(R.drawable.ic_launcher);
    }

    @Override
    public Drawable getDrawable(String source) {

        Log.d("sssss",source);
        Bitmap bitmap = MyImageLoader.getInstance().LoadImage(source, mContext);
        final URLDrawable urlDrawable = new URLDrawable();

        if (bitmap != null) {
            Log.d("sssss",source);
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
        return urlDrawable;

    }
    class URLDrawable extends BitmapDrawable{

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