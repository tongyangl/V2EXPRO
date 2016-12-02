package zero.tongyang.threegrand.com.x2expro.UI.fragment;

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

import com.romainpiel.shimmer.ShimmerTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;
import zero.tongyang.threegrand.com.x2expro.HomePage.Home_ViewPager.Some.JsoupAsyncTask;
import zero.tongyang.threegrand.com.x2expro.HomePage.Home_ViewPager.Some.NodeJsoupAsyncTask;
import zero.tongyang.threegrand.com.x2expro.R;
import zero.tongyang.threegrand.com.x2expro.Static;

/**
 * Created by tongyang on 16-11-12.
 */

public class transactionFragment extends Fragment {
    List<Map<String, String>> list;
    @BindView(R.id.lv)
    ListView listView;
    @BindView(R.id.ptr)
    PtrFrameLayout ptr;
    LayoutInflater inflater;
    @BindView(R.id.Home_Radio_all)
    RadioButton HomeRadioAll;
    @BindView(R.id.Home_Radio_ershou)
    RadioButton HomeRadioErshou;
    @BindView(R.id.Home_Radio_jiaohuan)
    RadioButton HomeRadioJiaohuan;
    @BindView(R.id.Home_Radio_zengsong)
    RadioButton HomeRadioZengsong;
    @BindView(R.id.Home_Radio_yuming)
    RadioButton HomeRadioYuming;
    @BindView(R.id.Home_Radio_tuangou)
    RadioButton HomeRadioTuangou;
    @BindView(R.id.Home_Radio_tishi)
    RadioButton HomeRadioTishi;
    @BindView(R.id.Home_radioGroup)
    RadioGroup HomeRadioGroup;
    @BindView(R.id.horizontalScrollView)
    HorizontalScrollView horizontalScrollView;
    @BindView(R.id.text_v2ex)
    ShimmerTextView textV2ex;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_transaction, container, false);
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
        JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask(listView, inflater, list, getContext(), getActivity(),textV2ex);
        jsoupAsyncTask.execute("?tab=deals");
        StoreHouseHeader header = new StoreHouseHeader(getContext());
        header.initWithString("v2ex");

        header.setTextColor(Color.BLACK);
        ptr.addPtrUIHandler(header);
        ptr.setHeaderView(header);
        ptr.disableWhenHorizontalMove(false);
        ptr.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {


                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                switch (HomeRadioGroup.getCheckedRadioButtonId()) {
                    case R.id.Home_Radio_ershou:
                        NodeJsoupAsyncTask jsoupAsyncTask = new NodeJsoupAsyncTask(listView, inflater, list, getContext(), getActivity(), "二手交易",null);
                        jsoupAsyncTask.execute("go/programmer");
                        break;
                    case R.id.Home_Radio_jiaohuan:
                        NodeJsoupAsyncTask jsoupAsyncTask1 = new NodeJsoupAsyncTask(listView, inflater, list, getContext(), getActivity(), "物物交换",null);
                        jsoupAsyncTask1.execute("go/python");
                        break;
                    case R.id.Home_Radio_zengsong:
                        NodeJsoupAsyncTask jsoupAsyncTask2 = new NodeJsoupAsyncTask(listView, inflater, list, getContext(), getActivity(), "免费赠送",null);
                        jsoupAsyncTask2.execute("go/idev");
                        break;
                    case R.id.Home_Radio_yuming:
                        NodeJsoupAsyncTask jsoupAsyncTask3 = new NodeJsoupAsyncTask(listView, inflater, list, getContext(), getActivity(), "域名",null);
                        jsoupAsyncTask3.execute("go/android");
                        break;
                    case R.id.Home_Radio_tuangou:
                        NodeJsoupAsyncTask jsoupAsyncTask4 = new NodeJsoupAsyncTask(listView, inflater, list, getContext(), getActivity(), "团购",null);
                        jsoupAsyncTask4.execute("go/linux");
                        break;
                    case R.id.Home_Radio_tishi:
                        NodeJsoupAsyncTask jsoupAsyncTask5 = new NodeJsoupAsyncTask(listView, inflater, list, getContext(), getActivity(), "安全提示",null);
                        jsoupAsyncTask5.execute("go/nodejs");
                        break;
                    case R.id.Home_Radio_all:
                        JsoupAsyncTask jsoupAsyncTask9 = new JsoupAsyncTask(listView, inflater, list, getContext(), getActivity(),null);
                        jsoupAsyncTask9.execute("?tab=deals");

                }
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
                switch (i) {

                    case R.id.Home_Radio_ershou:
                        NodeJsoupAsyncTask jsoupAsyncTask = new NodeJsoupAsyncTask(listView, inflater, list, getContext(), getActivity(), "二手交易",textV2ex);
                        jsoupAsyncTask.execute("go/programmer");
                        break;
                    case R.id.Home_Radio_jiaohuan:
                        NodeJsoupAsyncTask jsoupAsyncTask1 = new NodeJsoupAsyncTask(listView, inflater, list, getContext(), getActivity(), "物物交换",textV2ex);
                        jsoupAsyncTask1.execute("go/python");
                        break;
                    case R.id.Home_Radio_zengsong:
                        NodeJsoupAsyncTask jsoupAsyncTask2 = new NodeJsoupAsyncTask(listView, inflater, list, getContext(), getActivity(), "免费赠送",textV2ex);
                        jsoupAsyncTask2.execute("go/idev");
                        break;
                    case R.id.Home_Radio_yuming:
                        NodeJsoupAsyncTask jsoupAsyncTask3 = new NodeJsoupAsyncTask(listView, inflater, list, getContext(), getActivity(), "域名",textV2ex);
                        jsoupAsyncTask3.execute("go/android");
                        break;
                    case R.id.Home_Radio_tuangou:
                        NodeJsoupAsyncTask jsoupAsyncTask4 = new NodeJsoupAsyncTask(listView, inflater, list, getContext(), getActivity(), "团购",textV2ex);
                        jsoupAsyncTask4.execute("go/linux");
                        break;
                    case R.id.Home_Radio_tishi:
                        NodeJsoupAsyncTask jsoupAsyncTask5 = new NodeJsoupAsyncTask(listView, inflater, list, getContext(), getActivity(), "安全提示",textV2ex);
                        jsoupAsyncTask5.execute("go/nodejs");
                        break;
                    case R.id.Home_Radio_all:
                        JsoupAsyncTask jsoupAsyncTask9 = new JsoupAsyncTask(listView, inflater, list, getContext(), getActivity(),textV2ex);
                        jsoupAsyncTask9.execute("?tab=deals");


                }
            }
        });
    }
}
