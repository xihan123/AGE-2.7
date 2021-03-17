package cn.xihan.age.ui.fragments.home;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.qmuiteam.qmui.arch.QMUIFragment;
import com.qmuiteam.qmui.arch.QMUIFragmentPagerAdapter;
import com.qmuiteam.qmui.arch.effect.MapEffect;
import com.qmuiteam.qmui.arch.effect.QMUIFragmentEffectHandler;
import com.qmuiteam.qmui.arch.effect.QMUIFragmentMapEffectHandler;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.tab.QMUITab;
import com.qmuiteam.qmui.widget.tab.QMUITabBuilder;
import com.qmuiteam.qmui.widget.tab.QMUITabSegment;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import cn.xihan.age.R;
import cn.xihan.age.base.BaseFragment;
import cn.xihan.age.model.CustomEffect;
import cn.xihan.age.ui.fragments.controller.HomeController;
import cn.xihan.age.ui.fragments.controller.MlController;
import cn.xihan.age.ui.fragments.controller.MyController;
import cn.xihan.age.ui.fragments.controller.PhbController;
import cn.xihan.age.ui.fragments.controller.SyController;
import cn.xihan.age.ui.fragments.controller.TjController;


/**
 * @项目名 : AGE动漫
 * @作者 : MissYang
 * @创建时间 : 2021/2/21 16:22
 * @介绍 :
 */
public class HomeFragment extends BaseFragment {
    private final static String TAG = HomeFragment.class.getSimpleName();


    ViewPager      mViewPager;

    QMUITabSegment mTabSegment;
    private       HashMap<Pager, HomeController> mPages;
    private final PagerAdapter                   mPagerAdapter = new PagerAdapter() {

        private int mChildCount = 0;

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public int getCount() {
            return mPages.size();
        }

        @Override
        public Object instantiateItem(final ViewGroup container, int position) {
            HomeController page = mPages.get(Pager.getPagerFromPositon(position));
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            container.addView(page, params);
            return Objects.requireNonNull(page);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getItemPosition(Object object) {
            if (mChildCount == 0) {
                return POSITION_NONE;
            }
            return super.getItemPosition(object);
        }

        @Override
        public void notifyDataSetChanged() {
            mChildCount = getCount();
            super.notifyDataSetChanged();
        }

    };

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        registerEffect(this, new QMUIFragmentMapEffectHandler() {
            @Override
            public boolean shouldHandleEffect(@NonNull MapEffect effect) {
                return effect.getValue("interested_type_key") != null;
            }

            @Override
            public void handleEffect(@NonNull MapEffect effect) {
                Object value = effect.getValue("interested_value_key");
                if(value instanceof String){
                    Toast.makeText(context, ((String)value), Toast.LENGTH_SHORT).show();
                }
            }
        });

        registerEffect(this, new QMUIFragmentEffectHandler<CustomEffect>() {
            @Override
            public boolean shouldHandleEffect(@NonNull CustomEffect effect) {
                return true;
            }

            @Override
            public void handleEffect(@NonNull CustomEffect effect) {
                Toast.makeText(context, effect.getContent(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void handleEffect(@NonNull List<CustomEffect> effects) {
                // we can only handle the last effect.
                handleEffect(effects.get(effects.size() - 1));
            }
        });
    }

    FrameLayout layout;
    @Override
    protected View onCreateView() {
        layout = (FrameLayout) LayoutInflater.from(getActivity()).inflate(R.layout.fragment_home, null);
        initTabs();
        initPagers();
        return layout;
    }


    private void initTabs() {
        mTabSegment=layout.findViewById(R.id.tabs);
        QMUITabBuilder builder = mTabSegment.tabBuilder();
        builder.setTypeface(null, Typeface.DEFAULT_BOLD);
        builder.setSelectedIconScale(1.2f)
                .setTextSize(QMUIDisplayHelper.sp2px(requireContext(), 13), QMUIDisplayHelper.sp2px(requireContext(), 15))
                .setDynamicChangeIconColor(false);
        QMUITab sy = builder
                .setNormalDrawable(ContextCompat.getDrawable(getContext(), R.drawable.index))
                .setSelectedDrawable(ContextCompat.getDrawable(getContext(), R.drawable.index))
                .setText("首页")
                .build(getContext());
        QMUITab ml = builder
                .setNormalDrawable(ContextCompat.getDrawable(getContext(), R.drawable.catalog))
                .setSelectedDrawable(ContextCompat.getDrawable(getContext(), R.drawable.catalog))
                .setText("目录")
                .build(getContext());
        QMUITab tj = builder
                .setNormalDrawable(ContextCompat.getDrawable(getContext(), R.drawable.recommend))
                .setSelectedDrawable(ContextCompat.getDrawable(getContext(), R.drawable.recommend))
                .setText("推荐")
                .build(getContext());
        QMUITab phb = builder
                .setNormalDrawable(ContextCompat.getDrawable(getContext(), R.drawable.rank))
                .setSelectedDrawable(ContextCompat.getDrawable(getContext(), R.drawable.rank))
                .setText("排行榜")
                .build(getContext());
        QMUITab my = builder
                .setNormalDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_user))
                .setSelectedDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_user))
                .setText("我的")
                .build(getContext());

        mTabSegment.addTab(sy)
                .addTab(ml)
                .addTab(tj)
                .addTab(phb)
                .addTab(my);
    }

    private void initPagers() {
        mViewPager=layout.findViewById(R.id.pager);
        HomeController.HomeControlListener listener = HomeFragment.this::startFragment;

        mPages = new HashMap<>();

        HomeController SyController = new SyController(getActivity());
        SyController.setHomeControlListener(listener);
        mPages.put(Pager.sy, SyController);

        HomeController MlController = new MlController(getActivity());
        MlController.setHomeControlListener(listener);
        mPages.put(Pager.ml, MlController);

        HomeController TjController = new TjController(getActivity());
        TjController.setHomeControlListener(listener);
        mPages.put(Pager.tj, TjController);

        HomeController PhbLabController = new PhbController(getActivity());
        PhbLabController.setHomeControlListener(listener);
        mPages.put(Pager.phb, PhbLabController);

        HomeController MyController = new MyController(getActivity());
        MyController.setHomeControlListener(listener);
        mPages.put(Pager.my, MyController);

        QMUIFragmentPagerAdapter pagerAdapter = new QMUIFragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public QMUIFragment createFragment(int position) {
                switch (position) {
                    case 1:
                        return new MlFragment();
                    case 2:
                        return new TjFragment();
                    case 3:
                        return new PhbFragment();
                    case 4:
                        return new MyFragment();
                    case 0:
                    default:
                        return new SyFragment();
                }
            }
            @Override
            public int getCount() {
                return 5;
            }
        };
        mViewPager.setAdapter(pagerAdapter);
        mTabSegment.setupWithViewPager(mViewPager, false);
    }

    enum Pager {
        sy, ml, tj,phb,my;
        public static Pager getPagerFromPositon(int position) {
            switch (position) {
                case 1:
                    return ml;
                case 2:
                    return tj;
                case 3:
                    return phb;
                case 4:
                     return my;
                case 0:
                default:
                    return sy;
            }
        }
    }

    @Override
    protected boolean canDragBack() {
        return false;
    }

    @Override
    public Object onLastFragmentFinish() {
        return null;
    }
}
