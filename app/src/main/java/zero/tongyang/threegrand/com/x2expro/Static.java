package zero.tongyang.threegrand.com.x2expro;

import android.content.Context;

/**
 * Created by tongyang on 16-11-12.
 */

public class Static  {
    public static float dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (dp * scale);
    }
}
