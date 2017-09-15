package com.example.v2ex.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.v2ex.R;
import com.example.v2ex.adapter.UserListAdapter;
import com.example.v2ex.model.TopticdetalisModel;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by 佟杨 on 2017/9/14.
 */

public class UsersActivity extends AppCompatActivity {
    private ListView listView;
    private Toolbar toolbar;
    private List<TopticdetalisModel> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        listView = (ListView) findViewById(R.id.lv);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("选择@的人");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

        Intent intent = getIntent();

        list = (List<TopticdetalisModel>) intent.getSerializableExtra("list");
        List<TopticdetalisModel> newList = new ArrayList<TopticdetalisModel>();

        UserListAdapter adapter = new UserListAdapter(removeDuplicateUser(list), getLayoutInflater(), getApplicationContext());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent1 = new Intent();
                intent1.putExtra("name", list.get(position).getUsername());
                setResult(1, intent1);
                finish();
            }
        });
    }
    private static ArrayList<TopticdetalisModel> removeDuplicateUser(List<TopticdetalisModel> users) {
        Set<TopticdetalisModel> set = new TreeSet<TopticdetalisModel>(new Comparator<TopticdetalisModel>() {
            @Override
            public int compare(TopticdetalisModel o1, TopticdetalisModel o2) {
                //字符串,则按照asicc码升序排列
                return o1.getUsername().compareToIgnoreCase(o2.getUsername());
            }
        });
        set.addAll(users);
        return new ArrayList<TopticdetalisModel>(set);
    }
}
