package cn.xihan.age.manager;


import java.util.Map;

import cn.xihan.age.base.BaseFragment;
import cn.xihan.age.model.QDItemDescription;

/**
 * @项目名 : AGE动漫
 * @作者 : MissYang
 * @创建时间 : 2021/2/21 14:44
 * @介绍 :
 */
public class QDWidgetContainer {
    private static final QDWidgetContainer sInstance = new QDWidgetContainer();

    private Map<Class<? extends BaseFragment>, QDItemDescription> mWidgets;

    public static QDWidgetContainer getInstance() {
        return sInstance;
    }

    public QDItemDescription get(Class<? extends BaseFragment> fragment) {
        return mWidgets.get(fragment);
    }
}
