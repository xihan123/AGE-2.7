package cn.xihan.age.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * @项目名 : AGE动漫
 * @作者 : MissYang
 * @创建时间 : 2021/3/10 11:43
 * @介绍 :
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder> {
    private final List<T>             mData = new ArrayList<>();
    private final Context             mContext;
    private final LayoutInflater      mInflater;
    private       OnItemClickListener mClickListener;
    private OnItemLongClickListener mLongClickListener;

    public BaseRecyclerAdapter(Context ctx, @Nullable List<T> list) {
        if(list != null){
            mData.addAll(list);
        }
        mContext = ctx;
        mInflater = LayoutInflater.from(ctx);
    }

    public void setData(@Nullable List<T> list) {
        mData.clear();
        if(list != null){
            mData.addAll(list);
        }
        notifyDataSetChanged();
    }

    public void remove(int pos){
        mData.remove(pos);
        notifyItemRemoved(pos);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final RecyclerViewHolder holder = new RecyclerViewHolder(
                mInflater.inflate(getItemLayoutId(), parent, false));
        if (mClickListener != null) {
            holder.itemView.setOnClickListener(v -> mClickListener.onItemClick(holder.itemView, holder.getLayoutPosition()));
        }
        if (mLongClickListener != null) {
            holder.itemView.setOnLongClickListener(v -> {
                mLongClickListener.onItemLongClick(holder.itemView, holder.getLayoutPosition());
                return true;
            });
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        bindData(holder, mData.get(position));
    }

    public T getItem(int pos){
        return mData.get(pos);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void add(int pos, T item) {
        mData.add(pos, item);
        notifyItemInserted(pos);
    }

    public void prepend(@NonNull List<T> items){
        mData.addAll(0, items);
        notifyDataSetChanged();
    }

    public void append(@NonNull List<T> items){
        mData.addAll(items);
        notifyDataSetChanged();
    }

    public void delete(int pos) {
        mData.remove(pos);
        notifyItemRemoved(pos);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        mLongClickListener = listener;
    }

    @SuppressWarnings("SameReturnValue")
    abstract public int getItemLayoutId();

    abstract public void bindData(RecyclerViewHolder holder, T item);



    public interface OnItemClickListener {
        void onItemClick(View itemView, int pos);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View itemView, int pos);
    }
}


