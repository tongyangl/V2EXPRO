package zero.tongyang.threegrand.com.x2expro.HomePage.Home_ViewPager;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import zero.tongyang.threegrand.com.x2expro.HomePage.Home_ViewPager.Some.JsoupAsyncTask;
import zero.tongyang.threegrand.com.x2expro.HomePage.Home_ViewPager.Some.NodeJsoupAsyncTask;
import zero.tongyang.threegrand.com.x2expro.R;
import zero.tongyang.threegrand.com.x2expro.Static;

/**
 * Created by tongyang on 16-11-12.
 */

public class JobFragment extends Fragment {
    List<Map<String, String>> list;
    @BindView(R.id.lv)
    ListView listView;
    @BindView(R.id.ptr)
    PtrFrameLayout ptr;
    LayoutInflater inflater;
    @BindView(R.id.Home_Radio_quanbu)
    RadioButton HomeRadioQuanbu;
    @BindView(R.id.Home_Radio_kugongzuo)
    RadioButton HomeRadioKugongzuo;
    @BindView(R.id.Home_Radio_qiuzhi)
    RadioButton HomeRadioQiuzhi;
    @BindView(R.id.Home_Radio_zhichanghuati)
    RadioButton HomeRadioZhichanghuati;
    @BindView(R.id.Home_Radio_waibao)
    RadioButton HomeRadioWaibao;
    @BindView(R.id.Home_radioGroup)
    RadioGroup HomeRadioGroup;
    @BindView(R.id.horizontalScrollView)
    HorizontalScrollView horizontalScrollView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_job, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        inflater = getActivity().getLayoutInflater();
        listView.setDivider(new ColorDrawable(Color.argb(255, 242, 242, 242)));
        listView.setDividerHeight((int) Static.dp2px(getContext(), 10f));
        list = new ArrayList<>();
        JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask(listView, inflater, list, getContext(), getActivity());
        jsoupAsyncTask.execute("?tab=jobs");

        ptr.disableWhenHorizontalMove(false);
        ptr.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {


                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask(listView, inflater, list, getContext(), getActivity());
                jsoupAsyncTask.execute("?tab=jobs");
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ptr.refreshComplete();
                    }
                }, 1800);
            }
        });
        HomeRadioGroup.check(R.id.Home_Radio_quanbu);
        HomeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.Home_Radio_kugongzuo:
                        NodeJsoupAsyncTask jsoupAsyncTask  = new NodeJsoupAsyncTask(listView, inflater, list, getContext(), getActivity(),"酷工作");
                        jsoupAsyncTask.execute("go/jobs");break;
                    case R.id.Home_Radio_qiuzhi:
                        NodeJsoupAsyncTask jsoupAsyncTask1  = new NodeJsoupAsyncTask(listView, inflater, list, getContext(), getActivity(),"求职");
                        jsoupAsyncTask1.execute("go/cv");break;
                    case R.id.Home_Radio_zhichanghuati:
                        NodeJsoupAsyncTask jsoupAsyncTask2  = new NodeJsoupAsyncTask(listView, inflater, list, getContext(), getActivity(),"职场话题");
                        jsoupAsyncTask2.execute("go/career");break;
                    case R.id.Home_Radio_waibao:
                        NodeJsoupAsyncTask jsoupAsyncTask3  = new NodeJsoupAsyncTask(listView, inflater, list, getContext(), getActivity(),"外包");
                        jsoupAsyncTask3.execute("go/outsourcing");break;

                    case R.id.Home_Radio_quanbu:
                        JsoupAsyncTask jsoupAsyncTask8 = new JsoupAsyncTask(listView, inflater, list, getContext(), getActivity());
                        jsoupAsyncTask8.execute("?tab=jobs");

                }
            }
        });
    }
}
