package cn.xihan.age.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @项目名 : AGE动漫
 * @作者 : MissYang
 * @创建时间 : 2021/2/25 21:23
 * @介绍 :
 */
public class CommentsBean {

    /**
     * AllCnt : 145
     * comments : [{"sid":509365,"content":"看不了了那个→→","time":1614249843,"username":"游客11101402490233","cid":"20210034","ip":"111.14.249.233"},{"sid":509249,"content":"字幕君行家呀","time":1614246944,"username":"黑塊","cid":"20210034","ip":"221.220.202.241"},{"sid":508480,"content":"按套路肯定要第二发","time":1614226788,"username":"闪光之雷","cid":"20210034","ip":"59.56.179.18"},{"sid":508074,"content":"逢田姐大招是自爆，这波是刀啊","time":1614210798,"username":"游客140214014049","cid":"20210034","ip":"14.214.14.49"},{"sid":507890,"content":"\u2026\u2026拜托，只求女主这次能一发入魂\u2026\u2026","time":1614192202,"username":"游客183022501080105","cid":"20210034","ip":"183.225.108.105"},{"sid":507837,"content":"！！！！吊人胃口！！！！！！","time":1614189923,"username":"游客3603701310238","cid":"20210034","ip":"36.37.131.238"},{"sid":492581,"content":"哈哈哈哈哈哈哈哈哈哈，吃胖了笑死(｡>∀<｡)","time":1613721173,"username":"不笑猫君","cid":"20210034","ip":"223.104.210.129"}]
     * PageCtrl : [{"Title":"首页","Url":"/get_comments?cid=20210034&pageindex=-1","Index":-1},{"Title":"1","Url":"","Index":-1},{"Title":"2","Url":"/get_comments?cid=20210034&pageindex=1","Index":1},{"Title":"3","Url":"/get_comments?cid=20210034&pageindex=2","Index":2},{"Title":"4","Url":"/get_comments?cid=20210034&pageindex=3","Index":3},{"Title":"5","Url":"/get_comments?cid=20210034&pageindex=4","Index":4},{"Title":"6","Url":"/get_comments?cid=20210034&pageindex=5","Index":5},{"Title":"下一页","Url":"/get_comments?cid=20210034&pageindex=1","Index":1},{"Title":"尾页","Url":"/get_comments?cid=20210034&pageindex=20","Index":20}]
     * html_pageurls :
     <li><a class="pbutton pbutton_current asciifont" href="javascript:void(0)">首页</a></li>

     <li><a class="pbutton pbutton_current asciifont" href="javascript:void(0)">1</a></li>

     <li><a class="pbutton asciifont" href="javascript:void(0)" onclick="__age_show_comments_page(1)">2</a></li>

     <li><a class="pbutton asciifont" href="javascript:void(0)" onclick="__age_show_comments_page(2)">3</a></li>

     <li><a class="pbutton asciifont" href="javascript:void(0)" onclick="__age_show_comments_page(3)">4</a></li>

     <li><a class="pbutton asciifont" href="javascript:void(0)" onclick="__age_show_comments_page(4)">5</a></li>

     <li><a class="pbutton asciifont" href="javascript:void(0)" onclick="__age_show_comments_page(5)">6</a></li>

     <li><a class="pbutton asciifont" href="javascript:void(0)" onclick="__age_show_comments_page(1)">下一页</a></li>

     <li><a class="pbutton asciifont" href="javascript:void(0)" onclick="__age_show_comments_page(20)">尾页</a></li>

     */

    @SerializedName("AllCnt")
    private Integer           AllCnt;
    /**
     * comments
     */
    @SerializedName("comments")
    private List<CommentsDTO> comments;
    /**
     * PageCtrl
     */
    @SerializedName("PageCtrl")
    private List<PageCtrlDTO> PageCtrl;
    /**
     * htmlPageurls
     */
    @SerializedName("html_pageurls")
    private String            htmlPageurls;

    public Integer getAllCnt() {
        return AllCnt;
    }

    public void setAllCnt(Integer AllCnt) {
        this.AllCnt = AllCnt;
    }

    public List<CommentsDTO> getComments() {
        return comments;
    }

    public void setComments(List<CommentsDTO> comments) {
        this.comments = comments;
    }

    public List<PageCtrlDTO> getPageCtrl() {
        return PageCtrl;
    }

    public void setPageCtrl(List<PageCtrlDTO> PageCtrl) {
        this.PageCtrl = PageCtrl;
    }

    public String getHtmlPageurls() {
        return htmlPageurls;
    }

    public void setHtmlPageurls(String htmlPageurls) {
        this.htmlPageurls = htmlPageurls;
    }

    public static class CommentsDTO {


        /**
         * sid : 509365
         * content : 看不了了那个→→
         * time : 1614249843
         * username : 游客11101402490233
         * cid : 20210034
         * ip : 111.14.249.233
         */

        @SerializedName("sid")
        private Integer sid;
        /**
         * content
         */
        @SerializedName("content")
        private String content;
        /**
         * time
         */
        @SerializedName("time")
        private String time;
        /**
         * username
         */
        @SerializedName("username")
        private String  username;
        /**
         * cid
         */
        @SerializedName("cid")
        private String  cid;
        /**
         * ip
         */
        @SerializedName("ip")
        private String  ip;

        public Integer getSid() {
            return sid;
        }

        public void setSid(Integer sid) {
            this.sid = sid;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }
    }

    public static class PageCtrlDTO {
        /**
         * Title : 首页
         * Url : /get_comments?cid=20210034&pageindex=-1
         * Index : -1
         */

        @SerializedName("Title")
        private String Title;
        /**
         * Url
         */
        @SerializedName("Url")
        private String Url;
        /**
         * Index
         */
        @SerializedName("Index")
        private Integer Index;

        public String getTitle() {
            return Title;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }

        public String getUrl() {
            return Url;
        }

        public void setUrl(String Url) {
            this.Url = Url;
        }

        public Integer getIndex() {
            return Index;
        }

        public void setIndex(Integer Index) {
            this.Index = Index;
        }
    }
}
