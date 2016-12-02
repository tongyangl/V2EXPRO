package zero.tongyang.threegrand.com.x2expro.HomePage.Home_ViewPager.Some;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;
import com.zzhoujay.richtext.ImageHolder;
import com.zzhoujay.richtext.RichText;
import com.zzhoujay.richtext.callback.ImageFixCallback;
import com.zzhoujay.richtext.callback.OnURLClickListener;

import java.util.List;
import java.util.Map;

import zero.tongyang.threegrand.com.x2expro.Adaptar.TopicRepliceAdaptar;
import zero.tongyang.threegrand.com.x2expro.Utils.JsonTolist;
import zero.tongyang.threegrand.com.x2expro.Internet.GetTopics;
import zero.tongyang.threegrand.com.x2expro.Utils.htmlTolist;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by tongyang on 16-11-18.
 */

public class LoadTopicsById extends AsyncTask<String, Void, String> implements OnURLClickListener {
    private List<Map<String, String>> list;
    private Map<String, String> map;
    private ListView listView;
    private Context context;
    private LayoutInflater inflater;
    private TextView content;
    private ShimmerTextView textView;
    private Shimmer shimmer;

    public LoadTopicsById(List<Map<String, String>> list,
                          ListView listView, Map<String, String> map, Context context, LayoutInflater inflater, TextView content, ShimmerTextView textView) {
        this.list = list;
        this.listView = listView;
        this.map = map;
        this.content = content;
        this.context = context;
        this.inflater = inflater;
        this.textView = textView;
    }

    @Override
    protected void onPostExecute(String strings) {
        super.onPostExecute(strings);

        addTextView(htmlTolist.gettoticdetalis(strings), 8);
        if (textView==null){
            list = htmlTolist.getdetals(strings);
            TopicRepliceAdaptar repliceAdaptar = new TopicRepliceAdaptar(inflater, list, listView, context);
            listView.setAdapter(repliceAdaptar);

        }else {
            list = htmlTolist.getdetals(strings);
            TopicRepliceAdaptar repliceAdaptar = new TopicRepliceAdaptar(inflater, list, listView, context);
            listView.setVisibility(View.VISIBLE);
            shimmer.cancel();
            textView.setVisibility(View.GONE);
            listView.setAdapter(repliceAdaptar);
        }


    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (textView != null) {
            textView.setVisibility(View.VISIBLE);
            shimmer = new Shimmer();
            shimmer.start(textView);

        } else {


        }


    }

    @Override
    protected String doInBackground(String... strings) {
        GetTopics getTopics = new GetTopics(context);
        return getTopics.getTopic(strings[0]);
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
