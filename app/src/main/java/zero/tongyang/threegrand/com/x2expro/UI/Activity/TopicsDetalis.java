package zero.tongyang.threegrand.com.x2expro.UI.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.romainpiel.shimmer.ShimmerTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;
import zero.tongyang.threegrand.com.x2expro.HomePage.Home_ViewPager.Some.LoadTopicsById;
import zero.tongyang.threegrand.com.x2expro.R;
import zero.tongyang.threegrand.com.x2expro.Utils.AsyncImageLoader;

import static zero.tongyang.threegrand.com.x2expro.Adaptar.ListViewAdaptar.asyncImageLoader;

/**
 * Created by tongyang on 16-11-16.
 */

public class TopicsDetalis extends Activity {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.shadow_view)
    View shadowView;
    @BindView(R.id.lv)
    ListView lv;
    @BindView(R.id.ptr)
    PtrClassicFrameLayout ptr;
    @BindView(R.id.text_v2ex)
    ShimmerTextView textV2ex;

    private String title;
    private String repliceurl;
    private String img;
    private String Username;
    private String nodetitle;
    private List<Map<String, String>> list;

    private String t;
    String id;
    Map<String, String> map;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topics_detalis);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        title = intent.getStringExtra("topicstitle");
        repliceurl = intent.getStringExtra("repliceurl");
        img = intent.getStringExtra("img");
        Username = intent.getStringExtra("username");
        nodetitle = intent.getStringExtra("nodetitle");

        t = intent.getStringExtra("time");
        final LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.lv_header, null);
        TextView nodename = (TextView) view.findViewById(R.id.nodename);
        nodename.setBackgroundResource(R.drawable.nodetitle);
        TextView username = (TextView) view.findViewById(R.id.username);
        TextView topictitle = (TextView) view.findViewById(R.id.topictitle);
        TextView time = (TextView) view.findViewById(R.id.time);
        final TextView content = (TextView) view.findViewById(R.id.content);
        ImageView imageView = (ImageView) view.findViewById(R.id.imagView);
        Drawable cachedImage = asyncImageLoader.loadDrawable(img, new AsyncImageLoader.ImageCallback() {
            public void imageLoaded(Drawable imageDrawable, String imageUrl) {
                ImageView imageViewByTag = (ImageView) lv.findViewWithTag(imageUrl);
                if (imageViewByTag != null) {
                    imageViewByTag.setImageDrawable(imageDrawable);
                }
            }
        });
        if (cachedImage == null) {
            imageView.setImageResource(R.drawable.android);
        } else {
            imageView.setImageDrawable(cachedImage);
        }
        topictitle.setText(title);
        StoreHouseHeader header = new StoreHouseHeader(this);
        header.initWithString("V2EX");

        header.setTextColor(Color.BLACK);
        ptr.addPtrUIHandler(header);
        ptr.setHeaderView(header);
        nodename.setText(nodetitle);
        if (t.length() > 9) {
            String string = t.substring(5, t.length() - 9);
            time.setText(string);
        }

        username.setText(Username);

        lv.addHeaderView(view);
        map = new HashMap<>();
        list = new ArrayList<>();
        LoadTopicsById loadTopicsById = new LoadTopicsById(list, lv, map, getApplicationContext(), inflater, content,textV2ex);
        loadTopicsById.execute(repliceurl);
        ptr.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                LoadTopicsById loadTopicsById = new LoadTopicsById(list, lv, map, getApplicationContext(), inflater, content,null);
                loadTopicsById.execute(repliceurl);
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ptr.refreshComplete();
                    }
                }, 1800);
            }
        });
    }

    @OnClick(R.id.back)
    void back() {

        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


}
