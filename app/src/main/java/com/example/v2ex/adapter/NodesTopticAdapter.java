package com.example.v2ex.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.v2ex.R;
import com.example.v2ex.model.TopticModel;
import com.example.v2ex.utils.LoadImg;

import java.util.List;


/**
 * Created by 佟杨 on 2017/9/19.
 */

public class NodesTopticAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>

{
    private List<TopticModel> list;
    private Context context;

    public NodesTopticAdapter(List<TopticModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lv_toptics, parent, false);
        return new NormalHolder(view);


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        ((NormalHolder) holder).setData(position);


    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }

    class NormalHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView username;
        TextView nodename;
        TextView replice;
        TextView time;
        TextView lastreplice;
        TextView topictitle;

        public NormalHolder(View convertView) {
            super(convertView);
            imageView = (ImageView) convertView.findViewById(R.id.imagView);
            username = (TextView) convertView.findViewById(R.id.username);
            nodename = (TextView) convertView.findViewById(R.id.nodename);
            replice = (TextView) convertView.findViewById(R.id.replice);
            time = (TextView) convertView.findViewById(R.id.time);
            lastreplice = (TextView) convertView.findViewById(R.id.lastreplice);
            topictitle = (TextView) convertView.findViewById(R.id.topictitle);
        }

        public void setData(int position) {
            username.setText(list.get(position).getUserName());
            nodename.setText(list.get(position).getNodeTitle());

            if (list.get(position).getReplices().equals("")) {
                replice.setVisibility(View.GONE);
            } else {
                replice.setVisibility(View.VISIBLE);
                replice.setText(list.get(position).getReplices());

            }
            lastreplice.setText(list.get(position).getLastreplice());

            topictitle.setText(list.get(position).getTitle());
            time.setText(list.get(position).getTime());
            LoadImg.LoadCircleImageView(list.get(position).getImg(), imageView, context);
        }
    }


}
