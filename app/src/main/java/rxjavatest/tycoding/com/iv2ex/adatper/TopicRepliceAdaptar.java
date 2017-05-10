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
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zzhoujay.richtext.ImageHolder;
import com.zzhoujay.richtext.RichText;
import com.zzhoujay.richtext.callback.ImageFixCallback;
import com.zzhoujay.richtext.callback.OnImageClickListener;
import com.zzhoujay.richtext.callback.OnImageLongClickListener;
import com.zzhoujay.richtext.callback.OnUrlClickListener;
import com.zzhoujay.richtext.callback.OnUrlLongClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import rxjavatest.tycoding.com.iv2ex.BaseApplication;
import rxjavatest.tycoding.com.iv2ex.R;
import rxjavatest.tycoding.com.iv2ex.rxjava.rxjava;
import rxjavatest.tycoding.com.iv2ex.ui.activity.photoviewactivity;
import rxjavatest.tycoding.com.iv2ex.ui.widget.RichTextView;
import rxjavatest.tycoding.com.iv2ex.utils.imageloader;
import rxjavatest.tycoding.com.iv2ex.utils.tyutils;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by tongyang on 16-11-16.
 */

public class TopicRepliceAdaptar extends BaseAdapter  {

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
   public  void MyNotify(  List<Map<String, String>> list){
       this.list=list;
       notifyDataSetChanged();
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
            viewHolder.replice = (RichTextView) view.findViewById(R.id.replice_content);
            viewHolder.number = (TextView) view.findViewById(R.id.number);
            view.setTag(viewHolder);

        } else {
            viewHolder = (TopicRepliceAdaptar.viewHolder) view.getTag();
        }

        viewHolder.number.setText("第" + (i + 1) + "楼");
        viewHolder.time.setText(list.get(i).get("time"));
        viewHolder.username.setText(list.get(i).get("username"));

        ImageLoader.getInstance().displayImage(list.get(i).get("img"), viewHolder.imageView);

           viewHolder.replice.setRichText(list.get(i).get("content"));
        return view;


    }

    class viewHolder {

        TextView username;
        TextView time;
        ImageView imageView;
        RichTextView replice;
        TextView number;
    }


}
