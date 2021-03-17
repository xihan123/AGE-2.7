package cn.xihan.age.model;


import cn.xihan.age.base.BaseFragment;

/**
 * @项目名 : AGE动漫
 * @作者 : MissYang
 * @创建时间 : 2021/2/21 14:19
 * @介绍 :
 */
public class QDItemDescription {
    private final Class<? extends BaseFragment> mKitDemoClass;
    private final String                        mKitName;
    private final int                           mIconRes;
    private final String                        mDocUrl;

    public QDItemDescription(Class<? extends BaseFragment> kitDemoClass, String kitName){
        this(kitDemoClass, kitName, 0, "");
    }


    public QDItemDescription(Class<? extends BaseFragment> kitDemoClass, String kitName, int iconRes, String docUrl) {
        mKitDemoClass = kitDemoClass;
        mKitName = kitName;
        mIconRes = iconRes;
        mDocUrl = docUrl;
    }

    public Class<? extends BaseFragment> getDemoClass() {
        return mKitDemoClass;
    }

    public String getName() {
        return mKitName;
    }

    public int getIconRes() {
        return mIconRes;
    }

    public String getDocUrl() {
        return mDocUrl;
    }
}

