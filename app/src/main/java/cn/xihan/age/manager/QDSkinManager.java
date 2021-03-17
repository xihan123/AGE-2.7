package cn.xihan.age.manager;

import android.content.Context;
import android.content.res.Configuration;

import com.qmuiteam.qmui.skin.QMUISkinManager;

import cn.xihan.age.MyApp;
import cn.xihan.age.R;


/**
 * @项目名 : AGE动漫
 * @作者 : MissYang
 * @创建时间 : 2021/2/21 14:05
 * @介绍 :
 */
public class QDSkinManager {
    public static final int SKIN_BLUE = 1;
    public static final int SKIN_DARK = 2;
    public static final int SKIN_WHITE = 3;


    public static void install(Context context) {
        QMUISkinManager skinManager = QMUISkinManager.defaultInstance(context);
        skinManager.addSkin(SKIN_BLUE, R.style.app_skin_blue);
        skinManager.addSkin(SKIN_DARK, R.style.app_skin_dark);
        skinManager.addSkin(SKIN_WHITE, R.style.app_skin_white);
        boolean isDarkMode = (context.getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES;
        int storeSkinIndex = QDPreferenceManager.getInstance(context).getSkinIndex();
        if (isDarkMode && storeSkinIndex != SKIN_DARK) {
            skinManager.changeSkin(SKIN_DARK);
        } else if (!isDarkMode && storeSkinIndex == SKIN_DARK) {
            skinManager.changeSkin(SKIN_BLUE);
        }else{
            skinManager.changeSkin(storeSkinIndex);
        }
    }

    public static void changeSkin(int index) {
        QMUISkinManager.defaultInstance(MyApp.getInstance()).changeSkin(index);
        QDPreferenceManager.getInstance(MyApp.getInstance()).setSkinIndex(index);
    }

    public static int getCurrentSkin() {
        return QMUISkinManager.defaultInstance(MyApp.getInstance()).getCurrentSkin();
    }
}

