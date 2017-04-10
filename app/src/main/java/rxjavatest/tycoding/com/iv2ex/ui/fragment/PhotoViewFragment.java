package rxjavatest.tycoding.com.iv2ex.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import rxjavatest.tycoding.com.iv2ex.R;
import rxjavatest.tycoding.com.iv2ex.ui.widget.PinchImageView;
import rxjavatest.tycoding.com.iv2ex.utils.imageloader;

/**
 * Created by 佟杨 on 2017/4/9.
 */

public class PhotoViewFragment extends Fragment {
    private  String url;
    private  int position;
    private PinchImageView imageView;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("---","onActivityCreated");
        imageloader.dispalyimage(url,getActivity(),imageView);
    }

    public  void newInstance(String u, int i) {
        url = u;
        position = i;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photoview, container, false);
        imageView = (PinchImageView) view.findViewById(R.id.pinchimageView);
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

}
