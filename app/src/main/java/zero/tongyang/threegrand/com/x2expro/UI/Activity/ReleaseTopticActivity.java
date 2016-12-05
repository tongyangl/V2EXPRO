package zero.tongyang.threegrand.com.x2expro.UI.Activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zero.tongyang.threegrand.com.x2expro.Design.CircleImageView;
import zero.tongyang.threegrand.com.x2expro.HomePage.Home_ViewPager.Some.ReleaseAsyctask;
import zero.tongyang.threegrand.com.x2expro.Internet.GetTopics;
import zero.tongyang.threegrand.com.x2expro.R;

/**
 * Created by tongyang on 16-12-3.
 */

public class ReleaseTopticActivity extends Activity {

    @BindView(R.id.back)
    Button back;
    @BindView(R.id.release)
    Button release;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.topictitle)
    EditText title;
    @BindView(R.id.view1)
    View view1;
    @BindView(R.id.content)
    EditText content;
    @BindView(R.id.choose)
    TextView choose;
    @BindView(R.id.userimg)
    CircleImageView userimg;
    private GetTopics getTopics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release);
        ButterKnife.bind(this);
        init();

    }

    public void init() {
        String path1 = Environment.getExternalStorageDirectory().getPath() + "/v2expro/userimg/userimg.png";
        Bitmap bitmap = BitmapFactory.decodeFile(path1);
        userimg.setImageBitmap(bitmap);
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }


    @OnClick(R.id.release)
    void release() {
        if (title.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "请输入标题", Toast.LENGTH_SHORT).show();

        } else {
            if (choose.getText().equals("选择节点")) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                builder.setTitle("提示");
                builder.setMessage("您尚未选择节点，系统将默认发布在问与答节点上，是否继续？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String args[] = {
                                title.getText().toString(), content.getText().toString(),
                                choose.getText().toString()
                        };
                        ReleaseAsyctask asyctask = new ReleaseAsyctask(ReleaseTopticActivity.this);
                        asyctask.execute(args);

                    }
                });
                builder.setNegativeButton("取消", null);
                builder.show();
            } else {
                String args[] = {
                        title.getText().toString(), content.getText().toString(),
                        choose.getText().toString()
                };
                ReleaseAsyctask asyctask = new ReleaseAsyctask(ReleaseTopticActivity.this);
                asyctask.execute(args);
            }


        }

    }

}
