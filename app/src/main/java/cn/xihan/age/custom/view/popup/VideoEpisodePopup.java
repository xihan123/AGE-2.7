package cn.xihan.age.custom.view.popup;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import cn.xihan.age.R;
import cn.xihan.age.adapter.VideoEpisodeAdapter;
import cn.xihan.age.bean.AGEpsodeEntity;
import cn.xihan.age.utils.Utils;

/**
 * @项目名 : AGE动漫
 * @作者 : MissYang
 * @创建时间 : 2021/3/2 23:44
 * @介绍 :
 */
public class VideoEpisodePopup extends PopupWindow {
    private static final int              COMPLETED = 0;
    protected            DismissTimerTask mDismissTimerTask;
    private final        RecyclerView         episodeRecycler;
    private final        VideoEpisodeAdapter  episodeAdapter;


    private final        List<AGEpsodeEntity> episodeList;
    private              EpisodeClickListener episondeClickListener;
    /**
     * 当前正在播放的集数
     */
    private              int              playNum = 0;
    private              Timer            mDismissTimer;
    private final        Handler          handler   = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == COMPLETED) {
                dismiss();

            }
        }
    };

    public VideoEpisodePopup(Context context, List<AGEpsodeEntity> entities) {
        super(context);
        Context mC;
        mC = context;
        this.episodeList = entities;
        LayoutInflater inflater = (LayoutInflater) mC.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.popup_video_episode, null);
        setContentView(contentView);
        setHeight(WindowManager.LayoutParams.MATCH_PARENT);
        setWidth(Utils.dip2px(context, 320));

        setOutsideTouchable(true);
        //不设置该属性，弹窗于屏幕边框会有缝隙并且背景不是半透明
        setBackgroundDrawable(new BitmapDrawable());
        LinearLayout main = contentView.findViewById(R.id.video_main);
        episodeRecycler = contentView.findViewById(R.id.video_episode);
        episodeRecycler.setLayoutManager(new GridLayoutManager(context, 4));
       // episodeAdapter = new VideoEpisodeAdapter(mC, episodeList);
        episodeAdapter = new VideoEpisodeAdapter(mC, episodeList);
        episodeRecycler.setAdapter(episodeAdapter);

        episodeAdapter.setmOnItemClickListener((view, position) -> {
            if (episondeClickListener != null) {
                episondeClickListener.onEpisodeClickListener(position);
            }
            //更换当前正在播放的集数
            if (playNum < 1) {
                playNum = 1;
            }
            episodeList.get(playNum - 1).setPlay(false);
            playNum = position + 1;
            episodeList.get(playNum - 1).setPlay(true);
            episodeAdapter.notifyDataSetChanged();
        });


        main.setOnTouchListener((v, event) -> {
            startDismissTimer();
            return false;
        });
        episodeRecycler.setOnTouchListener((v, event) -> {
            startDismissTimer();
            return false;
        });
    }

    public EpisodeClickListener getEpisondeClickListener() {
        return episondeClickListener;
    }

    public void setEpisondeClickListener(EpisodeClickListener episondeClickListener) {
        this.episondeClickListener = episondeClickListener;
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        startDismissTimer();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        cancelDismissTimer();
    }

    public void setPlayNum(int playNum) {
        if (this.playNum != 0) {
            episodeList.get(this.playNum - 1).setPlay(false);
            this.playNum = playNum;
            episodeList.get(playNum - 1).setPlay(true);
        } else {
            this.playNum = playNum;
            episodeList.get(this.playNum - 1).setPlay(true);
        }

        Objects.requireNonNull(episodeRecycler.getAdapter()).notifyDataSetChanged();
    }

    public void startDismissTimer() {
        cancelDismissTimer();
        mDismissTimer = new Timer();
        mDismissTimerTask = new DismissTimerTask();
        mDismissTimer.schedule(mDismissTimerTask, 2500);
    }

    public void cancelDismissTimer() {
        if (mDismissTimer != null) {
            mDismissTimer.cancel();
        }
        if (mDismissTimerTask != null) {
            mDismissTimerTask.cancel();
        }

    }

    public interface EpisodeClickListener {
        /**
         * 选集发生变化
         *
         * @param position
         */
        void onEpisodeClickListener(int position);
    }

    public class DismissTimerTask extends TimerTask {

        @Override
        public void run() {
            Message message = new Message();
            message.what = COMPLETED;
            handler.sendMessage(message);
        }
    }


}


