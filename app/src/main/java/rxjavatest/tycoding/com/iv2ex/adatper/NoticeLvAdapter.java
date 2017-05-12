package rxjavatest.tycoding.com.iv2ex.adatper;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cpiz.android.bubbleview.BubbleTextView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zzhoujay.richtext.RichText;

import java.util.List;
import java.util.Map;

import rxjavatest.tycoding.com.iv2ex.R;
import rxjavatest.tycoding.com.iv2ex.rxjava.rxjava;
import rxjavatest.tycoding.com.iv2ex.ui.activity.TopicsDetalisActivity;

/**
 * Created by 佟杨 on 2017/4/10.
 */

public class NoticeLvAdapter extends BaseAdapter {
    private List<Map<String, String>> list;
    private Activity activity;
    private ViewHoder viewHoder;

    public NoticeLvAdapter(List<Map<String, String>> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewHoder = new ViewHoder();
        if (convertView == null) {
            convertView = activity.getLayoutInflater().inflate(R.layout.lv_notice, null);

            viewHoder.content = (BubbleTextView) convertView.findViewById(R.id.content);
            viewHoder.imageView = (ImageView) convertView.findViewById(R.id.imagView);
            viewHoder.notice = (TextView) convertView.findViewById(R.id.notice);
            viewHoder.time = (TextView) convertView.findViewById(R.id.time);
            viewHoder.username = (TextView) convertView.findViewById(R.id.username);
            viewHoder.replice = (TextView) convertView.findViewById(R.id.replice);

            convertView.setTag(viewHoder);
        } else {
            viewHoder = (ViewHoder) convertView.getTag();
        }
        viewHoder.time.setText(list.get(position).get("time"));
        viewHoder.notice.setText(list.get(position).get("notice"));
        ImageLoader.getInstance().displayImage(list.get(position).get("img"), viewHoder.imageView);
        Log.d("====", list.get(position).get("content") + "lll");
        if (!list.get(position).get("content").equals("")) {
            viewHoder.content.setVisibility(View.VISIBLE);
            viewHoder.content.setText(list.get(position).get("content"));
        } else {
            viewHoder.content.setVisibility(View.GONE);
        }
       final String username= list.get(position).get("username");
        final String url=list.get(position).get("url");
        viewHoder.username.setText(username);
        viewHoder.replice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity, TopicsDetalisActivity.class);
                intent.putExtra("@user",username);
                intent.putExtra("repliceurl", url);
                activity.startActivity(intent);
            }
        });
        return convertView;
    }

    class ViewHoder {
        public ImageView imageView;
        public TextView notice;
        public TextView time;
        public BubbleTextView content;
        public TextView username;
        public TextView replice;
    }
}
