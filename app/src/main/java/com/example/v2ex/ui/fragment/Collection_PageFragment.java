package com.example.v2ex.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.v2ex.R;
import com.example.v2ex.adapter.NodeRecycleAdapter;
import com.example.v2ex.model.NodesModel;
import com.example.v2ex.utils.LoadDate;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by 佟杨 on 2017/9/3.
 */

public class Collection_PageFragment extends BaseFragment implements SearchView.OnQueryTextListener {
    private NodeRecycleAdapter adapter;
    private SmartRefreshLayout mrefreshLayout;
    private RecyclerView recyclerView;
    private SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_node, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle);
        mrefreshLayout = (SmartRefreshLayout) view.findViewById(R.id.swipe);
        searchView = (SearchView) view.findViewById(R.id.bt_search);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mrefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {

                LoadDate.getNodesJson(getActivity(), mrefreshLayout, recyclerView);
            }
        });
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        searchView.setOnQueryTextListener(this);
    }

    @Override
    protected void loadData() {

        Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {

                subscriber.onNext("");
            }
        }).map(new Func1<String, List<NodesModel>>() {
            @Override
            public List<NodesModel> call(String s) {
                return getNodesList(getContext());
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<NodesModel>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<NodesModel> nodesModels) {

                        adapter = new NodeRecycleAdapter(nodesModels, getActivity());
                        adapter.notifiy(nodesModels);

                        recyclerView.setAdapter(adapter);
                    }
                });

    }

    public static List<NodesModel> getNodesList(Context context) {

        InputStream inputStream =context .getResources().openRawResource(R.raw.nodejson);
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(inputStream, "utf-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(inputStreamReader);
        StringBuffer sb = new StringBuffer("");
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<NodesModel> list = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(sb.toString());
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = new JSONObject(array.getString(i));

                NodesModel nodesModel = new NodesModel();
                nodesModel.setTitle(object.getString("title"));
                nodesModel.setContent(object.getString("header"));
                nodesModel.setNum(object.getString("topics"));
                nodesModel.setUrl(object.getString("url"));
                nodesModel.setName(object.getString("name"));

                list.add(nodesModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        adapter.getFilter().filter(newText);
        return true;
    }
}
