package rxjavatest.tycoding.com.iv2ex.adatper;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import rxjavatest.tycoding.com.iv2ex.R;
import rxjavatest.tycoding.com.iv2ex.rxjava.rxjava;

/**
 * Created by 佟杨 on 2017/4/12.
 */

public class NodeTopticsAdapter extends BaseAdapter {
    private List<Map<String, String>> list;
    private ViewHolder viewHolder;
    private Activity activity;

    public NodeTopticsAdapter(List<Map<String, String>> list, Activity activity) {
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
        viewHolder = new ViewHolder();
        if (convertView == null) {
            convertView = activity.getLayoutInflater().inflate(R.layout.item_lv_toptics, null);


            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imagView);
            viewHolder.username = (TextView) convertView.findViewById(R.id.username);
            viewHolder.nodename = (TextView) convertView.findViewById(R.id.nodename);
            viewHolder.replice = (TextView) convertView.findViewById(R.id.replice);
            viewHolder.time = (TextView) convertView.findViewById(R.id.time);
            viewHolder.lastreplice = (TextView) convertView.findViewById(R.id.lastreplice);
            viewHolder.topictitle = (TextView) convertView.findViewById(R.id.topictitle);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();

        }
        viewHolder.username.setText(list.get(position).get("username"));
        viewHolder.nodename.setText(list.get(position).get("nodetitle"));
        //holder.imageView;
        if (list.get(position).get("replies").equals("")) {
            viewHolder.replice.setVisibility(View.GONE);
        } else {
            viewHolder.replice.setVisibility(View.VISIBLE);
            viewHolder.replice.setText(list.get(position).get("replies"));

        }
        viewHolder.lastreplice.setText(list.get(position).get("lastreplice"));
        viewHolder.topictitle.setText(list.get(position).get("title"));
        viewHolder.time.setText(list.get(position).get("time"));

        final String imgurl = list.get(position).get("img");

        viewHolder.imageView.setTag(imgurl);

        rxjava.setImg(imgurl, viewHolder.imageView, activity);
        return convertView;
    }

    class ViewHolder {

        ImageView imageView;
        TextView username;
        TextView nodename;
        TextView replice;
        TextView time;
        TextView lastreplice;
        TextView topictitle;
    }
}
