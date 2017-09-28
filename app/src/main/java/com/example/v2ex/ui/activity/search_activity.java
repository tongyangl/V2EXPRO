package com.example.v2ex.ui.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Config;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.example.v2ex.R;
import com.example.v2ex.widget.SlideBackLayout;
import com.komi.slider.SliderConfig;
import com.komi.slider.SliderUtils;
import com.komi.slider.position.SliderPosition;

import java.util.Random;

/**
 * Created by 佟杨 on 2017/9/28.
 */

public class search_activity extends AppCompatActivity {

    private Toolbar toolbar;
    private SearchView searchView;
    private SlideBackLayout mSlideBackLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);
        Random random = new Random();
        int position = random.nextInt(SliderPosition.sPositionChildren.length);
        SliderPosition sliderPosition = SliderPosition.sPositionChildren[position];

        int primaryColor = getResources().getColor(R.color.colorPrimary);
        SliderConfig   mConfig = new SliderConfig.Builder()
                .primaryColor(primaryColor)
                .secondaryColor(Color.TRANSPARENT)
                .position(sliderPosition)
                .edge(false)
                .build();
        SliderUtils.attachActivity(this, mConfig);
       /* WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay();  //为获取屏幕宽、高
        WindowManager.LayoutParams p = getWindow().getAttributes();  //获取对话框当前的参数值
        p.height = (int) (d.getHeight() * 0.9);   //高度设置为屏幕的1.0
        p.width = (int) (d.getWidth() * 0.8);    //宽度设置为屏幕的0.8
        p.alpha = 1.0f;      //设置本身透明度
        p.dimAmount = 0.0f;      //设置黑暗度
        getWindow().setAttributes(p);*/
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        searchView = (SearchView) findViewById(R.id.search_view);
        toolbar.setNavigationIcon(R.drawable.ic_action_navigation_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mSlideBackLayout = new SlideBackLayout(this);
        mSlideBackLayout.bind();

        searchView.setQueryHint("搜索");

        searchView.setIconifiedByDefault(true);
        searchView.setFocusable(true);
        searchView.setIconified(false);
        searchView.requestFocusFromTouch();

    }


}
