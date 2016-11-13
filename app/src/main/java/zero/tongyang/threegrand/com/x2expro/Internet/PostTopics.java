package zero.tongyang.threegrand.com.x2expro.Internet;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by tongyang on 16-11-13.
 */

public class PostTopics {

    public static String  PostTopics(String url,String node_id,String node_name ,String username){
       String key="";
       String value="";
        OkHttpClient okHttpClient=new OkHttpClient();
        if (!node_id.equals(null)){

            key="node_id";
            value=node_id;
        }else if (!node_name.equals(null)){
            key="node_name";
            value=node_name;

        }else if (!username.equals(null)){
            key="username";
            value=username;
        }
        RequestBody formBody = new FormEncodingBuilder()
                .add(key, value)
                .build();

        Request request=new Request.Builder().url(url).post(formBody).build();
        try {
            Response response=okHttpClient.newCall(request).execute();
            if (response.isSuccessful()){

                return request.body().toString();

            }else {

                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return  null;
    }
}
