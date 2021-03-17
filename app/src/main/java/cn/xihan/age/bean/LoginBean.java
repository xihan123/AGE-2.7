package cn.xihan.age.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @项目名 : AGE动漫
 * @作者 : MissYang
 * @创建时间 : 2021/2/11 12:27
 * @介绍 :
 */
public class LoginBean {

    /**
     * Status : 200
     * Message : success
     * Username : fd1314
     * Sign_k : LJ841/mEN+/dG/bAt8gZ61sYijCa9q7WlwboE2ezUuM=
     * Sign_t : 1613017583
     */

    @SerializedName("Status")
    private Integer Status;
    /**
     * Message
     */
    @SerializedName("Message")
    private String  Message;
    /**
     * Username
     */
    @SerializedName("Username")
    private String  Username;
    /**
     * signK
     */
    @SerializedName("Sign_k")
    private String  signK;
    /**
     * signT
     */
    @SerializedName("Sign_t")
    private Integer signT;

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

    public String getUsername() {
        return Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

    public String getSignK() {
        return signK;
    }

    public void setSignK(String signK) {
        this.signK = signK;
    }

    public Integer getSignT() {
        return signT;
    }

    public void setSignT(Integer signT) {
        this.signT = signT;
    }
}
