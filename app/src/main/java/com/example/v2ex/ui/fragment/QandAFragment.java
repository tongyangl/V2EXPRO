package com.example.v2ex.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
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
 * Created by tongyang on 16-11-12.
 */

public class QandAFragment extends BaseFragment {
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
      //  smartRefreshLayout.autoRefresh(0);
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {

                LoadDate.loadTopticsData(true,"qna", listView, smartRefreshLayout,
                        getActivity().getLayoutInflater(),getActivity(),loadingLayout);

            }
        });
        LoadDate.loadTopticsData(false,"qna", listView, smartRefreshLayout,
                getActivity().getLayoutInflater(),getActivity(),loadingLayout);
    }
}
