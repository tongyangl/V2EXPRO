package rxjavatest.tycoding.com.iv2ex.ui.activity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import rxjavatest.tycoding.com.iv2ex.R;
import rxjavatest.tycoding.com.iv2ex.ui.widget.PinchImageView;
import rxjavatest.tycoding.com.iv2ex.utils.imageloader;


/**
 * Created by 佟杨 on 2017/4/9.
 */

public class photoviewactivity extends AppCompatActivity {


    private String list;
    private int position;
    private PinchImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photoview);
        Intent intent = getIntent();
        imageView = (PinchImageView) findViewById(R.id.pinchimageView);
        list = intent.getStringExtra("url");
        position = intent.getIntExtra("position", 0);
        Log.d("---", list + "list");
        imageloader.dispalyimage(list, this, imageView);
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupWindow popupWindow = new PopupWindow(photoviewactivity.this);
                View view = getLayoutInflater().inflate(R.layout.popu_pic, null);
                TextView share = (TextView) view.findViewById(R.id.share);
                TextView save = (TextView) view.findViewById(R.id.save);
                TextView shibie = (TextView) view.findViewById(R.id.shibie);

                TextView cancel = (TextView) view.findViewById(R.id.cancel);
               save.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       savepic();
                   }
               });
                 shibie.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         
                     }
                 });
                ColorDrawable dw = new ColorDrawable(0xffffffff);
                popupWindow.setBackgroundDrawable(dw);
                popupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
                popupWindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
                popupWindow.setFocusable(true);
                popupWindow.setOutsideTouchable(true);
                popupWindow.setContentView(view);
                popupWindow.showAtLocation(getLayoutInflater().inflate(R.layout.activity_photoview, null), Gravity.BOTTOM | Gravity.CENTER, 0, 0);

                return true;
            }
        });
    }

    public void savepic() {
        String path = getExternalFilesDir(Environment.DIRECTORY_PICTURES).getPath();
        final File file = new File(path + "/iv2ex");
        String u = list.replace("/", "!");
        u = u.replace(".", "!");
        if (!file.exists()) {
            file.mkdirs();
        }
        final File f = new File(file, u);
        try {
            FileInputStream fos = new FileInputStream(f);
            BitmapFactory.decodeStream(fos);

            Toast.makeText(photoviewactivity.this, "保存成功", Toast.LENGTH_SHORT).show();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(photoviewactivity.this, "保存失败", Toast.LENGTH_SHORT).show();
        }
    }
}