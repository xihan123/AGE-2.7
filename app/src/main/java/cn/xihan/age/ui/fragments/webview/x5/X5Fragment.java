package cn.xihan.age.ui.fragments.webview.x5;


import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.qmuiteam.qmui.util.QMUIViewHelper;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.xihan.age.R;
import cn.xihan.age.base.BaseFragment;
import cn.xihan.age.utils.Utils;


/**
 * @项目名 : AGE动漫
 * @作者 : MissYang
 * @创建时间 : 2021/3/3 2:32
 * @介绍 : X5内核页面
 */
public class X5Fragment extends BaseFragment {

    FrameLayout layout;
    Context     ctx;
    String      url;
    @BindView(R.id.myWebView)
    X5WebView        myWebView;
    @BindView(R.id.topbar)
    QMUITopBarLayout topbar;


    @Override
    protected View onCreateView() {
        layout = (FrameLayout) LayoutInflater.from(requireContext()).inflate(R.layout.fragment_x5_webview, null);
        ButterKnife.bind(this, layout);
        ctx = requireContext();
        Bundle bundle = getArguments();
        if (bundle != null) {
            url = bundle.getString("url");
        }
        initHardwareAccelerate();
        initWebView();
        initTopbar();
        return layout;
    }

    private void initTopbar() {
        topbar.addLeftBackImageButton().setOnClickListener(v -> {
            if (myWebView.canGoBack()) {
                myWebView.goBack();//返回上个页面
            }else {
                popBackStack();
            }
        });
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
            if (Integer.parseInt(Build.VERSION.SDK) >= 11) {
                requireActivity().getWindow()
                        .setFlags(
                                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initWebView() {
        if (!TextUtils.isEmpty(url)) {
            myWebView.loadUrl(url);
        }
    }



}
