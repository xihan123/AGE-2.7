package cn.xihan.age.model;

import com.qmuiteam.qmui.arch.effect.Effect;

/**
 * @项目名 : AGE动漫
 * @作者 : MissYang
 * @创建时间 : 2021/2/21 14:19
 * @介绍 :
 */
public class CustomEffect extends Effect {
    private final String mContent;

    public CustomEffect(String content){
        mContent = content;
    }

    public String getContent() {
        return mContent;
    }
}

