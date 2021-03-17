package cn.xihan.age.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SwitchCompat;

import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.span.QMUIBlockSpaceSpan;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.tencent.bugly.beta.Beta;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.xihan.age.BuildConfig;
import cn.xihan.age.Config;
import cn.xihan.age.R;
import cn.xihan.age.base.BaseFragment;
import cn.xihan.age.custom.SettingItem;
import cn.xihan.age.ui.fragments.webview.x5.X5Fragment;
import cn.xihan.age.utils.Utils;

import static android.content.Context.MODE_PRIVATE;
import static cn.xihan.age.utils.Utils.getBuildTimeDescription;

/**
 * @项目名 : AGE动漫
 * @作者 : MissYang
 * @创建时间 : 2021/3/2 10:20
 * @介绍 : 设置
 */
public class SettingsFragment extends BaseFragment {

    FrameLayout layout;

    Context ctx;
    @BindView(R.id.blockSpace)
    TextView blockSpace;

    @BindView(R.id.si_x5)
    SettingItem  siX5;
    @BindView(R.id.si_gx)
    SettingItem  siGx;
    @BindView(R.id.sc_yjz)
    SwitchCompat scYjz;
    @BindView(R.id.sc_zdqpbf)
    SwitchCompat scZdqpbf;
    @BindView(R.id.sc_plq)
    SwitchCompat scPlq;
    @BindView(R.id.sc_shield)
    SwitchCompat scShield;
    @BindView(R.id.tv_shieldurl)
    TextView     tvShieldurl;

    @BindView(R.id.topbar)
    QMUITopBarLayout topbar;

    private final int mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog;
    X5Fragment x5Fragment;
    @BindView(R.id.sc_sc2)
    SwitchCompat scSc2;
    @BindView(R.id.tv_api)
    TextView     tvApi;
    @BindView(R.id.sc_api)
    SwitchCompat scApi;
    @BindView(R.id.sc_tx)
    SwitchCompat scTx;
    @BindView(R.id.tv_tx)
    TextView     tvTx;
    @BindView(R.id.sc_opendm)
    SwitchCompat scOpendm;


    @Override
    protected View onCreateView() {
        layout = (FrameLayout) LayoutInflater.from(getActivity()).inflate(R.layout.fragment_setting, null);
        ButterKnife.bind(this, layout);
        ctx = requireContext();
        initTopbar();
        initContentView();
        initView();
        return layout;
    }

    private void initView() {

        SharedPreferences sharedPreferences = ctx.getSharedPreferences("data", MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        scZdqpbf.setOnCheckedChangeListener((compoundButton, b) -> {
            editor.putBoolean("自动全屏播放", b);
            editor.apply();
            //Log.d("xihan","开关状态:" +b );
        });
        scYjz.setOnCheckedChangeListener((compoundButton, b) -> {

            editor.putBoolean("预加载", b);
            editor.apply();
            //Log.d("xihan","开关状态:" +b );
        });

        scPlq.setOnCheckedChangeListener((compoundButton, b) -> {
            editor.putBoolean("显示评论区", b);
            if (b) {
                requireActivity().runOnUiThread(() -> scShield.setVisibility(View.VISIBLE));
                requireActivity().runOnUiThread(() -> tvShieldurl.setVisibility(View.VISIBLE));
            } else {
                requireActivity().runOnUiThread(() -> scShield.setVisibility(View.GONE));
                requireActivity().runOnUiThread(() -> tvShieldurl.setVisibility(View.GONE));
            }
            editor.apply();
        });
        scApi.setOnCheckedChangeListener((buttonView, isChecked) -> {
            editor.putBoolean("DefaultOpenApi", isChecked);
            if (isChecked) {
                requireActivity().runOnUiThread(() -> tvApi.setVisibility(View.VISIBLE));
            } else {
                requireActivity().runOnUiThread(() -> tvApi.setVisibility(View.GONE));
            }
            editor.apply();
        });
        scShield.setOnCheckedChangeListener((compoundButton, b) -> {
            editor.putBoolean("评论屏蔽词", b);
            editor.apply();
        });
        scSc2.setOnCheckedChangeListener((compoundButton, b) -> {
            editor.putBoolean("本地收藏", b);
            editor.apply();
        });
        scTx.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                requireActivity().runOnUiThread(() -> tvTx.setVisibility(View.VISIBLE));
            } else {
                requireActivity().runOnUiThread(() -> tvTx.setVisibility(View.GONE));
            }
            editor.putBoolean("自定义头像", b);
            editor.apply();
            //Log.d("xihan","开关状态:" +b );
        });
        scOpendm.setOnCheckedChangeListener((compoundButton, b) -> {
            editor.putBoolean("开启弹幕", b);
            editor.apply();
        });
        boolean isBdsc = sharedPreferences.getBoolean("本地收藏", false);
        boolean isOpenApi = sharedPreferences.getBoolean("DefaultOpenApi", false);
        boolean isDiyTx = sharedPreferences.getBoolean("自定义头像", false);
        boolean isOpenDanmu = sharedPreferences.getBoolean("开启弹幕", false);

        boolean isYjz = sharedPreferences.getBoolean("预加载", false);
        boolean isZdqpbf = sharedPreferences.getBoolean("自动全屏播放", false);
        boolean isShowplq = sharedPreferences.getBoolean("显示评论区", false);
        boolean isShield = sharedPreferences.getBoolean("评论屏蔽词", false);
        String diyShieldUrl = sharedPreferences.getString("diyShieldUrl", null);
        String diyApi = sharedPreferences.getString("diyApi", null);
        String diyTx = sharedPreferences.getString("diyTxUrl", null);

        if (!TextUtils.isEmpty(diyShieldUrl)) {
            tvShieldurl.setText("自定义地址:    " + diyShieldUrl);
        }
        if (!TextUtils.isEmpty(diyApi)) {
            tvApi.setText("自定义Api地址:" + diyApi);
        }
        if (!TextUtils.isEmpty(diyTx)) {
            tvTx.setText("自定义头像地址:    " + diyTx);
        }

        tvShieldurl.setOnClickListener(v -> ShowEditDialog());
        tvTx.setOnClickListener(v -> ShowEditDialogTx());
        requireActivity().runOnUiThread(() -> {
            if (isZdqpbf) {
                scZdqpbf.setChecked(true);
            }
            if (isYjz) {
                scYjz.setChecked(true);
            }
            if (isBdsc) {
                scSc2.setChecked(true);
            }
            if (isShowplq) {
                scPlq.setChecked(true);
                requireActivity().runOnUiThread(() -> scShield.setVisibility(View.VISIBLE));
                requireActivity().runOnUiThread(() -> tvShieldurl.setVisibility(View.VISIBLE));
            } else {
                requireActivity().runOnUiThread(() -> scShield.setVisibility(View.GONE));
                requireActivity().runOnUiThread(() -> tvShieldurl.setVisibility(View.GONE));
            }
            if (isOpenApi) {
                scApi.setChecked(true);
                requireActivity().runOnUiThread(() -> tvApi.setVisibility(View.VISIBLE));
            } else {
                requireActivity().runOnUiThread(() -> tvApi.setVisibility(View.GONE));
            }
            if (isShield) {
                scShield.setChecked(true);
                if (tvShieldurl.getText() == null) {
                    Toast.makeText(getContext(), "地址不能为空", Toast.LENGTH_SHORT).show();
                }
            }
            if (isDiyTx) {
                scTx.setChecked(true);
                if (tvTx.getText() == null) {
                    Toast.makeText(getContext(), "地址不能为空", Toast.LENGTH_SHORT).show();
                }
            } else {
                requireActivity().runOnUiThread(() -> tvTx.setVisibility(View.GONE));
            }
            if (isOpenDanmu){
                scOpendm.setChecked(true);
            }
        });


        siGx.setOnClickListener(v -> showUpdateDialog());

        tvApi.setOnClickListener(v -> ShowEditDialog3());

        requireActivity().runOnUiThread(this::initX5ItemView);

        siX5.setOnClickListener(v -> {
            if (x5Fragment == null) {
                x5Fragment = new X5Fragment();
            }
            Bundle bundle = new Bundle();
            bundle.putString("url", "http://debugtbs.qq.com");
            x5Fragment.setArguments(bundle);
            startFragment(x5Fragment);
        });
    }


    private void ShowEditDialogTx() {
        SharedPreferences.Editor edit = requireActivity().getSharedPreferences("data", 0).edit();
        final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(getActivity());
        builder.setTitle("自定义头像")
                .setSkinManager(QMUISkinManager.defaultInstance(requireActivity()))
                .setPlaceholder("填入你定义的头像绝对地址")
                .setInputType(InputType.TYPE_CLASS_TEXT)
                .addAction("取消", (dialog, index) -> dialog.dismiss())
                .addAction("确定", (dialog, index) -> {
                    CharSequence text = builder.getEditText().getText();
                    if (text != null && text.length() > 0) {
                        if (String.valueOf(text).contains("http")) {
                            Toast.makeText(getActivity(), "温馨提示:如果设定的地址无法访问可能会导致闪退哦~", Toast.LENGTH_SHORT).show();
                            try {
                                edit.putString("diyTxUrl", String.valueOf(text));
                                edit.apply();
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }
                            requireActivity().runOnUiThread(() -> tvTx.setText("自定义头像地址:\n" + text));
                            dialog.dismiss();
                        } else {
                            Toast.makeText(getActivity(), "请填入合法URL链接", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "请填入内容", Toast.LENGTH_SHORT).show();
                    }
                })
                .create(mCurrentDialogStyle).show();
    }

    private void initX5ItemView() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences("data", MODE_PRIVATE);
        boolean isX5 = sharedPreferences.getBoolean("X5State", false);
        if (!isX5) {
            siX5.setRightText("未下载");
        } else {
            siX5.setRightText("已下载");
        }
    }

    private void initContentView() {
        String json = "   {\n     \"shield\": [\n" +
                "    \"xx\",\n" +
                "    \"xx\"\n" +
                "   ]}";
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("data", MODE_PRIVATE);
        String paragraphFirst = "Ijkplayer播放器内核：B 站自研的 IJKPlayer。它是一款基于 FFmpeg 的轻量级 Android/iOS 视频播放器.支持硬件加速解码\n";
        String paragraphSecond5 = "x5内核: 腾讯出品，比系统自带WebView快的多，支持视频格式更多,网页内核,需要的可以自行下载(默认无)\n";
        String paragraphSecond7 = "自定义屏蔽词:所设的JSON注意格式要为:\n" + json;
        String paragraphSecond8 = "\n版本号: " + BuildConfig.VERSION_NAME + "\n" + "编译时间： " + getBuildTimeDescription();
        String paragraphSecond10 = sharedPreferences.getString("Api", null);
        String paragraphSecond11 = sharedPreferences.getString("Api2", null);
        String paragraphSecond9 = "自定义Api: " + "同步AGE官方的API托管在Github或国内CDN Github,加快访问速度.注意!!!要为源地址,可以自己fork我Github但是不能同步更新,下面举例两个,可复制" + "\n例如:" + "\n1." + paragraphSecond10 + "\n2." + paragraphSecond11 + "\n";
        String spaceString = "[space]";
        String spaceString2 = "\n";
        SpannableString paragraphText = new SpannableString(paragraphFirst + spaceString + spaceString2 + paragraphSecond5 + spaceString2 + paragraphSecond9 + spaceString2 + paragraphSecond7 + spaceString2 + paragraphSecond8);
        QMUIBlockSpaceSpan blockSpaceSpan = new QMUIBlockSpaceSpan(QMUIDisplayHelper.dp2px(ctx, 6));
        paragraphText.setSpan(blockSpaceSpan, paragraphFirst.length(), paragraphFirst.length() + spaceString.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        blockSpace.setText(paragraphText);
    }

    private void ShowEditDialog() {
        SharedPreferences.Editor edit = requireActivity().getSharedPreferences("data", 0).edit();
        final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(getActivity());
        builder.setTitle("自定义屏蔽词链接")
                .setSkinManager(QMUISkinManager.defaultInstance(requireActivity()))
                .setPlaceholder("填入你定义的屏蔽词链接")
                .setInputType(InputType.TYPE_CLASS_TEXT)
                .addAction("取消", (dialog, index) -> dialog.dismiss())
                .addAction("确定", (dialog, index) -> {
                    CharSequence text = builder.getEditText().getText();
                    if (text != null && text.length() > 0) {
                        if (String.valueOf(text).contains("http")) {
                            Toast.makeText(getActivity(), "温馨提示:重启APP后生效!如果屏蔽词格式不一致，会导致闪退哦~", Toast.LENGTH_SHORT).show();
                            try {
                                edit.putString("diyShieldUrl", String.valueOf(text));
                                edit.apply();
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }
                            requireActivity().runOnUiThread(() -> tvShieldurl.setText("自定义地址:\n" + text));
                            dialog.dismiss();
                        } else {
                            Toast.makeText(getActivity(), "请填入合法URL链接", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getActivity(), "请填入内容", Toast.LENGTH_SHORT).show();
                    }
                })
                .create(mCurrentDialogStyle).show();
    }

    private void ShowEditDialog3() {
        SharedPreferences.Editor edit = requireActivity().getSharedPreferences("data", 0).edit();
        final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(getActivity());
        builder.setTitle("自定义Api服务器")
                .setSkinManager(QMUISkinManager.defaultInstance(requireActivity()))
                .setPlaceholder("填入你要设定的Api服务器")
                .setInputType(InputType.TYPE_CLASS_TEXT)
                .addAction("取消", (dialog, index) -> dialog.dismiss())
                .addAction("确定", (dialog, index) -> {
                    CharSequence text = builder.getEditText().getText();
                    if (text != null && text.length() > 0) {
                        Utils.showToastTips(getContext(), "确保输入没有出错,不然可能会加载失败或者出错");
                        try {
                            edit.putString("diyApi", String.valueOf(text));
                            edit.apply();
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                        requireActivity().runOnUiThread(() -> tvApi.setText("自定义Api地址:" + text));
                        dialog.dismiss();
                    } else {
                        Toast.makeText(getActivity(), "请填入内容", Toast.LENGTH_SHORT).show();
                    }
                })
                .create(mCurrentDialogStyle).show();
    }

    String isUpdate;

    /**
     * 展示更新
     */
    private void showUpdateDialog() {
        int bdversionCode = 0;
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("data", MODE_PRIVATE);
        String versionName = sharedPreferences.getString("versionName", null);
        String GGRZ = sharedPreferences.getString("GGRZ", null);
        String DownloadApkUrl = sharedPreferences.getString("DownloadApkUrl", null);
        int zxversioncode = Integer.parseInt(sharedPreferences.getString("versionCode", "0"));
        try {
            bdversionCode = ctx.getApplicationContext().getPackageManager().getPackageInfo(ctx.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        Log.d("xihan", "最新:" + zxversioncode + "\n当前:" + bdversionCode);
        if (zxversioncode > bdversionCode) {
            isUpdate = "有更新! 最新版为:" + versionName;
        } else {
            isUpdate = "无更新! 最新版为:" + versionName;
        }
        new QMUIDialog.MessageDialogBuilder(requireActivity())
                .setTitle(isUpdate)
                .setMessage(GGRZ)
                .setSkinManager(QMUISkinManager.defaultInstance(requireActivity()))
                .addAction("蓝奏云", (dialog, index) -> {
                    dialog.dismiss();
                    Uri uri = Uri.parse(Config.UpdateUrl);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    Toast.makeText(getContext(), "密码:xihan", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                })
                .addAction("直接下载", (dialog, index) -> {
                    dialog.dismiss();
                    Uri uri = Uri.parse(DownloadApkUrl);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                })
                .addAction(0, "Bugly更新", QMUIDialogAction.ACTION_PROP_POSITIVE, (dialog, index) -> {
                    dialog.dismiss();
                    Beta.checkUpgrade();
                })

                .create(mCurrentDialogStyle).show();
    }

    /**
     * 设置标题
     */
    private void initTopbar() {
        topbar.addLeftBackImageButton().setOnClickListener(v -> popBackStack());
        topbar.setTitle("设置");
    }

}
