package rxjavatest.tycoding.com.iv2ex.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import rxjavatest.tycoding.com.iv2ex.R;
import rxjavatest.tycoding.com.iv2ex.internet.intertnet;
import rxjavatest.tycoding.com.iv2ex.rxjava.rxjava;
import rxjavatest.tycoding.com.iv2ex.utils.tyutils;

/**
 * Created by 佟杨 on 2017/4/10.
 */

public class NodeTopticsActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.lv)
    ListView listView;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    private String url;
    private String num;
    private String title;
    private  ProgressDialog dialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_node_toptics);
        ButterKnife.bind(this);

        url = getIntent().getStringExtra("url");
        num = getIntent().getStringExtra("num");
        title = getIntent().getStringExtra("title");
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        swipe.setSize(SwipeRefreshLayout.DEFAULT);
        swipe.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);

        SwipeRefreshLayout.OnRefreshListener listener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d("---", "onrefresh");
                rxjava.getNodetoptics(NodeTopticsActivity.this, url, swipe, listView, num);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        MenuItem mMenuItem;
        menuInflater.inflate(R.menu.collection, menu);

        mMenuItem = menu.findItem(R.id.it_collect);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.it_collect) {
            collecttion cool = new collecttion();
            cool.execute();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    class collecttion extends AsyncTask<String, Void, Integer> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(NodeTopticsActivity.this);
            dialog.setTitle("收藏中...");
            dialog.show();
        }

        @Override
        protected void onPostExecute(Integer i) {
            Log.d("---", i + "");
            if (i == 302) {
                if (rxjava.nodetoptics.startsWith("unfavorite")) {
                    rxjava.nodetoptics = rxjava.nodetoptics.substring(2);
                    dialog.dismiss();

                    Toast.makeText(NodeTopticsActivity.this, "取消收藏成功", Toast.LENGTH_SHORT).show();
                    Log.d("---", rxjava.nodetoptics);
                } else {
                    dialog.dismiss();

                    rxjava.nodetoptics = "un" + rxjava.nodetoptics;
                    Log.d("---", rxjava.nodetoptics);
                    Toast.makeText(NodeTopticsActivity.this, "节点收藏成功", Toast.LENGTH_SHORT).show();

                }
            } else {
                Toast.makeText(NodeTopticsActivity.this, "收藏失败", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(i);
        }

        @Override
        protected Integer doInBackground(String... params) {
            intertnet net = new intertnet(NodeTopticsActivity.this);
            String url = tyutils.BASE_URL + rxjava.nodetoptics;
            Log.d("---", url);
            return net.collection(url);
        }
    }
}
