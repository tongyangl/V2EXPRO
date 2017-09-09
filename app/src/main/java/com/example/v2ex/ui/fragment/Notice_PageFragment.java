package com.example.v2ex.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.v2ex.R;
import com.example.v2ex.utils.LoadDate;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.weavey.loading.lib.LoadingLayout;

/**
 * Created by 佟杨 on 2017/9/3.
 */

public class Notice_PageFragment extends BaseFragment {

    private ListView listView;
    private SmartRefreshLayout smartRefreshLayout;
    private LoadingLayout loadingLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_notice, container, false);
        listView = (ListView) view.findViewById(R.id.lv);
        smartRefreshLayout = (SmartRefreshLayout) view.findViewById(R.id.swipe);
        loadingLayout = (LoadingLayout) view.findViewById(R.id.loadingLayout);
        return view;
    }


    @Override
    protected void loadData() {
        Log.d("----", "Notice_PageFragment");
        loadingLayout.setStatus(LoadingLayout.Loading);
        //smartRefreshLayout.autoRefresh(0);
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {

                LoadDate.getNotice(true,loadingLayout,listView, smartRefreshLayout, getActivity(), getActivity().getLayoutInflater());

            }
        });
        LoadDate.getNotice(false,loadingLayout,listView, smartRefreshLayout, getActivity(), getActivity().getLayoutInflater());


    }
}
