package cn.xihan.age.bean;

/**
 * @项目名 : AGE动漫
 * @作者 : MissYang
 * @创建时间 : 2021/2/21 15:59
 * @介绍 :
 */
public class XinfanInfoBean {

    /**
     * isnew : false
     * id : 20200087
     * wd : 1
     * name : ReBIRTH
     * mtime : 2020-09-22 12:46:08
     * namefornew : 第24话
     */

    @com.google.gson.annotations.SerializedName("isnew")
    private Boolean isnew;
    @com.google.gson.annotations.SerializedName("id")
    private String  id;
    @com.google.gson.annotations.SerializedName("wd")
    private Integer wd;
    @com.google.gson.annotations.SerializedName("name")
    private String  name;
    @com.google.gson.annotations.SerializedName("mtime")
    private String  mtime;
    @com.google.gson.annotations.SerializedName("namefornew")
    private String  namefornew;

    public void setWd(Integer wd) {
        this.wd = wd;
    }

    public void setNamefornew(String namefornew) {
        this.namefornew = namefornew;
    }

    public void setMtime(String mtime) {
        this.mtime = mtime;
    }

    public void setIsnew(Boolean isnew) {
        this.isnew = isnew;
    }

    public String getNamefornew() {
        return namefornew;
    }

    public String getMtime() {
        return mtime;
    }

    public Integer getWd() {
        return wd;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsnew() {
        return isnew;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
}

