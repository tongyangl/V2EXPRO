package com.example.v2ex.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 佟杨 on 2017/9/6.
 */

public class SomeUtils {
    /*
      判断是否是数字
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /*
        获取回复的once
     */
    public static String getrepliceonce(String url) {

        Pattern pattern = Pattern.compile("<input type=\"hidden\" value=\"([0-9]+)\" name=\"once\" />");
        final Matcher matcher = pattern.matcher(url);
        if (matcher.find())
            return matcher.group(1);
        return null;

    }
}
