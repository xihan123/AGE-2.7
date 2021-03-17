package cn.xihan.DownloadUtil.callback;

/**
 * @项目名 : AGE动漫
 * @作者 : MissYang
 * @创建时间 : 2021/3/10 14:00
 * @介绍 :
 */
public abstract class SimpleUploadCallback implements UploadCallback {
    @Override
    public void onStart() {

    }

    @Override
    public void onProgress(long currentSize, long totalSize, float progress) {

    }

    @Override
    public void onFinish(String response) {

    }

    @Override
    public void onError(String error) {

    }
}

