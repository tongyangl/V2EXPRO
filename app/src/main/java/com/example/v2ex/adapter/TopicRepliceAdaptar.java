package com.example.v2ex.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.v2ex.R;
import com.example.v2ex.model.TopticdetalisModel;
import com.example.v2ex.widget.RichTextView;

import java.util.List;
import java.util.Map;


/**
 * Created by tongyang on 16-11-16.
 */

public class TopicRepliceAdaptar extends BaseAdapter {

    private List<TopticdetalisModel> list;
    private LayoutInflater inflater;
    private viewHolder viewHolder;
    private ListView lv;
    private Context context;

    public TopicRepliceAdaptar(LayoutInflater inflater, List<TopticdetalisModel> list, ListView lv, Context context) {
        this.inflater = inflater;
        this.list = list;
        this.lv = lv;
        this.context = context;
    }

    public void MyNotify(List<TopticdetalisModel> list) {
        this.list = list;
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
        viewHolder.time.setText(list.get(i).getTime());
        viewHolder.username.setText(list.get(i).getUsername());

        viewHolder.replice.setRichText(list.get(i).getContent());
        Glide.with(context)
                .load(list.get(i).getImg())
                .into(viewHolder.imageView);
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
