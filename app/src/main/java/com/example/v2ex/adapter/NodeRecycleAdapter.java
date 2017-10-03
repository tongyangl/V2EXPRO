package com.example.v2ex.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.example.v2ex.MainActivity;
import com.example.v2ex.R;
import com.example.v2ex.model.NodesModel;
import com.example.v2ex.ui.activity.CreateTopticActivity;
import com.example.v2ex.ui.activity.NodeTopticsActivity;
import com.example.v2ex.ui.activity.SiginActivity;
import com.example.v2ex.utils.SomeUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by 佟杨 on 2017/4/10.
 */

public class NodeRecycleAdapter extends RecyclerView.Adapter<NodeRecycleAdapter.ViewHolder> implements View.OnClickListener, Filterable {
    private List<NodesModel> list = new ArrayList<>();
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private List<NodesModel> mlist;
    private Context activity;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lv_node, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {

            Map<String, String> list = (HashMap<String, String>) v.getTag();
            mOnItemClickListener.onItemClick(v, list);
        }
    }

    public NodeRecycleAdapter(List<NodesModel> list, Context activity) {
        mlist = list;
        this.activity = activity;
    }

    public void notifiy(List<NodesModel> list) {

        this.list.clear();
        if (list != null && list.size() > 0) {
            this.list.addAll(list);
            notifyDataSetChanged();
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.title.setText(list.get(position).getTitle());

        if (!list.get(position).getContent().equals("null") | list.get(position).getContent().contains("nbsp")) {
            holder.dresprtion.setText(Html.fromHtml(list.get(position).getContent()));

        }

        holder.num.setText(list.get(position).getNum() + "条信息");
        holder.itemView.setTag(list.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SomeUtils.islogin(activity)) {

                    Intent intent = new Intent(activity, NodeTopticsActivity.class);
                    intent.putExtra("url", list.get(position).getUrl());
                    intent.putExtra("num", list.get(position).getNum());
                    intent.putExtra("title", list.get(position).getTitle());


                    activity.startActivity(intent);
                } else {
                    Toast.makeText(activity, "请登陆后再操作", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(activity, SiginActivity.class);
                    activity.startActivity(intent);
                }

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
        TextView title;
        TextView num;
        TextView dresprtion;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            num = (TextView) itemView.findViewById(R.id.num);
            dresprtion = (TextView) itemView.findViewById(R.id.drisprtion);
        }
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, Map<String, String> data);

    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                List<NodesModel> datalist = new ArrayList<>();

                for (int i = 0; i < mlist.size(); i++) {
                    String a = mlist.get(i).getTitle();
                    if (a.contains(constraint) | a.toLowerCase().contains(constraint) | a.toUpperCase().contains(constraint)) {
                        datalist.add(mlist.get(i));
                    }
                }
                results.values = datalist;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                list = ((List<NodesModel>) results.values);
                notifyDataSetChanged();

            }
        };
    }
}
