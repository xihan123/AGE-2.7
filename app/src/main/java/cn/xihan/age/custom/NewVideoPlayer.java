package cn.xihan.age.custom;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.provider.Settings;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import cn.jzvd.JZDataSource;
import cn.jzvd.JZUtils;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import cn.xihan.age.R;
import cn.xihan.age.custom.danmu.AcFunDanmakuParser;
import cn.xihan.age.custom.view.LoadingView;
import cn.xihan.age.custom.view.PlayAndPauseView;
import cn.xihan.age.utils.CenteredImageSpan;
import cn.xihan.age.utils.NetworkUtils;
import cn.xihan.age.utils.StatusBarUtil;
import cn.xihan.age.utils.Utils;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDisplayer;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.SpannedCacheStuffer;
import master.flame.danmaku.ui.widget.DanmakuView;

/**
 * @项目名 : AGE动漫
 * @作者 : MissYang
 * @创建时间 : 2021/3/2 23:30
 * @介绍 :
 */
public class NewVideoPlayer extends JzvdStd {

    private DanmakuView    mDanmakuView;
    private DanmakuContext mContext;

    private AcFunDanmakuParser mParser;

    protected DismissLockViewTimerTask mDismissLockViewTimerTask;
    private   JzVideoListener          jzVideoListener;
    private TextView tv_gd;
    private   ImageView                screenIV, quickRetreat, fastForward, start_bottom, next_bottom;
    private PlayAndPauseView playAndPauseView;
    private CheckBox         lock;
    private TextView         tvSpeed, tvSelectPart, next_set;
    private LinearLayout ll_bottom;
    private LinearLayout ll_top;
    //无网络布局
    private LoadingView  loadingView;
    //是否锁屏状态
    private boolean      isLock = false;
    //是否有下一集
    private boolean      isNext;
    private int          nextTimerDate = 3;
    private Timer        mDismissLockViewTimer, mDismissNextViewTimer;
    private DismissNextViewTimerTask mDismissNextViewTimerTask;
    private boolean clickPlayOrPause;


    public NewVideoPlayer(Context context) {
        super(context);
    }

    public NewVideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public JzVideoListener getJzVideoListener() {
        return jzVideoListener;
    }

    public void setJzVideoListener(JzVideoListener jzVideoListener) {
        this.jzVideoListener = jzVideoListener;
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_ag_video;
    }

    RelativeLayout videoPlayControlLayout;
    @Override
    public void init(Context context) {
        super.init(context);
        mDanmakuView = findViewById(R.id.danmakuView);
        mContext = DanmakuContext.create();
        // 设置最大显示行数
        HashMap<Integer, Integer> maxLinesPair = new HashMap<>();
        maxLinesPair.put(BaseDanmaku.TYPE_SCROLL_RL, 4); // 滚动弹幕最大显示5行
        // 设置是否禁止重叠
        HashMap<Integer, Boolean> overlappingEnablePair = new HashMap<>();
        overlappingEnablePair.put(BaseDanmaku.TYPE_SCROLL_RL, true);
        overlappingEnablePair.put(BaseDanmaku.TYPE_FIX_TOP, true);
        mContext.setDanmakuStyle(IDisplayer.DANMAKU_STYLE_STROKEN, 3) //描边的厚度
                .setDuplicateMergingEnabled(false)
                .setScrollSpeedFactor(1.2f) //弹幕的速度。注意！此值越小，速度越快！值越大，速度越慢。// by phil
                .setScaleTextSize(1.2f)  //缩放的值
                //.setCacheStuffer(new SpannedCacheStuffer(), mCacheStufferAdapter) // 图文混排使用SpannedCacheStuffer
//        .setCacheStuffer(new BackgroundCacheStuffer())  // 绘制背景使用BackgroundCacheStuffer
                .setMaximumLines(maxLinesPair)
                .preventOverlapping(overlappingEnablePair);

        mParser = new AcFunDanmakuParser();
        mDanmakuView.prepare(mParser, mContext);
        mDanmakuView.enableDanmakuDrawingCache(true);
        if (mDanmakuView != null) {
            mDanmakuView.setCallback(new master.flame.danmaku.controller.DrawHandler.Callback() {
                @Override
                public void updateTimer(DanmakuTimer timer) {
                }

                @Override
                public void drawingFinished() {

                }

                @Override
                public void danmakuShown(BaseDanmaku danmaku) {
                    Log.d("弹幕文本", "danmakuShown text=" + danmaku.text);
                }

                @Override
                public void prepared() {
                    mDanmakuView.start();
                }
            });}

        loadingView = findViewById(R.id.player_newLoading);

        //视频控制布局
        videoPlayControlLayout = findViewById(R.id.video_control_layout);
        tvSpeed = findViewById(R.id.tv_speed);
        tvSelectPart = findViewById(R.id.tv_select_parts);
        screenIV = findViewById(R.id.screen);
        tv_gd =findViewById(R.id.tv_gd);
        LinearLayout startLayout = findViewById(R.id.start_layout);
        quickRetreat = findViewById(R.id.quick_retreat);
        fastForward = findViewById(R.id.fast_forward);
        ll_bottom = findViewById(R.id.layout_bottom);
        ll_top = findViewById(R.id.layout_top);
        start_bottom = findViewById(R.id.start_bottom);
        playAndPauseView = findViewById(R.id.playAndPauseView);
        next_bottom = findViewById(R.id.next_bottom);
        lock = findViewById(R.id.lock);
        next_set = findViewById(R.id.next_set);

        next_set.setOnClickListener(this);
        replayTextView.setOnClickListener(this);
        tvSpeed.setOnClickListener(this);
        tvSelectPart.setOnClickListener(this);
        start_bottom.setOnClickListener(this);
        playAndPauseView.setOnClickListener(this);
        next_bottom.setOnClickListener(this);
        quickRetreat.setOnClickListener(this);
        fastForward.setOnClickListener(this);
        screenIV.setOnClickListener(this);
        tv_gd.setOnClickListener(this);
        lock.setOnCheckedChangeListener((buttonView, isChecked) -> {
            isLock = isChecked;
            if (isChecked) {
                //锁屏按钮单独延迟隐藏
                goneLock();
                //隐藏其他功能
                dissmissControlView();
            } else {
                cancelDismissControlViewTimer();
                startDismissControlViewTimer();
                //取消锁屏按钮的单独延迟隐藏，使锁屏按钮的延迟隐藏和其他功能按钮相同
                cancelGoneLock();
                onClickUiToggle();
            }
        });

    }

    public void sendDanmu(String text,boolean isSelf){
        mContext.setCacheStuffer(new SpannedCacheStuffer(), null);
        BaseDanmaku danmaku = mContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
        if (danmaku == null || mDanmakuView == null) {
            return;
        }
        danmaku.text = text;
        danmaku.padding = 3;
        danmaku.priority = 0;  // 可能会被各种过滤器过滤并隐藏显示
        danmaku.isLive = false;
        danmaku.setTime(mDanmakuView.getCurrentTime() + 1200);
        danmaku.textSize = Utils.dip2px(getContext(), isSelf ? 20 : 12); //文本弹幕字体大小
        danmaku.textColor = isSelf ? Color.BLUE : Color.WHITE; //文本的颜色
        danmaku.textShadowColor = Color.GRAY; //文本弹幕描边的颜色
        danmaku.underlineColor = isSelf ? Color.GREEN : Color.TRANSPARENT; //文本弹幕下划线的颜色
        danmaku.borderColor = isSelf ? Color.GREEN : Color.TRANSPARENT; //边框的颜色

        mDanmakuView.addDanmaku(danmaku);
    }


    /**
     * 发送自定义弹幕
     */
    public void addDanmakuWithDrawable(String text) {
        mContext.setCacheStuffer(new BackgroundCacheStuffer(), null);
        BaseDanmaku danmaku = mContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
        if (danmaku == null) {
            return;
        }
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.mipmap.ic_launcher);
        int size = Utils.dip2px(getContext(), 20);
        drawable.setBounds(0, 0, size, size);
        danmaku.text = createSpannable(drawable, text);
        danmaku.priority = 4;  // 可能会被各种过滤器过滤并隐藏显示
        danmaku.isLive = false;
        danmaku.setTime(mDanmakuView.getCurrentTime() + 1200);
        danmaku.textSize = Utils.dip2px(getContext(), 12);
        danmaku.textColor = Color.RED;
        danmaku.textShadowColor = Color.WHITE;
        mDanmakuView.addDanmaku(danmaku);

    }

    private SpannableStringBuilder createSpannable(Drawable drawable,String text) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(text);
        CenteredImageSpan span = new CenteredImageSpan(drawable);//ImageSpan.ALIGN_BOTTOM);
        spannableStringBuilder.setSpan(span, 0, text.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.append(text);
        return spannableStringBuilder;
    }

    /**
     * 绘制背景(自定义弹幕样式)
     */
    private class BackgroundCacheStuffer extends SpannedCacheStuffer {
        // 通过扩展SimpleTextCacheStuffer或SpannedCacheStuffer个性化你的弹幕样式
        final Paint paint = new Paint();

        @Override
        public void measure(BaseDanmaku danmaku, TextPaint paint, boolean fromWorkerThread) {
//            danmaku.padding = 5;  // 在背景绘制模式下增加padding
            super.measure(danmaku, paint, fromWorkerThread);
        }

        @Override
        public void drawBackground(BaseDanmaku danmaku, Canvas canvas, float left, float top) {
            paint.setAntiAlias(true);
            paint.setColor(Color.parseColor("#65777777"));//黑色 普通
            int radius = Utils.dip2px(getContext(), 10);
            canvas.drawRoundRect(new RectF(left, top, left + danmaku.paintWidth, top + danmaku.paintHeight), radius, radius, paint);
        }

        @Override
        public void drawStroke(BaseDanmaku danmaku, String lineText, Canvas canvas, float left, float top, Paint paint) {
            // 禁用描边绘制
        }
    }

    public void ShowDanmu(){
        mDanmakuView.show();
    }

    public void HideDanmu(){
        mDanmakuView.hide();
    }

    @Override
    public void onStatePreparing() {
        super.onStatePreparing();
        if (mDanmakuView.isPrepared()) {
            mDanmakuView.restart();
        }
        mDanmakuView.prepare(mParser, mContext);
    }


    @Override
    public void onStatePause() {
        super.onStatePause();
        if (mDanmakuView.isPrepared()) {
            mDanmakuView.pause();
        }
    }

    public void releaseDanMu() {
        mDanmakuView.release();
        mDanmakuView = null;
    }

    @Override
    public void onStateError() {
        super.onStateError();
        mDanmakuView.release();
    }


    /**
     * 从一系列颜色中随机选择一种颜色
     *
     * @return
     */
    private int getRandomColor() {
        int[] colors = {Color.RED, Color.YELLOW, Color.BLUE, Color.GREEN, Color.CYAN, Color.BLACK, Color.DKGRAY};
        int i = ((int) (Math.random() * 10)) % colors.length;
        return colors[i];
    }




    private void cancelGoneLock() {
        cancelDismissLockViewTimer();
    }

    private void goneLock() {
        startDismissLockViewTimer();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.start:
            case R.id.start_bottom:
            case R.id.playAndPauseView:
                clickPlayOrPause = true;
                playAndPauseView.playOrPause();
                if (jzDataSource == null || jzDataSource.urlsMap.isEmpty() || jzDataSource.getCurrentUrl() == null) {
                    Toast.makeText(getContext(), getResources().getString(cn.jzvd.R.string.no_url), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (state == STATE_NORMAL) {
                    if (!jzDataSource.getCurrentUrl().toString().startsWith("file") && !
                            jzDataSource.getCurrentUrl().toString().startsWith("/") &&
                            !JZUtils.isWifiConnected(getContext()) && !WIFI_TIP_DIALOG_SHOWED) {//这个可以放到std中
                        showWifiDialog();
                        return;
                    }
                    startVideo();
                } else if (state == STATE_PLAYING) {
                    Log.d(TAG, "pauseVideo [" + this.hashCode() + "] ");
                    mediaInterface.pause();
                    onStatePause();
                } else if (state == STATE_PAUSE) {
                    mediaInterface.start();
                    onStatePlaying();
                } else if (state == STATE_AUTO_COMPLETE) {
                    startVideo();
                }
                break;
            case R.id.poster:
                if (jzDataSource == null || jzDataSource.urlsMap.isEmpty() || jzDataSource.getCurrentUrl() == null) {
                    Toast.makeText(getContext(), getResources().getString(cn.jzvd.R.string.no_url), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (state == STATE_NORMAL) {
                    if (!jzDataSource.getCurrentUrl().toString().startsWith("file") &&
                            !jzDataSource.getCurrentUrl().toString().startsWith("/") &&
                            !JZUtils.isWifiConnected(getContext()) && !WIFI_TIP_DIALOG_SHOWED) {
                        showWifiDialog();
                        return;
                    }
                    startVideo();
                } else if (state == STATE_AUTO_COMPLETE) {
                    onClickUiToggle();
                }
                break;
            case R.id.surface_container:
                startDismissControlViewTimer();
                break;
            case R.id.back_tiny:
                clearFloatScreen();
                break;
            case R.id.clarity:
                LayoutInflater inflater = (LayoutInflater) getContext()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final LinearLayout layout = (LinearLayout) inflater.inflate(cn.jzvd.R.layout.jz_layout_clarity, null);

                OnClickListener mQualityListener = v1 -> {

//                    this.seekToInAdvance = getCurrentPositionWhenPlaying();
                    jzDataSource.currentUrlIndex = (int) v1.getTag();
//                    onStatePreparingChangeUrl();

                    changeUrl(jzDataSource, getCurrentPositionWhenPlaying());


                    clarity.setText(jzDataSource.getCurrentKey().toString());
                    for (int j = 0; j < layout.getChildCount(); j++) {//设置点击之后的颜色
                        if (j == jzDataSource.currentUrlIndex) {
                            ((TextView) layout.getChildAt(j)).setTextColor(Color.parseColor("#fff85959"));
                        } else {
                            ((TextView) layout.getChildAt(j)).setTextColor(Color.parseColor("#ffffff"));
                        }
                    }
                    if (clarityPopWindow != null) {
                        clarityPopWindow.dismiss();
                    }
                };

                for (int j = 0; j < jzDataSource.urlsMap.size(); j++) {
                    String key = jzDataSource.getKeyFromDataSource(j);
                    TextView clarityItem = (TextView) View.inflate(getContext(), cn.jzvd.R.layout.jz_layout_clarity_item, null);
                    clarityItem.setText(key);
                    clarityItem.setTag(j);
                    layout.addView(clarityItem, j);
                    clarityItem.setOnClickListener(mQualityListener);
                    if (j == jzDataSource.currentUrlIndex) {
                        clarityItem.setTextColor(Color.parseColor("#fff85959"));
                    }
                }

                clarityPopWindow = new PopupWindow(layout, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
                clarityPopWindow.setContentView(layout);
                clarityPopWindow.showAsDropDown(clarity);
                layout.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                int offsetX = clarity.getMeasuredWidth() / 3;
                int offsetY = clarity.getMeasuredHeight() / 3;
                clarityPopWindow.update(clarity, -offsetX, -offsetY, Math.round(layout.getMeasuredWidth() * 2), layout.getMeasuredHeight());
                break;
            case R.id.retry_btn:
                if (jzDataSource.urlsMap.isEmpty() || jzDataSource.getCurrentUrl() == null) {
                    Toast.makeText(getContext(), getResources().getString(cn.jzvd.R.string.no_url), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!jzDataSource.getCurrentUrl().toString().startsWith("file") && !
                        jzDataSource.getCurrentUrl().toString().startsWith("/") &&
                        !JZUtils.isWifiConnected(getContext()) && !WIFI_TIP_DIALOG_SHOWED) {
                    showWifiDialog();
                    return;
                }
                addTextureView();
                onStatePreparing();
                break;
            case R.id.replay_text:
                if (state == STATE_AUTO_COMPLETE) {
                    replayTextView.setVisibility(View.GONE);
                    next_set.setVisibility(View.GONE);
                    //点击重播，取消下一集倒计时
                    dismissNextView();
                    cancelDismissNextViewTimer();

                    //resetProgressAndTime();
                    //mediaInterface.seekTo(0);
                    changeUrl(jzDataSource, 0);
                }
                break;
            case R.id.next_set:
                dismissNextView();
                cancelDismissNextViewTimer();
                if (jzVideoListener != null) {
                    jzVideoListener.nextClick();
                }
                break;
            case R.id.back:
            case R.id.top_back:
                if (jzVideoListener != null) {
                    jzVideoListener.backClick();
                }
                break;
            case R.id.tv_speed:
                if (jzVideoListener != null) {
                    jzVideoListener.speedClick();
                }
                break;
            case R.id.tv_select_parts:
                if (jzVideoListener != null) {
                    jzVideoListener.selectPartsClick();
                }
                break;
            case R.id.next_bottom:
                if (jzVideoListener != null) {
                    jzVideoListener.nextClick();
                }
                break;
            case R.id.fast_forward:
                //总时间长度
                long duration = getDuration();
                //当前时间
                long currentPositionWhenPlaying = getCurrentPositionWhenPlaying();
                //快进（15S）
                long fastForwardProgress = currentPositionWhenPlaying + 15 * 1000;
                mediaInterface.seekTo(Math.min(duration, fastForwardProgress));
                break;
            case R.id.quick_retreat:
                //当前时间
                long quickRetreatCurrentPositionWhenPlaying = getCurrentPositionWhenPlaying();
                //快退（15S）
                long quickRetreatProgress = quickRetreatCurrentPositionWhenPlaying - 15 * 1000;
                if (quickRetreatProgress > 0) {
                    mediaInterface.seekTo(quickRetreatProgress);
                } else {
                    mediaInterface.seekTo(0);
                }
                break;
            case R.id.fullscreen:
                if (state == STATE_AUTO_COMPLETE) return;
                if (screen == SCREEN_FULLSCREEN) {
                    //quit fullscreen
                    backPress();
                } else {
                    gotoFullscreen();
                }
                break;
            case R.id.screen:
                if (jzVideoListener != null) {
                    jzVideoListener.throwingScreenClick();
                }
            case R.id.tv_gd:
                if (jzVideoListener != null) {
                    jzVideoListener.throwingGdClick();
                }

                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int id = v.getId();
        if (id == R.id.surface_container) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_MOVE:
                    break;
                case MotionEvent.ACTION_UP:
                    startDismissControlViewTimer();
                    if (mChangePosition) {
                        long duration = getDuration();
                        int progress = (int) (mSeekTimePosition * 100 / (duration == 0 ? 1 : duration));
                        bottomProgressBar.setProgress(progress);
                    }
                    if (!mChangePosition && !mChangeVolume) {
                        onClickUiToggle();
                    }
                    break;
            }
        } else if (id == R.id.bottom_seek_progress) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    cancelDismissControlViewTimer();
                    break;
                case MotionEvent.ACTION_UP:
                    startDismissControlViewTimer();
                    break;
            }
        }
        float x = event.getX();
        float y = event.getY();
        if (id == R.id.surface_container) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    Log.i(TAG, "onTouch surfaceContainer actionDown [" + this.hashCode() + "] ");
                    mTouchingProgressBar = true;

                    mDownX = x;
                    mDownY = y;
                    mChangeVolume = false;
                    mChangePosition = false;
                    mChangeBrightness = false;
                    break;
                case MotionEvent.ACTION_MOVE:
                    Log.i(TAG, "onTouch surfaceContainer actionMove [" + this.hashCode() + "] ");
                    float deltaX = x - mDownX;
                    float deltaY = y - mDownY;
                    if (!isLock) {
                        moveChange(event);
                    }
                    if (mChangePosition) {
                        long totalTimeDuration = getDuration();
                        mSeekTimePosition = (int) (mGestureDownPosition + deltaX * totalTimeDuration / mScreenWidth);
                        if (mSeekTimePosition > totalTimeDuration)
                            mSeekTimePosition = totalTimeDuration;
                        String seekTime = JZUtils.stringForTime(mSeekTimePosition);
                        String totalTime = JZUtils.stringForTime(totalTimeDuration);

                        showProgressDialog(deltaX, seekTime, mSeekTimePosition, totalTime, totalTimeDuration);
                    }
                    if (mChangeVolume) {
                        deltaY = -deltaY;
                        int max = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                        int deltaV = (int) (max * deltaY * 3 / mScreenHeight);
                        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mGestureDownVolume + deltaV, 0);
                        //dialog中显示百分比
                        int volumePercent = (int) (mGestureDownVolume * 100 / max + deltaY * 3 * 100 / mScreenHeight);
                        showVolumeDialog(-deltaY, volumePercent);
                    }

                    if (mChangeBrightness) {
                        deltaY = -deltaY;
                        int deltaV = (int) (255 * deltaY * 3 / mScreenHeight);
                        WindowManager.LayoutParams params = JZUtils.getWindow(getContext()).getAttributes();
                        if (((mGestureDownBrightness + deltaV) / 255) >= 1) {//这和声音有区别，必须自己过滤一下负值
                            params.screenBrightness = 1;
                        } else if (((mGestureDownBrightness + deltaV) / 255) <= 0) {
                            params.screenBrightness = 0.01f;
                        } else {
                            params.screenBrightness = (mGestureDownBrightness + deltaV) / 255;
                        }
                        JZUtils.getWindow(getContext()).setAttributes(params);
                        //dialog中显示百分比
                        int brightnessPercent = (int) (mGestureDownBrightness * 100 / 255 + deltaY * 3 * 100 / mScreenHeight);
                        showBrightnessDialog(brightnessPercent);
//                        mDownY = y;
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    Log.i(TAG, "onTouch surfaceContainer actionUp [" + this.hashCode() + "] ");
                    mTouchingProgressBar = false;
                    dismissProgressDialog();
                    dismissVolumeDialog();
                    dismissBrightnessDialog();
                    if (mChangePosition) {
                        if (mediaInterface != null) {
                            mediaInterface.seekTo(mSeekTimePosition);
                        }
                        long duration = getDuration();
                        int progress = (int) (mSeekTimePosition * 100 / (duration == 0 ? 1 : duration));
                        progressBar.setProgress(progress);
                    }
                    //change volume event
                    startProgressTimer();
                    break;
            }
        }
        return false;
    }


    @Override
    public void changeUrl(JZDataSource jzDataSource, long seekToInAdvance) {
        next_set.setVisibility(GONE);
        showProgress();
        super.changeUrl(jzDataSource, seekToInAdvance);
//        //切换播放地址之后继续以1倍速播放
        if (jzDataSource.objects == null) {
            jzDataSource.objects = new Object[]{1};
        }
        speedChange(1.0f);
        resetProgressAndTime();
    }

    @Override
    public void dissmissControlView() {
        if (state != STATE_NORMAL
                && state != STATE_ERROR
                && state != STATE_AUTO_COMPLETE) {
            post(() -> {
                bottomContainer.setVisibility(View.INVISIBLE);
                topContainer.setVisibility(View.INVISIBLE);
                startButton.setVisibility(View.INVISIBLE);
                fastForward.setVisibility(View.INVISIBLE);
                quickRetreat.setVisibility(View.INVISIBLE);
                if (!isLock) {
                    lock.setVisibility(View.INVISIBLE);
                }
                if (clarityPopWindow != null) {
                    clarityPopWindow.dismiss();
                }
                if (screen != SCREEN_TINY && screen != SCREEN_FULLSCREEN) {
                    bottomProgressBar.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    public void dismissLockView() {
        if (state != STATE_NORMAL
                && state != STATE_ERROR
                && state != STATE_AUTO_COMPLETE) {
            post(() -> lock.setVisibility(GONE));
        }
    }


    @Override
    public void changeUiToPreparing() {
        switch (screen) {
            case SCREEN_NORMAL:
            case SCREEN_FULLSCREEN:
                screenIV.setVisibility(GONE);
                tv_gd.setVisibility(GONE);
                titleTextView.setVisibility(GONE);
                batteryTimeLayout.setVisibility(GONE);
                setAllControlsVisiblity(View.VISIBLE, View.INVISIBLE, View.INVISIBLE,
                        View.VISIBLE, View.VISIBLE, View.INVISIBLE, View.INVISIBLE);
                updateStartImage();
                break;
            case SCREEN_TINY:
                break;
        }

    }


    @Override
    public void updateStartImage() {
        if (state == STATE_PLAYING) {
            startButton.setVisibility(VISIBLE);
            startButton.setImageResource(R.drawable.ag_btn_movie_suspend);
            start_bottom.setImageResource(R.drawable.ag_btn_movie_stop_bottom);
            fastForward.setVisibility(VISIBLE);
            quickRetreat.setVisibility(VISIBLE);
            replayTextView.setVisibility(GONE);
        } else if (state == STATE_PREPARING) {
            backButton.setVisibility(VISIBLE);
        } else if (state == STATE_ERROR) {
            startButton.setVisibility(INVISIBLE);
            replayTextView.setVisibility(GONE);
            fastForward.setVisibility(GONE);
            quickRetreat.setVisibility(GONE);
        } else if (state == STATE_AUTO_COMPLETE) {
            //视频播放完成状态
            startButton.setVisibility(View.GONE);
            replayTextView.setVisibility(VISIBLE);
            if (isNext) {
                next_set.setVisibility(VISIBLE);
            }
            fastForward.setVisibility(GONE);
            quickRetreat.setVisibility(GONE);
        } else {
            startButton.setImageResource(R.drawable.ag_btn_movie_play);
            start_bottom.setImageResource(R.drawable.ag_btn_movie_play_bottom);
            replayTextView.setVisibility(GONE);
            fastForward.setVisibility(GONE);
            quickRetreat.setVisibility(GONE);
        }
    }

    private void updateConfigChanged(int state) {
        Log.d(TAG, "updateConfigChanged state: " + (state == Jzvd.SCREEN_FULLSCREEN));
        if (state == Jzvd.SCREEN_FULLSCREEN) {
            StatusBarUtil.setNoTranslucentForImageView((Activity) getContext(), 0, findViewById(R.id.layout_top));
            ll_top.setBackgroundResource(R.drawable.jz_title_bg);
            ll_bottom.setBackgroundResource(R.drawable.jz_bottom_bg);
        } else {
            StatusBarUtil.setTranslucentForImageView((Activity) getContext(), 0, findViewById(R.id.layout_top));
            ll_top.setBackgroundResource(0);
            ll_bottom.setBackgroundResource(0);
        }
    }

    @Override
    public void setScreenNormal() {
        screen = SCREEN_NORMAL;
        fullscreenButton.setImageResource(cn.jzvd.R.drawable.jz_enlarge);
        backButton.setVisibility(View.VISIBLE);
        tinyBackImageView.setVisibility(View.INVISIBLE);
        changeStartButtonSize((int) getResources().getDimension(cn.jzvd.R.dimen.jz_start_button_w_h_normal));
        batteryTimeLayout.setVisibility(View.GONE);
        clarity.setVisibility(View.GONE);
        fullscreenButton.setVisibility(View.VISIBLE);
        next_bottom.setVisibility(View.GONE);
        tvSpeed.setVisibility(View.GONE);
        tvSelectPart.setVisibility(View.GONE);
        lock.setVisibility(View.GONE);
        changeUiToPlayingShow();
        startDismissControlViewTimer();
    }

    @Override
    public void setScreenFullscreen() {
        super.setScreenFullscreen();
        next_bottom.setVisibility(View.VISIBLE);
        tvSpeed.setVisibility(View.VISIBLE);
        tvSelectPart.setVisibility(View.VISIBLE);
        fullscreenButton.setVisibility(View.GONE);
        lock.setVisibility(View.VISIBLE);
        changeUiToPlayingShow();
        startDismissControlViewTimer();
        if (jzDataSource.objects == null) {
            jzDataSource.objects = new Object[]{1};
        }
    }

    @Override
    public void onStatePlaying() {
//        super.onStatePlaying();
        Log.i(TAG, "onStatePlaying " + " [" + this.hashCode() + "] ");
        if (state == STATE_PREPARED) {//如果是准备完成视频后第一次播放，先判断是否需要跳转进度。
            if (seekToInAdvance != 0) {
                mediaInterface.seekTo(seekToInAdvance);
                seekToInAdvance = 0;
            } else {
                long position = JZUtils.getSavedProgress(getContext(), jzDataSource.getCurrentUrl());
                if (position != 0) {
                    mediaInterface.seekTo(position);//这里为什么区分开呢，第一次的播放和resume播放是不一样的。 这里怎么区分是一个问题。然后
                }
            }
        }
        state = STATE_PLAYING;
        startProgressTimer();
        //Log.e("xihan", "单击播放或暂停:" + clickPlayOrPause);
        updateStartImage();
        if (clickPlayOrPause) {
            startDismissControlViewTimer();
        } else {
            changeUiToPlayingClear();
        }
        titleTextView.setVisibility(VISIBLE);
        screenIV.setVisibility(VISIBLE);
        tv_gd.setVisibility(VISIBLE);

    }

    @Override
    public void onCompletion() {
        Runtime.getRuntime().gc();
        Log.i(TAG, "onAutoCompletion " + " [" + this.hashCode() + "] ");
        cancelProgressTimer();
        dismissBrightnessDialog();
        dismissProgressDialog();
        dismissVolumeDialog();
        onStateAutoComplete();
        JZUtils.scanForActivity(getContext()).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        JZUtils.saveProgress(getContext(), jzDataSource.getCurrentUrl(), 0);
        cancelDismissControlViewTimer();
    }

    @Override
    public void onStateAutoComplete() {
        super.onStateAutoComplete();
        if (isNext) {
            startDismissNextViewTimer();
        }
        mDanmakuView.stop();
        mDanmakuView.clear();
        mDanmakuView.clearDanmakusOnScreen();
    }

    @Override
    public void changeUiToPlayingShow() {
        switch (screen) {
            case SCREEN_NORMAL:
                setAllControlsVisiblity(View.VISIBLE, View.VISIBLE, View.VISIBLE,
                        View.INVISIBLE, View.INVISIBLE, View.INVISIBLE, View.INVISIBLE);
                updateStartImage();
                updateConfigChanged(screen);
                break;
            case SCREEN_FULLSCREEN:
                if (!isLock) {
                    setAllControlsVisiblity(View.VISIBLE, View.VISIBLE, View.VISIBLE,
                            View.INVISIBLE, View.INVISIBLE, View.INVISIBLE, View.INVISIBLE);
                    updateStartImage();
                    updateConfigChanged(screen);
                }
                lock.setVisibility(View.VISIBLE);
                break;
            case SCREEN_TINY:
                break;
        }
    }


    @Override
    public void changeUiToPlayingClear() {
        switch (screen) {
            case SCREEN_NORMAL:
                setAllControlsVisiblity(View.INVISIBLE, View.INVISIBLE, View.INVISIBLE,
                        View.INVISIBLE, View.INVISIBLE, View.VISIBLE, View.INVISIBLE);
                break;
            case SCREEN_FULLSCREEN:
                setAllControlsVisiblity(View.INVISIBLE, View.INVISIBLE, View.INVISIBLE,
                        View.INVISIBLE, View.INVISIBLE, View.INVISIBLE, View.INVISIBLE);
                lock.setVisibility(View.INVISIBLE);
                break;
            case SCREEN_TINY:
                break;
        }
    }

    @Override
    public void changeUiToPauseShow() {
        switch (screen) {
            case SCREEN_NORMAL:
                setAllControlsVisiblity(View.VISIBLE, View.VISIBLE, View.VISIBLE,
                        View.INVISIBLE, View.INVISIBLE, View.INVISIBLE, View.INVISIBLE);
                updateStartImage();
                break;
            case SCREEN_FULLSCREEN:
                setAllControlsVisiblity(View.VISIBLE, View.VISIBLE, View.VISIBLE,
                        View.INVISIBLE, View.INVISIBLE, View.INVISIBLE, View.INVISIBLE);
                lock.setVisibility(View.VISIBLE);
                updateStartImage();
                break;
            case SCREEN_TINY:
                break;
        }

    }

    @Override
    public void changeUiToPauseClear() {
        switch (screen) {
            case SCREEN_NORMAL:
                setAllControlsVisiblity(View.INVISIBLE, View.INVISIBLE, View.INVISIBLE,
                        View.INVISIBLE, View.INVISIBLE, View.VISIBLE, View.INVISIBLE);
                break;
            case SCREEN_FULLSCREEN:
                setAllControlsVisiblity(View.INVISIBLE, View.INVISIBLE, View.INVISIBLE,
                        View.INVISIBLE, View.INVISIBLE, View.VISIBLE, View.INVISIBLE);
                lock.setVisibility(View.INVISIBLE);
                break;
            case SCREEN_TINY:
                break;
        }
    }

    @Override
    public void changeUiToComplete() {
        switch (screen) {
            case SCREEN_NORMAL:
                setAllControlsVisiblity(View.VISIBLE, View.INVISIBLE, View.VISIBLE,
                        View.INVISIBLE, View.INVISIBLE, View.INVISIBLE, View.INVISIBLE);
                updateStartImage();
                break;
            case SCREEN_FULLSCREEN:
                setAllControlsVisiblity(View.VISIBLE, View.INVISIBLE, View.VISIBLE,
                        View.INVISIBLE, View.INVISIBLE, View.INVISIBLE, View.INVISIBLE);
                updateStartImage();
                break;
            case SCREEN_TINY:
                break;
        }
    }

    @Override
    public void onClickUiToggle() {
        if (bottomContainer.getVisibility() != View.VISIBLE) {
            setSystemTimeAndBattery();
            clarity.setText(jzDataSource.getCurrentKey().toString());
        }
        if (state == STATE_PREPARING) {
            changeUiToPreparing();
            if (bottomContainer.getVisibility() == View.VISIBLE) {
            } else {
                setSystemTimeAndBattery();
            }
        } else if (state == STATE_PLAYING) {
            if (isLock) {
                if (lock.getVisibility() == View.VISIBLE) {
                    lock.setVisibility(INVISIBLE);
                } else {
                    lock.setVisibility(View.VISIBLE);
                    goneLock();
                }
            } else {
                if (bottomContainer.getVisibility() == View.VISIBLE) {
                    changeUiToPlayingClear();
                } else {
                    changeUiToPlayingShow();
                }
            }
        } else if (state == STATE_PAUSE) {
            if (isLock) {
                if (lock.getVisibility() == View.VISIBLE) {
                    lock.setVisibility(INVISIBLE);
                } else {
                    lock.setVisibility(View.VISIBLE);
                    goneLock();
                }
            } else {
                if (bottomContainer.getVisibility() == View.VISIBLE) {
                    changeUiToPauseClear();
                } else {
                    changeUiToPauseShow();
                }
            }
        }
    }

    @Override
    public void changeStartButtonSize(int size) {
        //修改框架原本的图标大小
        size = (int) getResources().getDimension(cn.jzvd.R.dimen.jz_start_button_w_h_normal);

        ViewGroup.LayoutParams lp = startButton.getLayoutParams();
        lp.height = size;
        lp.width = size;
        lp = loadingProgressBar.getLayoutParams();
        lp.height = size;
        lp.width = size;
    }

    @Override
    public void setAllControlsVisiblity(int topCon, int bottomCon, int startBtn, int loadingPro, int thumbImg, int bottomPro, int retryLayout) {
        topContainer.setVisibility(topCon);
        bottomContainer.setVisibility(bottomCon);
        startButton.setVisibility(startBtn);
        loadingView.setVisibility(loadingPro);
        posterImageView.setVisibility(thumbImg);
        bottomProgressBar.setVisibility(bottomPro);
        mRetryLayout.setVisibility(retryLayout);
        fastForward.setVisibility(startBtn);
        quickRetreat.setVisibility(startBtn);
    }

    /**
     * 普通窗口下亮度、音量、播放进度的调节功能
     *
     * @param event
     */
    private void moveChange(MotionEvent event) {
        if (screen == SCREEN_NORMAL || screen == SCREEN_FULLSCREEN) {
            float x = event.getX();
            float y = event.getY();
            float deltaX = x - mDownX;
            float deltaY = y - mDownY;
            float absDeltaX = Math.abs(deltaX);
            float absDeltaY = Math.abs(deltaY);
            if (!mChangePosition && !mChangeVolume && !mChangeBrightness) {
                if (absDeltaX > THRESHOLD || absDeltaY > THRESHOLD) {
                    cancelProgressTimer();
                    if (absDeltaX >= THRESHOLD) {
                        // 全屏模式下的CURRENT_STATE_ERROR状态下,不响应进度拖动事件.
                        // 否则会因为mediaplayer的状态非法导致App Crash
                        if (state != STATE_ERROR) {
                            mChangePosition = true;
                            mGestureDownPosition = getCurrentPositionWhenPlaying();
                        }
                    } else {
                        //如果y轴滑动距离超过设置的处理范围，那么进行滑动事件处理
                        if (mDownX < mScreenWidth * 0.5f) {//左侧改变亮度
                            mChangeBrightness = true;
                            WindowManager.LayoutParams lp = JZUtils.getWindow(getContext()).getAttributes();
                            if (lp.screenBrightness < 0) {
                                try {
                                    mGestureDownBrightness = Settings.System.getInt(getContext().getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
                                    Log.i(TAG, "current system brightness: " + mGestureDownBrightness);
                                } catch (Settings.SettingNotFoundException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                mGestureDownBrightness = lp.screenBrightness * 255;
                                Log.i(TAG, "current activity brightness: " + mGestureDownBrightness);
                            }
                        } else {//右侧改变声音
                            mChangeVolume = true;
                            mGestureDownVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                        }
                    }
                }
            }
        }
    }


    /**
     * 是否有网络并显示布局
     */
    public boolean isHaveNetWork() {
        return NetworkUtils.isConnected(this.getApplicationContext()) && NetworkUtils.isAvailable(this.getApplicationContext());
    }


    /**
     * 点击播放下一集时设置按钮状态
     *
     * @param isNext
     */
    public void changeNextBottonUi(boolean isNext) {
        this.isNext = isNext;
        if (isNext) {
            next_bottom.setImageResource(R.drawable.ag_btn_movie_next);
            next_bottom.setClickable(true);
        } else {
            next_bottom.setImageResource(R.drawable.ag_btn_movie_unll_next);
            next_bottom.setClickable(false);
        }
    }


    public void hideProgress() {
        if (loadingView != null) {
            loadingView.setVisibility(GONE);
        }
    }

    @Override
    public void onStatePreparingPlaying() {
        state = STATE_PREPARING_PLAYING;
        showProgress();
    }

    public void showProgress() {
        if (loadingView.getVisibility() != View.VISIBLE) {
            loadingView.setVisibility(VISIBLE);
        }
    }

    public void startDismissLockViewTimer() {
        cancelDismissLockViewTimer();
        mDismissLockViewTimer = new Timer();
        mDismissLockViewTimerTask = new DismissLockViewTimerTask();
        mDismissLockViewTimer.schedule(mDismissLockViewTimerTask, 2500);
    }

    public void cancelDismissLockViewTimer() {
        if (mDismissLockViewTimer != null) {
            mDismissLockViewTimer.cancel();
        }
        if (mDismissLockViewTimerTask != null) {
            mDismissLockViewTimerTask.cancel();
        }

    }

    public void startDismissNextViewTimer() {
        cancelDismissLockViewTimer();
        nextTimerDate = 3;
        mDismissNextViewTimer = new Timer();
        mDismissNextViewTimerTask = new DismissNextViewTimerTask();
        mDismissNextViewTimer.schedule(mDismissNextViewTimerTask, 0, 1000);
    }

    public void cancelDismissNextViewTimer() {
        if (mDismissNextViewTimer != null) {
            mDismissNextViewTimer.cancel();
        }
        if (mDismissNextViewTimerTask != null) {
            mDismissNextViewTimerTask.cancel();
        }
    }


    private void dismissNextView() {
        replayTextView.setVisibility(GONE);
        next_set.setVisibility(View.GONE);
    }

    /**
     * 改变倍数之后
     */
    public void speedChange(float speed) {
        if (speed == 1f) {
            tvSpeed.setText("倍数");
        } else {
            tvSpeed.setText(speed + "X");
        }
    }

    public interface JzVideoListener {

        void nextClick();

        void backClick();

        void throwingScreenClick();

        void selectPartsClick();

        void speedClick();

        void throwingGdClick();


    }


    public void gotoTinyScreen() {
        Log.i(TAG, "startWindowTiny " + " [" + this.hashCode() + "] ");
        if (state == STATE_NORMAL || state == STATE_ERROR || state == STATE_AUTO_COMPLETE)
            return;
        ViewGroup vg = (ViewGroup) getParent();
        jzvdContext = vg.getContext();
        blockLayoutParams = getLayoutParams();
        blockIndex = vg.indexOfChild(this);
        blockWidth = getWidth();
        blockHeight = getHeight();

        Log.d("xihan","宽:" +blockWidth +"\n高:" + blockHeight );

        vg.removeView(this);
        cloneAJzvd(vg);
        CONTAINER_LIST.add(vg);

        ViewGroup vgg = (ViewGroup) (JZUtils.scanForActivity(getContext())).getWindow().getDecorView();//和他也没有关系
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(blockWidth, blockHeight);
        //lp.gravity = Gravity.RIGHT | Gravity.BOTTOM;
        //添加滑动事件等
        vgg.addView(this, lp);
        setScreenTiny();
    }

    public class DismissLockViewTimerTask extends TimerTask {

        @Override
        public void run() {
            dismissLockView();
        }
    }

    public class DismissNextViewTimerTask extends TimerTask {

        @Override
        public void run() {
            post(() -> {
                if (nextTimerDate <= 0) {
                    dismissNextView();
                    cancelDismissNextViewTimer();
                    if (jzVideoListener != null) {
                        jzVideoListener.nextClick();
                    }
                } else {
                    next_set.setText(nextTimerDate + "秒后播放下一集");
                    nextTimerDate--;
                }
            });
        }
    }


}

