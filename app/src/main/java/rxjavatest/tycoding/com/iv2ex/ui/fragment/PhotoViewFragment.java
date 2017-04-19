package rxjavatest.tycoding.com.iv2ex.ui.fragment;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import rxjavatest.tycoding.com.iv2ex.R;
import rxjavatest.tycoding.com.iv2ex.ui.activity.photoviewactivity;
import rxjavatest.tycoding.com.iv2ex.ui.widget.PinchImageView;
import rxjavatest.tycoding.com.iv2ex.utils.imageloader;

/**
 * Created by 佟杨 on 2017/4/9.
 */

public class PhotoViewFragment extends Fragment {
    private String url;
    private int position;
    private PinchImageView imageView;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("---", "onActivityCreated");

    }

    public void setUrl(String url) {

        this.url = url;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photoview, container, false);
        imageView = (PinchImageView) view.findViewById(R.id.pinchimageView);
        imageloader.dispalyimage(url, getActivity(), imageView);
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final PopupWindow popupWindow = new PopupWindow(getContext());
                View view = getActivity().getLayoutInflater().inflate(R.layout.popu_pic, null);
                TextView save = (TextView) view.findViewById(R.id.save);
                TextView shibie = (TextView) view.findViewById(R.id.shibie);

                TextView cancel = (TextView) view.findViewById(R.id.cancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        savepic();
                        popupWindow.dismiss();

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
        Bitmap image = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), image, url, "v2ex保存的图片");
        Toast.makeText(getContext(), "已保存到系统图库", Toast.LENGTH_SHORT).show();
    }
}
