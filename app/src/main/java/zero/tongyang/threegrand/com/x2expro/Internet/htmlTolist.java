package zero.tongyang.threegrand.com.x2expro.Internet;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tongyang on 16-11-15.
 */

public class htmlTolist {

    public static List<Map<String, String>> TopicsToList(String string) {

        List<Map<String, String>> list = new ArrayList<>();

        Document document = Jsoup.parse(string);
        Elements elements = document.select("div[class=cell item]");
        for (int i = 0; i < elements.size(); i++) {

            Elements tr = elements.get(i).select("tr").select("td");
            Map<String, String> map = new HashMap<>();
            for (int j = 0; j < tr.size(); j++) {
                if (j == 0) {
                    String image = tr.get(j).select("img").attr("src");
                    String img = image.substring(2, image.length());
                    map.put("img", "http://" + img);
                } else if (j == 2) {
                    String lastreplice = "";
                    String username = "";
                    String title = tr.get(j).select("span[class=item_title]").select("a").text();

                    String nodetitle = tr.get(j).select("span[class=small fade]").select("a[class=node]").text();

                    Element t = tr.get(j).select("span[class=small fade]").first();
                    String ti = t.ownText();
                    String time=ti.substring(3,ti.length());

                    Elements elements1 = tr.get(j).select("span[class=small fade]").select("strong");
                    if (elements1.size() == 2) {
                        username = elements1.get(0).select("a").text();
                        if (elements1.get(1).hasText()) {
                            lastreplice = elements1.get(1).select("a").text();

                        } else {
                            username = elements1.get(0).select("a").text();
                            lastreplice = null;
                        }
                    }else if (elements1.size()==1){
                        username = elements1.get(0).select("a").text();
                        lastreplice = null;

                    }

                    map.put("title", title);
                    map.put("nodetitle", nodetitle);
                    map.put("time", time);
                    map.put("title", title);
                    map.put("username", username);
                    map.put("lastreplice", lastreplice);

                } else if (j == 3) {
                    String replice = "";
                    if (tr.get(j).hasText()) {
                        replice = tr.get(j).select("a").text();

                    }
                    map.put("replies", replice);

                }

            }
            list.add(map);
        }
        return list;
    }
}
