package rxjavatest.tycoding.com.iv2ex.ui.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import rxjavatest.tycoding.com.iv2ex.R;
import rxjavatest.tycoding.com.iv2ex.adatper.NodesArrayAdapter;
import rxjavatest.tycoding.com.iv2ex.internet.intertnet;
import rxjavatest.tycoding.com.iv2ex.rxjava.rxjava;

/**
 * Created by 佟杨 on 2017/4/13.
 */

public class CreateToptic extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.img)
    ImageView img;
    private ProgressDialog dialog;
    private String nodename;
    @BindView(R.id.topic_add_title)
    EditText mTitle;
    @BindView(R.id.topic_add_content)
    EditText mContent;
    String mNodeName;
    NodeModel mNode;
    @BindView(R.id.shadow_view)
    View shadowView;
    @BindView(R.id.node)
    TextView node;
    @BindView(R.id.view)
    View view;
    private PopupWindow popupWindow;
  private  SendAsynctask asynctask;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createtoptic);
        ButterKnife.bind(this);
        toolbar.setTitle("主题创建");

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        List<Map<String, String>> list = new ArrayList<>();
        list = NodeActivity.getlist(this, list);
        final ArrayList<NodeModel> arrayList = new ArrayList<>();
        for (Map<String, String> map : list) {
            NodeModel model = new NodeModel();
            model.setName(map.get("name"));
            model.setTitle(map.get("title"));
            arrayList.add(model);
        }
        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        if (sharedPreferences.getString("userimg", "").equals("")) {

        } else {
            ImageLoader.getInstance().displayImage(sharedPreferences.getString("userimg", ""), img);
        }
        final NodesArrayAdapter arrayAdapter = new NodesArrayAdapter(this, arrayList);
        popupWindow = new PopupWindow(CreateToptic.this);
        final View view = getLayoutInflater().inflate(R.layout.popu_create, null);
        SearchView searchView = (SearchView) view.findViewById(R.id.search);
        searchView.setFocusable(true);
        ListView listView = (ListView) view.findViewById(R.id.lv);
        popupWindow.setContentView(view);
        popupWindow.setFocusable(true);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        listView.setAdapter(arrayAdapter);
        Drawable drawable = new ColorDrawable(Color.WHITE);
        popupWindow.setBackgroundDrawable(drawable);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                arrayAdapter.getFilter().filter(newText);
                return true;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                node.setText(arrayAdapter.objects.get(position).getTitle());
                nodename = arrayAdapter.objects.get(position).getName();
                popupWindow.dismiss();
            }
        });
        node.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    Drawable drawable = getResources().getDrawable(R.drawable.ic_arrow_drop_up_black_24dp);
                    node.setCompoundDrawables(null, null, drawable, null);

                } else {
                    Drawable drawable = getResources().getDrawable(R.drawable.ic_arrow_drop_down_black_24dp);
                    node.setCompoundDrawables(null, null, drawable, null);
                    popupWindow.showAsDropDown(node);

                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.fasong, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.send) {
            if (mTitle.getText().length() == 0|node.getText().length()==0) {
                 Toast.makeText(getApplicationContext(),"请选择节点或标题",Toast.LENGTH_SHORT).show();
            } else {
                String args[] = {
                        mTitle.getText().toString(), mContent.getText().toString()
                };
                 asynctask = new SendAsynctask();
                asynctask.execute(args);
            }

        }
        return super.onOptionsItemSelected(item);
    }

    class SendAsynctask extends AsyncTask<String, View, Integer> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(CreateToptic.this);
            dialog.setTitle("创建中");
            dialog.show();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            if (integer == 302) {
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), "创建成功", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                dialog.dismiss();
                Toast.makeText(CreateToptic.this, "创建失败", Toast.LENGTH_SHORT).show();
                finish();
            }
        }

        @Override
        protected Integer doInBackground(String... params) {
            intertnet net = new intertnet(CreateToptic.this);

            int a = net.topicCreateWithNodeName(CreateToptic.this, nodename, params[0], params[1]);


            return a;
        }
    }

    @Override
    public void onBackPressed() {
        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        } else if (mTitle.getText().toString().isEmpty() &&
                mContent.getText().toString().isEmpty()) {
            finish();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            AlertDialog dialog = builder.setTitle("创作新主题")
                    .setMessage("放弃此次创建的主题？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    })
                    .setNegativeButton("取消", null).show();
            dialog.show();

        }
    }


    public class NodeModel {

        private String title;
        private String name;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
      if (  asynctask!=null){
          asynctask.cancel(true);
      }
    }
}
