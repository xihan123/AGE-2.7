package cn.xihan.age.ui.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.bumptech.glide.Glide;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.xihan.age.Config;
import cn.xihan.age.R;
import cn.xihan.age.base.BaseFragment;
import cn.xihan.age.base.BaseRecyclerAdapter;
import cn.xihan.age.base.RecyclerViewHolder;
import cn.xihan.age.bean.AniPreSimBean;
import cn.xihan.age.bean.ScBean;
import cn.xihan.age.utils.DatabaseUtil;

import static cn.xihan.age.Config.AniInfoApi;

/**
 * @项目名 : AGE动漫
 * @作者 : MissYang
 * @创建时间 : 2021/3/4 8:35
 * @介绍 : 本地收藏
 */
public class Sc2Fragment extends BaseFragment {

    FrameLayout      layout;
    AniInfosFragment aniInfosFragment;
    RequestQueue     queue;
    Context          ctx;


    static String fileName;
    @BindView(R.id.scgridview)
    RecyclerView        scgridview;
    @BindView(R.id.sv_scroll)
    ScrollView          svScroll;
    @BindView(R.id.topbar)
    QMUITopBarLayout      topbar;
    @BindView(R.id.mPull)
    QMUIPullRefreshLayout mPull;


    final   List<ScBean.DataDTO.ListDTO> dataScList     = new ArrayList<>();
    BaseRecyclerAdapter<ScBean.DataDTO.ListDTO> mScAdapter;

    @Override
    protected View onCreateView() {
        layout = (FrameLayout) LayoutInflater.from(getActivity()).inflate(R.layout.fragment_sc2, null);
        ButterKnife.bind(this, layout);
        ctx = requireContext();
        topbar.addLeftBackImageButton().setOnClickListener(v -> popBackStack());
        mPull.setOnPullListener(new QMUIPullRefreshLayout.OnPullListener() {
            @Override
            public void onMoveTarget(int offset) {

            }

            @Override
            public void onMoveRefreshView(int offset) {

            }

            @Override
            public void onRefresh() {
                dataScList.clear();
                mPull.postDelayed(() -> {
                    initViews();
                    mPull.finishRefresh();
                }, 2000);

            }
        });
        initViews();
        fileName = "sdcard/Android/data/cn.xihan.age/files/test.txt";
        return layout;
    }

    private void initViews() {
        File dataDir = new File("/storage/emulated/0/Android/data/cn.xihan.age/Database/");
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
        DatabaseUtil.CREATE_TABLES();
        int videosCount = DatabaseUtil.queryFavoriteCount();
        if (videosCount == 0){
            topbar.setTitle("收藏");
        }else {
            topbar.setTitle("收藏数: " +videosCount+" 部" );
        }
        dataScList.clear();
        List<AniPreSimBean> animeListBean2 = DatabaseUtil.queryAllFavorite();
        if (animeListBean2.size() > 0) {
            for (int i = 0; i < animeListBean2.size(); i++) {
                ScBean.DataDTO.ListDTO datas = new ScBean.DataDTO.ListDTO();
                datas.setTitle(animeListBean2.get(i).getTitle());
                datas.setAid(animeListBean2.get(i).getAid());
                datas.setPicSmall(animeListBean2.get(i).getPicSmall());
                dataScList.add(datas);
                Log.d("xihan", "收藏总数:" + videosCount + "\n标题:" + animeListBean2.get(i).getPicSmall());
            }
        }
        mScAdapter = new BaseRecyclerAdapter<ScBean.DataDTO.ListDTO>(ctx, dataScList) {
            @Override
            public int getItemLayoutId() {
                return R.layout.my_home_item_layout;
            }

            @Override
            public void bindData(RecyclerViewHolder holder, ScBean.DataDTO.ListDTO item) {
                TextView name = holder.itemView.findViewById(R.id.title);
                TextView newtitle = holder.itemView.findViewById(R.id.newtitle);
                ImageView cover = holder.itemView.findViewById(R.id.cover);
                String isHttp = item.getPicSmall().replace("http:", "").replace("https:", "");
                Glide.with(ctx).load(Config.Https + isHttp).into(cover);
                name.setText(item.getTitle());
                name.setTextColor(Color.WHITE);
                newtitle.setVisibility(View.GONE);

            }
        };
        scgridview.setAdapter(mScAdapter);
        scgridview.setLayoutManager(new GridLayoutManager(ctx, 2));
        mScAdapter.setOnItemClickListener((itemView, pos) -> {
            ScBean.DataDTO.ListDTO datas = dataScList.get(pos);
            String url = AniInfoApi + datas.getAid();
            String aid = datas.getAid();
            Bundle bundle = new Bundle();
            bundle.putString("url", url);
            bundle.putString("aid", aid);

            if (aniInfosFragment == null) {
                aniInfosFragment = new AniInfosFragment();
            }
            aniInfosFragment.setArguments(bundle);
            startFragment(aniInfosFragment);
        });
        mScAdapter.setOnItemLongClickListener((itemView, pos) -> {
            final PopupMenu popupMenu = new PopupMenu(ctx, itemView);
            popupMenu.getMenuInflater().inflate(R.menu.favorite_menu, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(menuItem -> {
                if (menuItem.getItemId() == R.id.remove_favorite) {
                    DatabaseUtil.deleteFavorite(dataScList.get(pos).getTitle());
                    mScAdapter.remove(pos);
                }
                return true;
            });
            popupMenu.show();

        });
    }

}
