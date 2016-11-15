package zero.tongyang.threegrand.com.x2expro.HomePage.Home_ViewPager;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import zero.tongyang.threegrand.com.x2expro.R;
import zero.tongyang.threegrand.com.x2expro.Static;

/**
 * Created by tongyang on 16-11-12.
 */

public class ListViewAdaptar extends BaseAdapter {
    private List<Map<String, String>> list;
    private LayoutInflater inflater;
    private Context context;
    private Viewholder viewholder = null;
    private ListView listView;
    private AsyncImageLoader asyncImageLoader;

    public ListViewAdaptar(List<Map<String, String>> list, Context context, LayoutInflater inflater, ListView listView) {
        this.list = list;
        this.context = context;
        this.inflater = inflater;
        this.listView = listView;
        asyncImageLoader = new AsyncImageLoader();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            viewholder = new Viewholder();
            view = inflater.inflate(R.layout.list_home, null);
            viewholder.nodename = (TextView) view.findViewById(R.id.nodename);
            viewholder.username = (TextView) view.findViewById(R.id.username);
            viewholder.replice = (TextView) view.findViewById(R.id.replice);
            viewholder.time = (TextView) view.findViewById(R.id.time);
            viewholder.title = (TextView) view.findViewById(R.id.topictitle);
            viewholder.imageView = (ImageView) view.findViewById(R.id.imagView);
            viewholder.lastreplice = (TextView) view.findViewById(R.id.lastreplice);
            view.setTag(viewholder);
        } else {
            viewholder = (Viewholder) view.getTag();
        }
        viewholder.nodename.setText(list.get(i).get("nodetitle"));
        viewholder.username.setText(list.get(i).get("username"));
        if (list.get(i).get("replies").equals("")){
            viewholder.replice.setVisibility(View.GONE);
        }else {
            viewholder.replice.setVisibility(View.VISIBLE);
            viewholder.replice.setText(list.get(i).get("replies"));

        }
        viewholder.time.setText(list.get(i).get("time"));
        viewholder.title.setText(list.get(i).get("title"));
        viewholder.lastreplice.setText(list.get(i).get("lastreplice"));
        viewholder.imageView.setTag(list.get(i).get("img"));

        Log.d("====", list.get(i).get("img").trim());
        Drawable cachedImage = asyncImageLoader.loadDrawable(list.get(i).get("img").trim(), new AsyncImageLoader.ImageCallback() {
            public void imageLoaded(Drawable imageDrawable, String imageUrl) {
                ImageView imageViewByTag = (ImageView) listView.findViewWithTag(imageUrl);
                if (imageViewByTag != null) {
                    imageViewByTag.setImageDrawable(imageDrawable);
                }
            }
        });
        if (cachedImage == null) {
            viewholder.imageView.setImageResource(R.drawable.android);
        } else {
            viewholder.imageView.setImageDrawable(cachedImage);
        }


        return view;
    }

    public class Viewholder {
        TextView username;
        TextView nodename;
        TextView replice;
        TextView time;
        TextView title;
        TextView lastreplice;

        ImageView imageView;


    }
}
