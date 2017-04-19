package rxjavatest.tycoding.com.iv2ex.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rxjavatest.tycoding.com.iv2ex.R;
import rxjavatest.tycoding.com.iv2ex.adatper.MyPhotoFragmentPagerAdaptar;
import rxjavatest.tycoding.com.iv2ex.ui.fragment.PhotoViewFragment;
import rxjavatest.tycoding.com.iv2ex.ui.widget.PinchImageView;


/**
 * Created by 佟杨 on 2017/4/9.
 */

public class photoviewactivity extends AppCompatActivity {


    @BindView(R.id.tv)
    TextView tv;
    private String list;
    private int position;
    private PinchImageView imageView;
    private List<String> urllist;
    private ViewPager viewPager;
    private List<Fragment> fragmentList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photoview);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        // imageView = (PinchImageView) findViewById(R.id.pinchimageView);
        list = intent.getStringExtra("url");
        position = intent.getIntExtra("position", 0);
        urllist = intent.getStringArrayListExtra("list");
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        Log.d("---", urllist.toString() + "list");
        fragmentList = new ArrayList<>();
        for (int i = 0; i < urllist.size(); i++) {
            PhotoViewFragment fragment = new PhotoViewFragment();
            fragment.setUrl(urllist.get(i));
            fragmentList.add(fragment);
        }
        FragmentManager manager = getSupportFragmentManager();
        MyPhotoFragmentPagerAdaptar adaptar = new MyPhotoFragmentPagerAdaptar(manager, fragmentList);
        viewPager.setAdapter(adaptar);
        viewPager.setCurrentItem(position);
        tv.setText((position+1)+"/"+(urllist.size()));
          viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
              @Override
              public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                  tv.setText((viewPager.getCurrentItem()+1)+"/"+(urllist.size()));
              }

              @Override
              public void onPageSelected(int position) {

              }

              @Override
              public void onPageScrollStateChanged(int state) {

              }
          });
    }

}
