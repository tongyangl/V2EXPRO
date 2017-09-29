package com.example.v2ex.model;

/**
 * Created by 佟杨 on 2017/9/3.
 */

public class TopticModel {

    private String title;
    private String img;
    private String replices;
    private String userName;
    private String lastreplice;
    private String time;
    private String repliceurl;
    private String nodeTitle;
    private String userUrl;

    public String getUserUrl() {
        return userUrl;
    }

    public void setUserUrl(String userUrl) {
        this.userUrl = userUrl;
    }

    public String getNodeTitle() {
        return nodeTitle;
    }

    public void setNodeTitle(String nodeTitle) {
        this.nodeTitle = nodeTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getReplices() {
        return replices;
    }

    public void setReplices(String replices) {
        this.replices = replices;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLastreplice() {
        return lastreplice;
    }

    public void setLastreplice(String lastreplice) {
        this.lastreplice = lastreplice;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRepliceurl() {
        return repliceurl;
    }

    public void setRepliceurl(String repliceurl) {
        this.repliceurl = repliceurl;
    }
}
