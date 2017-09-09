package com.example.v2ex.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewConfiguration;

import java.lang.reflect.Field;
import java.util.List;

public class MyViewPageradapter extends FragmentPagerAdapter {
    private List<Fragment> list;
    private List<String> tablist;


    public MyViewPageradapter(FragmentManager fm, List<Fragment> list, List<String> tablist) {
        super(fm);
        this.list = list;
        this.tablist = tablist;

    }


    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tablist.get(position);
    }



}
