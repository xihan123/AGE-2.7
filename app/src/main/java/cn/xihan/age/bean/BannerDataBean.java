package cn.xihan.age.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @项目名 : AGE动漫
 * @作者 : MissYang
 * @创建时间 : 2021/3/2 1:10
 * @介绍 :
 */
public class BannerDataBean {

    /**
     * AID : 20200328
     * Title : 恶玉DRIVE
     * NewTitle : 第12话(完结)
     * PicUrl : //sc02.alicdn.com/kf/H372ad71f1ea1486f8769b629025b0878c.jpg
     * Time : 1609090955425454775
     */

    @SerializedName("AID")
    private String aid;
    @SerializedName("Title")
    private String Title;
    @SerializedName("NewTitle")
    private String NewTitle;
    @SerializedName("PicUrl")
    private String PicUrl;
    @SerializedName("Time")
    private Long   Time;

    public void setTitle(String title) {
        Title = title;
    }

    public String getTitle() {
        return Title;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getAid() {
        return aid;
    }

    public String getNewTitle() {
        return NewTitle;
    }

    public void setNewTitle(String newTitle) {
        NewTitle = newTitle;
    }

    public void setTime(Long time) {
        Time = time;
    }

    public void setPicUrl(String picUrl) {
        PicUrl = picUrl;
    }

    public String getPicUrl() {
        return PicUrl;
    }

    public Long getTime() {
        return Time;
    }
}


