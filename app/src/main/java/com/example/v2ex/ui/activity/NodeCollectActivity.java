package com.example.v2ex.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.v2ex.R;
import com.example.v2ex.utils.LoadDate;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.weavey.loading.lib.LoadingLayout;

/**
 * Created by 佟杨 on 2017/9/7.
 */

public class NodeCollectActivity extends Activity {
    private SmartRefreshLayout smartRefreshLayout;
    private RecyclerView recyclerView;
    private LoadingLayout loadingLayout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_nodecollect);
        smartRefreshLayout = (SmartRefreshLayout) findViewById(R.id.smart);
        recyclerView = (RecyclerView) findViewById(R.id.recycle);
        loadingLayout = (LoadingLayout) findViewById(R.id.loadingLayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        toolbar.setTitle("节点收藏");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        LoadDate.getNodeCollect(false, smartRefreshLayout, getApplicationContext(), recyclerView, loadingLayout);

        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                LoadDate.getNodeCollect(true, smartRefreshLayout, getApplicationContext(), recyclerView, loadingLayout);

            }
        });
    }


}
