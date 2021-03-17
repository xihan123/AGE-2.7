package cn.xihan.age.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @项目名 : AGE动漫
 * @作者 : MissYang
 * @创建时间 : 2021/2/9 17:54
 * @介绍 :
 */

public class SearchBean {


    /**
     * AniPreL : [{"AID":"20200320","R动画名称":"BEM ～BECOME HUMAN～","R动画种类":"剧场版","R原版名称":"劇場版 BEM ～BECOME HUMAN～","R其他名称":"暂无","R首播时间":"2020-10-02","R播放状态":"完结","R原作":"ADKエモーションズ","R剧情类型":["奇幻","战斗","社会","悬疑"],"R制作公司":"Production I.G","R简介":"《BEM ～BECOME HUMAN～》是由Production I.G负责制作的剧场版动画作品。\r\nリブラシティの事件から2年。\r\n行方不明となったベムたち妖怪人間の行方を探し続けていた刑事・ソニアは、目撃情報を元に「ドラコ・ケミカル」という製薬会社を訪れる。\r\nそこで出会ったのは、逞しく成長し、たった一人で悪と戦い続けるベロ、戦いを拒み、普通の女の子としての暮らしを望むベラ、そして、ベムにそっくりな姿の\"ベルム\"という男だった・・・。\r\nベルムは、自分のことをベムと呼ぶソニアとの接触以来、毎夜、悪夢にうなされることになる。\r\n愛する妻と子どもに囲まれた暖かい家庭。優しい街の人々とのいつもの交流。気の置けない同僚と、やりがいのある仕事。\r\nそんな、自分にとってごく普通の、変わらないはずの日常が、音を立てて崩れ落ちていく\u2015。\r\n「俺は一体、誰なんだ・・・？」\r\n彼らの運命はすれ違い、交錯し、やがて妖怪人間誕生の秘密へと繋がる。\r\n長年抱いていた夢の答え。果たして妖怪人間が選び取った未来とは・・・！？","R封面图小":"//sc02.alicdn.com/kf/H9ff191c7756f4b598e972e300e8ae5e7O.jpg","R新番标题":"[全集]"},{"AID":"20190186","R动画名称":"BEM","R动画种类":"TV","R原版名称":"BEM","R其他名称":"妖怪人贝姆","R首播时间":"2019-07-14","R播放状态":"完结","R原作":"ADK Emotions","R剧情类型":["奇幻","战斗","社会","悬疑"],"R制作公司":"LandQ studios","R简介":"电视动画《BEM》改编自1968年至1969年播出的电视动画《妖怪人贝姆》，作为前作的五十周年纪念而推出，于2019年2月8日宣布了制作决定的消息。动画由LandQ studio负责制作，于2019年7月7日起每周日在东京电视台、爱知电视台首播。\r\n港湾都市\u201c里布拉市\u201d。作为政治经济文化中心将城市中的\u201c富\u201d集结起来的\u201c上城\u201d，以及充斥着渎职和犯罪、人们不得不互相猜疑的\u201c外围\u201d，分隔这两个区域的巨大运河以及一座\u201c桥\u201d。由这些所构成，正可谓是将人类的\u201c差别意识\u201d表面化的城市。\r\n从\u201c上城\u201d前往\u201c外围\u201d赴任的年轻女刑警·索妮娅，在追查种种事件的过程中，遇到了为保护人类而战的三个丑陋的人。他们究竟是什么人\u2026\u2026？\r\n这三个\u201c妖怪人\u201d，各怀心思地隐藏真身生活着。希望通过铲除为害人间的\u201c恶\u201d而成为人类的\u201c贝姆\u201d；憧憬人类，希望通过和人类上同样的学校来理解人类，以成为人类为目标的\u201c贝拉\u201d；看透人类和世间，冷淡地沉浸于游戏世界的\u201c贝罗\u201d。他们在各种各样的事件中，以及与人类相互接触的过程中，不断受伤，不断烦恼。即使为了人类而战，但他们丑陋的样貌决不可能被人类所接纳\u2026\u2026。\r\n有一个人正寻找着这样的三人。操纵着暗中控制里布拉市的\u201c看不见的议会\u201d、企图掌控贝姆等三人的她，究竟有何目的？","R封面图小":"//sc02.alicdn.com/kf/H83c57663064f4b47babbe67f4a450c9dI.jpg","R新番标题":"[TV 01-12]"}]
     * PageCtrl : [{"Title":"首页","Url":"/search?query=bem&page=0","Index":-1},{"Title":"1","Url":"","Index":-1},{"Title":"尾页","Url":"/search?query=bem&page=0","Index":-1}]
     * KeyWord : bem
     * SeaCnt : 2
     * Tip : AGE动漫网 备用地址：<a rel="nofollow" target="_blank" href="http://www.agefans.cc" style="color: #60b8cc;text-decoration: none;">www.agefans.cc</a> 欢迎大家分享给身边朋友！为确保正常观看，请使用谷歌浏览器。
     */

    @SerializedName("AniPreL")
    private List<AniPreLDTO> AniPreL;
    @SerializedName("PageCtrl")
    private List<PageCtrlDTO> PageCtrl;
    @SerializedName("KeyWord")
    private String            KeyWord;
    @SerializedName("SeaCnt")
    private Integer           SeaCnt;
    @SerializedName("Tip")
    private String            Tip;


    public static class AniPreLDTO {
        /**
         * AID : 20200320
         * R动画名称 : BEM ～BECOME HUMAN～
         * R动画种类 : 剧场版
         * R原版名称 : 劇場版 BEM ～BECOME HUMAN～
         * R其他名称 : 暂无
         * R首播时间 : 2020-10-02
         * R播放状态 : 完结
         * R原作 : ADKエモーションズ
         * R剧情类型 : ["奇幻","战斗","社会","悬疑"]
         * R制作公司 : Production I.G
         * R简介 : 《BEM ～BECOME HUMAN～》是由Production I.G负责制作的剧场版动画作品。
         リブラシティの事件から2年。
         行方不明となったベムたち妖怪人間の行方を探し続けていた刑事・ソニアは、目撃情報を元に「ドラコ・ケミカル」という製薬会社を訪れる。
         そこで出会ったのは、逞しく成長し、たった一人で悪と戦い続けるベロ、戦いを拒み、普通の女の子としての暮らしを望むベラ、そして、ベムにそっくりな姿の"ベルム"という男だった・・・。
         ベルムは、自分のことをベムと呼ぶソニアとの接触以来、毎夜、悪夢にうなされることになる。
         愛する妻と子どもに囲まれた暖かい家庭。優しい街の人々とのいつもの交流。気の置けない同僚と、やりがいのある仕事。
         そんな、自分にとってごく普通の、変わらないはずの日常が、音を立てて崩れ落ちていく―。
         「俺は一体、誰なんだ・・・？」
         彼らの運命はすれ違い、交錯し、やがて妖怪人間誕生の秘密へと繋がる。
         長年抱いていた夢の答え。果たして妖怪人間が選び取った未来とは・・・！？
         * R封面图小 : //sc02.alicdn.com/kf/H9ff191c7756f4b598e972e300e8ae5e7O.jpg
         * R新番标题 : [全集]
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


        public void setR首播时间(String r首播时间) {
            this.r首播时间 = r首播时间;
        }

        public String getAid() {
            return aid;
        }

        public void setAid(String aid) {
            this.aid = aid;
        }

        public String getR简介() {
            return r简介;
        }

        public void setR新番标题(String r新番标题) {
            this.r新番标题 = r新番标题;
        }

        public void setR播放状态(String r播放状态) {
            this.r播放状态 = r播放状态;
        }

        public void setR原版名称(String r原版名称) {
            this.r原版名称 = r原版名称;
        }

        public void setR原作(String r原作) {
            this.r原作 = r原作;
        }

        public void setR动画种类(String r动画种类) {
            this.r动画种类 = r动画种类;
        }

        public void setR动画名称(String r动画名称) {
            this.r动画名称 = r动画名称;
        }

        public void setR剧情类型(List<String> r剧情类型) {
            this.r剧情类型 = r剧情类型;
        }

        public void setR制作公司(String r制作公司) {
            this.r制作公司 = r制作公司;
        }

        public String getR新番标题() {
            return r新番标题;
        }

        public String getR首播时间() {
            return r首播时间;
        }

        public String getR原版名称() {
            return r原版名称;
        }

        public String getR播放状态() {
            return r播放状态;
        }

        public String getR原作() {
            return r原作;
        }

        public String getR动画名称() {
            return r动画名称;
        }

        public String getR制作公司() {
            return r制作公司;
        }

        public String getR动画种类() {
            return r动画种类;
        }

        public List<String> getR剧情类型() {
            return r剧情类型;
        }

        public String getR其他名称() {
            return r其他名称;
        }

        public String getR封面图小() {
            return r封面图小;
        }

        public void setR其他名称(String r其他名称) {
            this.r其他名称 = r其他名称;
        }

        public void setR封面图小(String r封面图小) {
            this.r封面图小 = r封面图小;
        }

        public void setR简介(String r简介) {
            this.r简介 = r简介;
        }
    }

    public static class PageCtrlDTO {
        /**
         * Title : 首页
         * Url : /search?query=bem&page=0
         * Index : -1
         */

        @SerializedName("Title")
        private String Title;
        @SerializedName("Url")
        private String  Url;
        @SerializedName("Index")
        private Integer Index;

        public String getTitle() {
            return Title;
        }

        public void setTitle(String title) {
            Title = title;
        }

        public void setUrl(String url) {
            Url = url;
        }

        public String getUrl() {
            return Url;
        }

        public Integer getIndex() {
            return Index;
        }

        public void setIndex(Integer index) {
            Index = index;
        }
    }


    public String getKeyWord() {
        return KeyWord;
    }

    public void setKeyWord(String keyWord) {
        KeyWord = keyWord;
    }

    public Integer getSeaCnt() {
        return SeaCnt;
    }

    public void setSeaCnt(Integer seaCnt) {
        SeaCnt = seaCnt;
    }

}
