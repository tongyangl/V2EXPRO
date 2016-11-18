package zero.tongyang.threegrand.com.x2expro.HomePage.Home_ViewPager.Detalis;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ldoublem.loadingviewlib.LVCircularZoom;
import com.ldoublem.loadingviewlib.LVNews;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zero.tongyang.threegrand.com.x2expro.MyStyle.MyListView;
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
    @BindView(R.id.linear2)
    LinearLayout linear2;
    @BindView(R.id.shadow_view)
    View shadowView;
    @BindView(R.id.view1)
    View view1;
    @BindView(R.id.lv)
    MyListView lv;
    @BindView(R.id.relat)
    RelativeLayout relat;
    @BindView(R.id.load)
    LVNews load;
    private String title;
    private String repliceurl;
    private String img;
    private String Username;
    private String nodetitle;
    private List<Map<String, String>> list;
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
    String id;
    Map<String, String> map;

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
        addView = new AddView(this, linear1, linear, linear2);
       /* LoadTopicDetalis loadTopicDetalis = new LoadTopicDetalis();
        loadTopicDetalis.execute(repliceurl);*/
        lv.setFocusable(false);
        id = repliceurl.substring(2, 8);
        map = new HashMap<>();
        list = new ArrayList<>();
        topictitle.setText(title);
        nodename.setText(nodetitle);
        username.setText(Username);
        LayoutInflater inflater = getLayoutInflater();
        LoadTopicsById loadTopicsById = new LoadTopicsById(list, linear,load, time, topictitle, username, imagView, lv, map, relat, getApplicationContext(), inflater);
        loadTopicsById.execute(id);

    }

    @OnClick(R.id.back)
    void back() {

        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
   /* class LoadTopicDetalis extends AsyncTask<String, Void, String> {
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            AddtopicView(s);
            AddRepiliceView(s);

        }

        @Override
        protected String doInBackground(String... strings) {

            return GetTopics.getTopic(strings[0]);
        }
    }

    public void AddRepiliceView(String s) {
        list = new ArrayList<>();

        Document doc = Jsoup.parse(s);
        Elements elements = doc.select("div[class=box]");
        if (elements.size() == 5) {

            if (elements.get(4).select("div").hasClass("fr")) {
                Elements e = elements.get(4).select("div[class=cell]");
                for (int i = 1; i < e.size(); i++) {
                    Map<String, String> map = new HashMap<>();
                    Elements td = e.get(i).select("td");
                    String img = td.get(0).select("img").attr("src");
                    String username = td.get(2).select("strong").select("a").text();
                    String time = td.get(2).select("span[class=fade small]").text();
                    String replicecontent = td.get(2).select("div[class=reply_content]").text();
                    map.put("img", "http://" + img.substring(2, img.length()));
                    map.put("username", username);
                    map.put("time", time);
                    map.put("replice", replicecontent);
                    list.add(map);
                }


            } else {

            }

        } else if (elements.size() == 4) {
            if (elements.get(3).select("div").hasClass("fr")) {
                Elements e = elements.get(3).select("div[class=cell]");
                for (int i = 1; i < e.size(); i++) {
                    Map<String, String> map = new HashMap<>();
                    Elements td = e.get(i).select("td");
                    String img = td.get(0).select("img[attr=src]").text();
                    String username = td.get(2).select("strong").select("a").text();
                    String time = td.get(2).select("span[class=fade small]").text();
                    String replicecontent = td.get(2).select("div[class=reply_content]").text();
                    map.put("img", img);
                    Log.d("imgimg", img);
                    map.put("username", username);
                    map.put("time", time);
                    map.put("replice", replicecontent);
                    list.add(map);
                }

            } else {

            }

        }
        Log.d("tttt", list.size() + "asasd");
        LayoutInflater inflater = getLayoutInflater();
        TopicRepliceAdaptar adaptar = new TopicRepliceAdaptar(inflater, list, lv);
        if (list.size() != 0) {

            lv.setAdapter(adaptar);

        }
    }


    public void AddtopicView(String s) {

        topictitle.setText(title);
        nodename.setText(nodetitle);
        username.setText(Username);

        Drawable cachedImage = asyncImageLoader.loadDrawable(img, new AsyncImageLoader.ImageCallback() {
            public void imageLoaded(Drawable imageDrawable, String imageUrl) {
                ImageView imageViewByTag = (ImageView) lv.findViewWithTag(imageUrl);
                if (imageViewByTag != null) {
                    imageViewByTag.setImageDrawable(imageDrawable);
                }
            }
        });
        if (cachedImage == null) {
            imagView.setImageResource(R.drawable.android);
        } else {
            imagView.setImageDrawable(cachedImage);
        }

        Document doc = Jsoup.parse(s);
        String a = doc.html().replace("<br>", "\n");
        doc = Jsoup.parse(a);

        String e = doc.select("small[class=gray]").first().text();
        time.setText(e.substring(username.length() + 1, e.length()));
        Element element = doc.select("div[class=topic_content]").first();

        if (element.select("div").hasClass("markdown_body")) {
            if (element.select("div[class=markdown_body]").hasText()) {

                Elements elements = element.getAllElements();

                for (Element element1 : elements) {
                    if (element1.tagName().equals("img")) {
                        addView.addImageView(element1.attr("src"));

                    } else {

                        switch (element1.tagName()) {

                            case "h1":
                                addView.addTextView(element1.ownText(), 20);
                                break;
                            case "h2":
                                addView.addTextView(element1.ownText(), 19);
                                break;
                            case "h3":
                                addView.addTextView(element1.ownText(), 18);
                                break;
                            case "h4":
                                addView.addTextView(element1.ownText(), 17);
                                break;
                            case "h5":
                                addView.addTextView(element1.ownText(), 16);
                                break;
                            case "h6":
                                addView.addTextView(element1.ownText(), 15);
                                break;
                            case "strong":
                                addView.addTextView(element1.ownText(), 14);
                                break;
                            default:
                                addView.addTextView(element1.ownText(), 14);
                                break;

                        }

                    }

                }

            }

        } else {

            addView.addTextView(element.text(), 14);
        }
        Elements suble = doc.select("div[class=subtle]");
        if (suble.size() > 0) {
            for (int i = 0; i < suble.size(); i++) {
                addView.addLinear(suble.get(i).select("span[class=fade]").text(), suble.get(i).select("div[class=topic_content").text());
            }

        }


    }*/


}
