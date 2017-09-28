package com.example.v2ex.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cpiz.android.bubbleview.BubbleTextView;
import com.example.v2ex.R;
import com.example.v2ex.model.NoticeModel;
import com.example.v2ex.ui.activity.TopicsDetalisActivity;
import com.example.v2ex.utils.LoadImg;

import java.util.List;
import java.util.Map;


/**
 * Created by 佟杨 on 2017/4/10.
 */

public class NoticeLvAdapter extends BaseAdapter {
    private List<NoticeModel> list;
    private Context activity;
    private ViewHoder viewHoder;
    private LayoutInflater layoutInflater;

    public NoticeLvAdapter(List<NoticeModel> list, Context activity, LayoutInflater layoutInflater) {
        this.list = list;
        this.activity = activity;
        this.layoutInflater = layoutInflater;
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
            convertView = layoutInflater.inflate(R.layout.lv_notice, null);

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
        viewHoder.time.setText(list.get(position).getTime());
        viewHoder.notice.setText(list.get(position).getNotice().trim());
        // ImageLoader.getInstance().displayImage(list.get(position).get("img"), viewHoder.imageView);
        LoadImg.LoadCircleImageView(list.get(position).getImg(), viewHoder.imageView, activity);

        if (!list.get(position).getContent().equals("")) {
            viewHoder.content.setVisibility(View.VISIBLE);
            viewHoder.content.setText(list.get(position).getContent().trim());
        } else {
            viewHoder.content.setVisibility(View.GONE);
        }
        final String username = list.get(position).getUsername();
        final String url = list.get(position).getUrl();
        viewHoder.username.setText(username);
        viewHoder.replice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, TopicsDetalisActivity.class);
                intent.putExtra("@user", username);

                intent.putExtra("url", url);
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
