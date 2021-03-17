package cn.xihan.age.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.StringRes;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cn.xihan.age.BuildConfig;
import cn.xihan.age.R;

import static com.blankj.utilcode.util.ActivityUtils.startActivity;

/**
 * @项目名 : AGE动漫
 * @作者 : MissYang
 * @创建时间 : 2021/3/10 10:40
 * @介绍 :
 */
public class Utils {
    private static Context context;

    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    public static void init(Context context) {
        Utils.context = context.getApplicationContext();
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getContext() {
        if (context != null) return context;
        throw new NullPointerException("u should init first");
    }

    public static void creatFile() {
        File dataDir = new File(Environment.getExternalStorageDirectory() + "/Android/data/cn.xihan.age/Database/");
        if (!dataDir.exists())
            dataDir.mkdirs();
    }

    public static  void  creatDownloadFile(){
        File dataDir = new File(Environment.getExternalStorageDirectory() + "/Android/data/cn.xihan.age/downloads/");
        if (!dataDir.exists())
            dataDir.mkdirs();
    }

    // 两次点击按钮之间的点击间隔不能少于500毫秒
    private static final int MIN_CLICK_DELAY_TIME = 500;
    private static long lastClickTime;

    public static boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }


    /**
     * 下载进度条
     *
     * @param context
     * @return
     */
    public static ProgressDialog showProgressDialog(Context context) {
        final ProgressDialog pd = new ProgressDialog(context);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);// 设置水平进度条
        pd.setCancelable(false);// 设置是否可以通过点击Back键取消
        pd.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
        pd.setMax(100);
        pd.setMessage("下载...");
        return pd;
    }

    /**
     * 关闭加载框
     *
     * @param p
     */
    public static void cancelProDialog(ProgressDialog p) {
        if (p != null)
            p.dismiss();
    }

    /**
     * 设置默认图片
     *
     * @param url
     */
    public static void setDefaultImage(Context context, String url, ImageView imageView) {
        DrawableCrossFadeFactory drawableCrossFadeFactory = new DrawableCrossFadeFactory.Builder(300).setCrossFadeEnabled(true).build();
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .format(DecodeFormat.PREFER_RGB_565)
                .placeholder(R.drawable.loading)
                .error(R.drawable.error);
        Glide.with(context)
                .load(url)
                .transition(DrawableTransitionOptions.withCrossFade(drawableCrossFadeFactory))
                .apply(options)
                .into(imageView);
    }



    /**
     * 安装应用
     *
     * @param activity
     */
    public static void startInstall(Activity activity) {
        //权限不存在，申请权限，并跳到当前包
        if (!isGranted(activity)) {
            Uri packageURI = Uri.parse("package:" + activity.getPackageName());
            Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
            activity.startActivityForResult(intent, 10001);
        } else {
            install(activity);
        }
    }

    /**
     * 安装应用
     *
     * @param activity
     */
    private static void install(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //android 7.0权限问题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(activity, BuildConfig.APPLICATION_ID + ".fileProvider", new File(Environment.getExternalStorageDirectory(), "base.apk"));
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "base.apk")), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        activity.startActivity(intent);
    }

    /**
     * 判断是否为Android O+
     *
     * @param activity
     * @return
     */
    private static boolean isGranted(Activity activity) {
        // 8.0 权限 安装apk 权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return activity.getPackageManager().canRequestPackageInstalls();
        }
        return true;
    }



    /**
     * 获取状态栏高度
     * @return
     */
    public static int getStatusBarHeight() {
        int statusBarHeight = 20;
        int resourceId = getContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getContext().getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    /**
     * X5内核加载状态
     * @return
     */
    public static boolean getX5State() {
        return (boolean) SharedPreferencesUtils.getParam(getContext(), "X5State", false);
    }

    /**
     * 是否启用x5内核
     * @return
     */
    public static boolean loadX5() {
        return (boolean) SharedPreferencesUtils.getParam(getContext(), "loadX5", false);
    }

    /**
     * 获取string.xml文本
     * @param id
     * @return
     */
    public static String getString(@StringRes int id) {
        return getContext().getResources().getString(id);
    }

    /**
     * 转换数字为 xx 万
     * @param number
     * @return
     */
    public static String intChange2Str(int number) {
        String str;
        if (number <= 0) {
            str = "";
        } else if (number < 10000) {
            str = number + "";
        } else {
            double num = (double) number / 10000;//1.将数字转换成以万为单位的数字

            BigDecimal b = new BigDecimal(num);
            double f1 = b.setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue();//2.转换后的数字四舍五入保留小数点后一位;
            str = f1 + "w";
        }
        return str;
    }

    /**
     * 复制到剪切板
     * @param context
     * @param string
     */
    public static void copyString(Context context, String string) {
        ClipboardManager clipManager;
        clipManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("copy text", string);//将数据放到clip对象
        clipManager.setPrimaryClip(clipData);//将clip对象放到剪切板

        //判断剪贴板里是否有内容
        if (!clipManager.hasPrimaryClip()) {
            return;
        }
        ClipData clip = clipManager.getPrimaryClip();
        //获取 ClipDescription
        ClipDescription description = clip.getDescription();
        //获取 label
        String label = description.getLabel().toString();
        //获取 text
        String copyText = clip.getItemAt(0).getText().toString();

    }

    /**
     * 获取当前日期是星期几
     *
     * @param dt
     * @return + 1 当前日期是星期几
     */
    public static int getWeekOfDate(Date dt) {
        int[] weekDays = {6, 0, 1, 2, 3, 4, 5};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    public static float dp2px(float paramFloat) {
        return TypedValue.applyDimension(1, paramFloat, Resources.getSystem().getDisplayMetrics());
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /* 时间戳转换成字符窜 */

    public static String times(String time){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int i = Integer.parseInt(time);
        return simpleDateFormat.format(new Date(i * 1000L));
    }


    public static void showToastTips(final Context context, final String tips) {
        Toast.makeText(context, tips, Toast.LENGTH_SHORT).show();
    }


    public static String getBuildTimeDescription() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(BuildConfig.BUILD_TIMESTAMP);
    }

    public static void goBrowser(String url){
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        startActivity(intent);
    }

    public static String getFromAssets(String fileName){
        String str="";
        try {
            InputStreamReader inputReader = new InputStreamReader( context.getResources().getAssets().open(fileName) );
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line;
            String Result="";
            while((line = bufReader.readLine()) != null)
                Result += line;
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }
}
