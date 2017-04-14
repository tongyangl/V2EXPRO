package rxjavatest.tycoding.com.iv2ex.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rxjavatest.tycoding.com.iv2ex.R;
import rxjavatest.tycoding.com.iv2ex.adatper.MyFragmentPagerAdaptar;
import rxjavatest.tycoding.com.iv2ex.ui.fragment.NodeCollectionFragment;
import rxjavatest.tycoding.com.iv2ex.ui.fragment.TopticCollectionFragment;

/**
 * Created by 佟杨 on 2017/4/14.
 */

public class CollectionActivity extends AppCompatActivity {

    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> titlelist = new ArrayList<>();
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.table)
    TabLayout table;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    NodeCollectionFragment nodeCollectionFragment;
    TopticCollectionFragment topticCollectionFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        ButterKnife.bind(this);
        toolbar.setTitle("收藏");

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        nodeCollectionFragment = new NodeCollectionFragment();
        topticCollectionFragment = new TopticCollectionFragment();
        fragmentList.add(nodeCollectionFragment);
        fragmentList.add(topticCollectionFragment);
        titlelist.add("节点收藏");
        titlelist.add("主题收藏");
        FragmentManager fragmentManager = getSupportFragmentManager();
        MyFragmentPagerAdaptar myFragmentPagerAdaptar = new MyFragmentPagerAdaptar(fragmentManager);
        for (int i = 0; i < titlelist.size(); i++) {
            myFragmentPagerAdaptar.addTab(fragmentList.get(i), titlelist.get(i));

        }
        viewPager.setAdapter(myFragmentPagerAdaptar);

        table.setupWithViewPager(viewPager);
    }
}
