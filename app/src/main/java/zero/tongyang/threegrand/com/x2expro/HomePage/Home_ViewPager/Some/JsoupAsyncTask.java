package zero.tongyang.threegrand.com.x2expro.HomePage.Home_ViewPager.Some;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import java.util.List;
import java.util.Map;

import zero.tongyang.threegrand.com.x2expro.Adaptar.ListViewAdaptar;
import zero.tongyang.threegrand.com.x2expro.Internet.GetTopics;
import zero.tongyang.threegrand.com.x2expro.Utils.htmlTolist;
import zero.tongyang.threegrand.com.x2expro.UI.Activity.TopicsDetalis;

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
    private ShimmerTextView textView;
    private Shimmer shimmer;

    public JsoupAsyncTask(ListView listView, LayoutInflater inflater, List<Map<String, String>> list, Context context, Activity activity, ShimmerTextView textView) {
        this.context = context;
        this.inflater = inflater;
        this.list = list;
        this.listView = listView;
        this.activity = activity;
        this.textView = textView;
    }

    @Override
    protected void onPreExecute() {
        if (textView != null) {
            textView.setVisibility(View.VISIBLE);
            shimmer = new Shimmer();
            shimmer.start(textView);

        } else {


        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        list.clear();
        list = htmlTolist.TopicsToList(s);
        if (textView == null) {

            adaptar = new ListViewAdaptar(list, context, inflater, listView);
            listView.setAdapter(adaptar);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(context, TopicsDetalis.class);
                    intent.putExtra("topicstitle", list.get(i).get("title"));
                    intent.putExtra("repliceurl", list.get(i).get("repliceurl"));
                    intent.putExtra("time", list.get(i).get("time"));
                    intent.putExtra("img", list.get(i).get("img"));
                    intent.putExtra("username", list.get(i).get("username"));

                    intent.putExtra("nodetitle", list.get(i).get("nodetitle"));
                    activity.startActivity(intent);


                }
            });
        } else {
            adaptar = new ListViewAdaptar(list, context, inflater, listView);
            shimmer.cancel();
            textView.setVisibility(View.GONE);
            listView.setAdapter(adaptar);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(context, TopicsDetalis.class);
                    intent.putExtra("topicstitle", list.get(i).get("title"));
                    intent.putExtra("repliceurl", list.get(i).get("repliceurl"));
                    intent.putExtra("time", list.get(i).get("time"));
                    intent.putExtra("img", list.get(i).get("img"));
                    intent.putExtra("username", list.get(i).get("username"));

                    intent.putExtra("nodetitle", list.get(i).get("nodetitle"));
                    activity.startActivity(intent);


                }
            });

        }


    }

    @Override
    protected String doInBackground(String... strings) {
        GetTopics getTopics = new GetTopics(activity);
        return getTopics.getTopic(strings[0]);


    }
}
