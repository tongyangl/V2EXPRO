package rxjavatest.tycoding.com.iv2ex.ui.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hss01248.pagestate.PageManager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rxjavatest.tycoding.com.iv2ex.R;
import rxjavatest.tycoding.com.iv2ex.adatper.NodeTopticsAdapter;
import rxjavatest.tycoding.com.iv2ex.internet.intertnet;
import rxjavatest.tycoding.com.iv2ex.ui.activity.TopicsDetalisActivity;

/**
 * Created by 佟杨 on 2017/4/14.
 */

public class TopticCollectionFragment extends Fragment {
    private ListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<Map<String, String>> list;
    private PageManager pageManager;
   private   getCollectToptic getCollectToptic;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_topticcollect, container, false);
        listView = (ListView) view.findViewById(R.id.lv);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SwipeRefreshLayout.OnRefreshListener listener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d("---", "onrefresh");
                 getCollectToptic = new getCollectToptic();
                getCollectToptic.execute();

            }
        };
        swipeRefreshLayout.setOnRefreshListener(listener);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
        listener.onRefresh();
        pageManager = PageManager.init(listView, "空空如也，什么也没有", false, new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    class getCollectToptic extends AsyncTask<String, Void, String> {
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            list = new ArrayList<>();
            Elements elements = Jsoup.parse(s).select("div[id=Main]").select("div[class=box]").select("div[class=cell item]");
            if (elements.size() != 0) {
                for (Element element : elements) {
                    Map<String, String> map = new HashMap<>();
                    Elements tr = element.select("tr").select("td");
                    String image = tr.get(0).select("img").attr("src");
                    String img = image.substring(2, image.length());
                    map.put("img", "http://" + img);
                    String replice = "";
                    String title = tr.get(2).select("span[class=item_title]").select("a").text();
                    if (tr.get(3).hasText()) {
                        replice = tr.get(3).select("a").text();

                    }
                    String username = "";
                    String lastreplice = "";

                    map.put("title", title);
                    map.put("replies", replice);
                    Elements elements1 = tr.get(2).select("span[class=small fade]").select("strong");
                    if (elements1.size() == 2) {
                        username = elements1.get(0).select("a").text();
                        if (elements1.get(1).hasText()) {
                            lastreplice = elements1.get(1).select("a").text();

                        } else {
                            username = elements1.get(0).select("a").text();
                            lastreplice = null;
                        }
                    } else if (elements1.size() == 1) {
                        username = elements1.get(0).select("a").text();
                        lastreplice = null;

                    }
                    map.put("username", username);
                    map.put("lastreplice", lastreplice);
                    Element t = tr.get(2).select("span[class=small fade]").first();
                    String ti = t.ownText();
                    String time = ti.substring(3, ti.length());
                    map.put("time", time);
                    String repliceurl = tr.get(2).select("span[class=item_title]").select("a").attr("href");
                    map.put("repliceurl", repliceurl.substring(1, repliceurl.length()));
                    list.add(map);
                }
                NodeTopticsAdapter adapter = new NodeTopticsAdapter(list, getActivity());
                listView.setAdapter(adapter);
                swipeRefreshLayout.setRefreshing(false);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(getContext(), TopicsDetalisActivity.class);
                        intent.putExtra("repliceurl", list.get(position).get("repliceurl"));
                        startActivity(intent);
                    }
                });
            } else {
                swipeRefreshLayout.setRefreshing(false);
                pageManager.showEmpty();
            }

        }


        @Override
        protected String doInBackground(String... params) {
            intertnet net = new intertnet(getContext());
            return net.getNodetoptic("https://www.v2ex.com/my/topics");

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (getCollectToptic!=null){
            getCollectToptic.cancel(true);

        }
    }
}
