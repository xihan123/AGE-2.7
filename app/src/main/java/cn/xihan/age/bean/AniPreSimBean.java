package cn.xihan.age.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @项目名 : AGE动漫
 * @作者 : MissYang
 * @创建时间 : 2021/2/9 15:11
 * @介绍 :
 */
public class AniPreSimBean {

    /**
     * AID : 20180008
     * Title : citrus～柑橘味香气～
     * NewTitle : [TV 01-12]
     * PicSmall : //sc02.alicdn.com/kf/H4b1344d2493f4ef290af7f7a72ace409i.jpg
     * Href : /detail/20180008
     */

    @SerializedName("AID")
    private String aid;
    @SerializedName("Title")
    private String Title;
    @SerializedName("NewTitle")
    private String NewTitle;
    @SerializedName("PicSmall")
    private String PicSmall;
    @SerializedName("Href")
    private String Href;

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

    public void setNewTitle(String newTitle) {
        NewTitle = newTitle;
    }

    public String getNewTitle() {
        return NewTitle;
    }

    public String getHref() {
        return Href;
    }

    public void setHref(String href) {
        Href = href;
    }

    public String getPicSmall() {
        return PicSmall;
    }

    public void setPicSmall(String picSmall) {
        PicSmall = picSmall;
    }
}

