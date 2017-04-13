package rxjavatest.tycoding.com.iv2ex.adatper;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rxjavatest.tycoding.com.iv2ex.R;
import rxjavatest.tycoding.com.iv2ex.ui.activity.CreateToptic;

/**
 * Created by 佟杨 on 2017/4/13.
 */

public class NodesArrayAdapter extends BaseAdapter implements Filterable {
    private Activity activity;
    public List<CreateToptic.NodeModel> objects;
    private List<CreateToptic.NodeModel> mydate;

    public NodesArrayAdapter(Activity activity, List<CreateToptic.NodeModel> objects) {
        this.activity = activity;
        this.objects = objects;
        mydate=objects;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = activity.getLayoutInflater().inflate(R.layout.item_createtoptic, null);
        TextView textView = (TextView) convertView.findViewById(R.id.text);
        textView.setText(objects.get(position).getTitle());
        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                List<CreateToptic.NodeModel> list = new ArrayList<>();

                for (int i = 0; i < mydate.size(); i++) {
                    String a = mydate.get(i).getName();
                    String b = mydate.get(i).getTitle();
                    if (a.contains(constraint) | a.toLowerCase().contains(constraint) | a.toUpperCase().contains(constraint)
                            | b.contains(constraint) | b.toLowerCase().contains(constraint) | b.toUpperCase().contains(constraint)
                            ) {
                        list.add(mydate.get(i));
                    }
                }
                results.values = list;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                objects = (List<CreateToptic.NodeModel>) results.values;
                notifyDataSetChanged();
            }
        };
    }

}
