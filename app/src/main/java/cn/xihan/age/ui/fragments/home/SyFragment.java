package cn.xihan.age.ui.fragments.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.qmuiteam.qmui.arch.QMUIFragment;
import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;
import com.youth.banner.Banner;
import com.youth.banner.util.BannerUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.xihan.age.Config;
import cn.xihan.age.R;
import cn.xihan.age.adapter.ImageTitleNumAdapter;
import cn.xihan.age.base.BaseRecyclerAdapter;
import cn.xihan.age.base.RecyclerViewHolder;
import cn.xihan.age.bean.BannerDataBean;
import cn.xihan.age.bean.BannerDataBean2;
import cn.xihan.age.bean.DataBean;
import cn.xihan.age.bean.XinfanInfoBean;
import cn.xihan.age.custom.MyTabLayout;
import cn.xihan.age.ui.fragments.AniInfosFragment;
import cn.xihan.age.ui.fragments.SsFragment;
import cn.xihan.age.utils.MySingleton;
import cn.xihan.age.utils.Utils;

import static android.content.Context.MODE_PRIVATE;
import static cn.xihan.age.Config.AniInfoApi;
import static cn.xihan.age.Config.BannerApi;
import static cn.xihan.age.Config.Mainapi;
import static cn.xihan.age.Config.SosuoApi;

/**
 * @项目名 : AGE动漫
 * @作者 : MissYang
 * @创建时间 : 2021/3/2 1:00
 * @介绍 :
 */
public class SyFragment extends QMUIFragment {

    AniInfosFragment aniInfosFragment;
    SsFragment ssFragment;

    final List<XinfanInfoBean>          lvDatas                = new ArrayList<>();
    final List<DataBean.AniPreEvDayDTO> dataBeanAniPreEvDayDTO = new ArrayList<>();
    final List<DataBean.AniPreUPDTO>    dataBeanAniPreUPDTO    = new ArrayList<>();

    final List<XinfanInfoBean> dataXinfan1 = new ArrayList<>();
    final List<XinfanInfoBean> dataXinfan2 = new ArrayList<>();
    final List<XinfanInfoBean> dataXinfan3 = new ArrayList<>();
    final List<XinfanInfoBean> dataXinfan4 = new ArrayList<>();
    final List<XinfanInfoBean> dataXinfan5 = new ArrayList<>();
    final List<XinfanInfoBean> dataXinfan6 = new ArrayList<>();
    final List<XinfanInfoBean> dataXinfan7 = new ArrayList<>();
    List<XinfanInfoBean>                dataXinfan = new ArrayList<>();
    BaseRecyclerAdapter<XinfanInfoBean> mXinfanAdapter;

    BaseRecyclerAdapter<DataBean.AniPreEvDayDTO> mAniPreEvDayAdapter;
    BaseRecyclerAdapter<DataBean.AniPreUPDTO>    mAniPreUPDTOAdapter;

    final List<BannerDataBean> AniPreLBean = new ArrayList<>();
    @BindView(R.id.emptyView)
    QMUIEmptyView emptyView;
    private List<String> mAIDs;

    int week;

    Bundle bundle;

    String xqj;

    FrameLayout layout;
    Context     ctx;
    @BindView(R.id.et_search)
    EditText              etSearch;
    @BindView(R.id.iv_search)
    ImageView             ivSearch;
    @BindView(R.id.banner)
    Banner                banner;
    @BindView(R.id.tab_layout)
    MyTabLayout           tabLayout;
    @BindView(R.id.listview)
    RecyclerView          listview;
    @BindView(R.id.mrtjgridview)
    RecyclerView          mrtjgridview;
    @BindView(R.id.zjgxgridview)
    RecyclerView          zjgxgridview;
    @BindView(R.id.sv_scroll)
    ScrollView            svScroll;
    @BindView(R.id.pull_to_refresh)
    QMUIPullRefreshLayout pullToRefresh;
    @BindView(R.id.topbar)
    QMUITopBarLayout      topbar;

    RequestQueue queue;

    @Override
    protected View onCreateView() {
        layout = (FrameLayout) LayoutInflater.from(getActivity()).inflate(R.layout.fragment_sy, null);
        ButterKnife.bind(this, layout);
        ctx = requireContext();
        queue = MySingleton.getInstance(ctx).getRequestQueue();
        initView();
        initTopBar();
        initData();
        initPullRefreshLayout();
        return layout;
    }

    private void initView() {
        emptyView.show(true);
        pullToRefresh.setVisibility(View.GONE);
        ivSearch.setOnClickListener(v -> {
            String searchText = etSearch.getText().toString();
            if (!searchText.equals("")) {
                String SearchUrl = SosuoApi + searchText;
                Bundle bundle = new Bundle();
                bundle.putString("url", SearchUrl);
        if (ssFragment == null){
            ssFragment = new SsFragment();
        }
        ssFragment.setArguments(bundle);
        startFragment(ssFragment);
            } else {
                Toast.makeText(getActivity(), "搜索内容不能为空", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initPullRefreshLayout() {
        pullToRefresh.setOnPullListener(new QMUIPullRefreshLayout.OnPullListener() {
            @Override
            public void onMoveTarget(int offset) {

            }

            @Override
            public void onMoveRefreshView(int offset) {

            }

            @Override
            public void onRefresh() {
                AniPreLBean.clear();
                dataBeanAniPreEvDayDTO.clear();
                dataBeanAniPreUPDTO.clear();
                dataXinfan1.clear();
                dataXinfan2.clear();
                dataXinfan3.clear();
                dataXinfan4.clear();
                dataXinfan5.clear();
                dataXinfan6.clear();
                dataXinfan7.clear();
                dataXinfan.clear();
                pullToRefresh.postDelayed(() -> {
                    initData();
                    pullToRefresh.finishRefresh();
                }, 2000);
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {
        bundle = new Bundle();
        AniPreLBean.clear();
        dataBeanAniPreEvDayDTO.clear();
        dataBeanAniPreUPDTO.clear();
        dataXinfan1.clear();
        dataXinfan2.clear();
        dataXinfan3.clear();
        dataXinfan4.clear();
        dataXinfan5.clear();
        dataXinfan6.clear();
        dataXinfan7.clear();
        dataXinfan.clear();
        getBannerData();
        getData();
        setWeekData();
    }

    /**
     * 得到新番列表
     */
    private void getData() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("data",MODE_PRIVATE);
        boolean isOpenDiyApi = sharedPreferences.getBoolean("DefaultOpenApi",false);
        String DiyApi =sharedPreferences.getString("diyApi",null);
        String Api = sharedPreferences.getString("Api",null);
        String Api2 = sharedPreferences.getString("Api2",null);
        String url = null;
        if (isOpenDiyApi){
            if (!TextUtils.isEmpty(DiyApi)){
                url = DiyApi+ "home-list.json";
                Log.d("xihan","自定义首页Api:" +url);
            }else{
                if (!TextUtils.isEmpty(Api)){
                    url = Api+ "home-list.json";
                    Log.d("xihan","自定义首页Api:" +url);
                }else if (!TextUtils.isEmpty(Api2)){
                    url = Api2+ "home-list.json";
                    Log.d("xihan","自定义首页Api:" +url);
                }
            }
        }else {
            url = Mainapi;
        }
        if (!TextUtils.isEmpty(url)){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, jsonObject -> {
            if (!TextUtils.isEmpty(String.valueOf(jsonObject))) {
                //Log.d("xihan","数据:" + String.valueOf(jsonObject));
                try {
                    JSONArray XinfansInfoArray = new JSONArray(jsonObject.optString("XinfansInfo"));
                    if (XinfansInfoArray.length() > 0) {
                        for (int i = 0; i < XinfansInfoArray.length(); i++) {
                            JSONObject XinfansInfo = XinfansInfoArray.getJSONObject(i);
                            XinfanInfoBean xinfanInfoBean = new XinfanInfoBean();
                            int wd = XinfansInfo.optInt("wd");
                            switch (wd) {
                                case 0:
                                    xinfanInfoBean.setName(XinfansInfo.optString("name"));
                                    xinfanInfoBean.setId(XinfansInfo.optString("id"));
                                    xinfanInfoBean.setIsnew(XinfansInfo.optBoolean("isnew"));
                                    xinfanInfoBean.setNamefornew(XinfansInfo.optString("namefornew"));
                                    dataXinfan7.add(xinfanInfoBean);
                                    break;
                                case 1:
                                    xinfanInfoBean.setName(XinfansInfo.optString("name"));
                                    xinfanInfoBean.setId(XinfansInfo.optString("id"));
                                    xinfanInfoBean.setIsnew(XinfansInfo.optBoolean("isnew"));
                                    xinfanInfoBean.setNamefornew(XinfansInfo.optString("namefornew"));
                                    dataXinfan1.add(xinfanInfoBean);
                                    break;
                                case 2:
                                    xinfanInfoBean.setName(XinfansInfo.optString("name"));
                                    xinfanInfoBean.setId(XinfansInfo.optString("id"));
                                    xinfanInfoBean.setIsnew(XinfansInfo.optBoolean("isnew"));
                                    xinfanInfoBean.setNamefornew(XinfansInfo.optString("namefornew"));
                                    dataXinfan2.add(xinfanInfoBean);
                                    break;
                                case 3:
                                    xinfanInfoBean.setName(XinfansInfo.optString("name"));
                                    xinfanInfoBean.setId(XinfansInfo.optString("id"));
                                    xinfanInfoBean.setIsnew(XinfansInfo.optBoolean("isnew"));
                                    xinfanInfoBean.setNamefornew(XinfansInfo.optString("namefornew"));
                                    dataXinfan3.add(xinfanInfoBean);
                                    break;
                                case 4:
                                    xinfanInfoBean.setName(XinfansInfo.optString("name"));
                                    xinfanInfoBean.setId(XinfansInfo.optString("id"));
                                    xinfanInfoBean.setIsnew(XinfansInfo.optBoolean("isnew"));
                                    xinfanInfoBean.setNamefornew(XinfansInfo.optString("namefornew"));
                                    dataXinfan4.add(xinfanInfoBean);
                                    break;
                                case 5:
                                    xinfanInfoBean.setName(XinfansInfo.optString("name"));
                                    xinfanInfoBean.setId(XinfansInfo.optString("id"));
                                    xinfanInfoBean.setIsnew(XinfansInfo.optBoolean("isnew"));
                                    xinfanInfoBean.setNamefornew(XinfansInfo.optString("namefornew"));
                                    dataXinfan5.add(xinfanInfoBean);
                                    break;
                                case 6:
                                    xinfanInfoBean.setName(XinfansInfo.optString("name"));
                                    xinfanInfoBean.setId(XinfansInfo.optString("id"));
                                    xinfanInfoBean.setIsnew(XinfansInfo.optBoolean("isnew"));
                                    xinfanInfoBean.setNamefornew(XinfansInfo.optString("namefornew"));
                                    dataXinfan6.add(xinfanInfoBean);
                                    break;
                            }
                        }
                    }
                    JSONArray AniPreEvDay = jsonObject.getJSONArray("AniPreEvDay");
                    if (AniPreEvDay.length() > 0) {
                        for (int i = 0; i < AniPreEvDay.length(); i++) {
                            JSONObject jb = AniPreEvDay.getJSONObject(i);
                            DataBean.AniPreEvDayDTO dataBean = new DataBean.AniPreEvDayDTO();
                            dataBean.setAID(jb.optString("AID"));
                            dataBean.setTitle(jb.optString("Title"));
                            dataBean.setNewTitle(jb.optString("NewTitle"));
                            dataBean.setPicSmall(jb.optString("PicSmall"));
                            //Log.d("xihan", String.valueOf(dataBeanAniPreEvDayDTO));
                            //Log.d("xihan", String.valueOf(dataBean));
                            dataBeanAniPreEvDayDTO.add(dataBean);
                        }
                    }
                    JSONArray AniPreUP = jsonObject.getJSONArray("AniPreUP");
                    if (AniPreUP.length() > 0) {
                        for (int i = 0; i < AniPreUP.length(); i++) {
                            JSONObject jb = AniPreUP.getJSONObject(i);
                            DataBean.AniPreUPDTO dataBean = new DataBean.AniPreUPDTO();
                            dataBean.setAID(jb.optString("AID"));
                            dataBean.setTitle(jb.optString("Title"));
                            dataBean.setNewTitle(jb.optString("NewTitle"));
                            dataBean.setPicSmall(jb.optString("PicSmall"));
                            dataBeanAniPreUPDTO.add(dataBean);
                        }
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }

                requireActivity().runOnUiThread(this::setXinfanList);
                requireActivity().runOnUiThread(this::setData);
            }
        }, null);
        queue.add(jsonObjectRequest);
        }
    }

    /**
     * 设置"每日推荐"和"最近更新"数据
     */
    private void setData() {
        int spanCount = 3;
        mAniPreEvDayAdapter = new BaseRecyclerAdapter<DataBean.AniPreEvDayDTO>(ctx, dataBeanAniPreEvDayDTO) {
            @Override
            public int getItemLayoutId() {
                return R.layout.my_home_item_layout;
            }

            @Override
            public void bindData(RecyclerViewHolder holder, DataBean.AniPreEvDayDTO item) {
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
        mAniPreUPDTOAdapter = new BaseRecyclerAdapter<DataBean.AniPreUPDTO>(ctx, dataBeanAniPreUPDTO) {
            @Override
            public int getItemLayoutId() {
                return R.layout.my_home_item_layout;
            }

            @Override
            public void bindData(RecyclerViewHolder holder, DataBean.AniPreUPDTO item) {
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
        mrtjgridview.setAdapter(mAniPreEvDayAdapter);
        zjgxgridview.setAdapter(mAniPreUPDTOAdapter);
        mrtjgridview.setLayoutManager(new GridLayoutManager(ctx, spanCount));
        zjgxgridview.setLayoutManager(new GridLayoutManager(ctx, spanCount));
        mAniPreEvDayAdapter.setOnItemClickListener((itemView, pos) -> {
            DataBean.AniPreEvDayDTO datas = dataBeanAniPreEvDayDTO.get(pos);
            String url = AniInfoApi + datas.getAID();
            String aid = datas.getAID();
            Bundle bundle = new Bundle();
            bundle.putString("url", url);
            bundle.putString("aid", aid);
            if (aniInfosFragment == null) {
                aniInfosFragment = new AniInfosFragment();
            }
            aniInfosFragment.setArguments(bundle);
            startFragment(aniInfosFragment);
        });
        mAniPreUPDTOAdapter.setOnItemClickListener(((itemView, pos) -> {
            DataBean.AniPreUPDTO datas = dataBeanAniPreUPDTO.get(pos);
            String url = AniInfoApi + datas.getAID();
            String aid = datas.getAID();
            Bundle bundle = new Bundle();
            bundle.putString("url", url);
            bundle.putString("aid", aid);
            if (aniInfosFragment == null) {
                aniInfosFragment = new AniInfosFragment();
            }
            aniInfosFragment.setArguments(bundle);
            startFragment(aniInfosFragment);
        }));
    }

    /**
     * 设置新番列表
     */
    private void setXinfanList() {
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //Toast.makeText(getActivity(),"点击了: " + tab.getPosition(),Toast.LENGTH_SHORT).show();

                //Log.d("xihan","点击了:" + tab.getText() + "\n" + tab.getTag());
                switch (String.valueOf(tab.getText())) {
                    case "周一":
                        bundle.putString("name", "1");
                        break;
                    case "周二":
                        bundle.putString("name", "2");
                        break;
                    case "周三":
                        bundle.putString("name", "3");
                        break;
                    case "周四":
                        bundle.putString("name", "4");
                        break;
                    case "周五":
                        bundle.putString("name", "5");
                        break;
                    case "周六":
                        bundle.putString("name", "6");
                        break;
                    case "周日":
                        bundle.putString("name", "7");
                        break;
                }
                setXinfanData();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        requireActivity().runOnUiThread(this::setWeek);
        requireActivity().runOnUiThread(this::setHide);
    }

    private void setHide() {
        emptyView.show(false);
        pullToRefresh.setVisibility(View.VISIBLE);
    }

    /**
     * 设置新番数据
     */
    private void setXinfanData() {
        listview.setLayoutManager(new LinearLayoutManager(ctx) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        if (!TextUtils.isEmpty(bundle.getString("name"))) {
            switch (bundle.getString("name")) {
                case "1":
                    dataXinfan = dataXinfan1;
                    break;
                case "2":
                    dataXinfan = dataXinfan2;
                    break;
                case "3":
                    dataXinfan = dataXinfan3;
                    break;
                case "4":
                    dataXinfan = dataXinfan4;
                    break;
                case "5":
                    dataXinfan = dataXinfan5;
                    break;
                case "6":
                    dataXinfan = dataXinfan6;
                    break;
                case "7":
                    dataXinfan = dataXinfan7;
                    break;
            }
        }
        mXinfanAdapter = new BaseRecyclerAdapter<XinfanInfoBean>(ctx, dataXinfan) {
            @Override
            public int getItemLayoutId() {
                return R.layout.item_xinfan;
            }

            @Override
            public void bindData(RecyclerViewHolder holder, XinfanInfoBean item) {
                TextView Fjtitle = holder.itemView.findViewById(R.id.Fjtitle);
                TextView Fjjishu = holder.itemView.findViewById(R.id.Fjjishu);
                ImageView iv_new = holder.itemView.findViewById(R.id.iv_new);
                Fjtitle.setTextColor(Color.WHITE);
                Fjtitle.setText(item.getName());
                Fjjishu.setTextColor(Color.WHITE);
                Fjjishu.setText(item.getNamefornew());
                boolean isXinfan = item.getIsnew();
                if (isXinfan) {
                    iv_new.setVisibility(View.VISIBLE);
                }
            }
        };
        listview.setAdapter(mXinfanAdapter);
        //Log.d("xihan","List大小:" + dataXinfan.size());
//        RelativeLayout.LayoutParams lp =
//                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, dataXinfan.size() * 95);
//        listview.setLayoutParams(lp);
        mXinfanAdapter.setOnItemClickListener((itemView, pos) -> {
            XinfanInfoBean xinfanInfoBean = dataXinfan.get(pos);
            String url = AniInfoApi + xinfanInfoBean.getId();
            String aid = xinfanInfoBean.getId();
            Bundle bundle = new Bundle();
            bundle.putString("url", url);
            bundle.putString("aid", aid);
            if (aniInfosFragment == null) {
                aniInfosFragment = new AniInfosFragment();
            }
            aniInfosFragment.setArguments(bundle);
            startFragment(aniInfosFragment);
        });

    }

    /**
     * 设置星期
     */
    private void setWeek() {
        week = Utils.getWeekOfDate(new Date());
        try {
            //pager.setCurrentItem(week);
            Objects.requireNonNull(tabLayout.getTabAt(week)).select();
            //Log.d("xihan","星期:" + week);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void setWeekData() {
        int i = 2;
        int weeks = Utils.getWeekOfDate(new Date());
        int weeki = weeks + i;

        try {
            if (weeks <= 5) {
                Objects.requireNonNull(tabLayout.getTabAt(weeki)).select();
                // Log.d("xihan","================WEEK=:" +weeki );
            } else {
                Objects.requireNonNull(tabLayout.getTabAt(1)).select();
                //Log.d("xihan","================WEEK=:" +weeki  );
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * 获取轮播图数据
     */
    private void getBannerData() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("data",MODE_PRIVATE);
        boolean isOpenDiyApi = sharedPreferences.getBoolean("DefaultOpenApi",false);
        String DiyApi =sharedPreferences.getString("diyApi",null);
        String Api = sharedPreferences.getString("Api",null);
        String Api2 = sharedPreferences.getString("Api2",null);
        String url = null;
        if (isOpenDiyApi){
            if (!TextUtils.isEmpty(DiyApi)){
                url = DiyApi+ "slipic.json";
                Log.d("xihan","自定义BannerApi:" +url);
            }else{
                if (!TextUtils.isEmpty(Api)){
                    url = Api+ "slipic.json";
                    Log.d("xihan","自定义BannerApi:" +url);
                }else if (!TextUtils.isEmpty(Api2)){
                    url = Api2+ "slipic.json";
                    Log.d("xihan","自定义BannerApi:" +url);
                }
            }
        }else {
            url = BannerApi;
        }
        if (!TextUtils.isEmpty(url)){
        JsonArrayRequest JsonObjectRequest = new JsonArrayRequest(BannerApi, jsonArray -> {
            if (!TextUtils.isEmpty(String.valueOf(jsonArray))) {
                //Log.d("xihan", "结果:" + jsonArray);
                try {
                    if (jsonArray.length() > 0) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject BannerJb = jsonArray.getJSONObject(i);
                            BannerDataBean bannerDataBean = new BannerDataBean();
                            bannerDataBean.setAid(BannerJb.optString("AID"));
                            bannerDataBean.setTitle(BannerJb.optString("Title"));
                            bannerDataBean.setNewTitle(BannerJb.optString("NewTitle"));
                            bannerDataBean.setPicUrl(BannerJb.optString("PicUrl"));
                            bannerDataBean.setTime(BannerJb.optLong("Time"));
                            AniPreLBean.add(bannerDataBean);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                requireActivity().runOnUiThread(this::setBannerData);
            }
        }, null);
        queue.add(JsonObjectRequest); }
    }

    /**
     * 设置轮播图数据
     */
    private void setBannerData() {
        List<String> mTitless = new ArrayList<>();
        mAIDs = new ArrayList<>();
        List<String> mNewTiles = new ArrayList<>();
        List<String> mPicUrls = new ArrayList<>();
        List<BannerDataBean2> list = new ArrayList<>();
        if (AniPreLBean.size() > 0) {
            for (int i = 0; i < AniPreLBean.size(); i++) {
                list.add(new BannerDataBean2("https:" + AniPreLBean.get(i).getPicUrl(), AniPreLBean.get(i).getTitle(), 1));
                mTitless.add(AniPreLBean.get(i).getTitle());
                mAIDs.add(AniPreLBean.get(i).getAid());
                mNewTiles.add(AniPreLBean.get(i).getNewTitle());
                mPicUrls.add("https:" + AniPreLBean.get(i).getPicUrl());
            }
        }


        //Log.d("xihan","标题解析:" + mTitless);

        banner.setBannerRound(90);
        banner.setAdapter(new ImageTitleNumAdapter(getActivity(), list));
        banner.setOnBannerListener((data, position) -> {

            String url = AniInfoApi + mAIDs.get(position);
            String aid = mAIDs.get(position);
            Bundle bundle = new Bundle();
            bundle.putString("url", url);
            bundle.putString("aid", aid);
            if (aniInfosFragment == null) {
                aniInfosFragment = new AniInfosFragment();
            }
            aniInfosFragment.setArguments(bundle);
            startFragment(aniInfosFragment);

            //Toast.makeText(getActivity(),"点击了" +position + "这是：" +mAIDs.get(position) ,Toast.LENGTH_SHORT ).show();


        });
        banner.addBannerLifecycleObserver(this);
        banner.setIndicatorSpace((int) BannerUtils.dp2px(4));
        banner.setIndicatorRadius(0);
    }

    /**
     * 设置标题栏
     */
    private void initTopBar() {
        QMUITopBarLayout mTopBar = layout.findViewById(R.id.topbar);
        mTopBar.setTitle("AGE动漫");
    }


    int scrollX, scrollY;

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (svScroll != null) {
            if (hidden) {//当fragment隐藏的时候记录scrollview滚动到的位置，当显示的时候，主动滚动的记录位置
                scrollX = svScroll.getScrollX();
                scrollY = svScroll.getScrollY();
            } else {
                svScroll.post(() -> svScroll.scrollTo(scrollX, scrollY));

            }
        }

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (svScroll != null) {
            if (isVisibleToUser) {//当fragment从隐藏到出现的时候
                svScroll.post(() -> svScroll.scrollTo(scrollX, scrollY));
            } else {
                scrollX = svScroll.getScrollX();
                scrollY = svScroll.getScrollY();
            }
        }
    }




}
