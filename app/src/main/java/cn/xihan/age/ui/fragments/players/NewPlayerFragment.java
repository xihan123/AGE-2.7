package cn.xihan.age.ui.fragments.players;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.qmuiteam.qmui.layout.QMUIFrameLayout;
import com.qmuiteam.qmui.recyclerView.QMUIRVDraggableScrollBar;
import com.qmuiteam.qmui.recyclerView.QMUIRVItemSwipeAction;
import com.qmuiteam.qmui.skin.QMUISkinHelper;
import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.skin.QMUISkinValueBuilder;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.widget.popup.QMUIFullScreenPopup;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;
import com.qmuiteam.qmui.widget.popup.QMUIPopups;
import com.qmuiteam.qmui.widget.pullLayout.QMUIPullLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jzvd.JZDataSource;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import cn.xihan.age.Config;
import cn.xihan.age.R;
import cn.xihan.age.base.BaseFragment;
import cn.xihan.age.base.BaseRecyclerAdapter;
import cn.xihan.age.base.RecyclerViewHolder;
import cn.xihan.age.bean.AGEpsodeEntity;
import cn.xihan.age.bean.CommentsBean;
import cn.xihan.age.bean.ZxPlayerBean;
import cn.xihan.age.custom.NewVideoPlayer;
import cn.xihan.age.custom.media.JZMediaIjk;
import cn.xihan.age.custom.view.popup.VideoEpisodePopup;
import cn.xihan.age.custom.view.popup.VideoSpeedPopup;
import cn.xihan.age.ui.fragments.DownloadFragment;
import cn.xihan.age.utils.Md5Util;
import cn.xihan.age.utils.MySingleton;
import cn.xihan.age.utils.ScreenRotateUtils;
import cn.xihan.age.utils.Utils;

import static android.content.Context.MODE_PRIVATE;
import static cn.xihan.age.Config.CommentsApi;

/**
 * @项目名 : AGE动漫
 * @作者 : MissYang
 * @创建时间 : 2021/3/2 23:28
 * @介绍 : 新版布局
 */
public class NewPlayerFragment extends BaseFragment implements NewVideoPlayer.JzVideoListener, ScreenRotateUtils.OrientationChangeListener
        , VideoSpeedPopup.SpeedChangeListener, VideoEpisodePopup.EpisodeClickListener {


    NewVideoPlayer mPlayer;

    @BindView(R.id.video_episode)
    TextView       videoEpisode;
    @BindView(R.id.video_episodes)
    TabLayout      episodes;
    @BindView(R.id.ll_xj)
    LinearLayout   ll_xj;
    @BindView(R.id.tv_fjtitle)
    TextView       tvFjtitle;
    @BindView(R.id.bflb)
    RecyclerView   bflb;
    @BindView(R.id.ll_playerlist)
    LinearLayout   ll_playerlist;
    @BindView(R.id.tv_zs)
    TextView       tv_zs;
    @BindView(R.id.recyclerView)
    RecyclerView   plq;
    @BindView(R.id.pull_layout)
    QMUIPullLayout mPullLayout;
    @BindView(R.id.ll_plq)
    LinearLayout   ll_plq;

    private boolean playFlag;//记录退出时播放状态 回来的时候继续播放
    private       int     position;//记录销毁时的进度 回来继续该进度播放
    private final Handler handler = new Handler();


    private List<AGEpsodeEntity> episodeList;
    private List<AGEpsodeEntity> episodeList2;

    //倍数弹窗
    private VideoSpeedPopup   videoSpeedPopup;
    private VideoEpisodePopup videoEpisodePopup;

    JZDataSource mJzDataSource;

    FrameLayout layout;
    static String[] shields;
    String url, fjjs, playid, fjmc, aid, QLIVE, PCWYD, QlivePlayUrl, AllCnt, Shield, diyShield, urll;
    boolean isOpenShield, isQt;
    int js, SIPlayerCoreText;

    SharedPreferences sharedPreferences;

    List<ZxPlayerBean>             dataPlayList      = new ArrayList<>();
    final List<CommentsBean.CommentsDTO> commentsDTOList   = new ArrayList<>();
    final List<CommentsBean.PageCtrlDTO> commentsPCDTOList = new ArrayList<>();
    List<CommentsBean.CommentsDTO> data              = new ArrayList<>();

    private BaseRecyclerAdapter<CommentsBean.CommentsDTO> mAdapter;
    BaseRecyclerAdapter<ZxPlayerBean> mPlayListAdapter;

    NewPlayerFragment newPlayerFragment;

    RequestQueue queue;
    Context      ctx;


    @Override
    protected View onCreateView() {
        layout = (FrameLayout) LayoutInflater.from(getActivity()).inflate(R.layout.fragment_new_player, null);
        ButterKnife.bind(this, layout);
        ctx = requireContext();
        queue = MySingleton.getInstance(ctx).getRequestQueue();
        Bundle bundle = getArguments();
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("data", MODE_PRIVATE);
        boolean isShowplq = sharedPreferences.getBoolean("显示评论区", false);
        if (bundle != null) {
            url = bundle.getString("url");
            fjjs = bundle.getString("fjjs");
            playid = bundle.getString("playid");
            fjmc = bundle.getString("fjmc");
            dataPlayList = (List<ZxPlayerBean>) bundle.getSerializable("PlayList");
//            for(int i = 0; i < dataPlayList.size(); i++) {
//                Log.d("xihan","传递的播放列表:" + dataPlayList.get(i).getPlayVid());
//            }
            js = bundle.getInt("position");
            aid = bundle.getString("aid");
            Log.d("xihan", "得到的AID:" + aid);
            if (isShowplq) {
                initCommentsData();
            } else {
                ll_plq.setVisibility(View.GONE);
            }
            //Log.d("xihan","传递的数据:" + url+fjjs+playid+fjmc+dataPlayList.toString());
            initVideoData();
            initView();
        }
        if (data != null) {
            commentsDTOList.clear();
            commentsPCDTOList.clear();
            data.clear();
        }
        if (fjjs != null) {
            dataPlayList.clear();
        }
        Log.d("xihan","运行========");
        ScreenRotateUtils.getInstance(this.getContext()).setOrientationChangeListener(this);
        return layout;
    }

    private void initCommentsData() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(CommentsApi + aid, null, jsonObject1 -> {
            if (!TextUtils.isEmpty(String.valueOf(jsonObject1))) {
                Shield = sharedPreferences.getString("Shield", "");
                diyShield = sharedPreferences.getString("diyShield", "");
                isOpenShield = sharedPreferences.getBoolean("评论屏蔽词", false);
                if (!TextUtils.isEmpty(diyShield)) {
                    shields = new Gson().fromJson(diyShield, String[].class);
                } else {
                    shields = new Gson().fromJson(Shield, String[].class);
                }
                try {
                    AllCnt = jsonObject1.optString("AllCnt");
                    JSONArray jsonArray = jsonObject1.getJSONArray("comments");
                    if (jsonArray.length() > 0) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            CommentsBean.CommentsDTO commentsDTO = new CommentsBean.CommentsDTO();
                            if (isOpenShield) {
                                if (!isShield(jsonObject.optString("content")) && !isShield(jsonObject.optString("username"))) {
                                    commentsDTO.setContent(jsonObject.optString("content"));
                                    commentsDTO.setUsername(jsonObject.optString("username"));
                                } else {
                                    commentsDTO.setContent("哎呀~被屏蔽了呢~（=´口｀=）");
                                }
                            } else {
                                commentsDTO.setContent(jsonObject.optString("content"));
                                commentsDTO.setUsername(jsonObject.optString("username"));
                            }
//                    commentsDTO.setContent(jsonObject.optString("content"));
//                    commentsDTO.setUsername(jsonObject.optString("username"));
                            commentsDTO.setTime(Utils.times(jsonObject.optString("time")));
                            //Log.d("xihan","评论:" +Utils.times( jsonObject.optString("time")) );
                            //Log.d("xihan","打印：" +jsonObject.optString("username"));
                            commentsDTOList.add(commentsDTO);
                        }
                    }

                    JSONArray jsonArray2 = jsonObject1.getJSONArray("PageCtrl");
                    if (jsonArray2.length() > 0) {
                        for (int i = 0; i < jsonArray2.length(); i++) {
                            JSONObject jsonObject = jsonArray2.getJSONObject(i);
                            CommentsBean.PageCtrlDTO commentsDTO = new CommentsBean.PageCtrlDTO();
                            commentsDTO.setTitle(jsonObject.optString("Title"));
                            commentsDTO.setUrl(jsonObject.optString("Url").replace("-1", "0"));
                            commentsDTO.setIndex(jsonObject.optInt("Index"));
                            commentsPCDTOList.add(commentsDTO);
                        }
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                requireActivity().runOnUiThread(() -> {
                    if (!TextUtils.isEmpty(AllCnt)) {
                        tv_zs.setText("总评论 " + AllCnt + " 条");
                    }
                    initData();
                });
            }

        }, null);

        queue.add(jsonObjectRequest);

    }

    private void initData() {
        mPullLayout.setActionListener(pullAction -> mPullLayout.postDelayed(() -> {
            if (pullAction.getPullEdge() == QMUIPullLayout.PULL_EDGE_TOP) {
                onRefreshData();
            } else if (pullAction.getPullEdge() == QMUIPullLayout.PULL_EDGE_BOTTOM) {
                onLoadMore();
            }
            mPullLayout.finishActionRun(pullAction);
        }, 3000));
        QMUIRVDraggableScrollBar scrollBar = new QMUIRVDraggableScrollBar(0, 0, 0);
        scrollBar.setEnableScrollBarFadeInOut(true);
        scrollBar.attachToRecyclerView(plq);
        QMUIRVItemSwipeAction swipeAction = new QMUIRVItemSwipeAction(true, new QMUIRVItemSwipeAction.Callback() {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                mAdapter.remove(viewHolder.getAdapterPosition());
            }

            @Override
            public int getSwipeDirection(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                return QMUIRVItemSwipeAction.SWIPE_RIGHT;
            }
        });
        swipeAction.attachToRecyclerView(plq);
        plq.setLayoutManager(new LinearLayoutManager(ctx) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        mAdapter = new BaseRecyclerAdapter<CommentsBean.CommentsDTO>(ctx, commentsDTOList) {
            @Override
            public int getItemLayoutId() {
                return R.layout.item_comments;
            }

            @Override
            public void bindData(RecyclerViewHolder holder, CommentsBean.CommentsDTO item) {
                TextView tv_name = holder.itemView.findViewById(R.id.tv_name);
                TextView tv_content = holder.itemView.findViewById(R.id.tv_content);
                TextView tv_time = holder.itemView.findViewById(R.id.tv_time);
                tv_name.setText(item.getUsername());
                tv_content.setText(item.getContent());
                tv_time.setText(item.getTime());
            }
        };
        plq.setAdapter(mAdapter);
        onDataLoaded();
    }

    private void onDataLoaded() {
        Collections.shuffle(commentsDTOList);
        mAdapter.setData(commentsDTOList);
    }

    /**
     * 下拉刷新
     */
    private void onRefreshData() {
        int mRv = Integer.parseInt(AllCnt);
        List<CommentsBean.CommentsDTO> data;

        while (mAdapter.getItemCount() != 0) {
            mAdapter.remove(0);
        }
        onLoadMore();

//        if (mAdapter.getItemCount() == 7) {
//            mAdapter.delete(0);
//            mAdapter.delete(0);
//            mAdapter.delete(0);
//            mAdapter.delete(0);
//            mAdapter.delete(0);
//            mAdapter.delete(0);
//            mAdapter.delete(0);
//            if (mAdapter.getItemCount() == 0) {
//                if (commentsDTOList.size() > 0) {
//                    data = getRemoveList(commentsDTOList);
//                    mAdapter.append(data);
//                }
//            }
//        } else if (mAdapter.getItemCount() == 0) {
//            if (commentsDTOList.size() > 0) {
//                data = getRemoveList(commentsDTOList);
//                mAdapter.append(data);
//            }
//        } else if (mAdapter.getItemCount() != 0) {
//
//            if (mAdapter.getItemCount() == 0) {
//                data = getRemoveList(commentsDTOList);
//                mAdapter.setData(data);
//                plq.setAdapter(mAdapter);
//            }
//        } else if (mAdapter.getItemCount() > mRv) {
//            data = getRemoveList(commentsDTOList);
//            mAdapter.setData(data);
//            plq.setAdapter(mAdapter);
//        }

    }
    int iiii ;
    /**
     * 加载更多
     */
    private void onLoadMore() {
        int allCnt = Integer.parseInt(AllCnt);
        int ii = allCnt / 7;
        if(data.size() >= allCnt){
            Toast.makeText(getContext(), "已加载完啦!", Toast.LENGTH_SHORT).show();
        }else{
            data.size();
            if (commentsDTOList.size() != allCnt) {
                    //Log.d("xihan","打印int:" + i);
                    //data.add("onLoadMore-" + id + "-" + i);
                    //Log.d("xihan","======日志:" + response);
                    iiii+=10;
                    for (int i = iiii-10; i < iiii;i++){
                        Log.e("xihan","i:" + i + "\niiii:" + (iiii-10));
                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, CommentsApi + aid + "&pagesize=10&pageindex=" +iiii, null, this::paserCommentss, null);
                        queue.add(jsonObjectRequest);
                    }


                    }

        }

        Handler handler = new Handler();
        handler.postDelayed(() -> {
//            data = getRemoveList2(commentsDTOList);
//            for (int i =0;i<data.size();i++) {
//                Log.e("xihan","删除后评论:" + data.get(i).getContent());
//            }
            mAdapter.append(commentsDTOList);
            plq.setAdapter(mAdapter);
            //Toast.makeText(getContext(), "已加载" + commentsDTOList.size() + "条", Toast.LENGTH_SHORT).show();
            //Log.e("xihan","打印int:" + ii + "\n列表数:" + data.size());
        }, 2000);

//        if (ii >= 10) {
//            handler.postDelayed(() -> {
//                if (commentsDTOList.size() > mAdapter.getItemCount()) {
//                    plq.setAdapter(null);
//                    if (plq.getAdapter() == null) {
//                        data = getRemoveList(commentsDTOList);
//                        mAdapter.setData(data);
//                        plq.setAdapter(mAdapter);
//                        Toast.makeText(getContext(), "已加载" + commentsDTOList.size() + "条", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(getContext(), "已加载完啦!", Toast.LENGTH_SHORT).show();
//                }
//            }, 5000);
//        }
        // mAdapter.append(data);
    }

    /**
     * 解析评论
     *
     * @param response
     */
    private void paserCommentss(JSONObject response) {
        try {
            Shield = sharedPreferences.getString("Shield", "");
            diyShield = sharedPreferences.getString("diyShield", "");
            isOpenShield = sharedPreferences.getBoolean("评论屏蔽词", false);
            if (!TextUtils.isEmpty(diyShield)) {
                shields = new Gson().fromJson(diyShield, String[].class);
            } else {
                shields = new Gson().fromJson(Shield, String[].class);
            }
            Log.d("xihan", "屏蔽词合集:" + Shield + "\n自定义屏蔽词集合:" + diyShield);
            JSONObject json = new JSONObject(String.valueOf(response));
            JSONArray jsonArray = json.getJSONArray("comments");
            if (jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    CommentsBean.CommentsDTO commentsDTO = new CommentsBean.CommentsDTO();
                    if (isOpenShield) {
                        if (!isShield(jsonObject.optString("content")) && !isShield(jsonObject.optString("username"))) {
                            commentsDTO.setContent(jsonObject.optString("content"));
                            commentsDTO.setUsername(jsonObject.optString("username"));
                        } else {
                            commentsDTO.setContent("哎呀~被屏蔽了呢~（=´口｀=）");
                        }
                    } else {
                        commentsDTO.setContent(jsonObject.optString("content"));
                        commentsDTO.setUsername(jsonObject.optString("username"));
                    }
                    commentsDTO.setTime(Utils.times(jsonObject.optString("time")));
                    //Log.d("xihan","评论:" +Utils.times( jsonObject.optString("time")) );
                    Log.d("xihan", "打印：" + jsonObject.optString("content") + "---------listSize:" + commentsDTOList.size());
                    commentsDTOList.add(commentsDTO);
                    data.add(commentsDTO);
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public List<CommentsBean.CommentsDTO> getRemoveList2(List<CommentsBean.CommentsDTO> list){
        List<CommentsBean.CommentsDTO> newList = new ArrayList<>();
        for(int i=0; i<newList.size(); i++){
            CommentsBean.CommentsDTO commentsDTO = new CommentsBean.CommentsDTO();
            String str = list.get(i).getContent();
            String str2 = list.get(i).getUsername();
            String str3 = list.get(i).getTime();
            if(!list.get(i).getContent().contains(str)){ //查看新集合中是否有指定的元素，如果没有则加入
                commentsDTO.setContent(str);
                commentsDTO.setContent(str2);
                commentsDTO.setContent(str3);
                list.add(commentsDTO);
            }
        }
        return newList;
    }

    private static List<CommentsBean.CommentsDTO> getRemoveList(List<CommentsBean.CommentsDTO> list) {
        Set set = new HashSet();
        List<CommentsBean.CommentsDTO> newList = new ArrayList<>();
        for (CommentsBean.CommentsDTO object : list) {
            if (set.add(object))
                newList.add(object);
        }
        return newList;
    }

    public static boolean isShield(String str) {
        for (String adUrl : shields) {
            if (str.contains(adUrl)) {
                return true;
            }
        }
        return false;
    }

    private void initView() {
        mPlayer=layout.findViewById(R.id.ag_player);
        initEpisodesTablayout();
        mPlayer.setJzVideoListener(this);
        mJzDataSource = new JZDataSource(episodeList.get(0).getVideoUrl(), episodeList.get(0).getVideoName());
        sharedPreferences = requireActivity().getSharedPreferences("data", MODE_PRIVATE);
        boolean isOpenDanmu = sharedPreferences.getBoolean("开启弹幕",false);
        if (isOpenDanmu){
            mPlayer.ShowDanmu();
        }else{
            mPlayer.HideDanmu();
        }
        SIPlayerCoreText = sharedPreferences.getInt("PlayerCore", 0);
//        if (playid.equals("<play>QLIVE</play>")) {
//            QLIVE = "<play>QLIVE</play>";
//            HideXj();
//            isQt = true;
//            testGet();
//        } else if (playid.equals("<play>PC-WYD</play>")) {
//            //Log.d("xihan","=============playid:" + playid);
//            PCWYD = "<play>PC-WYD</play>";
//            isQt = true;
//            HideXj();
//            testGet();
        Log.d("xihan","PlayId:" + playid);
        if (playid.equals("<play>web_mp4</play>")) {
            HidePlayerList();
            isQt = false;
            mPlayer.setUp(mJzDataSource, JzvdStd.SCREEN_NORMAL, JZMediaIjk.class);
            mPlayer.startVideo();
            YLoad();
        } else if (playid.equals("<play>web_m3u8</play>")){
            HidePlayerList();
            isQt = false;
            mPlayer.setUp(mJzDataSource, JzvdStd.SCREEN_NORMAL, JZMediaIjk.class);
            mPlayer.startVideo();
            YLoad();
         }else {
            for (int i = 0;i < dataPlayList.size() ;i++){
                initZxPlayer(dataPlayList.get(i).getPlayId(),dataPlayList.get(i).getEpName(),dataPlayList.get(i).getPlayVid(),dataPlayList.get(i).getTitle(),dataPlayList);
            }
            HidePlayerList();
        }

//        if (js >0){
//            requireActivity().runOnUiThread(this::setTabla);
//        }

        bflb.setLayoutManager(new LinearLayoutManager(ctx) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });

        mPlayListAdapter = new BaseRecyclerAdapter<ZxPlayerBean>(ctx, dataPlayList) {
            @Override
            public int getItemLayoutId() {
                return R.layout.item_play;
            }

            @Override
            public void bindData(RecyclerViewHolder holder, ZxPlayerBean item) {
                TextView Fjtitle = holder.itemView.findViewById(R.id.Fjtitle);
                Fjtitle.setTextColor(Color.WHITE);
                Fjtitle.setText("" + item.getTitle());
            }
        };
        bflb.setAdapter(mPlayListAdapter);
        mPlayListAdapter.setOnItemClickListener((itemView, pos) -> {
            ZxPlayerBean zxPlayerBean = dataPlayList.get(pos);
            String PlayUrl = zxPlayerBean.getPlayVid();
            String FjTitle = zxPlayerBean.getTitle();
            String Playid = zxPlayerBean.getPlayId();
            //Log.d("xihan","playUrl1:" +PlayUrl );
            Bundle bundle = new Bundle();
            bundle.putString("url", PlayUrl);
            bundle.putString("playid", Playid);
            bundle.putString("fjjs", FjTitle);
            bundle.putString("fjmc", fjmc);
            bundle.putSerializable("PlayList", (Serializable) dataPlayList);
            if (newPlayerFragment == null) {
                newPlayerFragment = new NewPlayerFragment();
            }
            newPlayerFragment.setArguments(bundle);
            startFragment(newPlayerFragment);
        });
    }

    private void testDanmu() {
        handler.removeCallbacksAndMessages(null);
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (commentsDTOList.size() > 0){
                    for (int i = 0; i < commentsDTOList.size(); i++) {
                        mPlayer.sendDanmu(commentsDTOList.get(i).getContent(), false);
                    }
                }
                handler.postDelayed(this, 2000);
            }
        });
    }

    private void initZxPlayer(String playId, String epName, String playVid,String title,List<ZxPlayerBean> list) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Config.getPlay, null, jsonObject -> {
            if (!TextUtils.isEmpty(String.valueOf(jsonObject))) {
                try {
                    ServerTime = jsonObject.optString("ServerTime");
                    if (!TextUtils.isEmpty(ServerTime)){
                        String PlayId = URLEncoder.encode(playId);
                        String i2 = ServerTime + "{|}" + playId
                                + "{|}" + playVid + "{|}agefans3382-getplay-1719";
                        String urll = "https://play.agefans.net:8443/_getplay2_m5?playid=" + PlayId
                                +"&vid=" + playVid + "&kt=" + ServerTime + "&kp=" + Md5Util.MD55(i2);
                        //Log.d("xihan","拼接：" + urll);
                        JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest(urll, null, jsonObject2 -> {
                            if (!TextUtils.isEmpty(String.valueOf(jsonObject2))) {
                                try {
                                    ZxPlayerBean zxPlayerBean = new ZxPlayerBean();
                                    zxPlayerBean.setPlayVid(URLDecoder.decode(jsonObject2.optString("vurl"), "UTF-8"));
                                    zxPlayerBean.setPlayId(playId);
                                    zxPlayerBean.setEpName(epName);
                                    zxPlayerBean.setTitle(title);
                                    list.add(zxPlayerBean);
                                    episodeList = new ArrayList<>();
                                    for (int i = 0; i < list.size(); i++) {
                                        episodeList.add(new AGEpsodeEntity(list.get(i).getPlayVid(), list.get(i).getTitle()));
                                        //Log.d("xihan","listSize:" + list.size() + "\nepisodeListSize:" + episodeList.size());
                                        //Log.d("xihan","标题:" + dataPlayList.get(i).getTitle()  +"\n播放地址:" +dataPlayList.get(i).getPlayVid());
                                    }
                                    JZDataSource mJzDataSource2 = new JZDataSource(episodeList.get(0).getVideoUrl(), episodeList.get(0).getVideoName());
                                    mPlayer.setUp(mJzDataSource2, JzvdStd.SCREEN_NORMAL, JZMediaIjk.class);
                                    mPlayer.startVideo();
                                    YLoad();
                                    //Log.d("xihan","结果：" + jsonObject2.optString("vurl") );
                                } catch (Exception exception) {
                                    exception.printStackTrace();
                                }
                            }
                        }, null);
                        queue.add(jsonObjectRequest2);
                    }
                    //Log.d("xihan","服务器时间:" + ServerTime);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, null);

        queue.add(jsonObjectRequest);
    }

    private void initEpisodesTablayout() {
        episodes.clearOnTabSelectedListeners();
        episodes.removeAllTabs();
        for (int i = 0; i < episodeList.size(); i++) {
            episodes.addTab(episodes.newTab().setText(String.valueOf((i + 1))));
        }
        //用来循环适配器中的视图总数
        for (int i = 0; i < episodes.getTabCount(); i++) {
            //获取每一个tab对象
            TabLayout.Tab tabAt = episodes.getTabAt(i);
            //将每一个条目设置我们自定义的视图
            assert tabAt != null;
            tabAt.setCustomView(R.layout.tab_video_episodes);
            //通过tab对象找到自定义视图的ID
            TextView textView = Objects.requireNonNull(tabAt.getCustomView()).findViewById(R.id.tab_video_episodes_tv);
            //设置tab上的文字
            //textView.setText(episodes.getTabAt(i).getText());
            textView.setText(episodeList.get(i).getVideoName());
            if (i == 0) {
                //选中后字体
                textView.setTextColor(getResources().getColor(R.color.ThemeColor));
            } else {
                textView.setTextColor(getResources().getColor(R.color.font_color));
            }

        }

        episodes.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //定义方法，判断是否选中
                int tag = Integer.parseInt(Objects.requireNonNull(tab.getText()).toString());
                AGEpsodeEntity entity = episodeList.get(tag - 1);
                mJzDataSource = new JZDataSource(entity.getVideoUrl(), entity.getVideoName());
                updateTabView(tab, true);
                playChangeUrl();
                isNext(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //定义方法，判断是否选中
                updateTabView(tab, false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        isNext(episodes.getSelectedTabPosition());
    }

    public void setTabla(){
        try {
            episodes.getTabAt(js).select();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        Jzvd.goOnPlayOnResume();
        super.onResume();
        ScreenRotateUtils.getInstance(getContext()).start(getActivity());
    }

    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onPause() {
        super.onPause();
        ScreenRotateUtils.getInstance(getContext()).stop();
        Jzvd.goOnPlayOnPause();


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Jzvd.releaseAllVideos();
        ScreenRotateUtils.getInstance(getContext()).setOrientationChangeListener(null);
        mPlayer.releaseDanMu();
    }

    /**
     * 用来改变tabLayout选中后的字体大小及颜色
     *
     * @param tab
     * @param isSelect
     */
    private void updateTabView(TabLayout.Tab tab, boolean isSelect) {
        //找到自定义视图的控件ID
        TextView tv_tab = Objects.requireNonNull(tab.getCustomView()).findViewById(R.id.tab_video_episodes_tv);
        if (isSelect) {
            //设置标签选中
            tv_tab.setSelected(true);
            //选中后字体
            tv_tab.setTextColor(getResources().getColor(R.color.ThemeColor));
        } else {
            //设置标签取消选中
            tv_tab.setSelected(false);
            //恢复为默认字体
            tv_tab.setTextColor(getResources().getColor(R.color.font_color));
        }
    }

    /**
     * 判断是否有下一集
     */
    private void isNext(int position) {
        //判断是否还有下一集
        mPlayer.changeNextBottonUi(position != (episodeList.size() - 1));
    }

    /**
     * 更换播放地址
     */
    private void playChangeUrl() {
        long progress = 0;
        mPlayer.changeUrl(mJzDataSource, progress);
    }


    /**
     * 关闭倍速播放弹窗和选集弹窗
     */
    private void dismissSpeedPopAndEpisodePop() {
        if (videoSpeedPopup != null) {
            videoSpeedPopup.dismiss();
        }
        if (videoEpisodePopup != null) {
            videoEpisodePopup.dismiss();
        }
    }

    /**
     * 改变播放倍速
     *
     * @param speed
     */
    private void changeSpeed(float speed) {
        Object[] object = {speed};
        mPlayer.mediaInterface.setSpeed(speed);
        mJzDataSource.objects[0] = object;
        Toast.makeText(getContext(), "正在以" + speed + "X倍速播放", Toast.LENGTH_SHORT).show();
        mPlayer.speedChange(speed);
    }

    @Override
    public void nextClick() {
        int position = episodes.getSelectedTabPosition() + 1;
        AGEpsodeEntity entity = episodeList.get(position);
        mJzDataSource = new JZDataSource(entity.getVideoUrl(), entity.getVideoName());
        TabLayout.Tab tab = episodes.getTabAt(position);
        if (tab != null) {
            tab.select();
        }
    }

    @Override
    public void backClick() {
        if (mPlayer.screen == Jzvd.SCREEN_FULLSCREEN) {
            dismissSpeedPopAndEpisodePop();
            NewVideoPlayer.backPress();
        }else {
            popBackStack();
        }

    }

    @Override
    public void throwingScreenClick() {
        Toast.makeText(getContext(), "暂未写投屏功能", Toast.LENGTH_SHORT).show();
    }


    private QMUIPopup mNormalPopup;
    DownloadFragment downloadFragment;
    @Override
    public void throwingGdClick() {
        String[] listItems = new String[]{
                "缓存",
                "发送弹幕",
                "开启弹幕"
        };
        List<String> data = new ArrayList<>();
        Collections.addAll(data, listItems);
        ArrayAdapter adapter = new ArrayAdapter<>(getContext(), R.layout.simple_list_item, data);
        AdapterView.OnItemClickListener onItemClickListener = (adapterView, view, i, l) -> {
            //Toast.makeText(getActivity(), "Item " + (i + 1), Toast.LENGTH_SHORT).show();

            switch (i+1){
                case 1:
                    if (downloadFragment == null){
                        downloadFragment = new DownloadFragment();
                    }
                    Bundle bundle2 = new Bundle();
                    bundle2.putString("fjmc",fjmc);
                    bundle2.putSerializable("PlayList", (Serializable) dataPlayList);
                    downloadFragment.setArguments(bundle2);
                    startFragment(downloadFragment);
                    break;
                case 2:
                    mPlayer.sendDanmu("666", false);
                    showEdit();
                    break;
                case 3:
                    mPlayer.ShowDanmu();
                    handler.postDelayed(this::testDanmu,5000);
                    break;
            }
            if (mNormalPopup != null) {
                mNormalPopup.dismiss();
            }
        };
        mNormalPopup = QMUIPopups.listPopup(requireContext(),
                QMUIDisplayHelper.dp2px(requireContext(), 250),
                QMUIDisplayHelper.dp2px(requireContext(), 300),
                adapter,
                onItemClickListener)
                .animStyle(QMUIPopup.ANIM_GROW_FROM_CENTER)
                .preferredDirection(QMUIPopup.DIRECTION_TOP)
                .shadow(true)
                .offsetYIfTop(QMUIDisplayHelper.dp2px(requireContext(), 5))
                .skinManager(QMUISkinManager.defaultInstance(requireContext()))
                .onDismiss(() -> {
                    //Toast.makeText(getContext(), "onDismiss", Toast.LENGTH_SHORT).show();
                })
                .show(mPlayer);
    }

    private void showEdit() {
        QMUISkinValueBuilder builder = QMUISkinValueBuilder.acquire();
//        QMUIFrameLayout frameLayout = new QMUIFrameLayout(requireContext());
//        frameLayout.setBackground(
//                QMUIResHelper.getAttrDrawable(requireContext(), R.attr.qmui_skin_support_popup_bg));
//        builder.background(R.attr.qmui_skin_support_popup_bg);
//        QMUISkinHelper.setSkinValue(frameLayout, builder);
//        frameLayout.setRadius(QMUIDisplayHelper.dp2px(requireContext(), 12));
//        int padding = QMUIDisplayHelper.dp2px(requireContext(), 20);
//        frameLayout.setPadding(padding, padding, padding, padding);


        FrameLayout editFitSystemWindowWrapped = new FrameLayout(requireContext());
        editFitSystemWindowWrapped.setFitsSystemWindows(true);

        int minHeight = QMUIDisplayHelper.dp2px(requireContext(), 48);
        QMUIFrameLayout editParent = new QMUIFrameLayout(requireContext());
        editParent.setMinimumHeight(minHeight);
        editParent.setRadius(minHeight / 2);
        editParent.setBackground(
                QMUIResHelper.getAttrDrawable(requireContext(), R.attr.qmui_skin_support_popup_bg));
        builder.clear();
        builder.background(R.attr.qmui_skin_support_popup_bg);
        QMUISkinHelper.setSkinValue(editParent, builder);


        EditText editText = new EditText(getContext());
        editText.setHint("发个友善的弹幕");
        editText.setBackground(null);
        builder.clear();
        builder.hintColor(R.attr.app_skin_common_desc_text_color);
        builder.textColor(R.attr.app_skin_common_title_text_color);
        QMUISkinHelper.setSkinValue(editText, builder);
        int paddingHor = QMUIDisplayHelper.dp2px(requireContext(), 20);
        int paddingVer = QMUIDisplayHelper.dp2px(requireContext(), 10);
        editText.setPadding(paddingHor, paddingVer, paddingHor, paddingVer);
        editText.setMaxHeight(QMUIDisplayHelper.dp2px(requireContext(), 100));

        FrameLayout.LayoutParams editLp = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        editLp.gravity = Gravity.CENTER_HORIZONTAL;
        editParent.addView(editText, editLp);

        editFitSystemWindowWrapped.addView(editParent,  new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));


        ConstraintLayout.LayoutParams eLp = new ConstraintLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
        int mar = QMUIDisplayHelper.dp2px(requireContext(), 20);
        eLp.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID;
        eLp.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID;
        eLp.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        eLp.leftMargin = mar;
        eLp.rightMargin = mar;
        eLp.bottomMargin = mar;

        QMUIPopups.fullScreenPopup(requireContext())
                //.addView(frameLayout, QMUIFullScreenPopup.getOffsetHalfKeyboardHeightListener())
                .addView(editFitSystemWindowWrapped, eLp, QMUIFullScreenPopup.getOffsetKeyboardHeightListener())
                .skinManager(QMUISkinManager.defaultInstance(requireContext()))
                .onBlankClick(popup -> {
                    popup.dismiss();
                    //Toast.makeText(getContext(), "暂未实现....." , Toast.LENGTH_SHORT).show();
                        if (!editText.getText().toString().equals("")){
                            mPlayer.addDanmakuWithDrawable(editText.getText().toString());
                            Toast.makeText(getContext(), "输入了" +editText.getText().toString() , Toast.LENGTH_SHORT).show();
                        }
                })
                .onDismiss(() -> {
                    //Toast.makeText(getContext(), "onDismiss", Toast.LENGTH_SHORT).show();
                })
                .show(mPlayer);
    }


    @Override
    public void selectPartsClick() {
        if (videoEpisodePopup == null) {
            videoEpisodePopup = new VideoEpisodePopup(getContext(), episodeList);
            videoEpisodePopup.setEpisondeClickListener(this);
        }
        videoEpisodePopup.setPlayNum(episodes.getSelectedTabPosition() + 1);
        videoEpisodePopup.showAtLocation(requireActivity().getWindow().getDecorView(), Gravity.RIGHT, 0, 0);
    }

    @SuppressLint("RtlHardcoded")
    @Override
    public void speedClick() {
        if (videoSpeedPopup == null) {
            videoSpeedPopup = new VideoSpeedPopup(getContext());
            videoSpeedPopup.setSpeedChangeListener(this);
        }
        videoSpeedPopup.showAtLocation(requireActivity().getWindow().getDecorView(), Gravity.RIGHT, 0, 0);
    }


    @Override
    public void speedChange(float speed) {
        changeSpeed(speed);
    }

    @Override
    public void onEpisodeClickListener(int position) {
        TabLayout.Tab tab = episodes.getTabAt(position);
        if (tab != null) {
            tab.select();
        }
    }


    private void initVideoData() {
        episodeList = new ArrayList<>();
        for (int i = 0; i < dataPlayList.size(); i++) {
            episodeList.add(new AGEpsodeEntity(dataPlayList.get(i).getPlayVid(), dataPlayList.get(i).getTitle()));
            //Log.d("xihan","播放地址:" +dataPlayList.get(i).getPlayVid() + "\n标题:" + dataPlayList.get(i).getTitle() );
        }
    }

    String ServerTime;

    private void testGet() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Config.getPlay, null, jsonObject -> {
            if (!TextUtils.isEmpty(String.valueOf(jsonObject))) {
                try {
                    ServerTime = jsonObject.optString("ServerTime");
                    //Log.d("xihan","服务器时间:" + ServerTime);
                    getPlayUrl();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, null);
        queue.add(jsonObjectRequest);

    }

    private void getPlayUrl() {
        if (dataPlayList.size() > 0){
            for(int i = 0; i<dataPlayList.size();i++){
                //Log.e("xihan", "========解析playid:" + dataPlayList.get(i).getPlayId());
                String PlayId = URLEncoder.encode(dataPlayList.get(i).getPlayId());
                String i2 = ServerTime + "{|}" + dataPlayList.get(i).getPlayId()
                        + "{|}" + dataPlayList.get(i).getPlayVid() + "{|}agefans3382-getplay-1719";
                String urll = "https://play.agefans.net:8443/_getplay2_m5?playid=" + PlayId
                        +"&vid=" + dataPlayList.get(i).getPlayVid() + "&kt=" + ServerTime + "&kp=" + Md5Util.MD55(i2);
                //Log.e("xihan","拼接得到的字符串:" +urll);
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(urll, null, jsonObject -> {
                    if (!TextUtils.isEmpty(String.valueOf(jsonObject))) {
                        try {
                            Log.d("xihan","结果：" + jsonObject.optString("vurl") );
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }
                }, null);
                queue.add(jsonObjectRequest);
            }
        }
//        if (!TextUtils.isEmpty(QLIVE)) {
//            String i = ServerTime + "{|}" + QLIVE
//                    + "{|}" + url + "{|}agefans3382-getplay-1719";
//            urll = Config.QLIVEApi
//                    + url
//                    + "&kt=" + ServerTime + "&kp=" + Md5Util.MD55(i);
//            Log.d("xihan", "========解析:" + i + "\n" + urll);
//        } else if (!TextUtils.isEmpty(PCWYD)) {
//            String i = ServerTime + "{|}" + PCWYD
//                    + "{|}" + url + "{|}agefans3382-getplay-1719";
//            urll = Config.PCWYDApi
//                    + url
//                    + "&kt=" + ServerTime + "&kp=" + Md5Util.MD55(i);
//            Log.d("xihan", "========解析:" + i + "\n" + urll);
//        }
//
//        if (!TextUtils.isEmpty(urll)) {
//            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(urll, null, jsonObject -> {
//                if (!TextUtils.isEmpty(String.valueOf(jsonObject))) {
//                    try {
//                        QlivePlayUrl = URLDecoder.decode(jsonObject.optString("vurl"), "UTF-8");
//                        //Log.d("xihan","最终视频URL：" + QlivePlayUrl);
//                    } catch (Exception exception) {
//                        exception.printStackTrace();
//                    }
//                    requireActivity().runOnUiThread(this::setData);
//                }
//            }, null);
//            queue.add(jsonObjectRequest);
//        }

    }



    private void YLoad() {
        boolean isZdqpbf = sharedPreferences.getBoolean("自动全屏播放", false);
        boolean isYjz = sharedPreferences.getBoolean("预加载", false);
        //Log.d("xihan", "自动全屏播放状态:" + isZdqpbf + "\n预加载状态:" + isYjz);
        if (isYjz) {
            mPlayer.startPreloading(); //开始预加载，加载完等待播放
            mPlayer.startVideoAfterPreloading(); //如果预加载完会开始播放，如果未加载则开始加载
        }
        if (isZdqpbf) {
            mPlayer.startVideoAfterPreloading(); //如果预加载完会开始播放，如果未加载则开始加载
            handler.post(() -> mPlayer.gotoFullscreen());

        }

    }

    private void HideXj() {
        requireActivity().runOnUiThread(() -> ll_xj.setVisibility(View.GONE));
    }

    private void HidePlayerList() {
        requireActivity().runOnUiThread(() -> ll_playerlist.setVisibility(View.GONE));
    }

    @Override
    public void orientationChange(int orientation) {
        if (Jzvd.CURRENT_JZVD != null
                && (mPlayer.state == Jzvd.STATE_PLAYING || mPlayer.state == Jzvd.STATE_PAUSE)
                && mPlayer.screen != Jzvd.SCREEN_TINY) {
            if (orientation >= 45 && orientation <= 315 && mPlayer.screen == Jzvd.SCREEN_NORMAL) {
                changeScreenFullLandscape(ScreenRotateUtils.orientationDirection);
            } else if (((orientation >= 0 && orientation < 45) || orientation > 315) && mPlayer.screen == Jzvd.SCREEN_FULLSCREEN) {
                changeScrenNormal();
            }
        }
    }

    /**
     * 竖屏并退出全屏
     */
    private void changeScrenNormal() {
        if (mPlayer != null && mPlayer.screen == Jzvd.SCREEN_FULLSCREEN) {
            mPlayer.autoQuitFullscreen();
        }
    }

    /**
     * 横屏
     */
    private void changeScreenFullLandscape(float x) {
        //从竖屏状态进入横屏
        if (mPlayer != null && mPlayer.screen != Jzvd.SCREEN_FULLSCREEN) {
            if ((System.currentTimeMillis() - Jzvd.lastAutoFullscreenTime) > 2000) {
                mPlayer.autoFullscreen(x);
                Jzvd.lastAutoFullscreenTime = System.currentTimeMillis();
            }
        }
    }

}