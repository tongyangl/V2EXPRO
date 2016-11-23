package zero.tongyang.threegrand.com.x2expro.HomePage.Home_ViewPager.Detalis;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by tongyang on 16-11-16.
 */

public class AddView {
    private Context context;
    private LinearLayout linear1;
    private LinearLayout linear;
    private LinearLayout linear2;
    private String tag;

    public AddView(Context context,  LinearLayout linear) {
        this.context = context;
        this.linear = linear;
    }

    private ImageView imageView;

    public void addLinear(String title, String text) {
        Log.d("---==", title + text);
        LinearLayout layout = new LinearLayout(context);
        //layout.setPadding((int) Static.dp2px(this, 10), (int) Static.dp2px(this, 10), (int) Static.dp2px(this, 10), (int) Static.dp2px(this, 10));
        layout.setOrientation(LinearLayout.VERTICAL);
        TextView textView = new TextView(context);
        textView.setPadding(10, 2, 10, 3);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setTextSize(12);
        textView.setTextColor(Color.GRAY);
        textView.setText(title);
        TextView textView1 = new TextView(context);
        textView1.setPadding(10, 5, 10, 5);
        textView1.setGravity(Gravity.CENTER_VERTICAL);
        textView1.setTextSize(12);
        textView1.setTextColor(Color.BLACK);
        textView1.setText(text);
        layout.addView(textView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layout.addView(textView1, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linear1.addView(layout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public void addImageView(String url) {
        Log.d("imgimgimg", url);
        imageView = new ImageView(context);
         imageView.setTag(url);
        tag = url;
        linear.addView(imageView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addimgView addimgView = new addimgView();
        addimgView.execute(url);
    }

    public void addbottomview(String s) {

        TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setText(s);
        textView.setTextColor(Color.GRAY);
        linear2.addView(textView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    class addimgView extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = windowManager.getDefaultDisplay();
            int heigth = display.getHeight();
            int imgheight = imageView.getHeight();
            if (imgheight > heigth) {
                imageView.setImageBitmap(bitmap);

            } else {
                imageView.setImageBitmap(bitmap);
            }
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 4;
                options.inJustDecodeBounds = false;
                Rect rect = new Rect(0, 0, 0, 0);
                return BitmapFactory.decodeStream(url.openStream(), rect, options);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }


    }

    public void addTextView(String s, int size) {
        TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setTextColor(Color.BLACK);
        textView.setText(s);
        textView.setTextSize(size);
        linear.addView(textView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }
}
