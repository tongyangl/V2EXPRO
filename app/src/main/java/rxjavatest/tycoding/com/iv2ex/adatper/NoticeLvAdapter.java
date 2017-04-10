package rxjavatest.tycoding.com.iv2ex.adatper;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zzhoujay.richtext.RichText;

import java.util.List;
import java.util.Map;

import rxjavatest.tycoding.com.iv2ex.R;
import rxjavatest.tycoding.com.iv2ex.rxjava.rxjava;

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


            viewHoder.content = (TextView) convertView.findViewById(R.id.content);
            viewHoder.imageView = (ImageView) convertView.findViewById(R.id.imagView);
            viewHoder.notice = (TextView) convertView.findViewById(R.id.notice);
            viewHoder.time = (TextView) convertView.findViewById(R.id.time);
            convertView.setTag(viewHoder);
        } else {
            viewHoder = (ViewHoder) convertView.getTag();
        }
        viewHoder.time.setText(list.get(position).get("time"));
        viewHoder.notice.setText(list.get(position).get("notice"));
        rxjava.setImg(list.get(position).get("img"), viewHoder.imageView, activity);
        viewHoder.content.setText(list.get(position).get("content"));
        return convertView;
    }

    class ViewHoder {
        public ImageView imageView;
        public TextView notice;
        public TextView time;
        public TextView content;

    }
}
