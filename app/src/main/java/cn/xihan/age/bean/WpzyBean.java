package cn.xihan.age.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @项目名 : AGE动漫
 * @作者 : MissYang
 * @创建时间 : 2021/2/9 15:10
 * @介绍 :
 */
public class WpzyBean {

    /**
     * Title : [全集 BD-1080P]
     * Link : https://pan.baidu.com/s/1f32fx3FY86C5FRqBk-slQw
     * ExCode : 1a17
     */

    @SerializedName("Title")
    private String Title;
    @SerializedName("Link")
    private String Link;
    @SerializedName("ExCode")
    private String ExCode;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getExCode() {
        return ExCode;
    }

    public String getLink() {
        return Link;
    }

    public void setExCode(String exCode) {
        ExCode = exCode;
    }

    public void setLink(String link) {
        Link = link;
    }
}

