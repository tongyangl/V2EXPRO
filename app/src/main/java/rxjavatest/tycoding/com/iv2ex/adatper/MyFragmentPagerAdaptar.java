package rxjavatest.tycoding.com.iv2ex.adatper;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tongyang on 16-11-12.
 */

public class MyFragmentPagerAdaptar extends FragmentPagerAdapter {
    private List<Fragment>fragmentList=new ArrayList<>();
    private List<String> titles = new ArrayList<>();

    public MyFragmentPagerAdaptar(FragmentManager fm) {
        super(fm);
    }
    public void addTab(Fragment fragment, String title) {
        fragmentList.add(fragment);
        titles.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return titles.get(position);
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
