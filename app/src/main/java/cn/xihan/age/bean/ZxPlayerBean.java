package cn.xihan.age.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @项目名 : AGE动漫
 * @作者 : MissYang
 * @创建时间 : 2021/2/12 9:01
 * @介绍 :
 */
public class ZxPlayerBean implements Serializable {




    /**
     * Title : 先导PV
     * Title_l : 先导PV 720P
     * PlayId : <play>PC-QPIC</play>
     * PlayVid : qpic-mup03/装甲娘战机 先导PV 720P
     * EpName : PV
     * EpPic :
     * Ex :
     */




    @SerializedName("Title")
    private String Title;
    /**
     * titleL
     */
    @SerializedName("Title_l")
    private String titleL;
    /**
     * PlayId
     */
    @SerializedName("PlayId")
    private String PlayId;
    /**
     * PlayVid
     */
    @SerializedName("PlayVid")
    private String PlayVid;
    /**
     * EpName
     */
    @SerializedName("EpName")
    private String EpName;
    /**
     * EpPic
     */
    @SerializedName("EpPic")
    private String EpPic;
    /**
     * Ex
     */
    @SerializedName("Ex")
    private String Ex;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getTitleL() {
        return titleL;
    }

    public void setTitleL(String titleL) {
        this.titleL = titleL;
    }

    public String getPlayId() {
        return PlayId;
    }

    public void setPlayId(String PlayId) {
        this.PlayId = PlayId;
    }

    public String getPlayVid() {
        return PlayVid;
    }

    public void setPlayVid(String PlayVid) {
        this.PlayVid = PlayVid;
    }

    public String getEpName() {
        return EpName;
    }

    public void setEpName(String EpName) {
        this.EpName = EpName;
    }

    public String getEpPic() {
        return EpPic;
    }

    public void setEpPic(String EpPic) {
        this.EpPic = EpPic;
    }

    public String getEx() {
        return Ex;
    }

    public void setEx(String Ex) {
        this.Ex = Ex;
    }
}
