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

public class PlayFragment extends Fragment {
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
    @BindView(R.id.Home_Radio_dianziyouxi)
    RadioButton HomeRadioDianziyouxi;
    @BindView(R.id.Home_Radio_dianying)
    RadioButton HomeRadioDianying;
    @BindView(R.id.Home_Radio_juji)
    RadioButton HomeRadioJuji;
    @BindView(R.id.Home_Radio_music)
    RadioButton HomeRadioMusic;
    @BindView(R.id.Home_Radio_lvyou)
    RadioButton HomeRadioLvyou;
    @BindView(R.id.Home_Radio_android)
    RadioButton HomeRadioAndroid;
    @BindView(R.id.Home_Radio_julebu)
    RadioButton HomeRadioJulebu;
    @BindView(R.id.Home_radioGroup)
    RadioGroup HomeRadioGroup;
    @BindView(R.id.horizontalScrollView)
    HorizontalScrollView horizontalScrollView;
    @BindView(R.id.text_v2ex)
    ShimmerTextView textV2ex;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_paly, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView.setDivider(new ColorDrawable(Color.argb(255, 242, 242, 242)));
        listView.setDividerHeight((int) Static.dp2px(getContext(), 10f));
        list = new ArrayList<>();
        inflater = getActivity().getLayoutInflater();
        JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask(listView, inflater, list, getContext(), getActivity(),textV2ex);
        jsoupAsyncTask.execute("?tab=play");
        ptr.disableWhenHorizontalMove(false);
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
                switch (HomeRadioGroup.getCheckedRadioButtonId()){
                    case R.id.Home_Radio_fenxiangfaxian:
                        NodeJsoupAsyncTask jsoupAsyncTask  = new NodeJsoupAsyncTask(listView, inflater, list, getContext(), getActivity(),"分享发现",null);
                        jsoupAsyncTask.execute("go/share");break;
                    case R.id.Home_Radio_dianziyouxi:
                        NodeJsoupAsyncTask jsoupAsyncTask1  = new NodeJsoupAsyncTask(listView, inflater, list, getContext(), getActivity(),"电子游戏",null);
                        jsoupAsyncTask1.execute("go/games");break;
                    case R.id.Home_Radio_dianying:
                        NodeJsoupAsyncTask jsoupAsyncTask2  = new NodeJsoupAsyncTask(listView, inflater, list, getContext(), getActivity(),"电影",null);
                        jsoupAsyncTask2.execute("go/movie");break;
                    case R.id.Home_Radio_juji:
                        NodeJsoupAsyncTask jsoupAsyncTask3  = new NodeJsoupAsyncTask(listView, inflater, list, getContext(), getActivity(),"剧集",null);
                        jsoupAsyncTask3.execute("go/tv");break;
                    case R.id.Home_Radio_music:
                        NodeJsoupAsyncTask jsoupAsyncTask4  = new NodeJsoupAsyncTask(listView, inflater, list, getContext(), getActivity(),"音乐",null);
                        jsoupAsyncTask4.execute("go/music");break;
                    case R.id.Home_Radio_lvyou:
                        NodeJsoupAsyncTask jsoupAsyncTask5 = new NodeJsoupAsyncTask(listView, inflater, list, getContext(), getActivity(),"旅游",null);
                        jsoupAsyncTask5.execute("go/travel");break;
                    case R.id.Home_Radio_android:
                        NodeJsoupAsyncTask jsoupAsyncTask6  = new NodeJsoupAsyncTask(listView, inflater, list, getContext(), getActivity(),"Android",null);
                        jsoupAsyncTask6.execute("go/android");break;
                    case R.id.Home_Radio_julebu:
                        NodeJsoupAsyncTask jsoupAsyncTask7  = new NodeJsoupAsyncTask(listView, inflater, list, getContext(), getActivity(),"午夜俱乐部",null);
                        jsoupAsyncTask7.execute("go/bb");break;
                    case R.id.Home_Radio_quanbu:
                        JsoupAsyncTask jsoupAsyncTask8 = new JsoupAsyncTask(listView, inflater, list, getContext(), getActivity(),null);
                        jsoupAsyncTask8.execute("?tab=play");


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
                switch (i){

                    case R.id.Home_Radio_fenxiangfaxian:
                        NodeJsoupAsyncTask jsoupAsyncTask  = new NodeJsoupAsyncTask(listView, inflater, list, getContext(), getActivity(),"分享发现",textV2ex);
                        jsoupAsyncTask.execute("go/share");break;
                    case R.id.Home_Radio_dianziyouxi:
                        NodeJsoupAsyncTask jsoupAsyncTask1  = new NodeJsoupAsyncTask(listView, inflater, list, getContext(), getActivity(),"电子游戏",textV2ex);
                        jsoupAsyncTask1.execute("go/games");break;
                    case R.id.Home_Radio_dianying:
                        NodeJsoupAsyncTask jsoupAsyncTask2  = new NodeJsoupAsyncTask(listView, inflater, list, getContext(), getActivity(),"电影",textV2ex);
                        jsoupAsyncTask2.execute("go/movie");break;
                    case R.id.Home_Radio_juji:
                        NodeJsoupAsyncTask jsoupAsyncTask3  = new NodeJsoupAsyncTask(listView, inflater, list, getContext(), getActivity(),"剧集",textV2ex);
                        jsoupAsyncTask3.execute("go/tv");break;
                    case R.id.Home_Radio_music:
                        NodeJsoupAsyncTask jsoupAsyncTask4  = new NodeJsoupAsyncTask(listView, inflater, list, getContext(), getActivity(),"音乐",textV2ex);
                        jsoupAsyncTask4.execute("go/music");break;
                    case R.id.Home_Radio_lvyou:
                        NodeJsoupAsyncTask jsoupAsyncTask5 = new NodeJsoupAsyncTask(listView, inflater, list, getContext(), getActivity(),"旅游",textV2ex);
                        jsoupAsyncTask5.execute("go/travel");break;
                    case R.id.Home_Radio_android:
                        NodeJsoupAsyncTask jsoupAsyncTask6  = new NodeJsoupAsyncTask(listView, inflater, list, getContext(), getActivity(),"Android",textV2ex);
                        jsoupAsyncTask6.execute("go/android");break;
                    case R.id.Home_Radio_julebu:
                        NodeJsoupAsyncTask jsoupAsyncTask7  = new NodeJsoupAsyncTask(listView, inflater, list, getContext(), getActivity(),"午夜俱乐部",textV2ex);
                        jsoupAsyncTask7.execute("go/bb");break;
                    case R.id.Home_Radio_quanbu:
                        JsoupAsyncTask jsoupAsyncTask8 = new JsoupAsyncTask(listView, inflater, list, getContext(), getActivity(),textV2ex);
                        jsoupAsyncTask8.execute("?tab=play");


                }
            }
        });
    }

}
