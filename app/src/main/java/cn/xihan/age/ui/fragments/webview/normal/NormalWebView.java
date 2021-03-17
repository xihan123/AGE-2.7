package cn.xihan.age.ui.fragments.webview.normal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.AttributeSet;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;

import com.google.gson.Gson;

/**
 * @项目名 : AGE动漫
 * @作者 : MissYang
 * @创建时间 : 2021/3/3 1:53
 * @介绍 :
 */
public class NormalWebView extends WebView {

    /**
     * 页面是否加载错误
     * */
    private boolean isError;

    /**
     * 页面是否加载成功
     * */
    private boolean isSuccess;
    static String[] adUrls;

    public final static String PC_USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.134 Safari/537.36";
    public final static String        PHONE_USER_AGENT = "Mozilla/5.0 (Linux; Android 9; ONEPLUS A6010 Build/PKQ1.180716.001; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/66.0.3359.158 Mobile Safari/537.36";

    public NormalWebView(Context arg0) {
        super(arg0);
        setBackgroundColor(85621);
    }

    /**
     * 展示错误页面
     * */
    private void showError() { loadUrl("file:///android_asset/html/Error.html"); }

    String VideoUrl;
    @SuppressLint("SetJavaScriptEnabled")
    public NormalWebView(Context arg0, AttributeSet arg1) {
        super(arg0, arg1);

        // 防止加载网页时调起系统浏览器
        //Log.d("xihan","广告URL:" + String.valueOf(url));
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

            @Nullable
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("data", Context.MODE_PRIVATE);
                String adUrl = sharedPreferences.getString("AdUrls", null);
                adUrls = new Gson().fromJson(adUrl, String[].class);
                //Log.d("xihan","广告URL:" + String.valueOf(url));
                //判断是否是广告相关的资源链接
                if (!isAd(String.valueOf(url))) {
                    //这里是不做处理的数据
//                    if (url.contains(".mp4")){
//                        Log.d("xihan","链接:" + url);
//
//                    }
                    return super.shouldInterceptRequest(view, url);
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
        WebSettings webSetting = this.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setAllowFileAccess(false);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(false);
        webSetting.setBuiltInZoomControls(false);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(true);
        webSetting.setAppCacheEnabled(true);
        webSetting.setLoadWithOverviewMode(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setGeolocationEnabled(false);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSetting.setUserAgentString(PC_USER_AGENT);
    }

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

