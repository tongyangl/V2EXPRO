package zero.tongyang.threegrand.com.x2expro.HomePage.Home_ViewPager.Some;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.widget.ListView;

import java.util.List;
import java.util.Map;

import zero.tongyang.threegrand.com.x2expro.Adaptar.ListViewAdaptar;
import zero.tongyang.threegrand.com.x2expro.Internet.PostTopics;
import zero.tongyang.threegrand.com.x2expro.Utils.ToList;

/**
 * Created by tongyang on 16-11-14.
 */

public class PostAsyncTask extends AsyncTask<String, Void, String> {
    private ListView listView;
    private List<Map<String, String>> list;
    private LayoutInflater inflater;
    private Context context;

    public PostAsyncTask(ListView listView, LayoutInflater inflater, List<Map<String, String>> list, Context context) {
        this.listView = listView;
        this.inflater = inflater;
        this.list = list;
        this.context = context;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        list = ToList.TopicsToList(s);
        ListViewAdaptar adaptar = new ListViewAdaptar(list, context, inflater, listView);
        listView.setAdapter(adaptar);
    }

    @Override
    protected String doInBackground(String... strings) {

        return PostTopics.PostTopics("", strings[0], "");


    }
}
