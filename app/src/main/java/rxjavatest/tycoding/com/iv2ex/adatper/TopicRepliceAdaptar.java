package rxjavatest.tycoding.com.iv2ex.adatper;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.zzhoujay.richtext.ImageHolder;
import com.zzhoujay.richtext.RichText;
import com.zzhoujay.richtext.callback.ImageFixCallback;
import com.zzhoujay.richtext.callback.OnImageClickListener;
import com.zzhoujay.richtext.callback.OnImageLongClickListener;
import com.zzhoujay.richtext.callback.OnUrlClickListener;
import com.zzhoujay.richtext.callback.OnUrlLongClickListener;

import java.net.URL;
import java.util.List;
import java.util.Map;


import rxjavatest.tycoding.com.iv2ex.R;
import rxjavatest.tycoding.com.iv2ex.rxjava.rxjava;
import rxjavatest.tycoding.com.iv2ex.ui.activity.photoviewactivity;
import rxjavatest.tycoding.com.iv2ex.utils.imageloader;
import rxjavatest.tycoding.com.iv2ex.utils.tyutils;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by tongyang on 16-11-16.
 */

public class TopicRepliceAdaptar extends BaseAdapter implements OnUrlClickListener {

    private List<Map<String, String>> list;
    private LayoutInflater inflater;
    private viewHolder viewHolder;
    private ListView lv;
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
            viewHolder.number = (TextView) view.findViewById(R.id.number);
            view.setTag(viewHolder);

        } else {
            viewHolder = (TopicRepliceAdaptar.viewHolder) view.getTag();
        }

        viewHolder.number.setText("第" + (i + 1) + "楼");
        viewHolder.time.setText(list.get(i).get("time"));
        viewHolder.username.setText(list.get(i).get("username"));
        viewHolder.imageView.setTag(list.get(i).get("img"));
        SharedPreferences sharedPreferences=context.getSharedPreferences("set",Context.MODE_PRIVATE);

        RichText.fromHtml(list.get(i).get("content")).autoFix(true).fix(new ImageFixCallback() {
            @Override
            public void onInit(ImageHolder holder) {

            }

            @Override
            public void onLoading(ImageHolder holder) {

            }

            @Override
            public void onSizeReady(ImageHolder holder, int width, int height) {

            }

            @Override
            public void onImageReady(ImageHolder holder, int width, int height) {
                holder.setImageType(ImageHolder.ImageType.JPG);
                imageloader.saveimage( holder.getSource(), (Activity) context);
            }

            @Override
            public void onFailure(ImageHolder holder, Exception e) {

            }


        }).noImage(sharedPreferences.getBoolean("wifi",false)).urlLongClick(new OnUrlLongClickListener() {
            @Override
            public boolean urlLongClick(String url) {
                ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                cmb.setText(url.trim());
                return true;
            }
        }).imageClick(new OnImageClickListener() {
            @Override
            public void imageClicked(List<String> imageUrls, int position) {
                Intent intent = new Intent(context, photoviewactivity.class);
                intent.putExtra("position", position);
                intent.putExtra("url",  imageUrls.get(position));
                Log.d("---", imageUrls.get(position));
                context.startActivity(intent);
            }
        }).imageLongClick(new OnImageLongClickListener() {
            @Override
            public boolean imageLongClicked(List<String> imageUrls, int position) {
                return true;
            }
        }).clickable(true).urlClick(this).into(viewHolder.replice);
        final String imgurl = list.get(i).get("img");

        rxjava.setImg(imgurl, viewHolder.imageView, context);


        return view;


    }

    class viewHolder {

        TextView username;
        TextView time;
        ImageView imageView;
        TextView replice;
        TextView number;
    }

    @Override
    public boolean urlClicked(String url) {
        if (!url.startsWith("http")){
            url= tyutils.BASE_URL+url.substring(1,url.length());
        }
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri uri = Uri.parse(url);
        intent.setData(uri);
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        return true;
    }
}
