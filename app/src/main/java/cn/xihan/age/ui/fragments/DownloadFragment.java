package cn.xihan.age.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qmuiteam.qmui.widget.QMUITopBarLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.xihan.DownloadUtil.data.DownloadData;
import cn.xihan.DownloadUtil.db.Db;
import cn.xihan.DownloadUtil.download.DownloadManger;
import cn.xihan.age.R;
import cn.xihan.age.adapter.DownloadListAdapter;
import cn.xihan.age.base.BaseFragment;
import cn.xihan.age.bean.ZxPlayerBean;

import static cn.xihan.age.utils.Utils.creatDownloadFile;

/**
 * @项目名 : AGE动漫
 * @作者 : MissYang
 * @创建时间 : 2021/3/10 14:16
 * @介绍 :
 */
public class DownloadFragment extends BaseFragment {

    FrameLayout layout;

    @BindView(R.id.list)
    RecyclerView     list;
    @BindView(R.id.topbar)
    QMUITopBarLayout topbar;

    List<ZxPlayerBean>                dataPlayList      = new ArrayList<>();
    private DownloadListAdapter downloadListAdapter;

    String fjmc;
    final List<DownloadData> datas = new ArrayList<>();
    Context ctx;

    @Override
    protected View onCreateView() {
        layout = (FrameLayout) LayoutInflater.from(getActivity()).inflate(R.layout.fragment_download, null);
        ButterKnife.bind(this, layout);
        ctx = requireContext();
        creatDownloadFile();
        dataPlayList.clear();
        topbar.addLeftBackImageButton().setOnClickListener(v -> popBackStack());
        Bundle bundle = getArguments();
        if (bundle != null){
            dataPlayList = (List<ZxPlayerBean>) bundle.getSerializable("PlayList");
            fjmc =bundle.getString("fjmc");
            if (dataPlayList.size() >0){
                for (int i =0;i<dataPlayList.size();i++){
                    //Log.d("xihan","打印:" + dataPlayList.get(i).getTitle() + "\n" + dataPlayList.get(i).getEpName());
                    String path = Environment.getExternalStorageDirectory() + "/Android/data/cn.xihan.age/downloads/" + fjmc+"/";

                    if (Db.getInstance(ctx).getData(dataPlayList.get(i).getPlayVid()) != null){
                        datas.add(Db.getInstance(ctx).getData(dataPlayList.get(i).getPlayVid()));
                    }else{
                        datas.add(new DownloadData(dataPlayList.get(i).getPlayVid(), path, fjmc+ "  " +dataPlayList.get(i).getTitle() + ".mp4"));
                    }
                    downloadListAdapter = new DownloadListAdapter(ctx,datas,false);
                    //开始
                    downloadListAdapter.setOnItemChildClickListener(R.id.start, (viewHolder, data, position) -> DownloadManger.getInstance(ctx).start(data.getUrl()));

                    //暂停
                    downloadListAdapter.setOnItemChildClickListener(R.id.pause, (viewHolder, data, position) -> DownloadManger.getInstance(ctx).pause(data.getUrl()));

                    //继续
                    downloadListAdapter.setOnItemChildClickListener(R.id.resume, (viewHolder, data, position) -> DownloadManger.getInstance(ctx).resume(data.getUrl()));

                    //取消
                    downloadListAdapter.setOnItemChildClickListener(R.id.cancel, (viewHolder, data, position) -> DownloadManger.getInstance(ctx).cancel(data.getUrl()));

                    //重新开始
                    downloadListAdapter.setOnItemChildClickListener(R.id.restart, (viewHolder, data, position) -> DownloadManger.getInstance(ctx).restart(data.getUrl()));
                    final LinearLayoutManager layoutManager = new LinearLayoutManager(ctx);
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    list.setLayoutManager(layoutManager);
                    list.setAdapter(downloadListAdapter);
                }
            }
        }

        return layout;
    }
}
