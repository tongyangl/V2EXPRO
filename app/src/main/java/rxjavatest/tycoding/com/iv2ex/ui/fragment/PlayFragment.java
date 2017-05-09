package rxjavatest.tycoding.com.iv2ex.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import butterknife.BindView;
import butterknife.ButterKnife;

import rxjavatest.tycoding.com.iv2ex.R;
import rxjavatest.tycoding.com.iv2ex.rxjava.rxjava;
import rxjavatest.tycoding.com.iv2ex.utils.MyDecoration;


/**
 * Created by tongyang on 16-11-12.
 */

public class PlayFragment extends Fragment {

    @BindView(R.id.recycle)
    RecyclerView recycle;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_paly, container, false);
        ButterKnife.bind(this, view);
        swipe.setSize(SwipeRefreshLayout.DEFAULT);
        swipe.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SwipeRefreshLayout.OnRefreshListener listener=new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d("---","onrefresh");
                LinearLayoutManager manager=new LinearLayoutManager(getContext());
                recycle.setLayoutManager(manager);
                recycle.addItemDecoration(new MyDecoration(getContext()));
                rxjava.getToptics(getActivity(),"?tab=play",swipe,recycle,false);
            }
        };
        swipe.setOnRefreshListener(listener);
        swipe.post(new Runnable() {
            @Override
            public void run() {
                swipe.setRefreshing(true);
            }
        });
        listener.onRefresh();
    }


}
