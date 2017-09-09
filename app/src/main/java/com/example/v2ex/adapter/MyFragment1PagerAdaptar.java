package com.example.v2ex.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tongyang on 16-11-12.
 */

public class MyFragment1PagerAdaptar extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;

    public MyFragment1PagerAdaptar(FragmentManager fm, List<Fragment> list) {
        super(fm);
        fragmentList = list;
    }


    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // super.destroyItem(container, position, object);
    }
}
