package zero.tongyang.threegrand.com.x2expro.Internet;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by tongyang on 16-11-13.
 */

public class ToList {

    public static List<Map<String, String>> TopicsToList(String string) {

        try {
            JSONArray jsonArray = new JSONArray(string);
            List<Map<String, String>> list = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = new JSONObject(jsonArray.get(i).toString());
                String id = object.getString("id");
                String title = object.getString("title");
                String url = object.getString("url");
                String content = object.getString("content");
                String content_rendered = object.getString("content_rendered");
                String replies = object.getString("replies");
                String member = object.getString("member");
                String time = object.getString("last_touched");
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Long t = new Long(time);
                String d = format.format(t);
                JSONObject jsonObject = new JSONObject(member);
                String username = jsonObject.getString("username");
                String img = jsonObject.getString("avatar_normal");

                JSONObject jsonObject1 = new JSONObject(object.getString("node"));
                String nodetitle = jsonObject1.getString("title");
                String nodeid = jsonObject1.getString("id");
                String nodename = jsonObject1.getString("name");

                Map<String, String> map = new HashMap<>();
                map.put("id", id);
                map.put("title", title);
                map.put("url", url);
                map.put("content", content);
                map.put("content_rendered", content_rendered);
                map.put("replies", replies);
                map.put("username", username);
                map.put("img", "http://"+img.substring(2,img.length()));
                map.put("nodetitle", nodetitle);
                map.put("nodeid", nodeid);
                map.put("nodename", nodename);

                map.put("time", d);

                list.add(map);

            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;

    }
}
