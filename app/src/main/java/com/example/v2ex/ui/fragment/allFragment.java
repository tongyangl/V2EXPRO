package com.example.v2ex.ui.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.v2ex.R;
import com.example.v2ex.adapter.TopticsListViewAdapter;
import com.example.v2ex.internet_service.Internet_Manager;
import com.example.v2ex.model.TopticModel;
import com.example.v2ex.utils.HtmlToList;
import com.example.v2ex.utils.LoadDate;
import com.scwang.smartrefresh.header.FlyRefreshHeader;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.header.WaveSwipeHeader;
import com.scwang.smartrefresh.header.fungame.FunGameHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.weavey.loading.lib.LoadingLayout;

import java.util.List;

import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by tongyang on 16-11-12.
 */

public class allFragment extends BaseFragment {
    private LoadingLayout loadingLayout;
    private SmartRefreshLayout smartRefreshLayout;
    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homechildren, container, false);
        listView = (ListView) view.findViewById(R.id.recycle);
        smartRefreshLayout = (SmartRefreshLayout) view.findViewById(R.id.swipe);
        loadingLayout = (LoadingLayout) view.findViewById(R.id.loadingLayout);

        return view;


    }


    @Override
    protected void loadData() {
        loadingLayout.setStatus(LoadingLayout.Loading);
        LoadDate.loadTopticsData(false,"all", listView, smartRefreshLayout, getActivity().getLayoutInflater(), getActivity(),loadingLayout);

        //smartRefreshLayout.autoRefresh(0);
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {

                LoadDate.loadTopticsData(true,"all", listView, smartRefreshLayout, getActivity().getLayoutInflater(), getActivity(),loadingLayout);

            }
        });


    }

}
