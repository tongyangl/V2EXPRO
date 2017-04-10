package rxjavatest.tycoding.com.iv2ex.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import butterknife.BindView;
import butterknife.ButterKnife;
import rxjavatest.tycoding.com.iv2ex.R;
import rxjavatest.tycoding.com.iv2ex.rxjava.rxjava;
import rxjavatest.tycoding.com.iv2ex.utils.MyDecoration;

/**
 * Created by 佟杨 on 2017/4/10.
 */

public class NodeTopticsActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycle)
    RecyclerView recycle;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    private String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_node_toptics);
        ButterKnife.bind(this);
        url = getIntent().getStringExtra("url");
        swipe.setSize(SwipeRefreshLayout.DEFAULT);
        swipe.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);

        SwipeRefreshLayout.OnRefreshListener listener=new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d("---","onrefresh");
                LinearLayoutManager manager=new LinearLayoutManager(NodeTopticsActivity.this);
                recycle.setLayoutManager(manager);
                recycle.addItemDecoration(new MyDecoration(NodeTopticsActivity.this));
                rxjava.getToptics(NodeTopticsActivity.this,"?tab=all",swipe,recycle,false);
            }
        };
        swipe.setOnRefreshListener(listener);
        swipe.post(new Runnable() {
            @Override
            public void run() {
                swipe.setRefreshing(true);
            }
        });
        listener.onRefresh();
    }
}
