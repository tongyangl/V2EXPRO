package zero.tongyang.threegrand.com.x2expro.HomePage.Home_ViewPager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import zero.tongyang.threegrand.com.x2expro.R;
import zero.tongyang.threegrand.com.x2expro.Static;

/**
 * Created by tongyang on 16-11-12.
 */

public class ListViewAdaptar extends BaseAdapter {
    private List<Map<String, String>> list;
    private LayoutInflater inflater;
    private  Context context;

    public ListViewAdaptar(List<Map<String, String>> list, Context context, LayoutInflater inflater) {
        this.list = list;
        this.context = context;
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {

            view = inflater.inflate(R.layout.list_home, null);
        }
        TextView textView = (TextView) view.findViewById(R.id.username);
      /*  *//*int width = (int) Static.dp2px(context,60);*//*
        textView.setHeight(width);*/
        textView.setText(list.get(i).get("i"));
        return view;
    }
}
