package cn.xihan.age.ui.fragments.webview.x5;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;


import com.google.gson.Gson;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebViewExtension;
import com.tencent.smtt.export.external.interfaces.*;
import com.tencent.smtt.sdk.*;


/**
 * @项目名 : AGE动漫
 * @作者 : MissYang
 * @创建时间 : 2021/3/3 2:24
 * @介绍 :
 */
public class X5WebView extends WebView {

    /**
     * 页面是否加载错误
     * */
    private boolean isError;

    /**
     * 页面是否加载成功
     * */
    private boolean isSuccess;

    public final static String        PC_USER_AGENT    = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.134 Safari/537.36";
    public final static String        PHONE_USER_AGENT = "Mozilla/5.0 (Linux; Android 9; ONEPLUS A6010 Build/PKQ1.180716.001; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/66.0.3359.158 Mobile Safari/537.36";


    /**
     * 展示错误页面
     * */
    private void showError() {
        loadUrl("file:///android_asset/html/Error.html"); }

    public X5WebView(Context arg0) {
        super(arg0);
        setBackgroundColor(85621);
    }

    @SuppressLint("SetJavaScriptEnabled")
    public X5WebView(Context arg0, AttributeSet arg1) {
        super(arg0, arg1);

        // 防止加载网页时调起系统浏览器
        //判断是否是广告相关的资源链接
        //这里是不做处理的数据
        //有广告的请求数据，我们直接返回空数据，注：不能直接返回null
        // 在访问失败的时候会首先回调onReceivedError，然后再回调onPageFinished
        // 还原变量
        WebViewClient client = new WebViewClient() {
            // 防止加载网页时调起系统浏览器
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView webView, String url) {
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("data", Context.MODE_PRIVATE);
                String adUrl = sharedPreferences.getString("AdUrls", null);
                adUrls = new Gson().fromJson(adUrl, String[].class);
                //判断是否是广告相关的资源链接
                if (!isAd(url)) {
                    //这里是不做处理的数据
                    return super.shouldInterceptRequest(webView, url);
                } else {
                    //有广告的请求数据，我们直接返回空数据，注：不能直接返回null
                    return new WebResourceResponse(null, null, null);
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                if (!isError) {
                    // 在访问失败的时候会首先回调onReceivedError，然后再回调onPageFinished
                    isSuccess = true;
                }
                // 还原变量
                isError = false;
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                isError = true;
                isSuccess = false;
                showError();
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                isError = true;
                isSuccess = false;

            }

        };
        this.setWebViewClient(client);
        // this.setWebChromeClient(chromeClient);
        // WebStorage webStorage = WebStorage.getInstance();
        initWebViewSettings();
        this.setClickable(true);
    }

    private void initWebViewSettings() {

        getSettings().setJavaScriptEnabled(true);
        getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        getSettings().setAllowFileAccess(false);
        getSettings().setSupportZoom(false);
        getSettings().setBuiltInZoomControls(false);
        getSettings().setUseWideViewPort(true);
        getSettings().setSupportMultipleWindows(true);
        // webSetting.setLoadWithOverviewMode(true);
        getSettings().setAppCacheEnabled(true);
        //是否调节内容 是否全屏
        getSettings().setLoadWithOverviewMode(true);
        // webSetting.setDatabaseEnabled(true);
        getSettings().setDomStorageEnabled(true);
        getSettings().setGeolocationEnabled(false);
        getSettings().setAppCacheMaxSize(Long.MAX_VALUE);

        // webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        getSettings().setUserAgent(PC_USER_AGENT);
        getSettings().setUseWideViewPort(true);

        getSettings().setLoadWithOverviewMode(true);
        getSettings().setDisplayZoomControls(false);
        IX5WebViewExtension ix5 = getX5WebViewExtension();
        if (null != ix5) {
            ix5.setScrollBarFadingEnabled(false);
        }
        getSettings().setDomStorageEnabled(true);
        getSettings().setJavaScriptEnabled(true);

        getSettings().setUseWideViewPort(true);
        getSettings().setLoadWithOverviewMode(true);
        requestFocus(130);
        setEnabled(true);

        if (Build.VERSION.SDK_INT >= 21)
            getSettings().setMixedContentMode(0);
        setFocusable(true);
        getSettings().setDefaultTextEncodingName("UTF-8");
        getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        getSettings().setDomStorageEnabled(true);
        getSettings().setDatabaseEnabled(true);
        getSettings().setAppCacheEnabled(true);
        getSettings().setAllowFileAccess(true);
        getSettings().setSavePassword(true);
        getSettings().setSupportZoom(true);
        getSettings().setBuiltInZoomControls(true);
        getSettings().setUseWideViewPort(true);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("data",Context.MODE_PRIVATE);
        boolean isZdbf = sharedPreferences.getBoolean("自动全屏播放", false);
        if (isZdbf){
           Bundle bundle = new Bundle();
            Bundle bundle2 = new Bundle();
            bundle.putBoolean("require", true);
            bundle2.putBoolean("standardFullScreen", false);
            bundle2.putInt("DefaultVideoScreen", 2);
            getX5WebViewExtension().invokeMiscMethod("setVideoParams", bundle2);
           getX5WebViewExtension().invokeMiscMethod("setVideoPlaybackRequiresUserGesture", bundle);
        }
    }

    static String[] adUrls;
    /**
     * 判断是否广告url
     * @param url
     * @return
     */
    public static boolean isAd(String url) {
        for (String adUrl : adUrls ) {
            if (url.contains(adUrl)) {
                return true;
            }
        }
        return false;
    }

}
