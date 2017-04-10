package rxjavatest.tycoding.com.iv2ex.adatper;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.zzhoujay.richtext.RichText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rxjavatest.tycoding.com.iv2ex.R;

/**
 * Created by 佟杨 on 2017/4/10.
 */

public class NodeRecycleAdapter extends RecyclerView.Adapter<NodeRecycleAdapter.ViewHolder> implements View.OnClickListener, Filterable {
    private List<Map<String, String>> list = new ArrayList<>();
    private OnRecyclerViewItemClickListener listener = null;
    private List<Map<String, String>> mlist;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lv_node, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {

            Map<String, String> list = (HashMap<String, String>) v.getTag();
            listener.onItemClick(v, list);
        }
    }

    public NodeRecycleAdapter(List<Map<String, String>> list) {
        mlist = list;
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
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(list.get(position).get("title"));

        if (!list.get(position).get("content").equals("null") | list.get(position).get("content").contains("nbsp")) {
            holder.dresprtion.setText(Html.fromHtml(list.get(position).get("content")));
            //   RichText.from(().into(holder.dresprtion);
        }

        holder.num.setText(list.get(position).get("num") + "条信息");
        holder.itemView.setTag(list.get(position));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.listener = listener;
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
                List<Map<String, String>> datalist = new ArrayList<>();

                for (int i = 0; i < mlist.size(); i++) {
                    if (mlist.get(i).get("title").contains(constraint)) {
                        datalist.add(mlist.get(i));
                    }
                }
                results.values = datalist;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                list=((List<Map<String, String>>) results.values);
                notifyDataSetChanged();

            }
        };
    }
}
