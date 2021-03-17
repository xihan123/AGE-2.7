package cn.xihan.age.adapter;

import android.content.Context;
import android.widget.ProgressBar;

import java.io.File;
import java.util.List;

import cn.xihan.DownloadUtil.Utils.Utils;
import cn.xihan.DownloadUtil.callback.DownloadCallback;
import cn.xihan.DownloadUtil.data.DownloadData;
import cn.xihan.DownloadUtil.download.DownloadManger;
import cn.xihan.age.R;
import cn.xihan.age.adapter.base.CommonBaseAdapter;

/**
 * @项目名 : AGE动漫
 * @作者 : MissYang
 * @创建时间 : 2021/3/10 14:46
 * @介绍 :
 */
public class DownloadListAdapter extends CommonBaseAdapter<DownloadData> {

    public DownloadListAdapter(Context context, List<DownloadData> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
    }

    @Override
    protected void convert(ViewHolder holder, DownloadData data,int position) {
        holder.setText(R.id.name, data.getName().replace(".mp4",""));
        holder.setText(R.id.download_size, Utils.formatSize(data.getCurrentLength()) + "/" + Utils.formatSize(data.getTotalLength()));
        holder.setText(R.id.percentage, data.getPercentage() + "%");
        ((ProgressBar) holder.getView(R.id.progress_bar)).setProgress((int) data.getPercentage());
        setListener(holder, data);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_download_layout;
    }

    private void setListener(final ViewHolder viewHolder, final DownloadData downloadData) {
        DownloadManger.getInstance(mContext).setOnDownloadCallback(downloadData, new DownloadCallback() {
            @Override
            public void onStart(long currentSize, long totalSize, float progress) {
                viewHolder.setText(R.id.name, downloadData.getName() + ":准备中");
            }

            @Override
            public void onProgress(long currentSize, long totalSize, float progress) {
                viewHolder.setText(R.id.name, downloadData.getName() + ":下载中");
                viewHolder.setText(R.id.download_size, Utils.formatSize(currentSize) + "/" + Utils.formatSize(totalSize));
                viewHolder.setText(R.id.percentage, progress + "%");
                ((ProgressBar) viewHolder.getView(R.id.progress_bar)).setProgress((int) progress);
            }

            @Override
            public void onPause() {
                viewHolder.setText(R.id.name, downloadData.getName() + ":已暂停");
            }

            @Override
            public void onCancel() {
                viewHolder.setText(R.id.name, downloadData.getName() + ":已取消");
            }

            @Override
            public void onFinish(File file) {
                viewHolder.setText(R.id.name, downloadData.getName() + ":已完成");
            }

            @Override
            public void onWait() {
                viewHolder.setText(R.id.name, downloadData.getName() + ":等待中");
            }

            @Override
            public void onError(String error) {
                viewHolder.setText(R.id.name, downloadData.getName() + ":出错");
            }
        });
    }
}
