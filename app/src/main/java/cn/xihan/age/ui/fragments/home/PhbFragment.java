package cn.xihan.age.ui.fragments.home;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.tabs.TabLayout;
import com.qmuiteam.qmui.arch.QMUIFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.xihan.age.Config;
import cn.xihan.age.R;
import cn.xihan.age.base.BaseRecyclerAdapter;
import cn.xihan.age.base.RecyclerViewHolder;
import cn.xihan.age.bean.RankBean;
import cn.xihan.age.custom.MyTabLayout;
import cn.xihan.age.ui.fragments.AniInfosFragment;
import cn.xihan.age.utils.MySingleton;

/**
 * @项目名 : AGE动漫
 * @作者 : MissYang
 * @创建时间 : 2021/3/2 0:59
 * @介绍 :
 */
public class PhbFragment extends QMUIFragment {

    FrameLayout      layout;
    RequestQueue     queue;
    Context          ctx;
    AniInfosFragment aniInfosFragment;

    @BindView(R.id.tab_layout1)
    MyTabLayout  tabLayout1;
    @BindView(R.id.tv_zongshu)
    TextView     tvZongshu;
    @BindView(R.id.listview)
    RecyclerView listview;

    Bundle bundle;

    String     zzsbnf;

    final List<RankBean.AniRankPreDTO> dataRankListView = new ArrayList<>();
    BaseRecyclerAdapter<RankBean.AniRankPreDTO> mRankListAdapter;

    @Override
    protected View onCreateView() {
        layout = (FrameLayout) LayoutInflater.from(getActivity()).inflate(R.layout.fragment_phb, null);
        ButterKnife.bind(this, layout);
        ctx = requireContext();
        queue = MySingleton.getInstance(ctx).getRequestQueue();
        initData();
        return layout;
    }

    private void initData() {
        bundle = new Bundle();
        requireActivity().runOnUiThread(() -> Objects.requireNonNull(tabLayout1.getTabAt(1)).select());
        tabLayout1.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch(tab.getPosition()) {
                    case 1:
                        bundle.putString("sbnf","2021");
                        break;
                    case 2:
                        bundle.putString("sbnf","2020");
                        break;
                    case 3:
                        bundle.putString("sbnf","2019");
                        break;
                    case 4:
                        bundle.putString("sbnf","2018");
                        break;
                    case 5:
                        bundle.putString("sbnf","2017");
                        break;
                    case 6:
                        bundle.putString("sbnf","2016");
                        break;
                    case 7:
                        bundle.putString("sbnf","2015");
                        break;
                    case 8:
                        bundle.putString("sbnf","2014");
                        break;
                    case 9:
                        bundle.putString("sbnf","2013");
                        break;
                    case 10:
                        bundle.putString("sbnf","2012");
                        break;
                    case 11:
                        bundle.putString("sbnf","2011");
                        break;
                    case 12:
                        bundle.putString("sbnf","2010");
                        break;
                    case 13:
                        bundle.putString("sbnf","2009");
                        break;
                    case 14:
                        bundle.putString("sbnf","2008");
                        break;
                    case 15:
                        bundle.putString("sbnf","2007");
                        break;
                    case 16:
                        bundle.putString("sbnf","2006");
                        break;
                    case 17:
                        bundle.putString("sbnf","2005");
                        break;
                    case 18:
                        bundle.putString("sbnf","2004");
                        break;
                    case 19:
                        bundle.putString("sbnf","2003");
                        break;
                    case 20:
                        bundle.putString("sbnf","2002");
                        break;
                    case 21:
                        bundle.putString("sbnf","2001");
                        break;
                    case 22:
                        bundle.putString("sbnf","2000");
                        break;
                    case 0:
                    default:
                        bundle.putString("sbnf","");
                        break;
                }

                requireActivity().runOnUiThread(this::startGet);

            }

            private void startGet() {
                if (tabLayout1.getSelectedTabPosition() == 0){
                    zzsbnf = "";
                } else {
                    zzsbnf = bundle.getString("sbnf");
                }
                String URL;
                if (!TextUtils.isEmpty(zzsbnf)) {
                    URL= Config.ShoubonianfengApi +zzsbnf ;
                }else {
                    URL= Config.ShoubonianfengApi;
                }
                dataRankListView.clear();
                getPaihangbang(URL);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        requireActivity().runOnUiThread(this::setTab);
}

    private void setTab() {
        try{
            Objects.requireNonNull(tabLayout1.getTabAt(0)).select();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void getPaihangbang(String url) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, jsonObject -> {
            //Log.d("xihan","排行榜结果:" +jsonObject );
            if (!TextUtils.isEmpty(String.valueOf(jsonObject))){
                try {
                    JSONArray AniRankPreArray =  jsonObject.getJSONArray("AniRankPre");
                    if (AniRankPreArray.length() > 0){
                        for (int i = 0; i < AniRankPreArray.length(); i++){
                            JSONObject AniRankJb = AniRankPreArray.getJSONObject(i);
                            RankBean.AniRankPreDTO rankPreDTO =new RankBean.AniRankPreDTO();
                            rankPreDTO.setAid(AniRankJb.optString("AID"));
                            rankPreDTO.setNewTitle(AniRankJb.optString("NewTitle"));
                            rankPreDTO.setPicSmall(AniRankJb.optString("PicSmall"));
                            rankPreDTO.setTitle(AniRankJb.optString("Title"));
                            rankPreDTO.setCCnt(AniRankJb.optInt("CCnt"));
                            dataRankListView.add(rankPreDTO);
                        }
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                requireActivity().runOnUiThread(this::setData);
            }
        }, null);
        queue.add(jsonObjectRequest);
    }

    private void setData() {
        listview.setLayoutManager(new LinearLayoutManager(ctx) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        mRankListAdapter = new BaseRecyclerAdapter<RankBean.AniRankPreDTO>(ctx,dataRankListView) {
            @Override
            public int getItemLayoutId() {
                return R.layout.item_rank;
            }

            @Override
            public void bindData(RecyclerViewHolder holder, RankBean.AniRankPreDTO item) {
                TextView tv_Fjtitle = holder.itemView.findViewById(R.id.tv_Fjtitle);
                TextView tv_Fjrd = holder.itemView.findViewById(R.id.tv_Fjrd);
                tv_Fjtitle.setText(""+item.getTitle());
                tv_Fjrd.setText(" "+item.getCCnt());
                tv_Fjtitle.setTextColor(Color.WHITE);
                tv_Fjrd.setTextColor(Color.WHITE);
            }
        };

        listview.setAdapter(mRankListAdapter);
        mRankListAdapter.setOnItemClickListener((itemView, pos) -> {
            String url = Config.AniInfoApi+dataRankListView.get(pos).getAid();
            String aid =  dataRankListView.get(pos).getAid();
            Bundle bundle = new Bundle();
            bundle.putString("url",url);
            bundle.putString("aid",aid);
            if (aniInfosFragment == null){
                aniInfosFragment = new AniInfosFragment();
            }
            aniInfosFragment.setArguments(bundle);
            startFragment(aniInfosFragment);
        });

    }
}
