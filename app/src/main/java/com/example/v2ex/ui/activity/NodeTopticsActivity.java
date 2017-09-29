package com.example.v2ex.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.v2ex.R;
import com.example.v2ex.utils.LoadDate;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.weavey.loading.lib.LoadingLayout;


/**
 * Created by 佟杨 on 2017/4/10.
 */

public class NodeTopticsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private LRecyclerView listView;
    // private SmartRefreshLayout swipe;
    private String url;
    private String num;
    private String title;
    private ProgressDialog dialog;
    private LoadingLayout loadingLayout;
    private CoordinatorLayout mParent;
    private CollapsingToolbarLayout toolbarLayout;
    private ImageView imageView;
    private ImageView iv;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        listView = (LRecyclerView) findViewById(R.id.lv);
        imageView = (ImageView) findViewById(R.id.iv);
        iv = (ImageView) findViewById(R.id.Iv);
        toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        //loadingLayout = (LoadingLayout) findViewById(R.id.loadingLayout);
        // swipe = (SmartRefreshLayout) findViewById(R.id.swape);

        url = getIntent().getStringExtra("url");
        num = getIntent().getStringExtra("num");
        title = getIntent().getStringExtra("title");

        toolbar.setTitle(title);
        toolbar.setSubtitle(num + "个主题");
        setSupportActionBar(toolbar);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, "我在v2ex找到个不错的主题！" + "https://www.v2ex.com/" + url);
                shareIntent.setType("text/plain");

                //设置分享列表的标题，并且每次都显示分享列表
                startActivity(Intent.createChooser(shareIntent, "分享主题到"));
                return true;
            }
        });
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        if (!url.contains("https")) {
            url = url.replace("http://www.v2ex.com/go/", "");

        } else if (url.contains("https")) {
            url = url.replace("https://www.v2ex.com/go/", "");
        }
        LoadDate.getNodeToptics(iv, toolbar, imageView, false,url
                , listView,
                getLayoutInflater(), NodeTopticsActivity.this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        MenuItem mMenuItem;
        menuInflater.inflate(R.menu.node, menu);


        return super.onCreateOptionsMenu(menu);

    }

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
