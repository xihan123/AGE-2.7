package cn.xihan.age.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @项目名 : AGE动漫
 * @作者 : MissYang
 * @创建时间 : 2021/2/9 15:11
 * @介绍 :
 */
public class VideoBean {

    /**
     * title : 第1集
     * videourl : https://1251316161.vod2.myqcloud.com/007a649dvodcq1251316161/6fb3a3565285890804982491933/F7udjKaD7N8A.mp4
     */

    @SerializedName("title")
    private String title;
    @SerializedName("videourl")
    private String videourl;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getVideourl() {
        return videourl;
    }

    public void setVideourl(String videourl) {
        this.videourl = videourl;
    }
}

