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

public class AppleFragment extends Fragment {
    List<Map<String, String>> list;
    @BindView(R.id.lv)
    ListView listView;
    @BindView(R.id.ptr)
    PtrFrameLayout ptr;
    LayoutInflater inflater;
    @BindView(R.id.Home_radioGroup)
    RadioGroup HomeRadioGroup;
    @BindView(R.id.Home_Radio_APPLE)
    RadioButton HomeRadioAPPLE;
    @BindView(R.id.Home_Radio_macOS)
    RadioButton HomeRadioMacOS;
    @BindView(R.id.Home_Radio_iPhone)
    RadioButton HomeRadioIPhone;
    @BindView(R.id.Home_Radio_iPad)
    RadioButton HomeRadioIPad;
    @BindView(R.id.Home_Radio_MBP)
    RadioButton HomeRadioMBP;
    @BindView(R.id.Home_Radio_iMac)
    RadioButton HomeRadioIMac;
    @BindView(R.id.Home_Radio_WATCH)
    RadioButton HomeRadioWATCH;
    @BindView(R.id.Home_Radio_Apple)
    RadioButton HomeRadioApple;
    @BindView(R.id.horizontalScrollView)
    HorizontalScrollView horizontalScrollView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_apple, container, false);
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
        jsoupAsyncTask.execute("?tab=apple");
        List<String> stringList = new ArrayList<>();

        ptr.disableWhenHorizontalMove(false);
        ptr.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {


                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask(listView, inflater, list, getContext(), getActivity());
                jsoupAsyncTask.execute("?tab=apple");
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ptr.refreshComplete();
                    }
                }, 1800);
            }
        });
        HomeRadioGroup.check(R.id.Home_Radio_APPLE);
        HomeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.Home_Radio_macOS:
                        NodeJsoupAsyncTask jsoupAsyncTask  = new NodeJsoupAsyncTask(listView, inflater, list, getContext(), getActivity(),"macOS");
                        jsoupAsyncTask.execute("go/macos");break;
                    case R.id.Home_Radio_iPhone:
                        NodeJsoupAsyncTask jsoupAsyncTask1  = new NodeJsoupAsyncTask(listView, inflater, list, getContext(), getActivity(),"iPhone");
                        jsoupAsyncTask1.execute("go/iphone");break;
                    case R.id.Home_Radio_iPad:
                        NodeJsoupAsyncTask jsoupAsyncTask2  = new NodeJsoupAsyncTask(listView, inflater, list, getContext(), getActivity(),"iPad");
                        jsoupAsyncTask2.execute("go/ipad");break;
                    case R.id.Home_Radio_MBP:
                        NodeJsoupAsyncTask jsoupAsyncTask3  = new NodeJsoupAsyncTask(listView, inflater, list, getContext(), getActivity(),"MBP");
                        jsoupAsyncTask3.execute("go/mbp");break;
                    case R.id.Home_Radio_iMac:
                        NodeJsoupAsyncTask jsoupAsyncTask4  = new NodeJsoupAsyncTask(listView, inflater, list, getContext(), getActivity(),"iMac");
                        jsoupAsyncTask4.execute("go/imac");break;
                    case R.id.Home_Radio_Apple:
                        NodeJsoupAsyncTask jsoupAsyncTask5  = new NodeJsoupAsyncTask(listView, inflater, list, getContext(), getActivity(),"apple");
                        jsoupAsyncTask5.execute("go/apple");break;
                    case R.id.Home_Radio_WATCH:
                        NodeJsoupAsyncTask jsoupAsyncTask9  = new NodeJsoupAsyncTask(listView, inflater, list, getContext(), getActivity(),"WWATCH");
                        jsoupAsyncTask9.execute("go/watch");break;
                    case R.id.Home_Radio_APPLE:
                        JsoupAsyncTask jsoupAsyncTask6 = new JsoupAsyncTask(listView, inflater, list, getContext(), getActivity());
                        jsoupAsyncTask6.execute("?tab=apple");break;

                }
            }
        });
    }


}
