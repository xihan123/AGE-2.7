package cn.xihan.age.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @项目名 : AGE动漫
 * @作者 : MissYang
 * @创建时间 : 2021/2/11 9:17
 * @介绍 :
 */
public class RankBean {


    /**
     * Year : 0
     * AniRankPre : [{"AID":"20000001","Href":"/detail/20000001","NewTitle":"12:00 第961话","PicSmall":"//sc02.alicdn.com/kf/H6eec4b52f5ed449b8583e5ab518e7849p.jpg","Title":"海贼王","CCnt":9205763,"NO":1}]
     * PageCtrl : null
     * AllCnt : 2857
     */

    @SerializedName("Year")
    private Integer             Year;
    /**
     * AniRankPre
     */
    @SerializedName("AniRankPre")
    private List<AniRankPreDTO> AniRankPre;
    /**
     * PageCtrl
     */
    @SerializedName("PageCtrl")
    private Object  PageCtrl;
    /**
     * AllCnt
     */
    @SerializedName("AllCnt")
    private Integer AllCnt;

    public Integer getYear() {
        return Year;
    }

    public void setYear(Integer Year) {
        this.Year = Year;
    }

    public List<AniRankPreDTO> getAniRankPre() {
        return AniRankPre;
    }

    public void setAniRankPre(List<AniRankPreDTO> AniRankPre) {
        this.AniRankPre = AniRankPre;
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

    public static class AniRankPreDTO {
        /**
         * AID : 20000001
         * Href : /detail/20000001
         * NewTitle : 12:00 第961话
         * PicSmall : //sc02.alicdn.com/kf/H6eec4b52f5ed449b8583e5ab518e7849p.jpg
         * Title : 海贼王
         * CCnt : 9205763
         * NO : 1
         */

        @SerializedName("AID")
        private String aid;
        /**
         * Href
         */
        @SerializedName("Href")
        private String Href;
        /**
         * NewTitle
         */
        @SerializedName("NewTitle")
        private String  NewTitle;
        /**
         * PicSmall
         */
        @SerializedName("PicSmall")
        private String  PicSmall;
        /**
         * Title
         */
        @SerializedName("Title")
        private String  Title;
        /**
         * CCnt
         */
        @SerializedName("CCnt")
        private Integer CCnt;
        /**
         * no
         */
        @SerializedName("NO")
        private Integer no;

        public String getAid() {
            return aid;
        }

        public void setAid(String aid) {
            this.aid = aid;
        }

        public String getHref() {
            return Href;
        }

        public void setHref(String Href) {
            this.Href = Href;
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

        public String getTitle() {
            return Title;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }

        public Integer getCCnt() {
            return CCnt;
        }

        public void setCCnt(Integer CCnt) {
            this.CCnt = CCnt;
        }

        public Integer getNo() {
            return no;
        }

        public void setNo(Integer no) {
            this.no = no;
        }
    }
}
