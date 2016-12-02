package zero.tongyang.threegrand.com.x2expro.Adaptar;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.zzhoujay.richtext.ImageHolder;
import com.zzhoujay.richtext.RichText;
import com.zzhoujay.richtext.callback.ImageFixCallback;
import com.zzhoujay.richtext.callback.OnURLClickListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import zero.tongyang.threegrand.com.x2expro.R;
import zero.tongyang.threegrand.com.x2expro.Utils.AsyncImageLoader;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by tongyang on 16-11-16.
 */

public class TopicRepliceAdaptar extends BaseAdapter implements OnURLClickListener {

    private List<Map<String, String>> list;
    private LayoutInflater inflater;
    private viewHolder viewHolder;
    private ListView lv;
    private AsyncImageLoader asyncImageLoader;
    private Context context;

    public TopicRepliceAdaptar(LayoutInflater inflater, List<Map<String, String>> list, ListView lv, Context context) {
        this.inflater = inflater;
        this.list = list;
        this.lv = lv;
        this.context = context;
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
            viewHolder = new viewHolder();
            view = inflater.inflate(R.layout.list_topic_replice, null);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.imagView);
            viewHolder.username = (TextView) view.findViewById(R.id.username);
            viewHolder.time = (TextView) view.findViewById(R.id.time);
            viewHolder.replice = (TextView) view.findViewById(R.id.replice_content);
            view.setTag(viewHolder);

        } else {
            viewHolder = (TopicRepliceAdaptar.viewHolder) view.getTag();
        }
       /* SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd mm:ss");
        Long t = new Long(list.get(i).get("created"));
        Long d = Calendar.getInstance().getTimeInMillis();
        Log.d("----", d + "ddd");
        String a = sdf.format(t * 1000);
        long day = (d - t*1000) / (24 * 60 * 60 * 1000);
        long hour = ((d - t*1000) / ((60 * 60 * 1000)) - day * 24);
        long min = (((d - t*1000) / (60 * 1000)) - day * 24 - hour * 60);
        long sec = ((d - t*1000) / 1000 - day * 24 * 24 * 60 - hour * 60 * 60 - min * 60);
         if (min == 0) {
            a = sec + "秒前";
        } else if (hour == 0) {

            a = min + "分" + sec + "秒前";
        } else if (day == 0) {
            a = hour + "小时" + min + "分前"; /*//**//**//*+ sec + "秒前";*//**//**//**//*

        } else {

            a =day+"天"+ hour + "小时前" ;/*//**//**//*+ min + "分" + sec + "秒前";*//**//**//**//*
        }*/

        viewHolder.time.setText(list.get(i).get("time"));
        viewHolder.username.setText(list.get(i).get("username"));
        viewHolder.imageView.setTag(list.get(i).get("img"));
        asyncImageLoader = new AsyncImageLoader();
        Drawable cachedImage = asyncImageLoader.loadDrawable(list.get(i).get("img").trim(), new AsyncImageLoader.ImageCallback() {
            public void imageLoaded(Drawable imageDrawable, String imageUrl) {
                ImageView imageViewByTag = (ImageView) lv.findViewWithTag(imageUrl);
                if (imageViewByTag != null) {
                    imageViewByTag.setImageDrawable(imageDrawable);
                }
            }
        });
        if (cachedImage == null) {
            viewHolder.imageView.setImageResource(R.drawable.moren);
        } else {
            viewHolder.imageView.setImageDrawable(cachedImage);
        }
        RichText.fromHtml(list.get(i).get("content")).autoFix(true).fix(new ImageFixCallback() {
            @Override
            public void onFix(ImageHolder holder) {
                holder.setImageType(ImageHolder.ImageType.JPG);
            }
        }).clickable(true).urlClick(this).into(viewHolder.replice);
        return view;
    }

    class viewHolder {

        TextView username;
        TextView time;
        ImageView imageView;
        TextView replice;

    }

    @Override
    public boolean urlClicked(String url) {

        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri uri = Uri.parse(url);
        intent.setData(uri);
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        return true;
    }
}
