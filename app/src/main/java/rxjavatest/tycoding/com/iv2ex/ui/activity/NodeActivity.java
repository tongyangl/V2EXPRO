package rxjavatest.tycoding.com.iv2ex.ui.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.RelativeLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import rxjavatest.tycoding.com.iv2ex.R;
import rxjavatest.tycoding.com.iv2ex.adatper.NodeRecycleAdapter;

/**
 * Created by 佟杨 on 2017/4/10.
 */

public class NodeActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycle)
    RecyclerView recycle;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    private SearchView searchView;
    private NodeRecycleAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_node);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        recycle.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recycle.setItemAnimator(new DefaultItemAnimator());
        InputStream inputStream = getResources().openRawResource(R.raw.nodejson);
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(inputStream, "utf-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(inputStreamReader);
        StringBuffer sb = new StringBuffer("");
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Map<String, String>> list = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(sb.toString());
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = new JSONObject(array.getString(i));
                Map<String, String> map = new HashMap<>();
                map.put("title", object.getString("title"));
                map.put("content", object.getString("header"));
                map.put("num", object.getString("topics"));
                map.put("url", object.getString("url"));
                list.add(map);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapter = new NodeRecycleAdapter(list);

        adapter.notifiy(list);
        recycle.setAdapter(adapter);

        adapter.setOnItemClickListener(new NodeRecycleAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Map<String, String> data) {
                Intent intent=new Intent(NodeActivity.this,NodeTopticsActivity.class);
                intent.putExtra("url",data.get("url"));
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView =
                (SearchView) menu.findItem(R.id.ab_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(this);
        return true;

    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.getFilter().filter(newText);
        return true;
    }
}
