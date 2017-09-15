package com.example.v2ex.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cpiz.android.bubbleview.BubbleTextView;
import com.example.v2ex.R;
import com.example.v2ex.model.TopticdetalisModel;
import com.example.v2ex.ui.activity.TopicsDetalisActivity;
import com.example.v2ex.utils.LoadImg;

import java.util.List;

/**
 * Created by 佟杨 on 2017/9/14.
 */

public class UserListAdapter extends BaseAdapter {

    private List<TopticdetalisModel> list;
    private LayoutInflater layoutInflater;

    private ViewHoder viewHoder;
    private Context context;

    public UserListAdapter(List<TopticdetalisModel> list, LayoutInflater layoutInflater, Context context) {
        this.list = list;
        this.layoutInflater = layoutInflater;
        this.context = context;
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
            convertView = layoutInflater.inflate(R.layout.lv_user, null);
            viewHoder.username = (TextView) convertView.findViewById(R.id.username);
            viewHoder.img = (ImageView) convertView.findViewById(R.id.img);
            convertView.setTag(viewHoder);
        } else {
            viewHoder = (ViewHoder) convertView.getTag();
        }

        LoadImg.LoadImage(list.get(position).getImg(), viewHoder.img, context);
        viewHoder.username.setText(list.get(position).getUsername());
        return convertView;
    }


    class ViewHoder {

        public TextView username;
        public ImageView img;
    }
}
