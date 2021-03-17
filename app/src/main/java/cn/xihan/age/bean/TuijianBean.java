package cn.xihan.age.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @项目名 : AGE动漫
 * @作者 : MissYang
 * @创建时间 : 2021/2/11 8:06
 * @介绍 :
 */
public class TuijianBean {


    /**
     * AniPre : [{"AID":"20160145","Title":"圣洁天使","NewTitle":"[TV 01-12]","PicSmall":"//sc02.alicdn.com/kf/H371c973d6be0480080f887dcb42b7b93L.jpg","Href":"/detail/20160145"}]
     * PageCtrl : null
     * AllCnt : 100
     * Tip : AGE动漫网 备用地址：<a rel="nofollow" target="_blank" href="http://www.agefans.cc" style="color: #60b8cc;text-decoration: none;">www.agefans.cc</a> 欢迎大家分享给身边朋友！为确保正常观看，请使用谷歌浏览器。
     */

    @SerializedName("AniPre")
    private List<AniPreDTO> AniPre;
    /**
     * PageCtrl
     */
    @SerializedName("PageCtrl")
    private Object          PageCtrl;
    /**
     * AllCnt
     */
    @SerializedName("AllCnt")
    private Integer AllCnt;
    /**
     * Tip
     */
    @SerializedName("Tip")
    private String  Tip;

    public List<AniPreDTO> getAniPre() {
        return AniPre;
    }

    public void setAniPre(List<AniPreDTO> AniPre) {
        this.AniPre = AniPre;
    }

    public Object getPageCtrl() {
        return PageCtrl;
    }

    public void setPageCtrl(Object PageCtrl) {
        this.PageCtrl = PageCtrl;
    }

    public Integer getAllCnt() {
        return AllCnt;
    }

    public void setAllCnt(Integer AllCnt) {
        this.AllCnt = AllCnt;
    }

    public String getTip() {
        return Tip;
    }

    public void setTip(String Tip) {
        this.Tip = Tip;
    }

    public static class AniPreDTO {
        /**
         * AID : 20160145
         * Title : 圣洁天使
         * NewTitle : [TV 01-12]
         * PicSmall : //sc02.alicdn.com/kf/H371c973d6be0480080f887dcb42b7b93L.jpg
         * Href : /detail/20160145
         */

        @SerializedName("AID")
        private String aid;
        /**
         * Title
         */
        @SerializedName("Title")
        private String Title;
        /**
         * NewTitle
         */
        @SerializedName("NewTitle")
        private String NewTitle;
        /**
         * PicSmall
         */
        @SerializedName("PicSmall")
        private String PicSmall;
        /**
         * Href
         */
        @SerializedName("Href")
        private String Href;

        public String getAid() {
            return aid;
        }

        public void setAid(String aid) {
            this.aid = aid;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }

        public String getNewTitle() {
            return NewTitle;
        }

        public void setNewTitle(String NewTitle) {
            this.NewTitle = NewTitle;
        }

        public String getPicSmall() {
            return PicSmall;
        }

        public void setPicSmall(String PicSmall) {
            this.PicSmall = PicSmall;
        }

        public String getHref() {
            return Href;
        }

        public void setHref(String Href) {
            this.Href = Href;
        }
    }
}
