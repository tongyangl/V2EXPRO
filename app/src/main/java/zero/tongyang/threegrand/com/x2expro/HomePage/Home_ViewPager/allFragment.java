package zero.tongyang.threegrand.com.x2expro.HomePage.Home_ViewPager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import zero.tongyang.threegrand.com.x2expro.R;

/**
 * Created by tongyang on 16-11-12.
 */

public class allFragment extends Fragment {
    List<Map<String,String>>list;
    @BindView(R.id.lv)
    ListView listView;
    @BindView(R.id.ptr)
    PtrFrameLayout ptr;
    LayoutInflater inflater;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_all, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        list=new ArrayList<>();
        for (int i=0;i<=10;i++){
            Map<String,String>map=new HashMap<>();
            map.put("i",i+"");
            list.add(map);
        }

        inflater=getActivity().getLayoutInflater();
        ListViewAdaptar adaptar=new ListViewAdaptar(list,getContext(),inflater);
        listView.setAdapter(adaptar);
        ptr.disableWhenHorizontalMove(false);
        ptr.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                 return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
               frame.postDelayed(new Runnable() {
                   @Override
                   public void run() {
                       ptr.refreshComplete();
                   }
               },1800);
            }
        });
    }
}
