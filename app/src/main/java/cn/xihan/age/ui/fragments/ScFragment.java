package cn.xihan.age.ui.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;

import org.json.JSONArray;
import org.json.JSONObject;

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
import cn.xihan.age.bean.ScBean;
import cn.xihan.age.utils.MySingleton;

import static android.content.Context.MODE_PRIVATE;
import static cn.xihan.age.Config.AniInfoApi;

/**
 * @项目名 : AGE动漫
 * @作者 : MissYang
 * @创建时间 : 2021/3/2 10:21
 * @介绍 : 网络收藏
 */
public class ScFragment extends BaseFragment {

    FrameLayout layout;
    @BindView(R.id.scgridview)
    RecyclerView     scgridview;
    @BindView(R.id.topbar)
    QMUITopBarLayout topbar;

    final List<ScBean.DataDTO.ListDTO> dataScList  = new ArrayList<>();
    final List<ScBean.DataDTO.ListDTO> dataScList2 = new ArrayList<>();

    BaseRecyclerAdapter<ScBean.DataDTO.ListDTO> mScAdapter;

    String Sign_k, UserName, Sign_t, ScZs, url;
    AniInfosFragment aniInfosFragment;
    RequestQueue     queue;
    Context          ctx;



    @Override
    protected View onCreateView() {
        layout = (FrameLayout) LayoutInflater.from(getActivity()).inflate(R.layout.fragment_sc, null);
        ButterKnife.bind(this, layout);
        ctx = requireContext();
        queue = MySingleton.getInstance(ctx).getRequestQueue();
        dataScList.clear();
        dataScList2.clear();
        initTopbar();
        Bundle bundle = getArguments();
        if (bundle != null) {
            UserName =  bundle.getString("UserName");
            Sign_t = bundle.getString("Sign_t");
            try {
                Sign_k = URLEncoder.encode(bundle.getString("Sign_k"),"UTF-8");
                //Log.d("xihan","编码结果:" + URLEncoder.encode(bundle.getString("Sign_k"),"UTF-8"));
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            url = Config.ScApi + "&username=" +UserName + "&sign_t=" + Sign_t + "&sign_k=" + Sign_k;
            startrequst(url);
        }
        return layout;
    }





    /**
     * 请求数据
     */
    private void startrequst(String url) {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, jsonObject -> {
                //Log.d("xihan","收藏结果:" + jsonObject);
                if (!TextUtils.isEmpty(String.valueOf(jsonObject))) {
                    try {
                        JSONObject ScDataJB = jsonObject.optJSONObject("Data");
                        JSONArray ScListArray = Objects.requireNonNull(ScDataJB).optJSONArray("List");
                        if (Objects.requireNonNull(ScListArray).length() > 0) {
                            for (int i = 0; i < ScListArray.length(); i++) {
                                JSONObject ScDataJb = ScListArray.getJSONObject(i);
                                ScBean.DataDTO.ListDTO sdl = new ScBean.DataDTO.ListDTO();
                                ScBean.DataDTO.ListDTO sd2 = new ScBean.DataDTO.ListDTO();
                                sd2.setAid(ScDataJb.optString("AID"));
                                sdl.setAid(ScDataJb.optString("AID"));
                                sdl.setNewTitle(ScDataJb.optString("NewTitle"));
                                sdl.setPicSmall(ScDataJb.optString("PicSmall"));
                                sdl.setTitle(ScDataJb.optString("Title"));
                                dataScList.add(sdl);
                                dataScList2.add(sd2);
                            }
                        }
                        ScZs = ScDataJB.optString("Allsize");
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                    requireActivity().runOnUiThread(this::setData);
                }
            }, null);
            queue.add(jsonObjectRequest);
        }




    /**
     * 设置数据
     */
    private void setData() {
        SharedPreferences.Editor editor = ctx.getSharedPreferences("data", MODE_PRIVATE).edit();
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
                newtitle.setText(item.getNewTitle());
                name.setTextColor(Color.WHITE);
                newtitle.setTextColor(Color.WHITE);
            }
        };
        scgridview.setAdapter(mScAdapter);
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
        scgridview.setLayoutManager(new GridLayoutManager(ctx, 2));
        try {
            editor.putInt("ScSize", dataScList2.size());
            Gson gson = new Gson();
            String ScData = gson.toJson(dataScList2);
            editor.putString("ScAid", ScData);
            editor.apply();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * 设置标题栏
     */
    private void initTopbar() {
        topbar.addLeftBackImageButton().setOnClickListener(v -> popBackStack());

        topbar.setTitle("收藏");
    }


}
