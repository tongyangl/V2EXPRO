package com.example.v2ex.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.example.v2ex.R;
import com.example.v2ex.utils.LoadDate;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.weavey.loading.lib.LoadingLayout;

/**
 * Created by 佟杨 on 2017/9/8.
 */

public class TopticCollectActivity extends Activity {
    private SmartRefreshLayout smartRefreshLayout;
    private LoadingLayout loadingLayout;
    private ListView listView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topticcollect);
        smartRefreshLayout = (SmartRefreshLayout) findViewById(R.id.smart);
        listView = (ListView) findViewById(R.id.listView);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        loadingLayout = (LoadingLayout) findViewById(R.id.loadingLayout);
        toolbar.setTitle("主题收藏");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        LoadDate.getTopticCollect(false, smartRefreshLayout, getApplicationContext(), listView, loadingLayout, getLayoutInflater());

        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                LoadDate.getTopticCollect(true, smartRefreshLayout, getApplicationContext(), listView, loadingLayout, getLayoutInflater());

            }
        });

    }
}
