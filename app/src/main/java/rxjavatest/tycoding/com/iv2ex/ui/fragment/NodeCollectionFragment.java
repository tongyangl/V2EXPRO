package rxjavatest.tycoding.com.iv2ex.ui.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rxjavatest.tycoding.com.iv2ex.R;
import rxjavatest.tycoding.com.iv2ex.adatper.NodeCollectAdapter;
import rxjavatest.tycoding.com.iv2ex.internet.intertnet;
import rxjavatest.tycoding.com.iv2ex.rxjava.rxjava;
import rxjavatest.tycoding.com.iv2ex.utils.MyDecoration;
import rxjavatest.tycoding.com.iv2ex.utils.tyutils;

/**
 * Created by 佟杨 on 2017/4/14.
 */

public class NodeCollectionFragment extends Fragment {

    private List<Map<String, String>> list;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nodecollect, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle);
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
                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                getCollectNode getCollectNode = new getCollectNode();
                getCollectNode.execute();
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
    }

    class getCollectNode extends AsyncTask<String, Void, String> {
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            list = new ArrayList<>();
            Elements elements = Jsoup.parse(s).select("div[id=MyNodes]").select("a[class=grid_item]");
            if (elements.size() != 0) {
                for (int i = 0; i < elements.size(); i++) {
                    Map<String, String> map = new HashMap();
                    String url = "http://www.v2ex.com/"+ elements.get(i).attr("href").substring(1);
                    String imgurl = "http://" + elements.get(i).select("img").attr("src").substring(2);
                    String title = elements.get(i).select("div").get(0).ownText();
                    String num = elements.get(i).select("span[class=fade f12]").text();
                    map.put("url", url);
                    map.put("imgurl", imgurl);
                    map.put("title", title);
                    map.put("num", num);
                    list.add(map);
                }
                NodeCollectAdapter adapter = new NodeCollectAdapter(list, getActivity());
                adapter.notifiy(list);
                recyclerView.setAdapter(adapter);
                swipeRefreshLayout.setRefreshing(false);
            }
        }


        @Override
        protected String doInBackground(String... params) {
            intertnet net = new intertnet(getContext());
            return net.getNodetoptic("https://www.v2ex.com/my/nodes");

        }
    }
}
