package rxjavatest.tycoding.com.iv2ex.ui.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rxjavatest.tycoding.com.iv2ex.Application;
import rxjavatest.tycoding.com.iv2ex.R;
import rxjavatest.tycoding.com.iv2ex.adatper.MyFragmentPagerAdaptar;
import rxjavatest.tycoding.com.iv2ex.rxjava.rxjava;
import rxjavatest.tycoding.com.iv2ex.service.noticeservice;
import rxjavatest.tycoding.com.iv2ex.ui.fragment.AppleFragment;
import rxjavatest.tycoding.com.iv2ex.ui.fragment.CityFragment;
import rxjavatest.tycoding.com.iv2ex.ui.fragment.HotFragment;
import rxjavatest.tycoding.com.iv2ex.ui.fragment.JobFragment;
import rxjavatest.tycoding.com.iv2ex.ui.fragment.PlayFragment;
import rxjavatest.tycoding.com.iv2ex.ui.fragment.QandAFragment;
import rxjavatest.tycoding.com.iv2ex.ui.fragment.R2Fragment;
import rxjavatest.tycoding.com.iv2ex.ui.fragment.allFragment;
import rxjavatest.tycoding.com.iv2ex.ui.fragment.technologyFragment;
import rxjavatest.tycoding.com.iv2ex.ui.fragment.transactionFragment;

public class MainActivity extends AppCompatActivity {
    List<Fragment> fragmentList;
    private long exitTime = 0;
    @BindView(R.id.Home_viewPager)
    ViewPager HomeViewPager;

    @BindView(R.id.drawer)
    DrawerLayout drawerLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ngv)
    NavigationView ngv;

    ActionBarDrawerToggle mydrawToggle;
    @BindView(R.id.table)
    TabLayout table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
        toolbar.setTitle("V2EX");
        setSupportActionBar(toolbar);
       /* Intent i = new Intent(this, noticeservice.class);
        startService(i);*/

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mydrawToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0) {


            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        View view = ngv.getHeaderView(0);
        ImageView imageView = (ImageView) view.findViewById(R.id.userimg);
        TextView textView= (TextView) view.findViewById(R.id.username);

        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        if (sharedPreferences.getString("userimg", "").equals("")) {

        } else {
            rxjava.setImg(sharedPreferences.getString("userimg", ""), imageView, this);
            textView.setText(sharedPreferences.getString("username", ""));
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SiginActivity.class);
                startActivity(intent);
            }
        });
        mydrawToggle.syncState();
        drawerLayout.setDrawerListener(mydrawToggle);
        ngv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            MenuItem mymenuItem;

            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                if (mymenuItem != null)
                    mymenuItem.setCheckable(false);
                item.setCheckable(true);
                drawerLayout.closeDrawers();

                mymenuItem = item;

                switch (item.getItemId()) {

                    case R.id.home:
                        break;
                    case R.id.notice:
                        if (Application.islogin(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, NoticeActivity.class);
                            startActivity(intent);
                        }else {
                            Intent intent3 = new Intent(MainActivity.this, SiginActivity.class);
                            startActivity(intent3);
                        }
                        break;
                    case R.id.node:
                            Intent intent1 = new Intent(MainActivity.this, NodeActivity.class);
                            startActivity(intent1);

                        break;

                    case R.id.setting:
                        Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.collection:
                        if (Application.islogin(getApplicationContext())){
                            Intent intent3 = new Intent(MainActivity.this, CollectionActivity.class);
                            startActivity(intent3);
                        }else {
                            Intent intent3 = new Intent(MainActivity.this, SiginActivity.class);
                            startActivity(intent3);
                        }

                        break;

                }
                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        MenuItem mMenuItem;
        menuInflater.inflate(R.menu.main_right, menu);


        return super.onCreateOptionsMenu(menu);


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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.replice) {
            if (Application.islogin(getApplicationContext())){
                Intent intent = new Intent(MainActivity.this, CreateToptic.class);
                startActivity(intent);
            }else {
                Intent intent3 = new Intent(MainActivity.this, SiginActivity.class);
                startActivity(intent3);
            }

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void init() {
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
        FragmentManager fragmentManager = getSupportFragmentManager();
        HomeViewPager.setCurrentItem(0);
        MyFragmentPagerAdaptar myFragmentPagerAdaptar = new MyFragmentPagerAdaptar(fragmentManager);
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
        //ngv.setBackgroundDrawable(drawable);
        for (int i = 0; i < list.size(); i++) {
            myFragmentPagerAdaptar.addTab(fragmentList.get(i), list.get(i));

        }
        HomeViewPager.setAdapter(myFragmentPagerAdaptar);
        table.setupWithViewPager(HomeViewPager);
        for (int i = 0; i < list.size(); i++) {

            table.getTabAt(i).setText(list.get(i));
        }
        HomeViewPager.setOffscreenPageLimit(2);
    }
}
