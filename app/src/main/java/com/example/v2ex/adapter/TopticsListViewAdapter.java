package com.example.v2ex.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.v2ex.MyApplication;
import com.example.v2ex.R;
import com.example.v2ex.model.TopticModel;
import com.example.v2ex.utils.LoadImg;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 佟杨 on 2017/9/4.
 */

public class TopticsListViewAdapter extends BaseAdapter {
    public List<Boolean> isTouch;
    private List<TopticModel> list;
    private ViewHoder viewHoder;
    private LayoutInflater layoutInflater;
    private Context context;
    private int istouchlength;

    public TopticsListViewAdapter(List<TopticModel> list,
                                  LayoutInflater layoutInflater, Context context) {
        this.list = list;
        this.context = context;
        this.layoutInflater = layoutInflater;
        isTouch = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            isTouch.add(false);
            istouchlength++;
        }
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();


        for (int i = istouchlength; i < list.size(); i++) {
            isTouch.add(false) ;
        }
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
            convertView = layoutInflater.inflate(R.layout.item_lv_toptics, null);
            viewHoder.imageView = (ImageView) convertView.findViewById(R.id.imagView);
            viewHoder.username = (TextView) convertView.findViewById(R.id.username);
            viewHoder.nodename = (TextView) convertView.findViewById(R.id.nodename);
            viewHoder.replice = (TextView) convertView.findViewById(R.id.replice);
            viewHoder.time = (TextView) convertView.findViewById(R.id.time);
            viewHoder.lastreplice = (TextView) convertView.findViewById(R.id.lastreplice);
            viewHoder.topictitle = (TextView) convertView.findViewById(R.id.topictitle);
            convertView.setTag(viewHoder);

        } else {
            viewHoder = (ViewHoder) convertView.getTag();

        }
        viewHoder.username.setText(list.get(position).getUserName());
        viewHoder.nodename.setText(list.get(position).getNodeTitle());
        if (isTouch.get(position)) {
            viewHoder.replice.setBackgroundResource(R.drawable.list_textview_replice1);
        } else {
            viewHoder.replice.setBackgroundResource(R.drawable.list_textview_replice);
        }
        if (list.get(position).getReplices().equals("")) {
            viewHoder.replice.setVisibility(View.GONE);
        } else {
            viewHoder.replice.setVisibility(View.VISIBLE);
            viewHoder.replice.setText(list.get(position).getReplices());

        }
        viewHoder.lastreplice.setText(list.get(position).getLastreplice());

        viewHoder.topictitle.setText(list.get(position).getTitle());
        viewHoder.time.setText(list.get(position).getTime());
        LoadImg.LoadImage(list.get(position).getImg(), viewHoder.imageView, context);
        return convertView;
    }

    class ViewHoder {
        ImageView imageView;
        TextView username;
        TextView nodename;
        TextView replice;
        TextView time;
        TextView lastreplice;
        TextView topictitle;
    }

}
