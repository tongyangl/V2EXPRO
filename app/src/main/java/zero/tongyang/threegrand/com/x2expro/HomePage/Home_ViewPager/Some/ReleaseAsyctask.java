package zero.tongyang.threegrand.com.x2expro.HomePage.Home_ViewPager.Some;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;

import zero.tongyang.threegrand.com.x2expro.Internet.GetTopics;

/**
 * Created by tongyang on 16-12-3.
 */

public class ReleaseAsyctask extends AsyncTask<String, Void, Boolean> {
    private Activity activity;

    public ReleaseAsyctask(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Boolean b) {
        super.onPostExecute(b);
        if (b) {


        } else {
            activity.finish();

        }
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        GetTopics getTopics = new GetTopics(activity);
        try {
            return getTopics.release(strings[0], strings[1], strings[2]);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
