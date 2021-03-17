package cn.xihan.age;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.multidex.MultiDex;

import com.qmuiteam.qmui.arch.QMUISwipeBackActivityManager;
import com.qmuiteam.qmui.skin.QMUISkinMaker;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.mmkv.MMKV;
import com.tencent.smtt.export.external.TbsCoreSettings;
import com.tencent.smtt.sdk.QbSdk;

import java.util.HashMap;

import cn.xihan.age.manager.QDSkinManager;
import cn.xihan.age.utils.Utils;

/**
 * @项目名 : AGE动漫
 * @作者 : MissYang
 * @创建时间 : 2021/3/10 10:38
 * @介绍 :
 */
public class MyApp extends Application {

    private static MyApp appContext;
    public static final boolean openSkinMake = false;

    public static MyApp getInstance() {
        return appContext;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initMMKV();
        initWebView();
        initQMUI();
        initTBS();
        initBugly();
//        initBmob();
        appContext = this;
        Utils.init(this);
    }

    private void initBugly() {
        Beta.initDelay = 5 * 1000;
        Beta.enableHotfix = true;
        Bugly.init(getApplicationContext(), "e86c1909ed", false);
    }

    //    private void initBmob() {
//        Bmob.initialize(this, "6b54823163a2aed5ec11f37f09119c2c");
//    }

    /**
     * 初始化x5内核
     */
    private void initTBS() {
        if (!android.os.Build.MODEL.contains("Pixel C")) {
            //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
            QbSdk.setDownloadWithoutWifi(true);//非wifi条件下允许下载X5内核
            QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
                @Override
                public void onViewInitFinished(boolean arg0) {
                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("data", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    try {
                        editor.putBoolean("X5State",arg0);
                        editor.apply();
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }

                @Override
                public void onCoreInitFinished() {
                }
            };
            //x5内核初始化接口
            QbSdk.initX5Environment(getApplicationContext(), cb);
            HashMap map = new HashMap();
            map.put(TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER, true);
            map.put(TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE, true);
            QbSdk.initTbsSettings(map);
    } }

    /**
     * QMUI
     */
    private void initQMUI() {
        QMUISwipeBackActivityManager.init(this);
        QDSkinManager.install(this);
        QMUISkinMaker.init(this,
                new String[]{"com.xihan.age"},
                new String[]{"app_skin_"}, R.attr.class);
    }

    private void initWebView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            String processName = getProcessName();
            if (!getPackageName().equals(processName)) {
                WebView.setDataDirectorySuffix(processName);
            }
        }
    }

    /**
     * 初始化MMKV
     */
    private void initMMKV() {
        String rootDir = MMKV.initialize(this);
        System.out.println("mmkv root: " + rootDir);
    }

    /**
     * QMUI
     */
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if ((newConfig.uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES) {
            QDSkinManager.changeSkin(QDSkinManager.SKIN_DARK);
        } else if (QDSkinManager.getCurrentSkin() == QDSkinManager.SKIN_DARK) {
            QDSkinManager.changeSkin(QDSkinManager.SKIN_BLUE);
        }
    }
}
