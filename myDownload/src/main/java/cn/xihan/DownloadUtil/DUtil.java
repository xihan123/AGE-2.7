package cn.xihan.DownloadUtil;

import android.content.Context;

import cn.xihan.DownloadUtil.download.DBuilder;

/**
 * @项目名 : AGE动漫
 * @作者 : MissYang
 * @创建时间 : 2021/3/10 13:58
 * @介绍 :
 */
public class DUtil {

    /**
     * 下载
     *
     * @param context
     * @return
     */
    public static DBuilder init(Context context) {
        return new DBuilder(context);
    }

}

