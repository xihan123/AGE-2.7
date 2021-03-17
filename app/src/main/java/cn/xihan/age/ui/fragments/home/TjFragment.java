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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.qmuiteam.qmui.arch.QMUIFragment;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.xihan.age.Config;
import cn.xihan.age.R;
import cn.xihan.age.base.BaseRecyclerAdapter;
import cn.xihan.age.base.RecyclerViewHolder;
import cn.xihan.age.bean.TuijianBean;
import cn.xihan.age.ui.fragments.AniInfosFragment;
import cn.xihan.age.utils.MySingleton;

import static android.content.Context.MODE_PRIVATE;

/**
 * @项目名 : AGE动漫
 * @作者 : MissYang
 * @创建时间 : 2021/3/2 0:59
 * @介绍 :
 */
public class TjFragment extends QMUIFragment {
    FrameLayout      layout;
    RequestQueue     queue;
    Context          ctx;
    AniInfosFragment aniInfosFragment;

    @BindView(R.id.Fjzongshu)
    TextView             tv_Fjzongshu;
    @BindView(R.id.tjgridview)
    RecyclerView          tjgridview;
    @BindView(R.id.sv_scroll)
    ScrollView            svScroll;
    @BindView(R.id.pull_to_refresh1)
    QMUIPullRefreshLayout pullToRefresh1;

    final List<TuijianBean.AniPreDTO> dataTuijian = new ArrayList<>();

    BaseRecyclerAdapter<TuijianBean.AniPreDTO> mTuijianAdapter;

    String Fjzongshu;

    @Override
    protected View onCreateView() {
        layout = (FrameLayout) LayoutInflater.from(getActivity()).inflate(R.layout.fragment_tj, null);
        ButterKnife.bind(this, layout);
        ctx = requireContext();
        queue = MySingleton.getInstance(ctx).getRequestQueue();
        initData();
        pullToRefresh1.setOnPullListener(new QMUIPullRefreshLayout.OnPullListener() {
            @Override
            public void onMoveTarget(int offset) {

            }

            @Override
            public void onMoveRefreshView(int offset) {

            }

            @Override
            public void onRefresh() {
                pullToRefresh1.postDelayed(() -> {
                    dataTuijian.clear();
                    initData();
                    pullToRefresh1.finishRefresh();
                }, 2000);
            }
        });
        return layout;
    }

    private void initData() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("data",MODE_PRIVATE);
        boolean isOpenDiyApi = sharedPreferences.getBoolean("DefaultOpenApi",false);
        String DiyApi =sharedPreferences.getString("diyApi",null);
        String Api = sharedPreferences.getString("Api",null);
        String Api2 = sharedPreferences.getString("Api2",null);
        String url = null;
        if (isOpenDiyApi){
            if (!TextUtils.isEmpty(DiyApi)){
                url = DiyApi+ "recommend.json";
                Log.d("xihan","自定义推荐Api:" +url);
            }else{
                if (!TextUtils.isEmpty(Api)){
                    url = Api+ "recommend.json";
                    Log.d("xihan","自定义推荐Api:" +url);
                }else if (!TextUtils.isEmpty(Api2)){
                    url = Api2+ "recommend.json";
                    Log.d("xihan","自定义推荐Api:" +url);
                }
            }
        }else {
            url = Config.TuijianApi;
        }
        if (!TextUtils.isEmpty(url)){

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, jsonObject -> {
            //Log.d("xihan","推荐结果:" + jsonObject);
            if (!TextUtils.isEmpty(String.valueOf(jsonObject))){
                try {
                    Fjzongshu=jsonObject.optString("AllCnt");
                    JSONArray AniPreEvDay = jsonObject.getJSONArray("AniPre");
                    if (AniPreEvDay.length() > 0 ){
                        for(int i = 0; i < AniPreEvDay.length(); i++) {
                            JSONObject jb = AniPreEvDay.getJSONObject(i);
                            TuijianBean.AniPreDTO dataBean = new TuijianBean.AniPreDTO();
                            dataBean.setAid(jb.optString("AID"));
                            dataBean.setTitle(jb.optString("Title"));
                            dataBean.setNewTitle(jb.optString("NewTitle"));
                            dataBean.setPicSmall(jb.optString("PicSmall"));
                            //Log.d("xihan", String.valueOf(dataBeanAniPreEvDayDTO));
                            //Log.d("xihan", String.valueOf(dataBean));
                            dataTuijian.add(dataBean);
                        }
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                requireActivity().runOnUiThread(this::setData);
            }
        }, null);
        queue.add(jsonObjectRequest);  }

    }

    private void setData() {
        tjgridview.setLayoutManager(new LinearLayoutManager(ctx) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        mTuijianAdapter = new BaseRecyclerAdapter<TuijianBean.AniPreDTO>(ctx,dataTuijian) {
            @Override
            public int getItemLayoutId() {
                return R.layout.my_home_item_layout;
            }

            @Override
            public void bindData(RecyclerViewHolder holder, TuijianBean.AniPreDTO item) {
                TextView name = holder.itemView.findViewById(R.id.title);
                TextView newtitle = holder.itemView.findViewById(R.id.newtitle);
                ImageView cover = holder.itemView.findViewById(R.id.cover);
                String isHttp = item.getPicSmall().replace("http:", "").replace("https:", "");
                Glide.with(ctx).load(Config.Https+isHttp).into(cover);
                name.setText(item.getTitle());
                newtitle.setText(item.getNewTitle());
                name.setTextColor(Color.WHITE);
                newtitle.setTextColor(Color.WHITE);
            }
        };
        tjgridview.setAdapter(mTuijianAdapter);
        tjgridview.setLayoutManager(new GridLayoutManager(ctx, 2));
        mTuijianAdapter.setOnItemClickListener((itemView, pos) -> {
            TuijianBean.AniPreDTO datas = dataTuijian.get(pos);
            String url = Config.AniInfoApi+datas.getAid();
            String aid =  datas.getAid();
            Bundle bundle = new Bundle();
            bundle.putString("url",url);
            bundle.putString("aid",aid);
            if (aniInfosFragment == null){
                aniInfosFragment = new AniInfosFragment();
            }
            aniInfosFragment.setArguments(bundle);
            startFragment(aniInfosFragment);
        });
        tv_Fjzongshu.setText( "共 " + Fjzongshu +" 部");
    }
}
