package zero.tongyang.threegrand.com.x2expro.HomePage.Home_ViewPager.Detalis;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
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


    public AddView(Context context, LinearLayout linear1, LinearLayout linear) {
        this.context = context;
        this.linear1 = linear1;
        this.linear = linear;
    }

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

        ImageView imageView = new ImageView(context);
        try {
            URL url1 = new URL(url);
            imageView.setImageBitmap(BitmapFactory.decodeStream(url1.openStream()));
            linear.addView(imageView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public void addTextView(String s) {
        TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setTextColor(Color.BLACK);
        textView.setText(s);
        textView.setTextSize(12);
        linear.addView(textView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);


    }
}
