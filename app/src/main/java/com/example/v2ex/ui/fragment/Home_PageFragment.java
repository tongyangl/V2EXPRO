package com.example.v2ex.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.example.v2ex.R;
import com.example.v2ex.adapter.MyViewPageradapter;
import com.example.v2ex.widget.NoScrollViewPager;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 佟杨 on 2017/9/3.
 */

public class Home_PageFragment extends Fragment {
    private SearchView searchView;
    private NoScrollViewPager viewPager;
    private TabLayout tabLayout;
    private List<Fragment> fragmentList;
    private PagerSlidingTabStrip tabs;
    private DisplayMetrics dm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        Log.d("---", "ddd");
        viewPager = (NoScrollViewPager) view.findViewById(R.id.viewPager);
        searchView = (SearchView) view.findViewById(R.id.searchView);
        //tabs = (PagerSlidingTabStrip) view.findViewById(R.id.my_tab);
        tabLayout = (TabLayout) view.findViewById(R.id.table);
        dm = getResources().getDisplayMetrics();
        searchView.setIconifiedByDefault(true);
        //searchView.setFocusable(true);
        searchView.setIconified(false);

        //searchView.requestFocusFromTouch();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fragmentList = new ArrayList<>();
        allFragment allFragment = new allFragment();
        AppleFragment appleFragment = new AppleFragment();
        CityFragment cityFragment = new CityFragment();
        HotFragment hotFragment = new HotFragment();
        JobFragment jobFragment = new JobFragment();
        QandAFragment qandAFragment = new QandAFragment();
        R2Fragment r2Fragment = new R2Fragment();
        technologyFragment technologyFragment = new technologyFragment();
        transactionFragment transactionFragment = new transactionFragment();
        PlayFragment playFragment = new PlayFragment();
        fragmentList.add(allFragment);
        fragmentList.add(hotFragment);
        fragmentList.add(technologyFragment);
        fragmentList.add(appleFragment);
        fragmentList.add(jobFragment);
        fragmentList.add(transactionFragment);
        fragmentList.add(cityFragment);
        fragmentList.add(qandAFragment);
        fragmentList.add(r2Fragment);
        fragmentList.add(playFragment);
        List<String> list = new ArrayList<>();
        list.add("全部");
        list.add("最热");
        list.add("技术");
        list.add("apple");
        list.add("酷工作");
        list.add("交易");
        list.add("城市");
        list.add("问与答");
        list.add("r2");
        list.add("好玩");
        MyViewPageradapter myFragmentPagerAdaptar = new MyViewPageradapter(getChildFragmentManager(), fragmentList, list);

        viewPager.setAdapter(myFragmentPagerAdaptar);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.setOffscreenPageLimit(9);
    }


}
