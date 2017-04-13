package rxjavatest.tycoding.com.iv2ex.ui.activity;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import rxjavatest.tycoding.com.iv2ex.R;
import rxjavatest.tycoding.com.iv2ex.adatper.NodeRecycleAdapter;
import rxjavatest.tycoding.com.iv2ex.internet.intertnet;

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
    private List<Map<String, String>> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_node);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recycle.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recycle.setItemAnimator(new DefaultItemAnimator());

        adapter = new NodeRecycleAdapter(getlist(this,list), this);
        adapter.notifiy(getlist(this,list));
        recycle.setAdapter(adapter);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getnodes g = new getnodes();
                g.execute();
            }
        });


    }

    public static List<Map<String, String>> getlist(Activity activity, List<Map<String, String>> list) {

        SharedPreferences sharedPreferences = activity.getSharedPreferences("nodejson", MODE_PRIVATE);
        if (sharedPreferences.getString("nodejson", "").equals("")) {
            InputStream inputStream = activity.getResources().openRawResource(R.raw.nodejson);
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

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("nodejson", sb.toString());
            editor.commit();
            list = new ArrayList<>();
            try {
                JSONArray array = new JSONArray(sb.toString());
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = new JSONObject(array.getString(i));
                    Map<String, String> map = new HashMap<>();
                    map.put("title", object.getString("title"));
                    map.put("content", object.getString("header"));
                    map.put("num", object.getString("topics"));
                    map.put("url", object.getString("url"));
                    map.put("name", object.getString("name"));

                    list.add(map);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            SharedPreferences sharedPreferences1 = activity.getSharedPreferences("nodejson", MODE_PRIVATE);
            list = new ArrayList<>();
            try {
                JSONArray array = new JSONArray(sharedPreferences1.getString("nodejson", ""));
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = new JSONObject(array.getString(i));
                    Map<String, String> map = new HashMap<>();
                    map.put("title", object.getString("title"));
                    map.put("content", object.getString("header"));
                    map.put("num", object.getString("topics"));
                    map.put("name", object.getString("name"));

                    map.put("url", object.getString("url"));
                    list.add(map);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    class getnodes extends AsyncTask<String, Void, String> {
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            SharedPreferences sharedPreferences = getSharedPreferences("nodejson", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("nodejson", s);
            editor.commit();
            list = new ArrayList<>();
            try {
                JSONArray array = new JSONArray(s);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = new JSONObject(array.getString(i));
                    Map<String, String> map = new HashMap<>();
                    map.put("title", object.getString("title"));
                    map.put("content", object.getString("header"));
                    map.put("num", object.getString("topics"));
                    map.put("url", object.getString("url"));
                    map.put("name", object.getString("name"));
                    list.add(map);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("sss", s);
            adapter.notifyDataSetChanged();
            swipe.setRefreshing(false);
            Toast.makeText(NodeActivity.this, "更新完成", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(String... params) {
            intertnet net = new intertnet(NodeActivity.this);
            String s = net.getNodetoptic("https://www.v2ex.com/api/nodes/all.json");

            return s;
        }
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
