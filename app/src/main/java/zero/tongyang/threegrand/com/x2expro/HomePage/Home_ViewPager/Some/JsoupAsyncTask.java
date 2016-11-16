package zero.tongyang.threegrand.com.x2expro.HomePage.Home_ViewPager.Some;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;
import java.util.Map;

import zero.tongyang.threegrand.com.x2expro.HomePage.Home_ViewPager.Detalis.TopicsDetalis;
import zero.tongyang.threegrand.com.x2expro.HomePage.Home_ViewPager.ListViewAdaptar;
import zero.tongyang.threegrand.com.x2expro.Internet.GetTopics;
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
    private Activity activity;

    public JsoupAsyncTask(ListView listView, LayoutInflater inflater, List<Map<String, String>> list, Context context, Activity activity) {
        this.context = context;
        this.inflater = inflater;
        this.list = list;
        this.listView = listView;
        this.activity = activity;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        list.clear();
        list = htmlTolist.TopicsToList(s);
        adaptar = new ListViewAdaptar(list, context, inflater, listView);
        listView.setAdapter(adaptar);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(context, TopicsDetalis.class);
                intent.putExtra("topicstitle", list.get(i).get("title"));
                intent.putExtra("repliceurl", list.get(i).get("repliceurl"));
                intent.putExtra("img", list.get(i).get("img"));
                intent.putExtra("username", list.get(i).get("username"));
                intent.putExtra("nodetitle", list.get(i).get("nodetitle"));
                activity.startActivity(intent);

            }
        });
    }

    @Override
    protected String doInBackground(String... strings) {
        return GetTopics.getTopic(strings[0]);


    }
}
