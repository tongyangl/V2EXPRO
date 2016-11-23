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

public class CityFragment extends Fragment {
    List<Map<String, String>> list;
    @BindView(R.id.lv)
    ListView listView;
    @BindView(R.id.ptr)
    PtrFrameLayout ptr;
    LayoutInflater inflater;
    @BindView(R.id.Home_Radio_all)
    RadioButton HomeRadioAll;
    @BindView(R.id.Home_Radio_beijing)
    RadioButton HomeRadioBeijing;
    @BindView(R.id.Home_Radio_shanghai)
    RadioButton HomeRadioShanghai;
    @BindView(R.id.Home_Radio_shenzhen)
    RadioButton HomeRadioShenzhen;
    @BindView(R.id.Home_Radio_guangzhou)
    RadioButton HomeRadioGuangzhou;
    @BindView(R.id.Home_Radio_hangzhou)
    RadioButton HomeRadioHangzhou;
    @BindView(R.id.Home_Radio_chengdu)
    RadioButton HomeRadioChengdu;
    @BindView(R.id.Home_Radio_kunming)
    RadioButton HomeRadioKunming;
    @BindView(R.id.Home_Radio_niuyue)
    RadioButton HomeRadioNiuyue;
    @BindView(R.id.Home_Radio_luoshanji)
    RadioButton HomeRadioLuoshanji;
    @BindView(R.id.Home_radioGroup)
    RadioGroup HomeRadioGroup;
    @BindView(R.id.horizontalScrollView)
    HorizontalScrollView horizontalScrollView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_city, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        inflater = getActivity().getLayoutInflater();
        list = new ArrayList<>();
        listView.setDivider(new ColorDrawable(Color.argb(255, 242, 242, 242)));
        listView.setDividerHeight((int) Static.dp2px(getContext(), 10f));
        JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask(listView, inflater, list, getContext(), getActivity());
        jsoupAsyncTask.execute("?tab=city");

        ptr.disableWhenHorizontalMove(false);
        ptr.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {


                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask(listView, inflater, list, getContext(), getActivity());
                jsoupAsyncTask.execute("?tab=city");
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ptr.refreshComplete();
                    }
                }, 1800);
            }
        });
        HomeRadioGroup.check(R.id.Home_Radio_all);
        HomeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){

                    case R.id.Home_Radio_beijing:
                        NodeJsoupAsyncTask jsoupAsyncTask  = new NodeJsoupAsyncTask(listView, inflater, list, getContext(), getActivity(),"北京");
                        jsoupAsyncTask.execute("go/beijing");break;
                    case R.id.Home_Radio_shanghai:
                        NodeJsoupAsyncTask jsoupAsyncTask1  = new NodeJsoupAsyncTask(listView, inflater, list, getContext(), getActivity(),"Python");
                        jsoupAsyncTask1.execute("go/shanghai");break;
                    case R.id.Home_Radio_shenzhen:
                        NodeJsoupAsyncTask jsoupAsyncTask2  = new NodeJsoupAsyncTask(listView, inflater, list, getContext(), getActivity(),"iDev");
                        jsoupAsyncTask2.execute("go/shenzhen");break;
                    case R.id.Home_Radio_guangzhou:
                        NodeJsoupAsyncTask jsoupAsyncTask3  = new NodeJsoupAsyncTask(listView, inflater, list, getContext(), getActivity(),"Android");
                        jsoupAsyncTask3.execute("go/guangzhou");break;
                    case R.id.Home_Radio_hangzhou:
                        NodeJsoupAsyncTask jsoupAsyncTask4  = new NodeJsoupAsyncTask(listView, inflater, list, getContext(), getActivity(),"Linux");
                        jsoupAsyncTask4.execute("go/hangzhou");break;
                    case R.id.Home_Radio_chengdu:
                        NodeJsoupAsyncTask jsoupAsyncTask5  = new NodeJsoupAsyncTask(listView, inflater, list, getContext(), getActivity(),"node.js");
                        jsoupAsyncTask5.execute("go/chengdu");break;
                    case R.id.Home_Radio_kunming:
                        NodeJsoupAsyncTask jsoupAsyncTask6  = new NodeJsoupAsyncTask(listView, inflater, list, getContext(), getActivity(),"云计算");
                        jsoupAsyncTask6.execute("go/kunming");break;
                    case R.id.Home_Radio_niuyue:
                        NodeJsoupAsyncTask jsoupAsyncTask7  = new NodeJsoupAsyncTask(listView, inflater, list, getContext(), getActivity(),"宽带症候群");
                        jsoupAsyncTask7.execute("go/nyc");break;

                    case R.id.Home_Radio_luoshanji:
                        JsoupAsyncTask jsoupAsyncTask8  = new JsoupAsyncTask(listView, inflater, list, getContext(), getActivity());
                        jsoupAsyncTask8.execute("go/la");break;

                    case R.id.Home_Radio_all:
                        JsoupAsyncTask jsoupAsyncTask9 = new JsoupAsyncTask(listView, inflater, list, getContext(), getActivity());
                        jsoupAsyncTask9.execute("?tab=city");
                }
            }
        });
    }
}
