package cn.xihan.age.ui.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.qmuiteam.qmui.util.QMUIViewHelper;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.xihan.age.Config;
import cn.xihan.age.R;
import cn.xihan.age.base.BaseFragment;
import cn.xihan.age.base.BaseRecyclerAdapter;
import cn.xihan.age.base.RecyclerViewHolder;
import cn.xihan.age.bean.AniPreSimBean;
import cn.xihan.age.bean.VideoBean;
import cn.xihan.age.bean.WpzyBean;
import cn.xihan.age.bean.ZxPlayerBean;
import cn.xihan.age.custom.MyTabLayout;
import cn.xihan.age.ui.fragments.players.NewPlayerFragment;
import cn.xihan.age.ui.fragments.webview.normal.NormalFragment;
import cn.xihan.age.ui.fragments.webview.x5.X5Fragment;
import cn.xihan.age.utils.DatabaseUtil;
import cn.xihan.age.utils.MySingleton;
import cn.xihan.age.utils.Utils;

import static android.content.Context.MODE_PRIVATE;
import static cn.xihan.age.Config.AniInfoApi;
import static cn.xihan.age.Config.tiebaApi;

/**
 * @项目名 : AGE动漫
 * @作者 : MissYang
 * @创建时间 : 2021/3/2 2:12
 * @介绍 : 番剧详情
 */
public class AniInfosFragment extends BaseFragment {

    FrameLayout layout;
    @BindView(R.id.fm)
    ImageView        fm;
    @BindView(R.id.fjmc)
    TextView         tv_fjmc;
    @BindView(R.id.ly)
    TextView         tv_ly;
    @BindView(R.id.ybfjmc)
    TextView         tv_ybfjmc;
    @BindView(R.id.sbsj)
    TextView         tv_sbsj;
    @BindView(R.id.jqlx)
    TextView         tv_jqlx;
    @BindView(R.id.rd)
    TextView         tv_rd;
    @BindView(R.id.tl)
    TextView         tv_tl;
    @BindView(R.id.sc)
    TextView         tv_sc;
    @BindView(R.id.jj)
    TextView         tv_jj;
    @BindView(R.id.tab_layout)
    MyTabLayout      tabLayout;
    @BindView(R.id.wpljbt)
    TextView         tv_wpljbt;
    @BindView(R.id.wpljmm)
    TextView         tv_wpljmm;
    @BindView(R.id.lv_xgdh)
    RecyclerView     lvXgdh;
    @BindView(R.id.cnxhgridview)
    RecyclerView     cnxhgridview;
    @BindView(R.id.topbar)
    QMUITopBarLayout topbar;
    @BindView(R.id.bflb)
    RecyclerView     bflb;

    final AniPreSimBean aniPreSimBean = new AniPreSimBean();

    final List<VideoBean>     dataVideoBean     = new ArrayList<>();
    final List<AniPreSimBean> dataBeanAniPreSim = new ArrayList<>();
    final List<AniPreSimBean> dataXgdhBean      = new ArrayList<>();
    final List<ZxPlayerBean>  dataPlayList0     = new ArrayList<>();
    final List<ZxPlayerBean>  dataPlayList1     = new ArrayList<>();
    final List<ZxPlayerBean>  dataPlayList2     = new ArrayList<>();
    final List<ZxPlayerBean>  dataPlayList3     = new ArrayList<>();
    List<ZxPlayerBean> dataPlayList = new ArrayList<>();

    BaseRecyclerAdapter<ZxPlayerBean>  mPlayListAdapter;
    BaseRecyclerAdapter<AniPreSimBean> mAniPreSimAdapter;
    BaseRecyclerAdapter<AniPreSimBean> mPreSimAdapter;

    String aid, url, fjmc, ly, ybfjmc, sbsj, jqlx, rd, tl, sc, wpljbt, wpljurl, wpljmm, xgdh, jj, fjfm;

    Bundle bundle, bundle2;
    RequestQueue queue;
    Context      ctx;

    AniInfosFragment  aniInfosFragment;

    NewPlayerFragment newPlayerFragment;
    X5Fragment x5Fragment;
    NormalFragment normalFragment;

    boolean isLogin;
    @BindView(R.id.iv_sc)
    ImageView ivSc;

    @Override
    protected View onCreateView() {
        layout = (FrameLayout) LayoutInflater.from(getActivity()).inflate(R.layout.fragment_aniinfos, null);
        ButterKnife.bind(this, layout);
        ctx = requireContext();
        queue = MySingleton.getInstance(ctx).getRequestQueue();
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("data",MODE_PRIVATE);
        boolean isOpenDiyApi = sharedPreferences.getBoolean("DefaultOpenApi",false);
        String DiyApi =sharedPreferences.getString("diyApi",null);
        String Api = sharedPreferences.getString("Api",null);
        String Api2 = sharedPreferences.getString("Api2",null);
        initClear();
        bundle2 = new Bundle();
        bundle = getArguments();
        if (bundle != null) {
            aid = bundle.getString("aid");
            url = bundle.getString("url");
            //Log.d("xihan","得到的数据:" +bundle.getString("aid") + "\n" + bundle.getString("url"));
            if (isOpenDiyApi){
                if (!TextUtils.isEmpty(DiyApi)){
                    startrequst(DiyApi +bundle.getString("aid") + ".json");
                    Log.d("xihan","自定义Api:" +DiyApi +bundle.getString("aid") + ".json");
                }else{
                    if (!TextUtils.isEmpty(Api)){
                        startrequst(Api +bundle.getString("aid") + ".json");
                        Log.d("xihan","自定义Api2:" +Api +bundle.getString("aid") + ".json");
                    }else if (!TextUtils.isEmpty(Api2)){
                        startrequst(Api2 +bundle.getString("aid") + ".json");
                        Log.d("xihan","自定义Api3:" +Api2 +bundle.getString("aid") + ".json");
                    }
                }

            }else{
                startrequst(bundle.getString("url"));
            }
            Log.d("xihan","得到的数据:" + bundle.getString("aid"));

        }
        initTopbar();
        return layout;
    }

    private void initClear() {
        dataPlayList0.clear();
        dataPlayList1.clear();
        dataPlayList2.clear();
        dataPlayList3.clear();
        dataBeanAniPreSim.clear();
        dataXgdhBean.clear();
        dataVideoBean.clear();
    }

    /**
     * 请求数据
     *
     * @param url
     */
    private void startrequst(String url) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, jsonObject -> {
            //Log.d("xihan","数据:" + jsonObject);
            if (!TextUtils.isEmpty(String.valueOf(jsonObject))) {
                try {
                    JSONObject AniInfo = new JSONObject(jsonObject.optString("AniInfo"));
                    fjmc = AniInfo.optString("R动画名称");
                    ly = AniInfo.optString("R地区") + " · " + AniInfo.optString("R动画种类") + " · " + AniInfo.optString("R原作");
                    ybfjmc = AniInfo.optString("R原版名称");
                    sbsj = AniInfo.optString("R首播时间");
                    jqlx = AniInfo.optString("R剧情类型");
                    int rdd = AniInfo.optInt("RankCnt");
                    rd = Utils.intChange2Str(rdd);
                    tl = AniInfo.optString("CommentCnt");
                    sc = AniInfo.optString("CollectCnt");
                    xgdh = AniInfo.optString("AniPreRel");
                    jj = AniInfo.optString("R简介");
                    fjfm = AniInfo.optString("R封面图").replace("http:", "").replace("https:", "");
                    JSONArray wpzy = new JSONArray(AniInfo.optString("R网盘资源2"));
                    if (wpzy.length() > 0) {
                        for (int i = 0; i < wpzy.length(); i++) {
                            JSONObject wpzyjb = wpzy.getJSONObject(i);
                            WpzyBean wpzyBean = new WpzyBean();
                            wpzyBean.setTitle(wpzyjb.optString("Title"));
                            wpzyBean.setLink(wpzyjb.optString("Link"));
                            wpzyBean.setExCode(wpzyjb.optString("ExCode"));
                            wpljbt = wpzyjb.optString("Title");
                            wpljmm = wpzyjb.optString("ExCode");
                            wpljurl = wpzyjb.optString("Link");

                        }
                    }
                    //猜你喜欢
                    JSONArray cnxharray = new JSONArray(jsonObject.optString("AniPreSim"));
                    //Log.d("xihan","猜你喜欢:"+cnxharray.toString());
                    if (cnxharray.length() > 0) {
                        for (int i = 0; i < cnxharray.length(); i++) {
                            JSONObject cnxhjb = cnxharray.getJSONObject(i);
                            AniPreSimBean aniPreSimBean = new AniPreSimBean();
                            aniPreSimBean.setAid(cnxhjb.optString("AID"));
                            aniPreSimBean.setTitle(cnxhjb.optString("Title"));
                            aniPreSimBean.setPicSmall(cnxhjb.optString("PicSmall"));
                            aniPreSimBean.setNewTitle(cnxhjb.optString("NewTitle"));
                            dataBeanAniPreSim.add(aniPreSimBean);
                        }
                    }
                    //相关动画
                    JSONArray xgdharray = new JSONArray(jsonObject.optString("AniPreRel"));
                    if (xgdharray.length() > 0) {
                        for (int i = 0; i < xgdharray.length(); i++) {
                            JSONObject xgdhjb = xgdharray.getJSONObject(i);
                            AniPreSimBean xgdhaniPreSimBean = new AniPreSimBean();
                            xgdhaniPreSimBean.setAid(xgdhjb.optString("AID"));
                            xgdhaniPreSimBean.setTitle(xgdhjb.optString("Title"));
                            //Log.d("xihan","相关动画获取："+xgdhjb.optString("AID")+xgdhjb.optString("Title"));
                            dataXgdhBean.add(xgdhaniPreSimBean);
                        }
                    }
                    String R在线播放All = AniInfo.optString("R在线播放All");
                    if (!TextUtils.isEmpty(R在线播放All)) {
                        JSONArray RzplayArray = new JSONArray(R在线播放All);
                        JSONArray jsonArray = RzplayArray.getJSONArray(0); //获取0数组
                        if (jsonArray.length() > 0) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject RZPlayJb = jsonArray.getJSONObject(i);
                                ZxPlayerBean zxPlayerBean = new ZxPlayerBean();
                                String PlayId = RZPlayJb.optString("PlayId");
                                //Log.d("xihan","播放列表1:" +PlayId );
                                if (PlayId.equals("<play>tieba</play>")) {
                                    zxPlayerBean.setPlayVid(tiebaApi + URLDecoder.decode(RZPlayJb.optString("PlayVid"), "UTF-8"));
                                } else {
                                    zxPlayerBean.setPlayVid(URLDecoder.decode(RZPlayJb.optString("PlayVid"), "UTF-8"));
                                }
                                zxPlayerBean.setTitle(RZPlayJb.optString("Title"));
                                zxPlayerBean.setPlayId(RZPlayJb.optString("PlayId"));
                                zxPlayerBean.setEpName(RZPlayJb.optString("EpName"));
                                dataPlayList0.add(zxPlayerBean);
                                //Log.d("xihan","dataPlayList0结果:" +URLDecoder.decode(RZPlayJb.optString("PlayVid"),"UTF-8") );
                            }
                        }

                        JSONArray jsonArray1 = RzplayArray.getJSONArray(1); //获取1数组
                        if (jsonArray1.length() > 0) {
                            for (int i = 0; i < jsonArray1.length(); i++) {
                                JSONObject RZPlayJb = jsonArray1.getJSONObject(i);
                                ZxPlayerBean zxPlayerBean = new ZxPlayerBean();
                                String PlayId = RZPlayJb.optString("PlayId");
                                //Log.d("xihan","播放列表2:" +PlayId );
                                if (PlayId.equals("<play>tieba</play>")) {
                                    zxPlayerBean.setPlayVid(tiebaApi + URLDecoder.decode(RZPlayJb.optString("PlayVid"), "UTF-8"));
                                } else {
                                    zxPlayerBean.setPlayVid(URLDecoder.decode(RZPlayJb.optString("PlayVid"), "UTF-8"));
                                }

                                        zxPlayerBean.setPlayVid(URLDecoder.decode(RZPlayJb.optString("PlayVid"), "UTF-8"));
                                        zxPlayerBean.setPlayId(RZPlayJb.optString("PlayId"));
                                        zxPlayerBean.setEpName(RZPlayJb.optString("EpName"));
                                        zxPlayerBean.setTitle(RZPlayJb.optString("Title"));
                                        dataPlayList1.add(zxPlayerBean);


                                //Log.d("xihan","dataPlayList1结果:" +URLDecoder.decode(RZPlayJb.optString("PlayVid"),"UTF-8") );
                            }
                        }

                        JSONArray jsonArray2 = RzplayArray.getJSONArray(2); //获取2数组
                        if (jsonArray2.length() > 0) {
                            for (int i = 0; i < jsonArray2.length(); i++) {
                                JSONObject RZPlayJb = jsonArray2.getJSONObject(i);
                                ZxPlayerBean zxPlayerBean = new ZxPlayerBean();
                                String PlayId = RZPlayJb.optString("PlayId");
                                //Log.d("xihan","播放列表3:" +PlayId );

                                if (PlayId.equals("<play>tieba</play>")) {
                                    zxPlayerBean.setPlayVid(tiebaApi + URLDecoder.decode(RZPlayJb.optString("PlayVid"), "UTF-8"));
                                } else {
                                    zxPlayerBean.setPlayVid(URLDecoder.decode(RZPlayJb.optString("PlayVid"), "UTF-8"));
                                }

                                        zxPlayerBean.setPlayVid(URLDecoder.decode(RZPlayJb.optString("PlayVid"), "UTF-8"));
                                        zxPlayerBean.setPlayId(RZPlayJb.optString("PlayId"));
                                        zxPlayerBean.setEpName(RZPlayJb.optString("EpName"));
                                        zxPlayerBean.setTitle(RZPlayJb.optString("Title"));
                                        dataPlayList2.add(zxPlayerBean);


                                //Log.d("xihan","dataPlayList2结果:" +URLDecoder.decode(RZPlayJb.optString("PlayVid"),"UTF-8") );
                            }
                        }

                        JSONArray jsonArray3 = RzplayArray.getJSONArray(3); //获取2数组
                        if (jsonArray3.length() > 0) {
                            for (int i = 0; i < jsonArray3.length(); i++) {
                                JSONObject RZPlayJb = jsonArray3.getJSONObject(i);
                                ZxPlayerBean zxPlayerBean = new ZxPlayerBean();
                                String PlayId = RZPlayJb.optString("PlayId");
                                //Log.d("xihan","播放列表4:" +PlayId );
                                zxPlayerBean.setTitle(RZPlayJb.optString("Title"));
                                if (PlayId.equals("<play>tieba</play>")) {
                                    zxPlayerBean.setPlayVid(tiebaApi + URLDecoder.decode(RZPlayJb.optString("PlayVid"), "UTF-8"));
                                } else {
                                    zxPlayerBean.setPlayVid(URLDecoder.decode(RZPlayJb.optString("PlayVid"), "UTF-8"));
                                }
                                    zxPlayerBean.setPlayVid(URLDecoder.decode(RZPlayJb.optString("PlayVid"), "UTF-8"));
                                    zxPlayerBean.setPlayId(RZPlayJb.optString("PlayId"));
                                    zxPlayerBean.setEpName(RZPlayJb.optString("EpName"));
                                    zxPlayerBean.setTitle(RZPlayJb.optString("Title"));
                                    dataPlayList3.add(zxPlayerBean);

                                //Log.d("xihan","dataPlayList2结果:" +URLDecoder.decode(RZPlayJb.optString("PlayVid"),"UTF-8") );
                            }
                        }
                    }
                    setPlayList();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                requireActivity().runOnUiThread(this::setData);

            }

        }, null);
        queue.add(jsonObjectRequest);
    }

//    String ServerTime;
//    String videoUrl;
//    private void initZxPlayer(String playId, String epName, String playVid,String title,List<ZxPlayerBean> list) {
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Config.getPlay, null, jsonObject -> {
//            if (!TextUtils.isEmpty(String.valueOf(jsonObject))) {
//                try {
//                    ServerTime = jsonObject.optString("ServerTime");
//                    if (!TextUtils.isEmpty(ServerTime)){
//                        String PlayId = URLEncoder.encode(playId);
//                        String i2 = ServerTime + "{|}" + playId
//                                + "{|}" + playVid + "{|}agefans3382-getplay-1719";
//                        String urll = "https://play.agefans.net:8443/_getplay2_m5?playid=" + PlayId
//                                +"&vid=" + playVid + "&kt=" + ServerTime + "&kp=" + Md5Util.MD55(i2);
//                        Log.d("xihan","拼接：" + urll);
//                        JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest(urll, null, jsonObject2 -> {
//                            if (!TextUtils.isEmpty(String.valueOf(jsonObject2))) {
//                                try {
//                                    ZxPlayerBean zxPlayerBean = new ZxPlayerBean();
//                                    videoUrl = jsonObject2.optString("vurl");
//                                    zxPlayerBean.setPlayVid(URLDecoder.decode(jsonObject2.optString("vurl"), "UTF-8"));
//                                    zxPlayerBean.setPlayId(playId);
//                                    zxPlayerBean.setEpName(epName);
//                                    zxPlayerBean.setTitle(title);
//                                    list.add(zxPlayerBean);
//                                    //Log.d("xihan","结果：" + jsonObject2.optString("vurl") );
//                                } catch (Exception exception) {
//                                    exception.printStackTrace();
//                                }
//                            }
//                        }, null);
//                        queue.add(jsonObjectRequest2);
//                    }
//                    //Log.d("xihan","服务器时间:" + ServerTime);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }, null);
//        queue.add(jsonObjectRequest);
//    }


    /**
     * 设置数据
     */
    private void setData() {
        Glide.with(ctx).load("https:" + fjfm).into(fm);
        tv_fjmc.setText(fjmc);
        tv_ly.setText(ly);
        tv_ybfjmc.setText("原版名称：" + ybfjmc);
        tv_sbsj.setText("首播时间：" + sbsj);
        tv_jqlx.setText("剧情类型：" + jqlx);
        tv_rd.setText(rd);
        tv_tl.setText(tl);
        tv_sc.setText(sc);
        tv_jj.setText("简介:" + jj);
        if (TextUtils.isEmpty(wpljbt)) {
            //Log.d("xihan","网盘链接:"+ wpljbt + "\n网盘密码:" + wpljmm);
            tv_wpljbt.setText("暂无");
        } else {
            tv_wpljbt.setText(wpljbt);
            tv_wpljmm.setText("提取码:" + wpljmm);
        }
        tv_wpljbt.setOnClickListener(v -> {
            if (TextUtils.isEmpty(wpljbt)) {
                Toast.makeText(getContext(), "暂无", Toast.LENGTH_SHORT).show();
            } else {
                goBDY();
            }

        });

        lvXgdh.setLayoutManager(new LinearLayoutManager(ctx) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        mAniPreSimAdapter = new BaseRecyclerAdapter<AniPreSimBean>(ctx, dataXgdhBean) {
            @Override
            public int getItemLayoutId() {
                return R.layout.item_xgdh;
            }

            @Override
            public void bindData(RecyclerViewHolder holder, AniPreSimBean item) {
                TextView tv_title = holder.itemView.findViewById(R.id.Fjtitle);
                tv_title.setText(item.getTitle());
                tv_title.setTextColor(Color.WHITE);

            }
        };
        lvXgdh.setAdapter(mAniPreSimAdapter);
        if (dataXgdhBean.size() < 10) {
            RelativeLayout.LayoutParams lp =
                    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, dataXgdhBean.size() * 91);
            lvXgdh.setLayoutParams(lp);
        }
        cnxhgridview.setLayoutManager(new LinearLayoutManager(ctx) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        mPreSimAdapter = new BaseRecyclerAdapter<AniPreSimBean>(ctx, dataBeanAniPreSim) {
            @Override
            public int getItemLayoutId() {
                return R.layout.my_home_item_layout;
            }

            @Override
            public void bindData(RecyclerViewHolder holder, AniPreSimBean item) {
                TextView name = holder.itemView.findViewById(R.id.title);
                TextView newtitle = holder.itemView.findViewById(R.id.newtitle);
                ImageView cover = holder.itemView.findViewById(R.id.cover);
                String isHttp = item.getPicSmall().replace("http:", "").replace("https:", "");
                Glide.with(ctx).load(Config.Https + isHttp).into(cover);
                name.setText(item.getTitle());
                newtitle.setText(item.getNewTitle());
                name.setTextColor(Color.WHITE);
                newtitle.setTextColor(Color.WHITE);
            }
        };
        cnxhgridview.setAdapter(mPreSimAdapter);
        cnxhgridview.setLayoutManager(new GridLayoutManager(ctx, 2));
        mPreSimAdapter.setOnItemClickListener((itemView, pos) -> {
            AniPreSimBean aniPreSimBean = dataBeanAniPreSim.get(pos);
            String url = AniInfoApi + aniPreSimBean.getAid();
            String aid = Config.detailUrl + aniPreSimBean.getAid();
            Bundle bundle = new Bundle();
            bundle.putString("url", url);
            bundle.putString("aid", aid);
            if (aniInfosFragment == null) {
                aniInfosFragment = new AniInfosFragment();
            }
            aniInfosFragment.setArguments(bundle);
            startFragment(aniInfosFragment);
        });
        requireActivity().runOnUiThread(this::setScData);
    }

    /**
     * 设置收藏数据
     */
    private void setScData() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("data", MODE_PRIVATE);
        String UserName = sharedPreferences.getString("username", "");
        String Sign_t = sharedPreferences.getString("Sign_t", "");
        String Sign_k = sharedPreferences.getString("Sign_k", "");
        isLogin = sharedPreferences.getBoolean("isLogin", false);
        String ScAid = sharedPreferences.getString("ScAid", "");
        boolean isSc = false;

        if (bundle != null) {
            isSc = ScAid.contains(bundle.getString("aid"));
            Log.d("xihan", "Aid:" + bundle.getString("aid") + "\n" + aid + "是否收藏:" + isSc);
        }

        if (isSc) {
            Glide.with(requireContext()).load(R.drawable.ic_sc).into(ivSc);
        }

        boolean finalIsSc = isSc;
        if (DatabaseUtil.checkFavorite(fjmc)){
            Glide.with(requireContext()).load(R.drawable.ic_sc).into(ivSc);
        }
        boolean isBdsc = sharedPreferences.getBoolean("本地收藏",false);
        if (isBdsc){
            ivSc.setOnClickListener(v -> {
                if (!TextUtils.isEmpty(fjmc)){
                    aniPreSimBean.setTitle(fjmc);
                    aniPreSimBean.setPicSmall("https:" + fjfm);
                    aniPreSimBean.setAid(aid);
                }
                boolean isFavorite = DatabaseUtil.favorite(aniPreSimBean);
                if (isFavorite){
                    Glide.with(requireContext()).load(R.drawable.ic_sc).into(ivSc);
                    Snackbar.make(v, "(´・ω・`) 自己追的番就要好好看完哦~", Snackbar.LENGTH_LONG).show();
                }else{
                    DatabaseUtil.deleteFavorite(fjmc);
                    Glide.with(requireContext()).load(R.drawable.ic_sc2).into(ivSc);
                    Snackbar.make(v, "(﹁\" ﹁) 哼！竟然真的抛弃了人家~", Snackbar.LENGTH_LONG).show();
                }
                Log.d("xihan","收藏结果:" +isFavorite);
            });
        }else{
            ivSc.setOnClickListener(v -> {
                if (isLogin) {
                    if (finalIsSc) {
                        ivSc.setOnClickListener(v1 -> {
                            deleteSc(UserName, Sign_t, Sign_k);
                            Snackbar.make(v, "(﹁\" ﹁) 哼！竟然真的抛弃了人家~", Snackbar.LENGTH_LONG).show();
                        });
                    } else {
                        ivSc.setOnClickListener(v2 -> {
                            AddSc(UserName, Sign_t, Sign_k);
                            Snackbar.make(v, "(´・ω・`) 自己追的番就要好好看完哦~", Snackbar.LENGTH_LONG).show();
                            Toast.makeText(requireActivity(), "请重进收藏看是否成功", Toast.LENGTH_SHORT).show();
                        });
                    }
                } else {
                    Toast.makeText(requireActivity(), "未登录,请登录后重试!", Toast.LENGTH_SHORT).show();
                }
            });
        }






    }

    /**
     * 删除收藏
     *
     * @param userName
     * @param sign_t
     * @param sign_k
     */
    private void deleteSc(String userName, String sign_t, String sign_k) {
        String DelData = Config.DeleteScApi + "?aid=" + aid
                + "&username=" + userName + "&sign_t=" + sign_t + "&sign_k=" + URLEncoder.encode(sign_k);
        JsonRequest jsonRequest = new JsonObjectRequest(1, DelData, null, jsonObject -> {
            if (!TextUtils.isEmpty(String.valueOf(jsonObject))) {
                String status = jsonObject.optString("Status");
                if (status.equals("200")) {
                    Toast.makeText(ctx, "删除成功，请重进收藏刷新", Toast.LENGTH_SHORT).show();
                }
                //Log.d("xihan","删除结果:" + status);
            }
        }, null);
        queue.add(jsonRequest);


    }

    /**
     * 加入收藏
     *
     * @param userName
     * @param sign_t
     * @param sign_k
     */
    private void AddSc(String userName, String sign_t, String sign_k) {
        String AddData = Config.AddScApi + "?aid=" + aid
                + "&username=" + userName + "&sign_t=" + sign_t + "&sign_k=" + URLEncoder.encode(sign_k);
        //Log.d("xihan","删除收藏拼接URL:" + DelData);
        JsonRequest jsonRequest = new JsonObjectRequest(1, AddData, null, jsonObject -> {
            if (!TextUtils.isEmpty(String.valueOf(jsonObject))) {
                String status = jsonObject.optString("Status");
                if (status.equals("200")) {
                    Toast.makeText(ctx, "添加成功，重进收藏生效", Toast.LENGTH_SHORT).show();
                }
                //Log.d("xihan","删除结果:" + status);
            }
        }, null);
        queue.add(jsonRequest);

    }

    /**
     * 设置播放列表
     */
    private void setPlayList() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("data", MODE_PRIVATE);
        boolean isXbj = sharedPreferences.getBoolean("启用新布局", false);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (String.valueOf(tab.getText())) {
                    case "播放列表1":
                        bundle2.putString("name", "1");
                        break;
                    case "播放列表2":
                        bundle2.putString("name", "2");
                        break;
                    case "播放列表3":
                        bundle2.putString("name", "3");
                        break;
                    case "播放列表4":
                        bundle2.putString("name", "4");
                        break;
                }
                setPlayListData();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        requireActivity().runOnUiThread(this::setTabLayout);
    }

    /**
     * 设置播放列表数据
     */
    private void setPlayListData() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("data", MODE_PRIVATE);
        boolean isXbj = sharedPreferences.getBoolean("启用新布局", false);
        bflb.setLayoutManager(new LinearLayoutManager(ctx) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        if (!TextUtils.isEmpty(bundle2.getString("name"))) {
            switch (bundle2.getString("name")) {
                case "1":
                    dataPlayList = dataPlayList0;
                    break;
                case "2":
                    dataPlayList = dataPlayList1;
                    break;
                case "3":
                    dataPlayList = dataPlayList2;
                    break;
                case "4":
                    dataPlayList = dataPlayList3;
                    break;
            }
        }
        mPlayListAdapter = new BaseRecyclerAdapter<ZxPlayerBean>(ctx, dataPlayList) {
            @Override
            public int getItemLayoutId() {
                return R.layout.item_play;
            }

            @Override
            public void bindData(RecyclerViewHolder holder, ZxPlayerBean item) {
                TextView tv_playlist = holder.itemView.findViewById(R.id.Fjtitle);
                tv_playlist.setText(item.getTitle());
            }
        };

        bflb.setAdapter(mPlayListAdapter);

//        if (dataPlayList.size() < 30) {
//            RelativeLayout.LayoutParams lp =
//                    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, dataPlayList.size() * 91);
//            bflb.setLayoutParams(lp);
//        }
        mPlayListAdapter.setOnItemClickListener((itemView, pos) -> {
            Log.e("xihan","值:" +dataPlayList);
            ZxPlayerBean zxPlayerBean = dataPlayList.get(pos);
            String PlayUrl = zxPlayerBean.getPlayVid();
            //Log.d("xihan","playUrl2:" +PlayUrl );
            String FjTitle = zxPlayerBean.getTitle();
            String Playid = zxPlayerBean.getPlayId();
            Bundle bundle = new Bundle();
            bundle.putString("url", PlayUrl);
            bundle.putString("playid", Playid);
            bundle.putString("fjjs", FjTitle);
            bundle.putString("fjmc", fjmc);
            bundle.putInt("position", pos);
            if (!TextUtils.isEmpty(aid)) {
                bundle.putString("aid", aid);
            } else {
                bundle.putString("aid", url.replace(AniInfoApi, ""));
                //Log.d("xihan","结果:" + url.replace(AniInfoApi, ""));
            }
            bundle.putSerializable("PlayList", (Serializable) dataPlayList);
            if (newPlayerFragment == null) {
                newPlayerFragment = new NewPlayerFragment();
            }
            newPlayerFragment.setArguments(bundle);
            startFragment(newPlayerFragment);

        });

    }

    /**
     * 设置播放列表几?
     */
    private void setTabLayout() {
        if (!TextUtils.isEmpty(String.valueOf(dataPlayList0))) {
            for (int i = 0; i < dataPlayList0.size(); i++) {
                ZxPlayerBean zxPlayerBean = dataPlayList0.get(i);
                if (zxPlayerBean.getPlayId().contains("mp4")) {
                    Objects.requireNonNull(tabLayout.getTabAt(2)).select();
                }else if (zxPlayerBean.getPlayId().contains("第")) {
                    Objects.requireNonNull(tabLayout.getTabAt(0)).select();
                } else if (zxPlayerBean.getTitle() != null) {
                    Objects.requireNonNull(tabLayout.getTabAt(0)).select();
                }
                //Log.d("xihan","data0"+zxPlayerBean.getTitle());
            }
        }
        if (!TextUtils.isEmpty(String.valueOf(dataPlayList1))) {
            for (int i = 0; i < dataPlayList1.size(); i++) {
                ZxPlayerBean zxPlayerBean = dataPlayList1.get(i);
                if (zxPlayerBean.getPlayId().contains("mp4")) {
                    Objects.requireNonNull(tabLayout.getTabAt(2)).select();
                }else if (zxPlayerBean.getTitle().contains("第")) {
                    Objects.requireNonNull(tabLayout.getTabAt(1)).select();
                } else if (zxPlayerBean.getTitle() != null) {
                    Objects.requireNonNull(tabLayout.getTabAt(1)).select();
                }
                //Log.d("xihan","data1"+zxPlayerBean.getTitle());
            }
        }
        if (!TextUtils.isEmpty(String.valueOf(dataPlayList2))) {
            for (int i = 0; i < dataPlayList2.size(); i++) {
                ZxPlayerBean zxPlayerBean = dataPlayList2.get(i);
                if (zxPlayerBean.getPlayId().contains("mp4")) {
                    Objects.requireNonNull(tabLayout.getTabAt(2)).select();
                }else if (zxPlayerBean.getTitle().contains("第")) {
                    Objects.requireNonNull(tabLayout.getTabAt(2)).select();
                } else if (zxPlayerBean.getTitle() != null) {
                    Objects.requireNonNull(tabLayout.getTabAt(2)).select();
                }
                //Log.d("xihan","data2"+zxPlayerBean.getTitle());
            }
        }
        if (!TextUtils.isEmpty(String.valueOf(dataPlayList3))) {
            for (int i = 0; i < dataPlayList3.size(); i++) {
                ZxPlayerBean zxPlayerBean = dataPlayList3.get(i);
                if (zxPlayerBean.getPlayId().contains("mp4")) {
                    Objects.requireNonNull(tabLayout.getTabAt(2)).select();
                }else if (zxPlayerBean.getTitle().contains("第")) {
                    Objects.requireNonNull(tabLayout.getTabAt(3)).select();
                } else if (zxPlayerBean.getTitle() != null) {
                    Objects.requireNonNull(tabLayout.getTabAt(3)).select();
                }
                //Log.d("xihan","data2"+zxPlayerBean.getTitle());
            }
        }

    }

    /**
     * 设置标题
     */
    private void initTopbar() {
        topbar.addLeftBackImageButton().setOnClickListener(v -> popBackStack());
        injectEntrance(topbar);
        topbar.setTitle(getString(R.string.app_name));
    }

    public void injectEntrance(QMUITopBarLayout topbar) {
        topbar.addRightTextButton("···", QMUIViewHelper.generateViewId())
                .setOnClickListener(v -> showBottomSheetList(topbar.getContext()));
    }

    public void showBottomSheetList(Context context) {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("data", MODE_PRIVATE);
        boolean isX5 = sharedPreferences.getBoolean("X5State", false);
        //Log.d("xihan", "是否加载X5内核:" + isX5);
        new QMUIBottomSheet.BottomListSheetBuilder(context)
                .addItem("刷新")
                .addItem("内置浏览器打开")
                .addItem("外部浏览器打开")
                .setOnSheetItemClickListener((dialog, itemView, position, tag) -> {
                    //Toast.makeText(context, "点击了:" + position, Toast.LENGTH_SHORT).show();
                    switch (position) {
                        case 0:
                            if (dataPlayList != null) {
                                initClear();
                            }
                            bundle = getArguments();
                            if (bundle != null) {
                                aid = bundle.getString("aid");
                                url = bundle.getString("url");
                                //Log.d("xihan","得到的数据:" +bundle.getString("aid") + "\n" + bundle.getString("url"));
                                startrequst(bundle.getString("url"));
                            }
                            break;
                        case 1:
                            if (isX5) {
                                if (x5Fragment == null) {
                                    x5Fragment = new X5Fragment();
                                }
                                bundle2.putString("url", Config.detailUrl + aid);
                                x5Fragment.setArguments(bundle2);
                                startFragment(x5Fragment);
                            } else {
                                if (normalFragment == null) {
                                    normalFragment = new NormalFragment();
                                }
                                bundle2.putString("url", Config.detailUrl + aid);
                                bundle2.putString("title", fjmc);
                                bundle2.putString("aid", aid);
                                normalFragment.setArguments(bundle2);
                                startFragment(normalFragment);
                            }
                            break;
                        case 2:
                            Utils.goBrowser(Config.detailUrl + aid);
                            break;
                    }
                    dialog.dismiss();
                })
                .build()
                .show();
    }

    /**
     * 调用浏览器打开百度云
     */
    public void goBDY() {
        if (!TextUtils.isEmpty(wpljurl) && !TextUtils.isEmpty(wpljmm)) {
            Utils.copyString(requireContext(), wpljmm);
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse(wpljurl);
            Toast.makeText(getContext(), "已复制提取码:" + wpljmm, Toast.LENGTH_SHORT).show();
            intent.setData(content_url);
            startActivity(intent);
        } else {
            ShowError();
        }
    }

    /**
     * 错误对话框
     */
    private void ShowError() {
        final android.app.AlertDialog.Builder ad = new AlertDialog.Builder(ctx);
        ad.setTitle("提示");
        ad.setMessage("网盘链接为空或者提取码为空,请以后再试");
        ad.setPositiveButton("了解",
                (dialog, which) -> {
                    //...To-do
                    dialog.dismiss();
                });
        ad.show();

    }


}
