package zero.tongyang.threegrand.com.x2expro.UI.fragment;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;
import in.srain.cube.views.ptr.header.StoreHouseHeader;
import zero.tongyang.threegrand.com.x2expro.HomePage.Home_ViewPager.Some.JsoupAsyncTask;
import zero.tongyang.threegrand.com.x2expro.R;
import zero.tongyang.threegrand.com.x2expro.Static;

/**
 * Created by tongyang on 16-11-12.
 */

public class allFragment extends Fragment {
    List<Map<String, String>> list;
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
        listView.setDivider(new ColorDrawable(Color.argb(255, 242, 242, 242)));
        listView.setDividerHeight((int) Static.dp2px(getContext(), 10f));
        inflater = getActivity().getLayoutInflater();
        list = new ArrayList<>();

        JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask(listView, inflater, list, getContext(), getActivity());
        jsoupAsyncTask.execute("?tab=all");
        ptr.disableWhenHorizontalMove(false);

        //ptr.setPinContent(true);
        StoreHouseHeader header=new StoreHouseHeader(getContext());
        header.initWithString("v2ex");

        header.setTextColor(Color.BLACK);
        ptr.addPtrUIHandler(header);
        ptr.setHeaderView(header);

        ptr.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {


                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask(listView, inflater, list, getContext(), getActivity());
                jsoupAsyncTask.execute("?tab=all");
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ptr.refreshComplete();
                    }
                }, 1800);
            }
        });


    }


}
