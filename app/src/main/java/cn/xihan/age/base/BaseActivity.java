package cn.xihan.age.base;

import android.annotation.SuppressLint;

import com.qmuiteam.qmui.arch.QMUIActivity;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;

import static cn.xihan.age.utils.Utils.getContext;

/**
 * @项目名 : AGE动漫
 * @作者 : MissYang
 * @创建时间 : 2021/2/21 14:11
 * @介绍 :
 */
@SuppressWarnings("deprecation")
@SuppressLint("Registered")
public class BaseActivity extends QMUIActivity {

    @Override
    protected int backViewInitOffset() {
        return QMUIDisplayHelper.dp2px(getContext(), 100);
    }



}

