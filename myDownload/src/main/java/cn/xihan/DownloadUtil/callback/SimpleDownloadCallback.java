package cn.xihan.DownloadUtil.callback;

import java.io.File;

/**
 * @项目名 : AGE动漫
 * @作者 : MissYang
 * @创建时间 : 2021/3/10 14:00
 * @介绍 :
 */
public abstract class SimpleDownloadCallback implements DownloadCallback {

    @Override
    public void onStart(long currentSize, long totalSize, float progress) {

    }

    @Override
    public void onProgress(long currentSize, long totalSize, float progress) {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onFinish(File file) {

    }

    @Override
    public void onWait() {

    }

    @Override
    public void onError(String error) {

    }
}

