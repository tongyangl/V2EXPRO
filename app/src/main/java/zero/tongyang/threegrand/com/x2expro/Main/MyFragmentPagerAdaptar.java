package zero.tongyang.threegrand.com.x2expro.Main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by tongyang on 16-11-12.
 */

public class MyFragmentPagerAdaptar extends FragmentPagerAdapter {
    private List<Fragment>fragmentList;

    public MyFragmentPagerAdaptar(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
