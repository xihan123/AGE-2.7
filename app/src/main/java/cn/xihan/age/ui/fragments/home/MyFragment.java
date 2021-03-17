package cn.xihan.age.ui.fragments.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.qmuiteam.qmui.arch.QMUIFragment;
import com.qmuiteam.qmui.layout.QMUIButton;
import com.xihan.age.paylibrary.MiniPayUtils;

import org.json.JSONObject;

import java.util.ResourceBundle;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.xihan.age.Config;
import cn.xihan.age.R;
import cn.xihan.age.custom.SettingItem;
import cn.xihan.age.ui.fragments.Sc2Fragment;
import cn.xihan.age.ui.fragments.ScFragment;
import cn.xihan.age.ui.fragments.SettingsFragment;
import cn.xihan.age.utils.HttpUtil;

import static android.content.Context.MODE_PRIVATE;

/**
 * @项目名 : AGE动漫
 * @作者 : MissYang
 * @创建时间 : 2021/3/2 1:00
 * @介绍 :
 */
public class MyFragment extends QMUIFragment {
    FrameLayout layout;

    Context           ctx;
    SharedPreferences sharedPreferences;

    @BindView(R.id.iv_bg)
    ImageView   ivBg;
    @BindView(R.id.iv_tx)
    ImageView   ivTx;
    @BindView(R.id.tv_yhm)
    TextView    tvYhm;
    @BindView(R.id.et_name)
    EditText    etName;
    @BindView(R.id.et_pass)
    EditText    etPass;
    @BindView(R.id.settingItemds)
    SettingItem settingItemds;
    @BindView(R.id.settingItemsc)
    SettingItem settingItemsc;
    @BindView(R.id.settingItemsz)
    SettingItem settingItemsz;
    @BindView(R.id.bt_tcdl)
    Button      btTcdl;
    @BindView(R.id.sv_scroll)
    ScrollView  svScroll;
    @BindView(R.id.settingItemsc2)
    SettingItem settingItemsc2;

    String ed_name2, ed_pass2;

    ScFragment       scFragment;
    SettingsFragment settingsFragment;
    Sc2Fragment      scFragment2;
    @BindView(R.id.btn_reg)
    QMUIButton btnReg;
    @BindView(R.id.btn_log)
    QMUIButton btnLog;


    @Override
    protected View onCreateView() {
        layout = (FrameLayout) LayoutInflater.from(getActivity()).inflate(R.layout.fragment_my, null);
        ButterKnife.bind(this, layout);
        ctx = requireContext();
        initView();
        return layout;
    }

    private void initView() {
        sharedPreferences = requireActivity().getSharedPreferences("data", 0);
        RequestOptions txoptions = new RequestOptions().error(R.drawable.mytx).bitmapTransform(new RoundedCorners(360));//图片圆角为30
        String diyTx = sharedPreferences.getString("diyTxUrl",null);
        boolean isDiyTx = sharedPreferences.getBoolean("自定义头像",false);
        if (isDiyTx){
            if (!TextUtils.isEmpty(diyTx)){
                Glide.with(requireContext()).load(diyTx).apply(txoptions).into(ivTx); }
            else {
                Glide.with(requireContext()).load(R.drawable.mytx).apply(txoptions).into(ivTx);
            }
        }else {
            Glide.with(requireContext()).load(R.drawable.mytx).apply(txoptions).into(ivTx);
        }

        String UserName = sharedPreferences.getString("username", "");
        String Sign_t = sharedPreferences.getString("Sign_t", "");
        String Sign_k = sharedPreferences.getString("Sign_k", "");
        boolean isLogin = sharedPreferences.getBoolean("isLogin", false);
        Bundle bundle = new Bundle();
        if (!UserName.equals("") | !Sign_t.equals("") | !Sign_k.equals("")) {
            bundle.putString("UserName", UserName);
            bundle.putString("Sign_t", Sign_t);
            bundle.putString("Sign_k", Sign_k);
        }

        if (!UserName.equals("")) {
            tvYhm.setText("用户名:" + UserName);
            etName.setVisibility(View.GONE);
            etPass.setVisibility(View.GONE);
            btnLog.setVisibility(View.GONE);
            btnReg.setVisibility(View.GONE);
        } else {
            tvYhm.setText("未登录");
        }



        if (isLogin) {
            btTcdl.setVisibility(View.VISIBLE);
        } else {
            btTcdl.setVisibility(View.GONE);
        }

        btTcdl.setOnClickListener(v -> deleteUserData());

        settingItemsc.setOnClickListener(v -> {
            boolean isLogin3 = sharedPreferences.getBoolean("isLogin", false);
            if (isLogin3) {
                sharedPreferences = requireActivity().getSharedPreferences("data", 0);
                String UserName1 = sharedPreferences.getString("username", "");
                String Sign_t1 = sharedPreferences.getString("Sign_t", "");
                String Sign_k1 = sharedPreferences.getString("Sign_k", "");
                if (!UserName1.equals("") | !Sign_t1.equals("") | !Sign_k1.equals("")) {
                    bundle.putString("UserName", UserName1);
                    bundle.putString("Sign_t", Sign_t1);
                    bundle.putString("Sign_k", Sign_k1);
                }
                if (scFragment == null) {
                    scFragment = new ScFragment();
                }
                scFragment.setArguments(bundle);
                startFragment(scFragment);
            } else {
                ShowNoLoginDialog();
            }

        });
        settingItemsz.setOnClickListener(v -> {
            if (settingsFragment == null) {
                settingsFragment = new SettingsFragment();
            }
            startFragment(settingsFragment);

        });

        settingItemsc2.setOnClickListener(v -> {
            if (scFragment2 == null) {
                scFragment2 = new Sc2Fragment();
            }
            startFragment(scFragment2);
        });
        settingItemds.setOnClickListener(v -> {
//                ShowPayDialog();
            MiniPayUtils.setupPay(requireActivity(), new com.xihan.age.paylibrary.Config.Builder(Config.payCode, R.drawable.zfb2, R.drawable.wx2).build());
        });
    }

    private void ShowNoLoginDialog() {
        Toast.makeText(getContext(), "未登录", Toast.LENGTH_SHORT).show();
    }

    /**
     * 更新UI
     */
    private void updataUi() {
        String UserName = sharedPreferences.getString("username", "");
        boolean isLogin = sharedPreferences.getBoolean("isLogin", false);
        if (isLogin) {
            btTcdl.setVisibility(View.VISIBLE);
        } else {
            btTcdl.setVisibility(View.GONE);
        }
        if (!UserName.equals("")) {
            tvYhm.setText("用户名:" + UserName);
            etName.setVisibility(View.GONE);
            etPass.setVisibility(View.GONE);
            btnLog.setVisibility(View.GONE);
            btnReg.setVisibility(View.GONE);
        } else {
            tvYhm.setText("未登录");
            etName.setVisibility(View.VISIBLE);
            etPass.setVisibility(View.VISIBLE);
            btnLog.setVisibility(View.VISIBLE);
            btnReg.setVisibility(View.VISIBLE);
        }
        String diyTx = sharedPreferences.getString("diyTxUrl",null);
        RequestOptions txoptions = new RequestOptions().error(R.drawable.mytx).bitmapTransform(new RoundedCorners(360));//图片圆角为30
        ResourceBundle resourceBundle;
        boolean isDiyTx = sharedPreferences.getBoolean("自定义头像",false);
        if (isDiyTx){
            if (!TextUtils.isEmpty(diyTx)){
                Glide.with(requireContext()).load(diyTx).apply(txoptions).into(ivTx); }
            else {
                Glide.with(requireContext()).load(R.drawable.mytx).apply(txoptions).into(ivTx);
            }
        }else {
            Glide.with(requireContext()).load(R.drawable.mytx).apply(txoptions).into(ivTx);
        }

    }

    @OnClick({R.id.btn_reg, R.id.btn_log})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_reg:
                if (!etName.getText().toString().equals("") && !etPass.getText().toString().equals("")) {
                    if (etPass.getText().toString().length() < 8) {
                        Toast.makeText(getContext(), "密码不能少于8位", Toast.LENGTH_SHORT).show();
                    } else {
                        ed_name2 = etName.getText().toString();
                        ed_pass2 = etPass.getText().toString();
                        regRequest();
                    }
                } else {
                    Toast.makeText(getContext(), "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_log:
                SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("data", 0);
                boolean isLogin2 = sharedPreferences.getBoolean("isLogin", false);
                if (isLogin2) {
                    Toast.makeText(getContext(), "已登录", Toast.LENGTH_SHORT).show();
                } else {
                    if (!etName.getText().toString().equals("") && !etPass.getText().toString().equals("")) {
                        if (etPass.getText().toString().length() < 8) {
                            Toast.makeText(getContext(), "密码不能少于8位", Toast.LENGTH_SHORT).show();
                        } else {
                            ed_name2 = etName.getText().toString();
                            ed_pass2 = etPass.getText().toString();
                            loginRequest();
                        }
                    } else {
                        Toast.makeText(getContext(), "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    private void loginRequest() {
        String post = "username=" + ed_name2 + "&password=" + ed_pass2;
        String user = HttpUtil.createHttpsPostBystring(Config.LoginApi, post, "");
        SharedPreferences.Editor editor = requireActivity().getSharedPreferences("data", MODE_PRIVATE).edit();
        try {
            JSONObject jsonObject = new JSONObject(user);
            String Username = jsonObject.optString("Username");
            String Sign_k = jsonObject.optString("Sign_k");
            String Sign_t = jsonObject.optString("Sign_t");
            String Message = jsonObject.optString("Message");
            if (Message.equals("success")) {
                Toast.makeText(ctx, "登录成功,欢迎使用", Toast.LENGTH_SHORT).show();
                try {
                    editor.putString("Sign_k", Sign_k);
                    editor.putString("Sign_t", Sign_t);
                    editor.putString("username", Username);
                    editor.putBoolean("isLogin", true);
                    editor.apply();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                updataUi();
            } else {
                Toast.makeText(ctx, Message, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }


        //updataUi();
        //requireActivity().runOnUiThread(this::updataUi);
        //requireActivity().runOnUiThread(this::startrequst);
    }

    private void regRequest() {
        String post = "username=" + ed_name2 + "&password1=" + ed_pass2 + "&password2=" + ed_pass2;
        String str = HttpUtil.createHttpsPostBystring(Config.RegisterApi, post, "");
        SharedPreferences.Editor editor = requireActivity().getSharedPreferences("data", MODE_PRIVATE).edit();
        if (!TextUtils.isEmpty(str)) {
            try {
                JSONObject jsonObject = new JSONObject(str);
                String Username = jsonObject.optString("Username");
                String Sign_k = jsonObject.optString("Sign_k");
                String Sign_t = jsonObject.optString("Sign_t");
                String Message = jsonObject.optString("Message");
                if (Message.contains("注册失败")) {
                    Toast.makeText(ctx, Message, Toast.LENGTH_SHORT).show();
                } else if (Message.equals("success")) {
                    Toast.makeText(ctx, "注册成功,欢迎使用", Toast.LENGTH_SHORT).show();
                    try {
                        editor.putString("Sign_k", Sign_k);
                        editor.putString("Sign_t", Sign_t);
                        editor.putString("username", Username);
                        editor.putBoolean("isLogin", true);
                        editor.apply();
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                    updataUi();
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        requireActivity().runOnUiThread(this::updataUi);
    }

    private void deleteUserData() {
        SharedPreferences.Editor editor = requireActivity().getSharedPreferences("data", Context.MODE_PRIVATE).edit();
        try {
            editor.putString("Sign_k", "");
            editor.putString("Sign_t", "");
            editor.putString("username", "");
            editor.putBoolean("isLogin", false);
            editor.apply();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        updataUi();
    }

}
