package cn.xihan.age.ui.activitys;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import com.qmuiteam.qmui.arch.QMUIFragment;
import com.qmuiteam.qmui.arch.QMUIFragmentActivity;
import com.qmuiteam.qmui.arch.annotation.DefaultFirstFragment;
import com.qmuiteam.qmui.arch.annotation.FirstFragments;
import com.qmuiteam.qmui.arch.annotation.LatestVisitRecord;
import com.qmuiteam.qmui.skin.QMUISkinHelper;
import com.qmuiteam.qmui.skin.QMUISkinMaker;
import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.skin.QMUISkinValueBuilder;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.util.QMUIViewOffsetHelper;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView2;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;
import com.qmuiteam.qmui.widget.popup.QMUIPopups;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.xihan.age.MyApp;
import cn.xihan.age.R;
import cn.xihan.age.base.BaseFragment;
import cn.xihan.age.base.BaseFragmentActivity;
import cn.xihan.age.manager.QDSkinManager;
import cn.xihan.age.ui.fragments.home.HomeFragment;
import cn.xihan.age.ui.fragments.home.MlFragment;
import cn.xihan.age.ui.fragments.home.MyFragment;
import cn.xihan.age.ui.fragments.home.PhbFragment;
import cn.xihan.age.ui.fragments.home.SyFragment;
import cn.xihan.age.ui.fragments.home.TjFragment;


@FirstFragments(
        value = {
                HomeFragment.class,
                SyFragment.class,
                MlFragment.class,
                TjFragment.class,
                PhbFragment.class,
                MyFragment.class,
        })
@DefaultFirstFragment(HomeFragment.class)
@LatestVisitRecord
public class MainActivity extends BaseFragmentActivity {

    private QMUIPopup mGlobalAction;

    private final QMUISkinManager.OnSkinChangeListener mOnSkinChangeListener = (skinManager, oldSkin, newSkin) -> {
        if (newSkin == QDSkinManager.SKIN_WHITE) {
            QMUIStatusBarHelper.setStatusBarLightMode(MainActivity.this);
        } else {
            QMUIStatusBarHelper.setStatusBarDarkMode(MainActivity.this);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QMUIStatusBarHelper.translucent(this);
        QMUISkinManager skinManager = QMUISkinManager.defaultInstance(this);
        setSkinManager(skinManager);
        mOnSkinChangeListener.onSkinChange(skinManager, -1, skinManager.getCurrentSkin());
    }


    @Override
    protected RootView onCreateRootView(int fragmentContainerId) {
        return new CustomRootView(this, fragmentContainerId);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private void renderSkinMakerBtn() {
        Fragment baseFragment = getCurrentFragment();
        if (baseFragment instanceof BaseFragment) {
            if (MyApp.openSkinMake) {
                ((BaseFragment) baseFragment).openSkinMaker();
            } else {
                QMUISkinMaker.getInstance().unBindAll();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (getSkinManager() != null) {
            getSkinManager().addSkinChangeListener(mOnSkinChangeListener);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        renderSkinMakerBtn();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (getSkinManager() != null) {
            getSkinManager().removeSkinChangeListener(mOnSkinChangeListener);
        }
    }

    private void showGlobalActionPopup(View v) {
        String[] listItems = new String[]{
                "更换主题",
        };
        List<String> data = new ArrayList<>();

        Collections.addAll(data, listItems);

        ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.simple_list_item, data);
        AdapterView.OnItemClickListener onItemClickListener = (adapterView, view, i, l) -> {
            if (i == 0) {
                final String[] items = new String[]{"黑色（默认）", "白色"};
                new QMUIDialog.MenuDialogBuilder(MainActivity.this)
                        .addItems(items, (dialog, which) -> {
                            QDSkinManager.changeSkin(which + 1);
                            switch (which) {
                                case 0:
                                    QDSkinManager.changeSkin(2);
                                    break;
                                case 1:
                                    QDSkinManager.changeSkin(3);
                                    break;
                            }
                            //Log.d("xihan","数据:" + which + 1);
                            dialog.dismiss();
                        })
                        .setSkinManager(QMUISkinManager.defaultInstance(MainActivity.this))
                        .create()
                        .show();
//                } else if (i == 1) {
//                    MyApp.openSkinMake = !MyApp.openSkinMake;
//                    renderSkinMakerBtn();
//                } else if (i == 2) {
//                    QMUISkinMaker.getInstance().export(MainActivity.this);
//                }
            }
            if (mGlobalAction != null) {
                mGlobalAction.dismiss();
            }
        };
        mGlobalAction = QMUIPopups.listPopup(this,
                QMUIDisplayHelper.dp2px(this, 250),
                QMUIDisplayHelper.dp2px(this, 300),
                adapter,
                onItemClickListener)
                .animStyle(QMUIPopup.ANIM_GROW_FROM_CENTER)
                .preferredDirection(QMUIPopup.DIRECTION_TOP)
                .shadow(true)
                .edgeProtection(QMUIDisplayHelper.dp2px(this, 10))
                .offsetYIfTop(QMUIDisplayHelper.dp2px(this, 5))
                .skinManager(QMUISkinManager.defaultInstance(this))
                .show(v);
    }




    public static Intent of(@NonNull Context context,
                            @NonNull Class<? extends QMUIFragment> firstFragment) {
        return QMUIFragmentActivity.intentOf(context, MainActivity.class, firstFragment);
    }

    public static Intent of(@NonNull Context context,
                            @NonNull Class<? extends QMUIFragment> firstFragment,
                            @Nullable Bundle fragmentArgs) {
        return QMUIFragmentActivity.intentOf(context, MainActivity.class, firstFragment, fragmentArgs);
    }

    class CustomRootView extends RootView {

        private final FragmentContainerView fragmentContainer;
        private final QMUIRadiusImageView2 globalBtn;
        private final QMUIViewOffsetHelper globalBtnOffsetHelper;
        private final int                  touchSlop;
        private float touchDownX = 0;
        private float touchDownY = 0;
        private float lastTouchX = 0;
        private float lastTouchY = 0;
        private boolean isDragging;
        private boolean isTouchDownInGlobalBtn = false;

        public CustomRootView(Context context, int fragmentContainerId) {
            super(context, fragmentContainerId);

            int btnSize = QMUIDisplayHelper.dp2px(context, 56);

            fragmentContainer = new FragmentContainerView(context);
            fragmentContainer.setId(fragmentContainerId);
            addView(fragmentContainer, new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));


            globalBtn = new QMUIRadiusImageView2(context);
            globalBtn.setImageResource(R.drawable.icon_theme);
            globalBtn.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            globalBtn.setRadiusAndShadow(btnSize / 2,
                    QMUIDisplayHelper.dp2px(getContext(), 16), 0.4f);
            globalBtn.setBorderWidth(1);
            globalBtn.setBorderColor(QMUIResHelper.getAttrColor(context, R.attr.qmui_skin_support_color_separator));
            globalBtn.setBackgroundColor(QMUIResHelper.getAttrColor(context, R.attr.app_skin_common_background));
            globalBtn.setOnClickListener(MainActivity.this::showGlobalActionPopup);
            FrameLayout.LayoutParams globalBtnLp = new FrameLayout.LayoutParams(btnSize, btnSize);
            globalBtnLp.gravity = Gravity.BOTTOM | Gravity.RIGHT;
            globalBtnLp.bottomMargin = QMUIDisplayHelper.dp2px(context, 60);
            globalBtnLp.rightMargin = QMUIDisplayHelper.dp2px(context, 24);
            QMUISkinValueBuilder builder = QMUISkinValueBuilder.acquire();
            builder.background(R.attr.app_skin_common_background);
            builder.border(R.attr.qmui_skin_support_color_separator);
            builder.tintColor(R.attr.app_skin_common_img_tint_color);
            QMUISkinHelper.setSkinValue(globalBtn, builder);
            builder.release();
            addView(globalBtn, globalBtnLp);
            globalBtnOffsetHelper = new QMUIViewOffsetHelper(globalBtn);
            touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        }

        @Override
        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
            super.onLayout(changed, left, top, right, bottom);
            globalBtnOffsetHelper.onViewLayout();
        }

        @Override
        public boolean onInterceptTouchEvent(MotionEvent event) {
            float x = event.getX(), y = event.getY();
            int action = event.getAction();
            if (action == MotionEvent.ACTION_DOWN) {
                isTouchDownInGlobalBtn = isDownInGlobalBtn(x, y);
                touchDownX = lastTouchX = x;
                touchDownY = lastTouchY = y;
            } else if (action == MotionEvent.ACTION_MOVE) {
                if (!isDragging && isTouchDownInGlobalBtn) {
                    int dx = (int) (x - touchDownX);
                    int dy = (int) (y - touchDownY);
                    if (Math.sqrt(dx * dx + dy * dy) > touchSlop) {
                        isDragging = true;
                    }
                }

                if (isDragging) {
                    int dx = (int) (x - lastTouchX);
                    int dy = (int) (y - lastTouchY);
                    int gx = globalBtn.getLeft();
                    int gy = globalBtn.getTop();
                    int gw = globalBtn.getWidth(), w = getWidth();
                    int gh = globalBtn.getHeight(), h = getHeight();
                    if (gx + dx < 0) {
                        dx = -gx;
                    } else if (gx + dx + gw > w) {
                        dx = w - gw - gx;
                    }

                    if (gy + dy < 0) {
                        dy = -gy;
                    } else if (gy + dy + gh > h) {
                        dy = h - gh - gy;
                    }
                    globalBtnOffsetHelper.setLeftAndRightOffset(
                            globalBtnOffsetHelper.getLeftAndRightOffset() + dx);
                    globalBtnOffsetHelper.setTopAndBottomOffset(
                            globalBtnOffsetHelper.getTopAndBottomOffset() + dy);
                }
                lastTouchX = x;
                lastTouchY = y;
            } else if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
                isDragging = false;
                isTouchDownInGlobalBtn = false;
            }
            return isDragging;
        }

        private boolean isDownInGlobalBtn(float x, float y) {
            return globalBtn.getLeft() < x && globalBtn.getRight() > x &&
                    globalBtn.getTop() < y && globalBtn.getBottom() > y;
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float x = event.getX(), y = event.getY();
            int action = event.getAction();
            if (action == MotionEvent.ACTION_DOWN) {
                isTouchDownInGlobalBtn = isDownInGlobalBtn(x, y);
                touchDownX = lastTouchX = x;
                touchDownY = lastTouchY = y;
            } else if (action == MotionEvent.ACTION_MOVE) {
                if (!isDragging && isTouchDownInGlobalBtn) {
                    int dx = (int) (x - touchDownX);
                    int dy = (int) (y - touchDownY);
                    if (Math.sqrt(dx * dx + dy * dy) > touchSlop) {
                        isDragging = true;
                    }
                }

                if (isDragging) {
                    int dx = (int) (x - lastTouchX);
                    int dy = (int) (y - lastTouchY);
                    int gx = globalBtn.getLeft();
                    int gy = globalBtn.getTop();
                    int gw = globalBtn.getWidth(), w = getWidth();
                    int gh = globalBtn.getHeight(), h = getHeight();
                    if (gx + dx < 0) {
                        dx = -gx;
                    } else if (gx + dx + gw > w) {
                        dx = w - gw - gx;
                    }

                    if (gy + dy < 0) {
                        dy = -gy;
                    } else if (gy + dy + gh > h) {
                        dy = h - gh - gy;
                    }
                    globalBtnOffsetHelper.setLeftAndRightOffset(
                            globalBtnOffsetHelper.getLeftAndRightOffset() + dx);
                    globalBtnOffsetHelper.setTopAndBottomOffset(
                            globalBtnOffsetHelper.getTopAndBottomOffset() + dy);
                }
                lastTouchX = x;
                lastTouchY = y;
            } else if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
                isDragging = false;
                isTouchDownInGlobalBtn = false;
            }
            return isDragging || super.onTouchEvent(event);
        }

        @Override
        public FragmentContainerView getFragmentContainerView() {
            return fragmentContainer;
        }
    }
}