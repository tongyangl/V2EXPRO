package rxjavatest.tycoding.com.iv2ex.adatper;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rxjavatest.tycoding.com.iv2ex.R;
import rxjavatest.tycoding.com.iv2ex.rxjava.rxjava;
import rxjavatest.tycoding.com.iv2ex.ui.activity.NodeTopticsActivity;

/**
 * Created by 佟杨 on 2017/4/10.
 */

public class NodeCollectAdapter extends RecyclerView.Adapter<NodeCollectAdapter.ViewHolder> implements View.OnClickListener {
    private List<Map<String, String>> list = new ArrayList<>();
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private List<Map<String, String>> mlist;
    private Activity activity;

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

    public NodeCollectAdapter(List<Map<String, String>> list, Activity activity) {
        mlist = list;
        this.activity = activity;
    }

    public void notifiy(List<Map<String, String>> list) {

        this.list.clear();
        if (list != null && list.size() > 0) {
            this.list.addAll(list);
            notifyDataSetChanged();

        }
        // this.list.clear();
        //   this.list.addAll(mlist);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.title.setText(list.get(position).get("title"));
        holder.num.setText(list.get(position).get("num") + "条信息");
        ImageLoader.getInstance().displayImage(list.get(position).get("img"), holder.img);
        holder.itemView.setTag(list.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, NodeTopticsActivity.class);
                intent.putExtra("url", list.get(position).get("url"));
                intent.putExtra("num", list.get(position).get("num"));
                intent.putExtra("title", list.get(position).get("title"));

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
