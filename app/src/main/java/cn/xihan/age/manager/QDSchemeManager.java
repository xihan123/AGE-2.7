package cn.xihan.age.manager;

import android.util.Log;
import android.widget.Toast;

import com.qmuiteam.qmui.arch.QMUISwipeBackActivityManager;
import com.qmuiteam.qmui.arch.scheme.QMUISchemeHandler;
import com.qmuiteam.qmui.arch.scheme.QMUISchemeParamValueDecoder;

/**
 * @项目名 : AGE动漫
 * @作者 : MissYang
 * @创建时间 : 2021/2/21 15:24
 * @介绍 :
 */
public class QDSchemeManager {
    private static final String TAG = "QDSchemeManager";
    public static final String SCHEME_PREFIX = "qmui://";
    private static QDSchemeManager sInstance;

    public static QDSchemeManager getInstance() {
        if (sInstance == null) {
            sInstance = new QDSchemeManager();
        }
        return sInstance;
    }

    private final QMUISchemeHandler mSchemeHandler;

    private QDSchemeManager() {
        mSchemeHandler = new QMUISchemeHandler.Builder(SCHEME_PREFIX)
                .blockSameSchemeTimeout(1000)
                .addInterpolator((schemeHandler, activity, action, params, origin) -> {
                    // Log the scheme.
                    Log.i(TAG, "handle scheme: " + origin);
                    return false;
                })
                .addInterpolator(new QMUISchemeParamValueDecoder())
                .build();
    }

    public boolean handle(String scheme) {
        if (!mSchemeHandler.handle(scheme)) {
            Log.i(TAG, "scheme can not be handled: " + scheme);
            Toast.makeText(QMUISwipeBackActivityManager.getInstance().getCurrentActivity(),
                    "scheme can not be handled: " + scheme, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}

