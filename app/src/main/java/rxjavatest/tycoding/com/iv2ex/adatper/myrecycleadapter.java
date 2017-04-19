package rxjavatest.tycoding.com.iv2ex.adatper;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rxjavatest.tycoding.com.iv2ex.R;
import rxjavatest.tycoding.com.iv2ex.rxjava.rxjava;
import rxjavatest.tycoding.com.iv2ex.ui.activity.TopicsDetalisActivity;

/**
 * Created by 佟杨 on 2017/4/6.
 */

public class myrecycleadapter extends RecyclerView.Adapter<myrecycleadapter.ViewHolder> implements View.OnClickListener {
    private List<Map<String, String>> list;
    private Activity activity;
    private boolean args[];
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public myrecycleadapter(List<Map<String, String>> list, Activity activity) {
        this.list = list;
        this.activity = activity;
        args = new boolean[list.size()];
        for (int i=0;i<list.size();i++){
            args[i]=false;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lv_toptics, parent, false);
        view.setOnClickListener(this);

        return new ViewHolder(view);
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            Map<String, String> list = (HashMap<String, String>) v.getTag();
            mOnItemClickListener.onItemClick(v, list);
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.username.setText(list.get(position).get("username"));
        holder.nodename.setText(list.get(position).get("nodetitle"));
        //holder.imageView;
        if (list.get(position).get("replies").equals("")) {
            holder.replice.setVisibility(View.GONE);
        } else {
            holder.replice.setVisibility(View.VISIBLE);
            holder.replice.setText(list.get(position).get("replies"));

        }
        holder.lastreplice.setText(list.get(position).get("lastreplice"));
        if (args[position]) {
            holder.replice.setBackgroundResource(R.drawable.list_textview_replice1);
        }
        holder.topictitle.setText(list.get(position).get("title"));
        holder.time.setText(list.get(position).get("time"));
        final String imgurl = list.get(position).get("img");
        holder.itemView.setTag(list.get(position));
        holder.imageView.setTag(imgurl);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, TopicsDetalisActivity.class);
                intent.putExtra("repliceurl", list.get(position).get("repliceurl"));
                intent.putExtra("nodetitle", list.get(position).get("nodetitle"));
                intent.putExtra("time", list.get(position).get("time"));
                intent.putExtra("username", list.get(position).get("username"));
                intent.putExtra("img", list.get(position).get("img"));
                intent.putExtra("title", list.get(position).get("title"));
                activity.startActivity(intent);
                args[position]=true;
            }
        });
        rxjava.setImg(imgurl, holder.imageView, activity);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView username;
        TextView nodename;
        TextView replice;
        TextView time;
        TextView lastreplice;
        TextView topictitle;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imagView);
            username = (TextView) itemView.findViewById(R.id.username);
            nodename = (TextView) itemView.findViewById(R.id.nodename);
            replice = (TextView) itemView.findViewById(R.id.replice);
            time = (TextView) itemView.findViewById(R.id.time);
            lastreplice = (TextView) itemView.findViewById(R.id.lastreplice);
            topictitle = (TextView) itemView.findViewById(R.id.topictitle);

        }
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, Map<String, String> data);

    }

}
