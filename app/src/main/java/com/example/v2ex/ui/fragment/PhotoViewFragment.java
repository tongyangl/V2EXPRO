package com.example.v2ex.ui.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.v2ex.R;
import com.example.v2ex.utils.ScreenUtils;
import com.example.v2ex.widget.PinchImageView;

import static android.content.ContentValues.TAG;
import static android.support.v4.content.ContextCompat.checkSelfPermission;

/**
 * Created by 佟杨 on 2017/4/9.
 */

public class PhotoViewFragment extends Fragment {
    private String url;
    private int position;
    private PinchImageView imageView;
    private int mMaxWidth;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mMaxWidth = ScreenUtils.getDisplayWidth(getContext()) - ScreenUtils.dp(getContext(), 100);
    }

    public void setUrl(String url) {

        this.url = url;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photoview, container, false);
        imageView = (PinchImageView) view.findViewById(R.id.pinchimageView);

        Glide.with(getContext())

                .load(url)
                .asBitmap()
                .placeholder(R.drawable.ic_loading)
                .error(R.drawable.ic_error)
                .into(imageView);

        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final PopupWindow popupWindow = new PopupWindow(getContext());
                View view = getActivity().getLayoutInflater().inflate(R.layout.popu_pic, null);
                Button save = (Button) view.findViewById(R.id.save);
                // TextView shibie = (TextView) view.findViewById(R.id.shibie);

                Button cancel = (Button) view.findViewById(R.id.cancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        requestReadExternalPermission();
                        popupWindow.dismiss();

                    }
                });

                ColorDrawable dw = new ColorDrawable(0xffffffff);
                popupWindow.setBackgroundDrawable(dw);
                popupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
                popupWindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
                popupWindow.setFocusable(true);
                popupWindow.setOutsideTouchable(true);
                popupWindow.setContentView(view);
                popupWindow.setAnimationStyle(R.style.popu_style);
                popupWindow.showAtLocation(getActivity().getLayoutInflater().inflate(R.layout.activity_photoview, null), Gravity.BOTTOM | Gravity.CENTER, 0, 0);

                return true;
            }
        });
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void savepic() {

        Bitmap bm = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), bm, url, "v2ex保存的图片");
        Toast.makeText(getContext(), "已保存到系统图库", Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("NewApi")
    private void requestReadExternalPermission() {
        if (checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "READ permission IS NOT granted...");

            if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                Log.d(TAG, "11111111111111");
            } else {
                // 0 是自己定义的请求coude
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                Log.d(TAG, "222222222222");
            }
        } else {
            Log.d(TAG, "READ permission is granted...");
            savepic();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case 0: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    savepic();
                    // permission was granted
                    // request successfully, handle you transactions

                } else {
                    Toast.makeText(getContext(), "保存失败，权限未授予", Toast.LENGTH_SHORT).show();
                    // permission denied
                    // request failed
                }

                return;
            }
            default:
                break;

        }


    }
}
