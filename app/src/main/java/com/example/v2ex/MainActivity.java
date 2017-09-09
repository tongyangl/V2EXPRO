package com.example.v2ex;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.v2ex.adapter.MyFragment1PagerAdaptar;
import com.example.v2ex.adapter.MyFragmentPagerAdaptar;
import com.example.v2ex.internet_service.AddCookiesInterceptor;
import com.example.v2ex.internet_service.Internet_Manager;
import com.example.v2ex.internet_service.ReceivedCookiesInterceptor;
import com.example.v2ex.model.TopticModel;
import com.example.v2ex.ui.fragment.Collection_PageFragment;
import com.example.v2ex.ui.fragment.Home_PageFragment;
import com.example.v2ex.ui.fragment.Notice_PageFragment;
import com.example.v2ex.ui.fragment.Setting_PageFragment;
import com.example.v2ex.utils.HtmlToList;
import com.example.v2ex.widget.NoScrollViewPager;
import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private RadioGroup radioGroup;
    private RadioButton Home_Page_Button;
    private RadioButton setting_page_Button;
    private RadioButton notice_page_Button;
    private RadioButton colliection_page_Button;
    private RadioButton createToptic;
    private List<Fragment> fragmentList;
    private Toolbar toolbar;
    private long exitTime = 0;
    private int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Internet_Manager.context = this;

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        Home_Page_Button = (RadioButton) findViewById(R.id.home_page);
        setting_page_Button = (RadioButton) findViewById(R.id.setting_page);
        notice_page_Button = (RadioButton) findViewById(R.id.notice_page);
        colliection_page_Button = (RadioButton) findViewById(R.id.collection_page);
        createToptic = (RadioButton) findViewById(R.id.createtoptic_button);
        // toolbar = (Toolbar) findViewById(R.id.toolBar);
        //  setSupportActionBar(toolbar);
        // toolbar.setTitle("V2EX");
        fragmentList = new ArrayList<>();
        Home_PageFragment home_pageFragment = new Home_PageFragment();
        Notice_PageFragment notice_pageFragment = new Notice_PageFragment();
        Collection_PageFragment collection_pageFragment = new Collection_PageFragment();
        Setting_PageFragment setting_pageFragment = new Setting_PageFragment();
        fragmentList.add(home_pageFragment);
        fragmentList.add(notice_pageFragment);

        fragmentList.add(collection_pageFragment);
        fragmentList.add(setting_pageFragment);
        FragmentManager fragmentManager = getSupportFragmentManager();
        MyFragment1PagerAdaptar myFragmentPagerAdaptar = new MyFragment1PagerAdaptar(fragmentManager, fragmentList);
        viewPager.setAdapter(myFragmentPagerAdaptar);
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(3);
        radioGroup.check(R.id.home_page);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {

                    case R.id.home_page:
                        viewPager.setCurrentItem(0);
                        radioGroup.check(R.id.home_page);
                        id = R.id.home_page;
                        break;
                    case R.id.collection_page:
                        viewPager.setCurrentItem(2);
                        radioGroup.check(R.id.collection_page);
                        id = R.id.collection_page;
                        break;
                    case R.id.setting_page:
                        viewPager.setCurrentItem(3);
                        id = R.id.setting_page;
                        radioGroup.check(R.id.setting_page);
                        break;
                    case R.id.notice_page:
                        viewPager.setCurrentItem(1);
                        radioGroup.check(R.id.notice_page);
                        id = R.id.notice_page;
                        break;
                    case R.id.createtoptic_button:

                        radioGroup.check(id);
                        break;

                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {

                moveTaskToBack(true);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
