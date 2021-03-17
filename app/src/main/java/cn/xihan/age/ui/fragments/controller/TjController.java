package cn.xihan.age.ui.fragments.controller;

import android.content.Context;

import cn.xihan.age.manager.QDDataManager;


/**
 * @项目名 : AGE动漫
 * @作者 : MissYang
 * @创建时间 : 2021/2/21 16:26
 * @介绍 :
 */
 public class TjController extends HomeController{

    public TjController(Context context) {
        super(context);
    }

    @Override
    protected String getTitle() {
        return "推荐";
    }

    @Override
    protected ItemAdapter getItemAdapter() {
        return new ItemAdapter(getContext(), QDDataManager.getInstance().getComponentsDescriptions());
    }
}
