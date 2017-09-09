package com.example.v2ex.ui.activity;

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
import android.view.View;
import android.widget.ListView;

import com.example.v2ex.R;
import com.example.v2ex.utils.LoadDate;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.weavey.loading.lib.LoadingLayout;


/**
 * Created by 佟杨 on 2017/4/10.
 */

public class NodeTopticsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView listView;
    private SmartRefreshLayout swipe;
    private String url;
    private String num;
    private String title;
    private ProgressDialog dialog;
    private LoadingLayout loadingLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_node_toptics);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        listView = (ListView) findViewById(R.id.lv);
        loadingLayout = (LoadingLayout) findViewById(R.id.loadingLayout);
        swipe = (SmartRefreshLayout) findViewById(R.id.swipe);
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

        // swipe.autoRefresh(0);
        swipe.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {


                LoadDate.getNodeToptics(true, loadingLayout, url.replace("http://www.v2ex.com/go/", "")
                        , swipe, listView, getLayoutInflater(), NodeTopticsActivity.this, Integer.parseInt(num));

            }
        });

        LoadDate.getNodeToptics(false, loadingLayout, url.replace("http://www.v2ex.com/go/", "")
                , swipe, listView, getLayoutInflater(), NodeTopticsActivity.this, Integer.parseInt(num));

    }

  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        MenuItem mMenuItem;
        menuInflater.inflate(R.menu.collection, menu);

        mMenuItem = menu.findItem(R.id.it_collect);

        return super.onCreateOptionsMenu(menu);

    }*/

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.it_collect) {

            if (BaseApplication.islogin(getApplicationContext())) {
                collecttion cool = new collecttion();
                cool.execute();
            } else {
                Intent intent3 = new Intent(NodeTopticsActivity.this, SiginActivity.class);
                startActivity(intent3);
            }

            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/

 /*   class collecttion extends AsyncTask<String, Void, Integer> {
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
            return net.collection(url);
        }
    }*/
}
