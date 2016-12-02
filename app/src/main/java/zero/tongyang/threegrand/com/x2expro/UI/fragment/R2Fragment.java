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

public class R2Fragment extends Fragment {
    List<Map<String, String>> list;
    @BindView(R.id.lv)
    ListView listView;
    @BindView(R.id.ptr)
    PtrFrameLayout ptr;
    LayoutInflater inflater;
    @BindView(R.id.Home_Radio_quanbu)
    RadioButton HomeRadioQuanbu;
    @BindView(R.id.Home_Radio_fenxiangfaxian)
    RadioButton HomeRadioFenxiangfaxian;
    @BindView(R.id.Home_Radio_fenxiangchuangzao)
    RadioButton HomeRadioFenxiangchuangzao;
    @BindView(R.id.Home_Radio_qad)
    RadioButton HomeRadioQad;
    @BindView(R.id.Home_Radio_kugongzuo)
    RadioButton HomeRadioKugongzuo;
    @BindView(R.id.Home_Radio_chengxuyuan)
    RadioButton HomeRadioChengxuyuan;
    @BindView(R.id.Home_Radio_zhichanghuati)
    RadioButton HomeRadioZhichanghuati;
    @BindView(R.id.Home_Radio_qisimiaoxiang)
    RadioButton HomeRadioQisimiaoxiang;
    @BindView(R.id.Home_Radio_youhuixinxi)
    RadioButton HomeRadioYouhuixinxi;
    @BindView(R.id.Home_radioGroup)
    RadioGroup HomeRadioGroup;
    @BindView(R.id.horizontalScrollView)
    HorizontalScrollView horizontalScrollView;
    @BindView(R.id.text_v2ex)
    ShimmerTextView textV2ex;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_r2, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        inflater = getActivity().getLayoutInflater();
        list = new ArrayList<>();
        StoreHouseHeader header = new StoreHouseHeader(getContext());
        header.initWithString("v2ex");

        header.setTextColor(Color.BLACK);
        ptr.addPtrUIHandler(header);
        ptr.setHeaderView(header);
        JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask(listView, inflater, list, getContext(), getActivity(),textV2ex);
        jsoupAsyncTask.execute("?tab=r2");
        listView.setDivider(new ColorDrawable(Color.argb(255, 242, 242, 242)));
        listView.setDividerHeight((int) Static.dp2px(getContext(), 10f));
        ptr.disableWhenHorizontalMove(false);
        ptr.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {


                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                switch (HomeRadioGroup.getCheckedRadioButtonId()) {


                    case R.id.Home_Radio_fenxiangfaxian:
                        NodeJsoupAsyncTask jsoupAsyncTask = new NodeJsoupAsyncTask(listView, inflater, list, getContext(), getActivity(), "分享发现",null);
                        jsoupAsyncTask.execute("go/share");
                        break;
                    case R.id.Home_Radio_fenxiangchuangzao:
                        NodeJsoupAsyncTask jsoupAsyncTask1 = new NodeJsoupAsyncTask(listView, inflater, list, getContext(), getActivity(), "分享创造",null);
                        jsoupAsyncTask1.execute("go/create");
                        break;
                    case R.id.Home_Radio_qad:
                        NodeJsoupAsyncTask jsoupAsyncTask2 = new NodeJsoupAsyncTask(listView, inflater, list, getContext(), getActivity(), "问与答",null);
                        jsoupAsyncTask2.execute("go/qna");
                        break;
                    case R.id.Home_Radio_kugongzuo:
                        NodeJsoupAsyncTask jsoupAsyncTask3 = new NodeJsoupAsyncTask(listView, inflater, list, getContext(), getActivity(), "酷工作",null);
                        jsoupAsyncTask3.execute("go/jobs");
                        break;
                    case R.id.Home_Radio_chengxuyuan:
                        NodeJsoupAsyncTask jsoupAsyncTask4 = new NodeJsoupAsyncTask(listView, inflater, list, getContext(), getActivity(), "程序员",null);
                        jsoupAsyncTask4.execute("go/programmer");
                        break;
                    case R.id.Home_Radio_zhichanghuati:
                        NodeJsoupAsyncTask jsoupAsyncTask5 = new NodeJsoupAsyncTask(listView, inflater, list, getContext(), getActivity(), "职场话题",null);
                        jsoupAsyncTask5.execute("go/career");
                        break;
                    case R.id.Home_Radio_qisimiaoxiang:
                        NodeJsoupAsyncTask jsoupAsyncTask6 = new NodeJsoupAsyncTask(listView, inflater, list, getContext(), getActivity(), "奇思妙想",null);
                        jsoupAsyncTask6.execute("go/ideas");
                        break;
                    case R.id.Home_Radio_youhuixinxi:
                        NodeJsoupAsyncTask jsoupAsyncTask7 = new NodeJsoupAsyncTask(listView, inflater, list, getContext(), getActivity(), "优惠信息",null);
                        jsoupAsyncTask7.execute("go/deals");
                        break;
                    case R.id.Home_Radio_quanbu:
                        JsoupAsyncTask jsoupAsyncTask8 = new JsoupAsyncTask(listView, inflater, list, getContext(), getActivity(),null);
                        jsoupAsyncTask8.execute("?tab=r2");

                }
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

                    case R.id.Home_Radio_fenxiangfaxian:
                        NodeJsoupAsyncTask jsoupAsyncTask = new NodeJsoupAsyncTask(listView, inflater, list, getContext(), getActivity(), "分享发现",textV2ex);
                        jsoupAsyncTask.execute("go/share");
                        break;
                    case R.id.Home_Radio_fenxiangchuangzao:
                        NodeJsoupAsyncTask jsoupAsyncTask1 = new NodeJsoupAsyncTask(listView, inflater, list, getContext(), getActivity(), "分享创造",textV2ex);
                        jsoupAsyncTask1.execute("go/create");
                        break;
                    case R.id.Home_Radio_qad:
                        NodeJsoupAsyncTask jsoupAsyncTask2 = new NodeJsoupAsyncTask(listView, inflater, list, getContext(), getActivity(), "问与答",textV2ex);
                        jsoupAsyncTask2.execute("go/qna");
                        break;
                    case R.id.Home_Radio_kugongzuo:
                        NodeJsoupAsyncTask jsoupAsyncTask3 = new NodeJsoupAsyncTask(listView, inflater, list, getContext(), getActivity(), "酷工作",textV2ex);
                        jsoupAsyncTask3.execute("go/jobs");
                        break;
                    case R.id.Home_Radio_chengxuyuan:
                        NodeJsoupAsyncTask jsoupAsyncTask4 = new NodeJsoupAsyncTask(listView, inflater, list, getContext(), getActivity(), "程序员",textV2ex);
                        jsoupAsyncTask4.execute("go/programmer");
                        break;
                    case R.id.Home_Radio_zhichanghuati:
                        NodeJsoupAsyncTask jsoupAsyncTask5 = new NodeJsoupAsyncTask(listView, inflater, list, getContext(), getActivity(), "职场话题",textV2ex);
                        jsoupAsyncTask5.execute("go/career");
                        break;
                    case R.id.Home_Radio_qisimiaoxiang:
                        NodeJsoupAsyncTask jsoupAsyncTask6 = new NodeJsoupAsyncTask(listView, inflater, list, getContext(), getActivity(), "奇思妙想",textV2ex);
                        jsoupAsyncTask6.execute("go/ideas");
                        break;
                    case R.id.Home_Radio_youhuixinxi:
                        NodeJsoupAsyncTask jsoupAsyncTask7 = new NodeJsoupAsyncTask(listView, inflater, list, getContext(), getActivity(), "优惠信息",textV2ex);
                        jsoupAsyncTask7.execute("go/deals");
                        break;
                    case R.id.Home_Radio_quanbu:
                        JsoupAsyncTask jsoupAsyncTask8 = new JsoupAsyncTask(listView, inflater, list, getContext(), getActivity(),textV2ex);
                        jsoupAsyncTask8.execute("?tab=r2");


                }
            }
        });
    }
}
