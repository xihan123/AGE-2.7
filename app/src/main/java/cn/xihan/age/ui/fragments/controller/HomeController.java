package cn.xihan.age.ui.fragments.controller;

import android.content.Context;
import android.os.Parcelable;
import android.util.SparseArray;
import android.view.LayoutInflater;

import androidx.recyclerview.widget.RecyclerView;

import com.qmuiteam.qmui.util.QMUIViewHelper;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.QMUIWindowInsetLayout;

import java.util.List;

import butterknife.ButterKnife;
import cn.xihan.age.R;
import cn.xihan.age.base.BaseFragment;
import cn.xihan.age.base.BaseRecyclerAdapter;
import cn.xihan.age.base.RecyclerViewHolder;
import cn.xihan.age.model.QDItemDescription;

/**
 * @项目名 : AGE动漫
 * @作者 : MissYang
 * @创建时间 : 2021/2/21 16:21
 * @介绍 :
 */
public abstract class HomeController extends QMUIWindowInsetLayout {


    QMUITopBarLayout mTopBar;

    RecyclerView     mRecyclerView;

    private HomeControlListener mHomeControlListener;
    private       ItemAdapter mItemAdapter;
    private final int         mDiffRecyclerViewSaveStateId = QMUIViewHelper.generateViewId();

    public HomeController(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.home_layout, this);
        ButterKnife.bind(this);
        initTopBar();

    }

    protected void startFragment(BaseFragment fragment) {
        if (mHomeControlListener != null) {
            mHomeControlListener.startFragment(fragment);
        }
    }

    public void setHomeControlListener(HomeControlListener homeControlListener) {
        mHomeControlListener = homeControlListener;
    }

    protected abstract String getTitle();

    private void initTopBar() {
        mTopBar=findViewById(R.id.topbar);
        mTopBar.setTitle("AGE动漫");
//        mTopBar.addRightImageButton(R.mipmap.icon_topbar_about, R.id.topbar_right_about_button).setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                QDAboutFragment fragment = new QDAboutFragment();
//                startFragment(fragment);
//            }
//        });
    }



    protected abstract ItemAdapter getItemAdapter();

    public interface HomeControlListener {
        void startFragment(BaseFragment fragment);
    }

    @Override
    protected void dispatchSaveInstanceState(SparseArray<Parcelable> container) {
        int id = mRecyclerView.getId();
        mRecyclerView.setId(mDiffRecyclerViewSaveStateId);
        super.dispatchSaveInstanceState(container);
        mRecyclerView.setId(id);
    }

    @Override
    protected void dispatchRestoreInstanceState(SparseArray<Parcelable> container) {
        int id = mRecyclerView.getId();
        mRecyclerView.setId(mDiffRecyclerViewSaveStateId);
        super.dispatchRestoreInstanceState(container);
        mRecyclerView.setId(id);
    }

    static class ItemAdapter extends BaseRecyclerAdapter<QDItemDescription> {

        public ItemAdapter(Context ctx, List<QDItemDescription> data) {
            super(ctx, data);
        }

        @Override
        public int getItemLayoutId() {
            return R.layout.home_item_layout;
        }

        @Override
        public void bindData(RecyclerViewHolder holder, QDItemDescription item) {
            holder.getTextView(R.id.item_name).setText(item.getName());
            if (item.getIconRes() != 0) {
                holder.getImageView(R.id.item_icon).setImageResource(item.getIconRes());
            }
        }
    }
}

