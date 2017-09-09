package com.example.v2ex.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.v2ex.R;
import com.example.v2ex.model.NodeCollectModel;
import com.example.v2ex.ui.activity.NodeTopticsActivity;
import com.example.v2ex.utils.LoadImg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by 佟杨 on 2017/4/10.
 */

public class NodeCollectAdapter extends RecyclerView.Adapter<NodeCollectAdapter.ViewHolder> implements View.OnClickListener {
    private List<NodeCollectModel> list = new ArrayList<>();
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private Context activity;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lv_node_collect, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {

            Map<String, String> list = (HashMap<String, String>) v.getTag();
            mOnItemClickListener.onItemClick(v, list);
        }
    }

    public NodeCollectAdapter(List<NodeCollectModel> list, Context activity) {
        this.list = list;
        this.activity = activity;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.title.setText(list.get(position).getTitle());
        holder.num.setText(list.get(position).getNum() + "条信息");
        Log.d("====", list.get(position).getImgurl() + "ss");
        LoadImg.LoadImage(list.get(position).getImgurl(), holder.img, activity);
        //ImageLoader.getInstance().displayImage(list.get(position).get("imgurl"),holder.img);
        //    SetImg.setImg(list.get(position).get("img"),holder.img,activity);
        holder.itemView.setTag(list.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, NodeTopticsActivity.class);
                intent.putExtra("url", list.get(position).getUrl());
                intent.putExtra("num", list.get(position).getNum());
                intent.putExtra("title", list.get(position).getTitle());
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView num;
        TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.imagView);
            num = (TextView) itemView.findViewById(R.id.num);
            title = (TextView) itemView.findViewById(R.id.title);
        }
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, Map<String, String> data);

    }

}
