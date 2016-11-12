package zero.tongyang.threegrand.com.x2expro.Main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import zero.tongyang.threegrand.com.x2expro.Collection.CollectionFragment;
import zero.tongyang.threegrand.com.x2expro.CustomViewPager;
import zero.tongyang.threegrand.com.x2expro.HomePage.HomePageFragment;
import zero.tongyang.threegrand.com.x2expro.Like.LikeFragment;
import zero.tongyang.threegrand.com.x2expro.Node.NodeFragment;
import zero.tongyang.threegrand.com.x2expro.Personal.PersonalFragment;
import zero.tongyang.threegrand.com.x2expro.R;

public class MainActivity extends AppCompatActivity {
    List<Fragment> fragmentList;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.radio1)
    RadioButton radio1;
    @BindView(R.id.radio2)
    RadioButton radio2;
    @BindView(R.id.radio4)
    RadioButton radio4;
    @BindView(R.id.radio3)
    RadioButton radio3;
    @BindView(R.id.radio5)
    RadioButton radio5;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.viewPager)
    CustomViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();


    }

    public void init() {
        fragmentList = new ArrayList<>();
        CollectionFragment collectionFragment = new CollectionFragment();
        PersonalFragment personalFragment = new PersonalFragment();
        HomePageFragment homePageFragment = new HomePageFragment();
        NodeFragment nodeFragment = new NodeFragment();
        LikeFragment likeFragment = new LikeFragment();
        fragmentList.add(homePageFragment);
        fragmentList.add(nodeFragment);
        fragmentList.add(likeFragment);
        fragmentList.add(collectionFragment);
        fragmentList.add(personalFragment);
        FragmentManager fragmentManager = getSupportFragmentManager();
        viewPager.setOffscreenPageLimit(5);
        viewPager.setNoScroll(true);
        MyFragmentPagerAdaptar myFragmentPagerAdaptar = new MyFragmentPagerAdaptar(fragmentManager, fragmentList);
        viewPager.setAdapter(myFragmentPagerAdaptar);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {

                    case 0:
                        radioGroup.check(R.id.radio1);
                        break;
                    case 1:
                        radioGroup.check(R.id.radio2);
                        break;
                    case 2:
                        radioGroup.check(R.id.radio3);
                        break;
                    case 3:
                        radioGroup.check(R.id.radio4);
                        break;
                    case 4:
                        radioGroup.check(R.id.radio5);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        radioGroup.check(R.id.radio1);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.radio1:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.radio2:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.radio3:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.radio4:
                        viewPager.setCurrentItem(3);
                        break;
                    case R.id.radio5:
                        viewPager.setCurrentItem(4);
                        break;

                }
            }
        });
    }
}
