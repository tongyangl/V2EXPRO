package zero.tongyang.threegrand.com.x2expro.HomePage.Home_ViewPager;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ListView;

import java.util.List;
import java.util.Map;

import zero.tongyang.threegrand.com.x2expro.Internet.GetTopics;
import zero.tongyang.threegrand.com.x2expro.Internet.PostTopics;
import zero.tongyang.threegrand.com.x2expro.Internet.ToList;
import zero.tongyang.threegrand.com.x2expro.Internet.htmlTolist;

/**
 * Created by tongyang on 16-11-14.
 */

public class JsoupAsyncTask extends AsyncTask<String, Void, String> {
    private ListView listView;
    private List<Map<String, String>> list;
    private LayoutInflater inflater;
    private Context context;
    private ListViewAdaptar adaptar;

    public JsoupAsyncTask( ListView listView, LayoutInflater inflater, List<Map<String, String>> list,Context context) {
        this.context = context;
        this.inflater = inflater;
        this.list = list;
        this.listView = listView;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        list.clear();
        list = htmlTolist.TopicsToList(s);
        adaptar = new ListViewAdaptar(list, context, inflater, listView);
        listView.setAdapter(adaptar);
    }

    @Override
    protected String doInBackground(String... strings) {
        return GetTopics.getTopic(strings[0]);


    }
}
