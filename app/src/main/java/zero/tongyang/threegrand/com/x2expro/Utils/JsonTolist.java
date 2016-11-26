package zero.tongyang.threegrand.com.x2expro.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tongyang on 16-11-18.
 */

public class JsonTolist {


    public static Map<String, String> GetTopicContent(String s) {


        Map<String, String> map = new HashMap<>();
        try {
            JSONArray jsonArray=new JSONArray(s);

            JSONObject jsonObject = new JSONObject(jsonArray.get(0).toString());

            String topictitle = jsonObject.getString("title");
            String url = jsonObject.getString("url");
            String content = jsonObject.getString("content");
            if (jsonObject.has("content_rendered")) {
                String content_rendered = jsonObject.getString("content_rendered");
                map.put("content_rendered", content_rendered);
            }
            String replies = jsonObject.getString("replies");
            JSONObject object = new JSONObject(jsonObject.getString("member"));
            String username = object.getString("username");
            String img = "http://" + object.getString("avatar_normal").substring(2, object.getString("avatar_normal").length());
            String userid = object.getString("id");
            JSONObject object1 = new JSONObject(jsonObject.getString("node"));
            String nodeid = object1.getString("id");
            String nodetitle = object1.getString("title");
            if (object1.has("created")){
                String created = object1.getString("created");
                map.put("created", created);

            }else if (object1.has("last_touched")){

                String last_touched = object1.getString("last_touched");
                map.put("last_touched", last_touched);

            }else  if (object1.has("last_modified")){
                String last_modified = object1.getString("last_modifided");
                map.put("last_modified", last_modified);

            }

            map.put("topictitle", topictitle);
            map.put("url", url);
            map.put("content", content);
            map.put("replies", replies);
            map.put("username", username);
            map.put("img", img);
            map.put("userid", userid);
            map.put("nodetitle", nodetitle);
            map.put("nodeid", nodeid);
            return map;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;

    }

    public static List<Map<String, String>> GetReplices(String s) {

        List<Map<String, String>> list = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(s);
            for (int i = 0; i < jsonArray.length(); i++) {
                Map<String, String> map = new HashMap<>();
                JSONObject jsonObject = new JSONObject(jsonArray.get(i).toString());
                String thanks = jsonObject.getString("thanks");
                String content = jsonObject.getString("content");
                String content_rendered = jsonObject.getString("content_rendered");
                JSONObject object = new JSONObject(jsonObject.getString("member"));
                String userid = object.getString("id");
                String username = object.getString("username");

                String img = "http://" + object.getString("avatar_normal").substring(2, object.getString("avatar_normal").length());
                String created = jsonObject.getString("created");
                map.put("thanks", thanks);
                map.put("content", content);
                map.put("content_rendered", content_rendered);
                map.put("userid", userid);
                map.put("username", username);
                map.put("img", img);
                map.put("created", created);
                list.add(map);

            }

            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
