package cn.xihan.age.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * @项目名 : AGE动漫
 * @作者 : MissYang
 * @创建时间 : 2021/2/11 22:28
 * @介绍 :
 */
public class ScBean  implements Serializable {


    /**
     * Status : 200
     * Message :
     * Data : {"List":[{"AID":"20170183","Time":1613053051,"UpTime":1517376689,"Title":"魔法科高校的劣等生剧场版 呼唤星辰的少女","NewTitle":"[全集]","PicSmall":"//sc02.alicdn.com/kf/Habb8f5352ac440d281d90470a024b59dG.jpg","Href":"/detail/20170183"},{"AID":"20170009","Time":1613053078,"UpTime":1497618756,"Title":"风夏","NewTitle":"[TV 01-12]","PicSmall":"//sc02.alicdn.com/kf/H37b8a4e1f65543e380131009551d0da82.jpg","Href":"/detail/20170009"}],"Allsize":2}
     */

    @SerializedName("Status")
    private Integer Status;
    /**
     * Message
     */
    @SerializedName("Message")
    private String  Message;
    /**
     * Data
     */
    @SerializedName("Data")
    private DataDTO Data;

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer Status) {
        this.Status = Status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public DataDTO getData() {
        return Data;
    }

    public void setData(DataDTO Data) {
        this.Data = Data;
    }

    public static class DataDTO {
        /**
         * List : [{"AID":"20170183","Time":1613053051,"UpTime":1517376689,"Title":"魔法科高校的劣等生剧场版 呼唤星辰的少女","NewTitle":"[全集]","PicSmall":"//sc02.alicdn.com/kf/Habb8f5352ac440d281d90470a024b59dG.jpg","Href":"/detail/20170183"},{"AID":"20170009","Time":1613053078,"UpTime":1497618756,"Title":"风夏","NewTitle":"[TV 01-12]","PicSmall":"//sc02.alicdn.com/kf/H37b8a4e1f65543e380131009551d0da82.jpg","Href":"/detail/20170009"}]
         * Allsize : 2
         */

        @SerializedName("List")
        private java.util.List<ListDTO> List;
        /**
         * Allsize
         */
        @SerializedName("Allsize")
        private Integer Allsize;

        public List<ListDTO> getList() {
            return List;
        }

        public void setList(List<ListDTO> List) {
            this.List = List;
        }

        public Integer getAllsize() {
            return Allsize;
        }

        public void setAllsize(Integer Allsize) {
            this.Allsize = Allsize;
        }

        public static class ListDTO {
            /**
             * AID : 20170183
             * Time : 1613053051
             * UpTime : 1517376689
             * Title : 魔法科高校的劣等生剧场版 呼唤星辰的少女
             * NewTitle : [全集]
             * PicSmall : //sc02.alicdn.com/kf/Habb8f5352ac440d281d90470a024b59dG.jpg
             * Href : /detail/20170183
             */

            @SerializedName("AID")
            private String aid;
            /**
             * Time
             */
            @SerializedName("Time")
            private Integer Time;
            /**
             * UpTime
             */
            @SerializedName("UpTime")
            private Integer UpTime;
            /**
             * Title
             */
            @SerializedName("Title")
            private String  Title;
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
             * Href
             */
            @SerializedName("Href")
            private String  Href;

            public String getAid() {
                return aid;
            }

            public void setAid(String aid) {
                this.aid = aid;
            }

            public Integer getTime() {
                return Time;
            }

            public void setTime(Integer Time) {
                this.Time = Time;
            }

            public Integer getUpTime() {
                return UpTime;
            }

            public void setUpTime(Integer UpTime) {
                this.UpTime = UpTime;
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
}
