package zero.tongyang.threegrand.com.x2expro.UI.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import zero.tongyang.threegrand.com.x2expro.Adaptar.MyFragmentPagerAdaptar;
import zero.tongyang.threegrand.com.x2expro.R;
import zero.tongyang.threegrand.com.x2expro.UI.fragment.AppleFragment;
import zero.tongyang.threegrand.com.x2expro.UI.fragment.CityFragment;
import zero.tongyang.threegrand.com.x2expro.UI.fragment.HotFragment;
import zero.tongyang.threegrand.com.x2expro.UI.fragment.JobFragment;
import zero.tongyang.threegrand.com.x2expro.UI.fragment.PlayFragment;
import zero.tongyang.threegrand.com.x2expro.UI.fragment.QandAFragment;
import zero.tongyang.threegrand.com.x2expro.UI.fragment.R2Fragment;
import zero.tongyang.threegrand.com.x2expro.UI.fragment.allFragment;
import zero.tongyang.threegrand.com.x2expro.UI.fragment.technologyFragment;
import zero.tongyang.threegrand.com.x2expro.UI.fragment.transactionFragment;

public class MainActivity extends AppCompatActivity {
    List<Fragment> fragmentList;


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
        setSupportActionBar(toolbar);
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

        mydrawToggle.syncState();
        drawerLayout.setDrawerListener(mydrawToggle);
        View view = ngv.getHeaderView(0);
        TextView textView = (TextView) view.findViewById(R.id.username);
        ImageView imageView = (ImageView) view.findViewById(R.id.userimg);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, SiginActivity.class);
                startActivity(intent);
            }
        });
       imageView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent=new Intent(MainActivity.this, SiginActivity.class);
               startActivity(intent);
           }
       });
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
                        break;
                    case R.id.node:
                        break;
                    case R.id.mine:
                        break;
                    case R.id.setting:
                        break;
                    case R.id.more:
                        break;
                    case R.id.collection:
                        break;

                }
                return true;
            }
        });


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

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.f);

        Bitmap bitmap1 = blur(bitmap, 25f);
        Drawable drawable = new BitmapDrawable(bitmap1);
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
        HomeViewPager.setOffscreenPageLimit(5);
    }

    private Bitmap blur(Bitmap bitmap, float radius) {
        Bitmap output = Bitmap.createBitmap(bitmap); // 创建输出图片
        RenderScript rs = RenderScript.create(this); // 构建一个RenderScript对象
        ScriptIntrinsicBlur gaussianBlue = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs)); // 创建高斯模糊脚本
        Allocation allIn = Allocation.createFromBitmap(rs, bitmap); // 创建用于输入的脚本类型
        Allocation allOut = Allocation.createFromBitmap(rs, output); // 创建用于输出的脚本类型
        gaussianBlue.setRadius(radius); // 设置模糊半径，范围0f<radius<=25f
        gaussianBlue.setInput(allIn); // 设置输入脚本类型
        gaussianBlue.forEach(allOut); // 执行高斯模糊算法，并将结果填入输出脚本类型中
        allOut.copyTo(output); // 将输出内存编码为Bitmap，图片大小必须注意
        rs.destroy(); // 关闭RenderScript对象，API>=23则使用rs.releaseAllContexts()
        return output;
    }
}

