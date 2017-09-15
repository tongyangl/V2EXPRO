package com.example.v2ex.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.v2ex.R;
import com.example.v2ex.model.NodesModel;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 佟杨 on 2017/4/13.
 */

public class NodesArrayAdapter extends BaseAdapter implements Filterable {
    private Context activity;
    public List<NodesModel> objects;
    private List<NodesModel> mydate;
    private LayoutInflater layoutInflater;
    public NodesArrayAdapter(Context activity, List<NodesModel> objects, LayoutInflater layoutInflater) {
        this.activity = activity;
        this.objects = objects;
        this.layoutInflater=layoutInflater;
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
        convertView = layoutInflater.inflate(R.layout.item_createtoptic, null);
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
                List<NodesModel> list = new ArrayList<>();

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

                objects = (List<NodesModel>) results.values;
                notifyDataSetChanged();
            }
        };
    }

}
