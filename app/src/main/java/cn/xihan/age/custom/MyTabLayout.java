package cn.xihan.age.custom;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.tabs.TabLayout;

/**
 * @项目名 : AGE动漫
 * @作者 : MissYang
 * @创建时间 : 2021/2/21 15:06
 * @介绍 :
 */
public class MyTabLayout extends TabLayout {
    // 一屏显示多少个tab
    private static final int TabViewNumber = 7;
    // support 低版本可能不一样
    private static final String SCROLLABLE_TAB_MIN_WIDTH = "scrollableTabMinWidth";


    public MyTabLayout(@NonNull Context context) {
        super(context);
    }

    public MyTabLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTabLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


}

