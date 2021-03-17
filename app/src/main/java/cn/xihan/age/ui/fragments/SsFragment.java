package cn.xihan.age.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.xihan.age.Config;
import cn.xihan.age.R;
import cn.xihan.age.base.BaseFragment;
import cn.xihan.age.base.BaseRecyclerAdapter;
import cn.xihan.age.base.RecyclerViewHolder;
import cn.xihan.age.bean.SearchBean;
import cn.xihan.age.utils.MySingleton;

/**
 * @项目名 : AGE动漫
 * @作者 : MissYang
 * @创建时间 : 2021/3/8 8:50
 * @介绍 : 搜索页面
 */
public class SsFragment extends BaseFragment {

    FrameLayout layout;
    @BindView(R.id.listview)
    RecyclerView     listview;
    @BindView(R.id.topbar)
    QMUITopBarLayout topbar;

    RequestQueue queue;
    Context      ctx;
    Bundle bundle;
    String url,SerachGJC,SerachSSJG;
    final List<SearchBean.AniPreLDTO> AniPreLBean = new ArrayList<>();
    BaseRecyclerAdapter<SearchBean.AniPreLDTO> mAniPreAdapter;

    AniInfosFragment aniInfosFragment;

    @Override
    protected View onCreateView() {
        layout = (FrameLayout) LayoutInflater.from(getActivity()).inflate(R.layout.fragment_serach, null);
        ButterKnife.bind(this, layout);
        ctx = requireContext();
        queue = MySingleton.getInstance(ctx).getRequestQueue();
        bundle = getArguments();
        if (bundle != null) {
            url = bundle.getString("url");
            Log.d("xihan","接收的数据:" + url);
            if(!TextUtils.isEmpty(url)){
                startrequst(url);
            }

        }
        if (  AniPreLBean != null){
            AniPreLBean.clear();
        }
        return layout;
    }



    private void startrequst(String url) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, jsonObject -> {
            if (!TextUtils.isEmpty(String.valueOf(jsonObject))){
                try {
                    JSONArray AniPreLArray = jsonObject.getJSONArray("AniPreL");
                    if (AniPreLArray.length() > 0){
                        for (int i = 0; i < AniPreLArray.length();i++){
                            JSONObject Serachjb = AniPreLArray.getJSONObject(i);
                            SearchBean.AniPreLDTO SerachBean = new SearchBean.AniPreLDTO();
                            SerachBean.setR动画名称(Serachjb.optString("R动画名称"));
                            SerachBean.setR首播时间(Serachjb.optString("R首播时间"));
                            SerachBean.setR制作公司(Serachjb.optString("R制作公司"));
                            SerachBean.setR简介(Serachjb.optString("R简介"));
                            SerachBean.setR播放状态(Serachjb.optString("R播放状态"));
                            SerachBean.setR原版名称(Serachjb.optString("R原版名称"));
                            SerachBean.setR封面图小(Serachjb.optString("R封面图小"));
                            SerachBean.setAid(Serachjb.optString("AID"));
                            //Log.d("xihan","数组解析结果:"+Serachjb.optString("R封面图小"));
                            AniPreLBean.add(SerachBean);
                        }
                    }
                    SerachGJC = jsonObject.optString("KeyWord");
                    SerachSSJG = jsonObject.optString("SeaCnt");
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                requireActivity().runOnUiThread(this::setDate);
            }
        }, null);
        queue.add(jsonObjectRequest);
    }

    private void setDate() {
        initTopBar();
        listview.setLayoutManager(new LinearLayoutManager(ctx) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        mAniPreAdapter = new BaseRecyclerAdapter<SearchBean.AniPreLDTO>(ctx,AniPreLBean) {
            @Override
            public int getItemLayoutId() {
                return R.layout.item_serach;
            }

            @Override
            public void bindData(RecyclerViewHolder holder, SearchBean.AniPreLDTO item) {
                TextView name = holder.itemView.findViewById(R.id.name);
                TextView createdAt =holder.itemView.findViewById(R.id.createdAt);
                TextView updatedAt =holder.itemView.findViewById(R.id.updatedAt);
                TextView description=holder.itemView.findViewById(R.id.description);
                TextView state= holder.itemView.findViewById(R.id.state);
                TextView region=holder.itemView.findViewById(R.id.region);
                ImageView imageView=holder.itemView.findViewById(R.id.cover);
                name.setText( "番剧名称:"+item.getR动画名称());
                createdAt.setText("首播时间:"+item.getR首播时间());
                updatedAt.setText("制作公司:"+item.getR制作公司());
                description.setText("介绍:"+item.getR简介());
                state.setText("状态:"+item.getR播放状态());
                region.setText("原版名称:"+item.getR原版名称());
                //Log.v("xihan cover","http:"+bangumiData.getCover());
                String fmt =item.getR封面图小().replace("http:", "").replace("https:", "");
                //Log.d("xihan","封面图url:" + fmt);
                Glide.with(requireContext()).load( "https:" +fmt).into(imageView);
            }
        };
        listview.setAdapter(mAniPreAdapter);

        mAniPreAdapter.setOnItemClickListener((itemView, pos) -> {
            String url = Config.AniInfoApi+AniPreLBean.get(pos).getAid();
            String aid =AniPreLBean.get(pos).getAid();
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

    private void initTopBar() {
        topbar.setTitle("「" +SerachGJC+"」" +   "搜索结果          共" + SerachSSJG + "部");
    }


}
