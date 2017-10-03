package com.example.v2ex;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.v2ex.adapter.MyFragment1PagerAdaptar;
import com.example.v2ex.internet_service.Internet_Manager;
import com.example.v2ex.ui.activity.CreateTopticActivity;

import com.example.v2ex.ui.activity.SiginActivity;
import com.example.v2ex.ui.activity.WebViewActivity;
import com.example.v2ex.ui.fragment.Collection_PageFragment;
import com.example.v2ex.ui.fragment.Home_PageFragment;
import com.example.v2ex.ui.fragment.Notice_PageFragment;
import com.example.v2ex.ui.fragment.Setting_PageFragment;
import com.example.v2ex.utils.LoadImg;
import com.example.v2ex.utils.SomeUtils;
import com.miguelcatalan.materialsearchview.MaterialSearchView;


import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements MaterialSearchView.OnQueryTextListener {
    private ViewPager viewPager;
    // private RadioGroup radioGroup;
    private RadioButton Home_Page_Button;
    private RadioButton setting_page_Button;
    private RadioButton notice_page_Button;
    private RadioButton colliection_page_Button;
    private ImageButton createToptic;
    private List<Fragment> fragmentList;
    private Toolbar toolbar;
    private long exitTime = 0;
    private int id = 0;
    // private TextView mTitle;
    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Internet_Manager.context = this;
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        //  radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        Home_Page_Button = (RadioButton) findViewById(R.id.home_page);
        setting_page_Button = (RadioButton) findViewById(R.id.setting_page);
        notice_page_Button = (RadioButton) findViewById(R.id.notice_page);
        colliection_page_Button = (RadioButton) findViewById(R.id.collection_page);
        createToptic = (ImageButton) findViewById(R.id.createtoptic_button);
        // mTitle = (TextView) findViewById(R.id.mTitle);
        //  searchView= (MaterialSearchView) findViewById(R.id.search_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("V2EX");
        //toolbar.setSubtitle("首页");
        //toolbar.setLogo(R.drawable.v2ex);
        setSupportActionBar(toolbar);
        //   mTitle.setText("V2EX");
        fragmentList = new ArrayList<>();
        Intent i = getIntent();
        if (i != null && i.hasExtra("finishApp")) {
            this.finish();
        }
        Home_PageFragment home_pageFragment = new Home_PageFragment();
        Notice_PageFragment notice_pageFragment = new Notice_PageFragment();
        Collection_PageFragment collection_pageFragment = new Collection_PageFragment();
        Setting_PageFragment setting_pageFragment = new Setting_PageFragment();
        fragmentList.add(home_pageFragment);
        fragmentList.add(collection_pageFragment);
        fragmentList.add(notice_pageFragment);
        fragmentList.add(setting_pageFragment);
        FragmentManager fragmentManager = getSupportFragmentManager();
        MyFragment1PagerAdaptar myFragmentPagerAdaptar = new MyFragment1PagerAdaptar(fragmentManager, fragmentList);
        viewPager.setAdapter(myFragmentPagerAdaptar);
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(3);
        Home_Page_Button.setChecked(true);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {

                    case 0:
                        Home_Page_Button.setChecked(true);
                        colliection_page_Button.setChecked(false);
                        setting_page_Button.setChecked(false);
                        notice_page_Button.setChecked(false);
                        break;
                    case 1:

                        Home_Page_Button.setChecked(false);
                        colliection_page_Button.setChecked(true);
                        setting_page_Button.setChecked(false);
                        notice_page_Button.setChecked(false);
                        break;
                    case 2:
                        Home_Page_Button.setChecked(false);
                        colliection_page_Button.setChecked(false);
                        setting_page_Button.setChecked(false);
                        notice_page_Button.setChecked(true);
                        break;
                    case 3:
                        Home_Page_Button.setChecked(false);
                        colliection_page_Button.setChecked(false);
                        setting_page_Button.setChecked(true);
                        notice_page_Button.setChecked(false);
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        createToptic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SomeUtils.islogin(MainActivity.this)) {
                    Intent intent = new Intent(MainActivity.this, CreateTopticActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(),"请登陆后再操作",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, SiginActivity.class);
                    startActivity(intent);
                }

            }
        });
        Home_Page_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0);
                Home_Page_Button.setChecked(true);
                colliection_page_Button.setChecked(false);
                setting_page_Button.setChecked(false);
                notice_page_Button.setChecked(false);
            }
        });
        colliection_page_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
                Home_Page_Button.setChecked(false);
                colliection_page_Button.setChecked(true);
                setting_page_Button.setChecked(false);
                notice_page_Button.setChecked(false);
            }
        });
        setting_page_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(3);
                Home_Page_Button.setChecked(false);
                colliection_page_Button.setChecked(false);
                setting_page_Button.setChecked(true);
                notice_page_Button.setChecked(false);
            }
        });
        notice_page_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(2);
                Home_Page_Button.setChecked(false);
                colliection_page_Button.setChecked(false);
                setting_page_Button.setChecked(false);
                notice_page_Button.setChecked(true);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search, menu);

        MenuItem item = menu.findItem(R.id.ab_search);

        android.widget.SearchView searchView = (android.widget.SearchView) MenuItemCompat.getActionView(item);
        searchView.setIconifiedByDefault(true);
        searchView.setFocusable(true);
        searchView.setQueryHint("搜索");
        searchView.setIconified(false);
        searchView.requestFocusFromTouch();
        searchView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                intent.putExtra("intent", "http://www.baidu.com/s?ie=UTF-8&wd=" + query + "site:www.v2ex.com");
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;

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

    @Override
    public boolean onQueryTextSubmit(String query) {


        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse("http://www.baidu.com/s?wd=" + query);
        intent.setData(content_url);
        intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
        startActivity(intent);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }


}
