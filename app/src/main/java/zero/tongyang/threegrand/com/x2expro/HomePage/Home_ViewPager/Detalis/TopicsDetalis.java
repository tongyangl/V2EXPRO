package zero.tongyang.threegrand.com.x2expro.HomePage.Home_ViewPager.Detalis;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zero.tongyang.threegrand.com.x2expro.Internet.GetTopics;
import zero.tongyang.threegrand.com.x2expro.R;

/**
 * Created by tongyang on 16-11-16.
 */

public class TopicsDetalis extends Activity {
    AddView addView;
    @BindView(R.id.top)
    LinearLayout top;
    @BindView(R.id.linear)
    LinearLayout linear;
    @BindView(R.id.linear1)
    LinearLayout linear1;
    private String title;
    private String repliceurl;
    private String img;
    private String Username;
    private String nodetitle;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.imagView)
    ImageView imagView;
    @BindView(R.id.username)
    TextView username;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.nodename)
    TextView nodename;
    @BindView(R.id.topictitle)
    TextView topictitle;
    @BindView(R.id.topic_content)
    TextView topicContent;
    @BindView(R.id.lv)
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topics_detalis);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        title = intent.getStringExtra("topicstitle");
        repliceurl = intent.getStringExtra("repliceurl");
        img = intent.getStringExtra("img");
        Username = intent.getStringExtra("username");
        nodetitle = intent.getStringExtra("nodetitle");
        addView = new AddView(this, linear1, linear);
        LoadTopicDetalis loadTopicDetalis = new LoadTopicDetalis();
        loadTopicDetalis.execute(repliceurl);

        TopicRepliceAdaptar adaptar = new TopicRepliceAdaptar();
        lv.setAdapter(adaptar);

    }

    @OnClick(R.id.back)
    void back() {

        finish();
    }

    class LoadTopicDetalis extends AsyncTask<String, Void, String> {
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            topictitle.setText(title);
            nodename.setText(nodetitle);
            username.setText(Username);
            Document doc = Jsoup.parse(s);
            String a = doc.html().replace("<br>", "\n");
            doc = Jsoup.parse(a);
            String e = doc.select("small[class=gray]").first().text();
            time.setText(e.substring(username.length() + 1, e.length()));
            Element element = doc.select("div[class=topic_content]").first();

            if (element.select("div[class=markdown_body]").hasText()) {
                Elements elements = element.select("div[class=markdown_body]").select("p");

                for (int i = 0; i < elements.size(); i++) {
                    if (elements.get(i).hasAttr("alt src")) {
                        addView.addImageView(elements.get(i).attr("alt src"));

                        if (elements.get(i).hasText()) {
                            addView.addTextView(elements.get(i).text());

                        }
                    } else if (elements.get(i).hasAttr("src")) {

                        addView.addImageView(elements.get(i).attr("src"));
                        Log.d("iiii", elements.get(i).attr("src"));
                        if (elements.get(i).hasText()) {
                            addView.addTextView(elements.get(i).text());

                        }

                    } else {

                        addView.addTextView(elements.get(i).text());
                    }

                }
            } else {

                topicContent.setText(element.text());

            }
            Elements suble = doc.select("div[class=subtle]");
            if (suble.size() > 0) {
                for (int i = 0; i < suble.size(); i++) {
                    addView.addLinear(suble.get(i).select("span[class=fade]").text(), suble.get(i).select("div[class=topic_content").text());
                }

            }

        }


        @Override
        protected String doInBackground(String... strings) {


            return GetTopics.getTopic(strings[0]);
        }
    }


}
