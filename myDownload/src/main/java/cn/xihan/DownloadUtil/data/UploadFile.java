package cn.xihan.DownloadUtil.data;

import java.io.File;

/**
 * @项目名 : AGE动漫
 * @作者 : MissYang
 * @创建时间 : 2021/3/10 14:00
 * @介绍 :
 */
public class UploadFile {
    private String name;
    private String filename;
    private File file;

    /**
     * @param name 表单中name属性的值
     * @param filename 文件名
     * @param file 要上传的文件
     */
    public UploadFile(String name, String filename, File file) {
        this.name = name;
        this.filename = filename;
        this.file = file;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}

