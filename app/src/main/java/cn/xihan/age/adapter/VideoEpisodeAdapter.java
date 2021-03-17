package cn.xihan.age.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cn.xihan.age.R;
import cn.xihan.age.bean.AGEpsodeEntity;

/**
 * @项目名 : AGE动漫
 * @作者 : MissYang
 * @创建时间 : 2021/3/8 10:38
 * @介绍 :
 */
public class VideoEpisodeAdapter extends RecyclerView.Adapter<VideoEpisodeAdapter.ViewHolder> {
    private final Context              mC;
    private final List<AGEpsodeEntity> entities;
    private       OnItemClickListener  mOnItemClickListener;

    public VideoEpisodeAdapter(Context context, List<AGEpsodeEntity> entities) {
        this.mC = context;
        this.entities = entities;
    }

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video_episode, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(position + 1 + "");
        if (entities.get(position).isPlay()) {
            holder.textView.setTextColor(mC.getResources().getColor(R.color.ThemeColor));
            holder.textView.setBackground(mC.getResources().getDrawable(R.drawable.bg_video_episodes_check));
        } else {
            holder.textView.setTextColor(mC.getResources().getColor(R.color.colorWhite));
            holder.textView.setBackground(mC.getResources().getDrawable(R.drawable.bg_video_episodes_uncheck));
        }

        holder.textView.setOnClickListener(v -> {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClicked(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return entities.size();
    }

    public interface OnItemClickListener {
        void onItemClicked(View view, int position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.item_episodeNum);
        }
    }
}

