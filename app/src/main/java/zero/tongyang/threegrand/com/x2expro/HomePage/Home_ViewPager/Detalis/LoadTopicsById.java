package zero.tongyang.threegrand.com.x2expro.HomePage.Home_ViewPager.Detalis;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzhoujay.richtext.ImageHolder;
import com.zzhoujay.richtext.RichText;
import com.zzhoujay.richtext.callback.ImageFixCallback;
import com.zzhoujay.richtext.callback.OnURLClickListener;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

import zero.tongyang.threegrand.com.x2expro.Internet.GetTopics;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by tongyang on 16-11-18.
 */

public class LoadTopicsById extends AsyncTask<String, Void, String[]> implements OnURLClickListener {
    private List<Map<String, String>> list;
    private Map<String, String> map;
    private ListView listView;
    private ProgressBar progressBar;
    private LinearLayout linearLayout;
    private RelativeLayout relativeLayout;
    private Context context;
    private LayoutInflater inflater;
    private com.ldoublem.loadingviewlib.LVNews load;

    public LoadTopicsById(List<Map<String, String>> list, LinearLayout linearLayout, com.ldoublem.loadingviewlib.LVNews load,
                          TextView time, TextView nodetitle, TextView username, ImageView imageView,
                          ListView listView, Map<String, String> map, RelativeLayout relativeLayout, Context context, LayoutInflater inflater) {
        this.list = list;
        this.linearLayout = linearLayout;
        this.listView = listView;
        this.map = map;
        this.relativeLayout = relativeLayout;
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

        TopicRepliceAdaptar repliceAdaptar = new TopicRepliceAdaptar(inflater, list, listView);
        listView.setAdapter(repliceAdaptar);
        load.stopAnim();
        load.setVisibility(View.GONE);
        relativeLayout.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        load.startAnim();
        relativeLayout.setVisibility(View.GONE);
    }

    @Override
    protected String[] doInBackground(String... strings) {

        String topics = "api/topics/show.json?id=" + strings[0];
        String topicsreplice = "api/replies/show.json?topic_id=" + strings[0];

        String a = GetTopics.getTopic(topics);
        String b = GetTopics.getTopic(topicsreplice);
        return new String[]{

                a, b
        };


    }

    public void addTextView(String s, int size) {
        TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setTextColor(Color.BLACK);
        textView.setText(s);
        textView.setTextSize(size);
        RichText.fromHtml(s).autoFix(true).clickable(true).urlClick(this).fix(new ImageFixCallback() {
            @Override
            public void onFix(ImageHolder holder) {

            }
        }).into(textView);
        linearLayout.addView(textView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public boolean urlClicked(String url) {
        Intent intent=new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri uri=Uri.parse(url);
        intent.setData(uri);
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

        return true;
    }
}
