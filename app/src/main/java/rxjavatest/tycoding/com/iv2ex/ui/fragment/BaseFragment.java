package rxjavatest.tycoding.com.iv2ex.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by 佟杨 on 2017/8/1.
 */

public abstract class BaseFragment extends Fragment {

    protected boolean isVisable;
    protected boolean isprepared = false;
    protected boolean isdateInit=false;
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisable=isVisibleToUser;
        prepareFetchData(false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isprepared=true;
        prepareFetchData(false);

    }

   protected  void prepareFetchData(boolean force){

       if (isVisable && isprepared && (!isdateInit || force)) {
           loadData();
           isdateInit = true;
       }


   }

    protected abstract void loadData( );
}
