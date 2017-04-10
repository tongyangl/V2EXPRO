package rxjavatest.tycoding.com.iv2ex.adatper;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import rxjavatest.tycoding.com.iv2ex.ui.fragment.PhotoViewFragment;

public class PhotoViewerPagerAdapter extends FragmentStatePagerAdapter {

    protected final HashMap<Object, Integer> mObjectRowMap = new HashMap<Object, Integer>();
    private ArrayList<String> mList = new ArrayList<String>();
    private List<Fragment>list;
    public PhotoViewerPagerAdapter(FragmentManager fm,List<Fragment> list) {
        super(fm);
        this.list=list;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final Object obj = super.instantiateItem(container, position);
        if (obj != null) {
            mObjectRowMap.put(obj, position);
        }
        return obj;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        mObjectRowMap.remove(object);
        super.destroyItem(container, position, object);
    }

    @Override
    public Fragment getItem(int position) {
       // PhotoViewFragment.newInstance(mList.get(position), position);
        return list.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        final Integer rowId = mObjectRowMap.get(object);
        if (rowId == null) {
            return PagerAdapter.POSITION_NONE;
        } else {
            return rowId;
        }
    }

    @Override
    public int getCount() {
        if (mList != null) {
            return mList.size();
        } else {
            return 0;
        }
    }

    public ArrayList<String> getData() {
        return mList;
    }

    public void setData(List<String> datas) {
        mList.clear();
        mList.addAll(datas);
        notifyDataSetChanged();
    }
}
