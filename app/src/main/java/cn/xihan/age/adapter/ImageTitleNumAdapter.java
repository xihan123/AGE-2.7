package cn.xihan.age.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

import cn.xihan.age.R;
import cn.xihan.age.bean.BannerDataBean2;

/**
 * @项目名 : AGE动漫
 * @作者 : MissYang
 * @创建时间 : 2021/3/2 1:55
 * @介绍 :
 */
public class ImageTitleNumAdapter extends BannerAdapter<BannerDataBean2, ImageTitleNumAdapter.BannerViewHolder> {

    private final Context mContext;

    public ImageTitleNumAdapter(Context context , List<BannerDataBean2> mDatas) {
        //设置数据，也可以调用banner提供的方法
        super(mDatas);
        mContext=context;
    }

    //创建ViewHolder，可以用viewType这个字段来区分不同的ViewHolder
    @Override
    public BannerViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        //注意布局文件，item布局文件要设置为match_parent，这个是viewpager2强制要求的
        //或者调用BannerUtils.getView(parent,R.layout.banner_image_title_num);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.banner_image_title_num, parent, false);
        return new BannerViewHolder(view);
    }

    //绑定数据
    @Override
    public void onBindView(BannerViewHolder holder, BannerDataBean2 data, int position, int size) {
        //holder.imageView.setImageResource(data.imageUrl);
        Glide.with(mContext).load(data.imageUrl).into(holder.imageView);

        holder.title.setText(data.title);
        //可以在布局文件中自己实现指示器，亦可以使用banner提供的方法自定义指示器，目前样式较少，后面补充
        holder.numIndicator.setText((position + 1) + "/" + size);
    }


    static class BannerViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageView;
        final TextView  title;
        final TextView  numIndicator;

        public BannerViewHolder(@NonNull View view) {
            super(view);
            imageView = view.findViewById(R.id.image);
            title = view.findViewById(R.id.bannerTitle);
            numIndicator = view.findViewById(R.id.numIndicator);
        }
    }

}


