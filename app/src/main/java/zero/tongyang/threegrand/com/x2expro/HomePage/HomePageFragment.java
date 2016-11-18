package zero.tongyang.threegrand.com.x2expro.HomePage;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import zero.tongyang.threegrand.com.x2expro.HomePage.Home_ViewPager.AppleFragment;
import zero.tongyang.threegrand.com.x2expro.HomePage.Home_ViewPager.CityFragment;
import zero.tongyang.threegrand.com.x2expro.HomePage.Home_ViewPager.HotFragment;
import zero.tongyang.threegrand.com.x2expro.HomePage.Home_ViewPager.JobFragment;
import zero.tongyang.threegrand.com.x2expro.HomePage.Home_ViewPager.PlayFragment;
import zero.tongyang.threegrand.com.x2expro.HomePage.Home_ViewPager.QandAFragment;
import zero.tongyang.threegrand.com.x2expro.HomePage.Home_ViewPager.R2Fragment;
import zero.tongyang.threegrand.com.x2expro.HomePage.Home_ViewPager.allFragment;
import zero.tongyang.threegrand.com.x2expro.HomePage.Home_ViewPager.technologyFragment;
import zero.tongyang.threegrand.com.x2expro.HomePage.Home_ViewPager.transactionFragment;
import zero.tongyang.threegrand.com.x2expro.Main.MyFragmentPagerAdaptar;
import zero.tongyang.threegrand.com.x2expro.R;
import zero.tongyang.threegrand.com.x2expro.Static;

/**
 * Created by tongyang on 16-11-12.
 */

public class HomePageFragment extends Fragment {
    List<Fragment> fragmentList;
    @BindView(R.id.Home_Radio_all)
    RadioButton HomeRadioAll;
    @BindView(R.id.Home_Radio_hot)
    RadioButton HomeRadioHot;
    @BindView(R.id.Home_Radio_technology)
    RadioButton HomeRadioTechnology;
    @BindView(R.id.Home_Radio_Apple)
    RadioButton HomeRadioApple;
    @BindView(R.id.Home_Radio_job)
    RadioButton HomeRadioJob;
    @BindView(R.id.Home_Radio_transaction)
    RadioButton HomeRadioTransaction;
    @BindView(R.id.Home_Radio_city)
    RadioButton HomeRadioCity;
    @BindView(R.id.Home_Radio_QANDA)
    RadioButton HomeRadioQANDA;
    @BindView(R.id.Home_Radio_r2)
    RadioButton HomeRadioR2;
    @BindView(R.id.Home_radioGroup)
    RadioGroup HomeRadioGroup;
    @BindView(R.id.Home_viewPager)
    ViewPager HomeViewPager;
    @BindView(R.id.Home_Radio_play)
    RadioButton HomeRadioPlay;
    @BindView(R.id.horizontalScrollView)
    HorizontalScrollView horizontalScrollView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homepage, container, false);
        ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
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
        FragmentManager fragmentManager = getFragmentManager();
        HomeViewPager.setCurrentItem(0);
        HomeViewPager.setOffscreenPageLimit(5);
        HomeRadioGroup.check(R.id.Home_Radio_all);
        MyFragmentPagerAdaptar myFragmentPagerAdaptar = new MyFragmentPagerAdaptar(fragmentManager, fragmentList);
        HomeViewPager.setAdapter(myFragmentPagerAdaptar);
        HomeViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {

                    case 0:
                        HomeRadioGroup.check(R.id.Home_Radio_all);
                        break;
                    case 1:
                        HomeRadioGroup.check(R.id.Home_Radio_hot);
                        break;
                    case 2:
                        HomeRadioGroup.check(R.id.Home_Radio_technology);
                        break;
                    case 3:
                        HomeRadioGroup.check(R.id.Home_Radio_Apple);
                        break;
                    case 4:
                        HomeRadioGroup.check(R.id.Home_Radio_job);
                        break;
                    case 5:
                        HomeRadioGroup.check(R.id.Home_Radio_transaction);
                        break;
                    case 6:
                        HomeRadioGroup.check(R.id.Home_Radio_city);
                        break;
                    case 7:
                        HomeRadioGroup.check(R.id.Home_Radio_QANDA);
                        break;
                    case 8:
                        HomeRadioGroup.check(R.id.Home_Radio_r2);
                        break;
                    case 9:
                        HomeRadioGroup.check(R.id.Home_Radio_play);
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        HomeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton rb = (RadioButton) getActivity().findViewById(i);

                int mCurrentCheckedRadioLeft = rb.getLeft();//更新当前按钮距离左边的距离
                int width = (int) Static.dp2px(getActivity(), 140);
                horizontalScrollView.smoothScrollTo((int) mCurrentCheckedRadioLeft - width, 0);

                switch (i) {

                    case R.id.Home_Radio_all:
                        HomeViewPager.setCurrentItem(0);
                        break;
                    case R.id.Home_Radio_hot:
                        HomeViewPager.setCurrentItem(1);
                        break;
                    case R.id.Home_Radio_technology:
                        HomeViewPager.setCurrentItem(2);
                        break;
                    case R.id.Home_Radio_Apple:
                        HomeViewPager.setCurrentItem(3);
                        break;
                    case R.id.Home_Radio_job:
                        HomeViewPager.setCurrentItem(4);
                        break;
                    case R.id.Home_Radio_transaction:
                        HomeViewPager.setCurrentItem(5);
                        break;
                    case R.id.Home_Radio_city:
                        HomeViewPager.setCurrentItem(6);
                        break;
                    case R.id.Home_Radio_QANDA:
                        HomeViewPager.setCurrentItem(7);
                        break;
                    case R.id.Home_Radio_r2:
                        HomeViewPager.setCurrentItem(8);
                        break;
                    case R.id.Home_Radio_play:
                        HomeViewPager.setCurrentItem(9);
                        break;

                }
            }
        });
    }

}
