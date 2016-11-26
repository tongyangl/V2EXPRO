package zero.tongyang.threegrand.com.x2expro.HomePage.Home_ViewPager.Some;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.zzhoujay.richtext.ImageHolder;
import com.zzhoujay.richtext.RichText;
import com.zzhoujay.richtext.callback.ImageFixCallback;
import com.zzhoujay.richtext.callback.OnURLClickListener;

import java.util.List;
import java.util.Map;

import zero.tongyang.threegrand.com.x2expro.Adaptar.TopicRepliceAdaptar;
import zero.tongyang.threegrand.com.x2expro.Utils.JsonTolist;
import zero.tongyang.threegrand.com.x2expro.Internet.GetTopics;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by tongyang on 16-11-18.
 */

public class LoadTopicsById extends AsyncTask<String, Void, String[]> implements OnURLClickListener {
    private List<Map<String, String>> list;
    private Map<String, String> map;
    private ListView listView;
    private Context context;
    private LayoutInflater inflater;
    private com.ldoublem.loadingviewlib.LVNews load;
    private TextView content;

    public LoadTopicsById(List<Map<String, String>> list, com.ldoublem.loadingviewlib.LVNews load,
                          ListView listView, Map<String, String> map, Context context, LayoutInflater inflater, TextView content) {
        this.list = list;

        this.listView = listView;
        this.map = map;
        this.content = content;
        this.context = context;
        this.inflater = inflater;
        this.load = load;
    }

    @Override
    protected void onPostExecute(String[] strings) {
        super.onPostExecute(strings);

        map = JsonTolist.GetTopicContent(strings[0]);
        list = JsonTolist.GetReplices(strings[1]);
        if (map.containsKey("content_rendered")) {
            addTextView(map.get("content_rendered"), 14);
        } else {
            addTextView(map.get("content"), 14);

        }
        TopicRepliceAdaptar repliceAdaptar = new TopicRepliceAdaptar(inflater, list, listView, context);
        listView.setAdapter(repliceAdaptar);
        load.stopAnim();
        load.setVisibility(View.GONE);

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        load.startAnim();

    }

    @Override
    protected String[] doInBackground(String... strings) {

        String topics = "api/topics/show.json?id=" + strings[0];
        String topicsreplice = "api/replies/show.json?topic_id=" + strings[0];
        GetTopics getTopics = new GetTopics(context);

        String a = getTopics.getTopic(topics);
        String b = getTopics.getTopic(topicsreplice);
        return new String[]{

                a, b
        };


    }

    public void addTextView(String s, int size) {
        RichText.fromHtml(s).autoFix(true).clickable(true).urlClick(this).fix(new ImageFixCallback() {
            @Override
            public void onFix(ImageHolder holder) {

            }
        }).into(content);

    }

    @Override
    public boolean urlClicked(String url) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri uri = Uri.parse(url);
        intent.setData(uri);
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

        return true;
    }
}
