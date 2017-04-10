package rxjavatest.tycoding.com.iv2ex.adatper;

import android.app.Activity;
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

/**
 * Created by 佟杨 on 2017/4/6.
 */

public class myrecycleadapter extends RecyclerView.Adapter<myrecycleadapter.ViewHolder>implements View.OnClickListener {
    private List<Map<String, String>> list;
    private Activity activity;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    public myrecycleadapter(List<Map<String, String>> list, Activity activity) {
        this.list = list;
        this.activity = activity;
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
           Map<String,String>list= (HashMap<String, String>) v.getTag();
            mOnItemClickListener.onItemClick(v,list);
        }
    }
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
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
        holder.topictitle.setText(list.get(position).get("title"));
        holder.time.setText(list.get(position).get("time"));
        /*Drawable drawable=activity.getResources().getDrawable(R.drawable.ic_person_outline_black_24dp);
        holder.imageView.setImageDrawable(drawable);*/
        final String imgurl=list.get(position).get("img");
        holder.itemView.setTag(list.get(position));
        holder.imageView.setTag(imgurl);

        rxjava.setImg(imgurl,holder.imageView,activity);

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

    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , Map<String,String> data);

    }

}
