package cn.xihan.age.ui.fragments.webview.normal;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;

import com.google.gson.Gson;
import com.qmuiteam.qmui.util.QMUIViewHelper;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.xihan.age.R;
import cn.xihan.age.base.BaseFragment;
import cn.xihan.age.bean.ZxPlayerBean;
import cn.xihan.age.ui.fragments.players.NewPlayerFragment;
import cn.xihan.age.utils.Utils;

/**
 * @项目名 : AGE动漫
 * @作者 : MissYang
 * @创建时间 : 2021/3/3 2:02
 * @介绍 : 默认内核
 */
public class NormalFragment extends BaseFragment {

    FrameLayout layout;
    Context     ctx;
    @BindView(R.id.myWebView)
    NormalWebView      myWebView;
    @BindView(R.id.topbar)
    QMUITopBarLayout topbar;

    String url,fjmc,aid;


    @Override
    protected View onCreateView() {
        layout = (FrameLayout) LayoutInflater.from(requireContext()).inflate(R.layout.fragment_normal_webview, null);
        ButterKnife.bind(this, layout);
        ctx = requireContext();
        Bundle bundle = getArguments();
        if (bundle != null) {
            url = bundle.getString("url");
            fjmc= bundle.getString("fjmc");
            aid=bundle.getString("aid");
        }
        initHardwareAccelerate();
        initWebView();
        initTopbar();
        return layout;
    }

    private void initTopbar() {
        topbar.addLeftBackImageButton().setOnClickListener(v -> popBackStack());
        injectEntrance(topbar);
        topbar.setTitle("AGE动漫");
    }

    public  void injectEntrance(final QMUITopBarLayout topbar) {
        topbar.addRightTextButton("···", QMUIViewHelper.generateViewId())
                .setOnClickListener(v -> showBottomSheetList(topbar.getContext()));
    }

    public  void showBottomSheetList(final Context context) {
        new QMUIBottomSheet.BottomListSheetBuilder(context)
                .addItem("刷新")
                .addItem("外部浏览器打开")
                .setOnSheetItemClickListener((dialog, itemView, position, tag) -> {
                    //Toast.makeText(context, "点击了:" + position, Toast.LENGTH_SHORT).show();
                    switch (position) {
                        case 0:
                            if (!TextUtils.isEmpty(url)) {
                                myWebView.loadUrl(url);
                            }
                            break;
                        case 1:
                            Utils.goBrowser(url);
                            break;
                    }
                    dialog.dismiss();
                })
                .build()
                .show();
    }

    /**
     * 启用硬件加速
     */
    private void initHardwareAccelerate() {
        try {
            if (Integer.parseInt(android.os.Build.VERSION.SDK) >= 11) {
                requireActivity().getWindow()
                        .setFlags(
                                android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                                android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    String VideoUrl;
    /**
     * 页面是否加载错误
     * */
    private boolean isError;

    /**
     * 页面是否加载成功
     * */
    private boolean isSuccess;
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
    /**
     * 展示错误页面
     * */
    private void showError() { myWebView.loadUrl("file:///android_asset/html/Error.html"); }
    List<ZxPlayerBean> dataPlayList = new ArrayList<>();
    NewPlayerFragment newPlayerFragment;
    Bundle bundle2;
    private void initWebView() {
        if (!TextUtils.isEmpty(url)){
            myWebView = layout.findViewById(R.id.myWebView);
            myWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
            myWebView.getSettings().setJavaScriptEnabled(true);
            myWebView.setWebViewClient(new WebViewClient(){
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
                    if (url.contains(".mp4")){
                        VideoUrl=url;
                        Log.d("xihan","mp4链接:" + url);
                    }else if (url.contains(".m3u8")){
                        VideoUrl=url;
                        Log.d("xihan","m3u8链接:" + url);
                    }
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
            });
            myWebView.loadUrl(url);

            Log.e("xihan","网页标题:" +  myWebView.getTitle());
            myWebView.setOnLongClickListener(v -> {
               final PopupMenu popupMenu = new PopupMenu(ctx, v);
               popupMenu.getMenuInflater().inflate(R.menu.item_menu, popupMenu.getMenu());
               popupMenu.setOnMenuItemClickListener(menuItem -> {
                   if (menuItem.getItemId() == R.id.item_play) {
                       if (!TextUtils.isEmpty(VideoUrl)){
                           ZxPlayerBean zxPlayerBean = new ZxPlayerBean();
                           bundle2 = new Bundle();
                           zxPlayerBean.setPlayId("<play>web_mp4</play>");
                           zxPlayerBean.setPlayVid(VideoUrl);
                           zxPlayerBean.setTitle(fjmc);
                           dataPlayList.add(zxPlayerBean);
                           bundle2.putString("url", VideoUrl);
                           bundle2.putString("playid", "<play>web_mp4</play>");
                           bundle2.putString("fjjs", "阿巴阿巴");
                           bundle2.putString("fjmc", fjmc);
                           bundle2.putSerializable("PlayList", (Serializable) dataPlayList);
                           bundle2.putString("aid", aid);
                           if (newPlayerFragment == null) {
                               newPlayerFragment = new NewPlayerFragment();
                           }
                           newPlayerFragment.setArguments(bundle2);
                           startFragment(newPlayerFragment);
                       }else{
                           Toast.makeText(ctx,"未捕获到视频地址!",Toast.LENGTH_SHORT).show();
                       }
                   }else if (menuItem.getItemId() == R.id.item_download){
                       if (!TextUtils.isEmpty(VideoUrl)){
                           Utils.goBrowser(VideoUrl);
                       }else{
                           Toast.makeText(ctx,"未捕获到视频地址!",Toast.LENGTH_SHORT).show();
                       }
                   }
                   return true;
               });
               popupMenu.show();
               return false;
           });
    }
    }






}
