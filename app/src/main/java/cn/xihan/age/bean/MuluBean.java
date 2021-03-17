package cn.xihan.age.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @项目名 : AGE动漫
 * @作者 : MissYang
 * @创建时间 : 2021/2/10 10:50
 * @介绍 :
 */

public class MuluBean {

    /**
     * Labels_region : ["region","地区","全部","日本","中国","欧美"]
     * Labels_genre : ["genre","版本","全部","TV","剧场版","OVA"]
     * Labels_letter : ["letter","首字母","全部","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"]
     * Labels_year : ["year","年份","全部","2021","2020","2019","2018","2017","2016","2015","2014","2013","2012","2011","2010","2009","2008","2007","2006","2005","2004","2003","2002","2001","2000"]
     * Labels_season : ["season","季度","全部","1","4","7","10"]
     * Labels_status : ["status","状态","全部","连载","完结","未播放"]
     * Labels_label : ["label","类型","全部","搞笑","运动","励志","热血","战斗","竞技","校园","青春","爱情","冒险","后宫","百合","治愈","萝莉","魔法","悬疑","推理","奇幻","科幻","游戏","神魔","恐怖","血腥","机战","战争","犯罪","历史","社会","职场","剧情","伪娘","耽美","童年","教育","亲子","真人","歌舞","肉番","美少女","轻小说","吸血鬼","女性向","泡面番","欢乐向"]
     * Labels_resource : ["resource","资源","全部","BDRIP","AGERIP"]
     * Labels_order : ["order","排序","更新时间","名称","点击量"]
     * AniPreL : [{"AID":"20210054","R动画名称":"世界魔女 起飞！","R动画种类":"TV","R原版名称":"ワールドウィッチーズ 発進しますっ！","R其他名称":"世界魔女出发","R首播时间":"2021-01-12","R播放状态":"连载","R原作":"島田フミカネ","R剧情类型":["搞笑","百合","科幻","奇幻"],"R制作公司":"Acca effe / 戯画プロダクション","R简介":"电视动画《世界魔女起飞！》是由Acca effe 戯画プロダクション负责制作，2021年放送予定。\r\n讲述了为了对抗突然出现在世界各地的人类之敌《NEUROI》，世界各国组成联合军队，然而，能够与敌人对抗的只有其中有着特殊魔法之力的少女们！合同501部队，勇敢的502巫女部队展开激烈大冒险，拯救世界。","R封面图小":"//sc02.alicdn.com/kf/H929ecdfac2354e89bb539fa3f43a76ff6.jpg","R新番标题":"00:45 第5话"}]
     * PageCtrl : null
     * WebTitle : 全部动漫
     * AllCnt : 2855
     * Tip : AGE动漫网 备用地址：<a rel="nofollow" target="_blank" href="http://www.agefans.cc" style="color: #60b8cc;text-decoration: none;">www.agefans.cc</a> 欢迎大家分享给身边朋友！为确保正常观看，请使用谷歌浏览器。
     */

    @SerializedName("Labels_region")
    private List<String> labelsRegion;
    @SerializedName("Labels_genre")
    private List<String>     labelsGenre;
    @SerializedName("Labels_letter")
    private List<String>     labelsLetter;
    @SerializedName("Labels_year")
    private List<String>     labelsYear;
    @SerializedName("Labels_season")
    private List<String>     labelsSeason;
    @SerializedName("Labels_status")
    private List<String>     labelsStatus;
    @SerializedName("Labels_label")
    private List<String>     labelsLabel;
    @SerializedName("Labels_resource")
    private List<String>     labelsResource;
    @SerializedName("Labels_order")
    private List<String>     labelsOrder;
    @SerializedName("AniPreL")
    private List<AniPreLDTO> AniPreL;
    @SerializedName("PageCtrl")
    private Object           PageCtrl;
    @SerializedName("WebTitle")
    private String           WebTitle;
    @SerializedName("AllCnt")
    private Integer          AllCnt;
    @SerializedName("Tip")
    private String           Tip;


    public void setLabelsRegion(List<String> labelsRegion) {
        this.labelsRegion = labelsRegion;
    }

    public List<AniPreLDTO> getAniPreL() {
        return AniPreL;
    }

    public void setTip(String tip) {
        Tip = tip;
    }

    public String getTip() {
        return Tip;
    }

    public Integer getAllCnt() {
        return AllCnt;
    }

    public List<String> getLabelsGenre() {
        return labelsGenre;
    }

    public List<String> getLabelsLabel() {
        return labelsLabel;
    }

    public List<String> getLabelsOrder() {
        return labelsOrder;
    }

    public List<String> getLabelsLetter() {
        return labelsLetter;
    }

    public List<String> getLabelsRegion() {
        return labelsRegion;
    }

    public List<String> getLabelsResource() {
        return labelsResource;
    }

    public List<String> getLabelsSeason() {
        return labelsSeason;
    }

    public List<String> getLabelsStatus() {
        return labelsStatus;
    }

    public List<String> getLabelsYear() {
        return labelsYear;
    }

    public Object getPageCtrl() {
        return PageCtrl;
    }

    public String getWebTitle() {
        return WebTitle;
    }

    public void setAllCnt(Integer allCnt) {
        AllCnt = allCnt;
    }

    public void setAniPreL(List<AniPreLDTO> aniPreL) {
        AniPreL = aniPreL;
    }

    public void setLabelsGenre(List<String> labelsGenre) {
        this.labelsGenre = labelsGenre;
    }

    public void setLabelsLabel(List<String> labelsLabel) {
        this.labelsLabel = labelsLabel;
    }

    public void setLabelsLetter(List<String> labelsLetter) {
        this.labelsLetter = labelsLetter;
    }

    public void setLabelsOrder(List<String> labelsOrder) {
        this.labelsOrder = labelsOrder;
    }

    public void setLabelsResource(List<String> labelsResource) {
        this.labelsResource = labelsResource;
    }

    public void setLabelsSeason(List<String> labelsSeason) {
        this.labelsSeason = labelsSeason;
    }

    public void setLabelsStatus(List<String> labelsStatus) {
        this.labelsStatus = labelsStatus;
    }

    public void setLabelsYear(List<String> labelsYear) {
        this.labelsYear = labelsYear;
    }

    public void setPageCtrl(Object pageCtrl) {
        PageCtrl = pageCtrl;
    }

    public void setWebTitle(String webTitle) {
        WebTitle = webTitle;
    }

    @Override
    public String toString() {
        return "MuluBean{" +
                "labelsRegion=" + labelsRegion +
                ", labelsGenre=" + labelsGenre +
                ", labelsLetter=" + labelsLetter +
                ", labelsYear=" + labelsYear +
                ", labelsSeason=" + labelsSeason +
                ", labelsStatus=" + labelsStatus +
                ", labelsLabel=" + labelsLabel +
                ", labelsResource=" + labelsResource +
                ", labelsOrder=" + labelsOrder +
                ", AniPreL=" + AniPreL +
                ", PageCtrl=" + PageCtrl +
                ", WebTitle='" + WebTitle + '\'' +
                ", AllCnt=" + AllCnt +
                ", Tip='" + Tip + '\'' +
                '}';
    }

    public static class AniPreLDTO  {
        /**
         * AID : 20210054
         * R动画名称 : 世界魔女 起飞！
         * R动画种类 : TV
         * R原版名称 : ワールドウィッチーズ 発進しますっ！
         * R其他名称 : 世界魔女出发
         * R首播时间 : 2021-01-12
         * R播放状态 : 连载
         * R原作 : 島田フミカネ
         * R剧情类型 : ["搞笑","百合","科幻","奇幻"]
         * R制作公司 : Acca effe / 戯画プロダクション
         * R简介 : 电视动画《世界魔女起飞！》是由Acca effe 戯画プロダクション负责制作，2021年放送予定。
         讲述了为了对抗突然出现在世界各地的人类之敌《NEUROI》，世界各国组成联合军队，然而，能够与敌人对抗的只有其中有着特殊魔法之力的少女们！合同501部队，勇敢的502巫女部队展开激烈大冒险，拯救世界。
         * R封面图小 : //sc02.alicdn.com/kf/H929ecdfac2354e89bb539fa3f43a76ff6.jpg
         * R新番标题 : 00:45 第5话
         */

        @SerializedName("AID")
        private String aid;
        @SerializedName("R动画名称")
        private String       r动画名称;
        @SerializedName("R动画种类")
        private String       r动画种类;
        @SerializedName("R原版名称")
        private String       r原版名称;
        @SerializedName("R其他名称")
        private String       r其他名称;
        @SerializedName("R首播时间")
        private String       r首播时间;
        @SerializedName("R播放状态")
        private String       r播放状态;
        @SerializedName("R原作")
        private String       r原作;
        @SerializedName("R剧情类型")
        private List<String> r剧情类型;
        @SerializedName("R制作公司")
        private String       r制作公司;
        @SerializedName("R简介")
        private String       r简介;
        @SerializedName("R封面图小")
        private String       r封面图小;
        @SerializedName("R新番标题")
        private String       r新番标题;

        public String getAid() {
            return aid;
        }

        public void setAid(String aid) {
            this.aid = aid;
        }

        public void setR简介(String r简介) {
            this.r简介 = r简介;
        }

        public void setR封面图小(String r封面图小) {
            this.r封面图小 = r封面图小;
        }

        public void setR其他名称(String r其他名称) {
            this.r其他名称 = r其他名称;
        }

        public String getR封面图小() {
            return r封面图小;
        }

        public String getR其他名称() {
            return r其他名称;
        }

        public List<String> getR剧情类型() {
            return r剧情类型;
        }

        public String getR动画种类() {
            return r动画种类;
        }

        public String getR动画名称() {
            return r动画名称;
        }

        public String getR制作公司() {
            return r制作公司;
        }

        public String getR原作() {
            return r原作;
        }

        public String getR播放状态() {
            return r播放状态;
        }

        public String getR原版名称() {
            return r原版名称;
        }

        public String getR首播时间() {
            return r首播时间;
        }

        public String getR新番标题() {
            return r新番标题;
        }

        public void setR制作公司(String r制作公司) {
            this.r制作公司 = r制作公司;
        }

        public void setR剧情类型(List<String> r剧情类型) {
            this.r剧情类型 = r剧情类型;
        }

        public void setR动画名称(String r动画名称) {
            this.r动画名称 = r动画名称;
        }

        public void setR动画种类(String r动画种类) {
            this.r动画种类 = r动画种类;
        }

        public void setR原作(String r原作) {
            this.r原作 = r原作;
        }

        public void setR原版名称(String r原版名称) {
            this.r原版名称 = r原版名称;
        }

        public void setR播放状态(String r播放状态) {
            this.r播放状态 = r播放状态;
        }

        public void setR新番标题(String r新番标题) {
            this.r新番标题 = r新番标题;
        }

        public String getR简介() {
            return r简介;
        }

        public void setR首播时间(String r首播时间) {
            this.r首播时间 = r首播时间;
        }
    }
}
