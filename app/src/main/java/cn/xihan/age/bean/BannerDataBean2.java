package cn.xihan.age.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @项目名 : AGE动漫
 * @作者 : MissYang
 * @创建时间 : 2021/3/2 1:41
 * @介绍 :
 */
public class BannerDataBean2 {
    public Integer imageRes;
    public       String imageUrl;
    public final String title;
    public final int    viewType;

    public BannerDataBean2(Integer imageRes, String title, int viewType) {
        this.imageRes = imageRes;
        this.title = title;
        this.viewType = viewType;
    }

    public BannerDataBean2(String imageUrl, String title, int viewType) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.viewType = viewType;
    }


    public static List<BannerDataBean2> getTestData3() {
        List<BannerDataBean2> list = new ArrayList<>();
        list.add(new BannerDataBean2("https://img.zcool.cn/community/013de756fb63036ac7257948747896.jpg", "null", 1));
        list.add(new BannerDataBean2("https://img.zcool.cn/community/01639a56fb62ff6ac725794891960d.jpg", "null", 1));
        list.add(new BannerDataBean2("https://img.zcool.cn/community/01270156fb62fd6ac72579485aa893.jpg", "null", 1));
        list.add(new BannerDataBean2("https://img.zcool.cn/community/01233056fb62fe32f875a9447400e1.jpg", "null", 1));
        list.add(new BannerDataBean2("https://img.zcool.cn/community/016a2256fb63006ac7257948f83349.jpg", "null", 1));
        return list;
    }

}

