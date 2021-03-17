package cn.xihan.age.utils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import cn.xihan.age.Config;
import cn.xihan.age.bean.AniPreSimBean;

/**
 * @项目名 : AGE动漫
 * @作者 : MissYang
 * @创建时间 : 2021/3/5 5:42
 * @介绍 :
 */
public class DatabaseUtil {
    private static SQLiteDatabase db;

    private static final String DB_PATH = "/storage/emulated/0/Android/data/cn.xihan.age/Database/age.db";


    /**
     * 创建tables
     */
    public static void CREATE_TABLES() {
        db = SQLiteDatabase.openOrCreateDatabase(DB_PATH, null);
        db.execSQL("create table if not exists f_favorite(id integer primary key autoincrement, f_title text, f_url text, f_desc text, f_img text)");
        db.execSQL("create table if not exists f_anime(id integer primary key autoincrement, f_id text, f_title text)");
        db.execSQL("create table if not exists f_index(id integer primary key autoincrement, f_pid text, f_url text)");
        db.execSQL("create table if not exists f_api(id integer primary key autoincrement, f_id text, f_title text, f_url text)");
    }


    /**
     * 关闭数据库连接
     */
    public static void closeDB() {
        db.close();

    }

    /**
     * 新增点击过的番剧名称
     *
     * @param title
     */
    public static void addAnime(String title) {
        if (!checkAnime(title))
            db.execSQL("insert into f_anime values(?,?,?)",
                    new Object[]{null, UUID.randomUUID().toString(), title});
    }

    /**
     * 检查番剧名称是否存在
     *
     * @param title
     * @return
     */
    public static boolean checkAnime(String title) {
        String Query = "select * from f_anime where f_title =?";
        Cursor cursor = db.rawQuery(Query, new String[]{title});
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }

    /**
     * 获取番剧fid
     *
     * @param title
     * @return
     */
    public static String getAnimeID(String title) {
        String Query = "select * from f_anime where f_title =?";
        Cursor cursor = db.rawQuery(Query, new String[]{title});
        cursor.moveToNext();
        return cursor.getString(1);
    }

    /**
     * 新增点击过的剧集名称
     *
     * @param fid 父id
     * @param url 播放地址
     */
    public static void addIndex(String fid, String url) {
        if (!checkIndex(fid, url.substring(Config.AniInfoApi.length())))
            db.execSQL("insert into f_index values(?,?,?)",
                    new Object[]{null, fid, url.substring(Config.AniInfoApi.length())});
    }

    /**
     * 检查剧集名称是否存在
     *
     * @param fid 父id
     * @param url 播放地址
     * @return
     */
    private static boolean checkIndex(String fid, String url) {
        String Query = "select * from f_index where f_pid =? and f_url =?";
        Cursor cursor = db.rawQuery(Query, new String[]{fid, url});
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }

    /**
     * 检查当前fid所有剧集
     *
     * @param fid 番剧ID
     * @return
     */
    public static String queryAllIndex(String fid) {
        StringBuffer buffer = new StringBuffer();
        String Query = "select * from f_index where f_pid =?";
        Cursor c = db.rawQuery(Query, new String[]{fid});
        while (c.moveToNext()) {
            buffer.append(c.getString(2));
        }
        c.close();
        return buffer.toString();
    }

    /**
     * 查询用户收藏的番剧
     */
    public static List<AniPreSimBean> queryAllFavorite() {
        List<AniPreSimBean> list = new ArrayList<>();
        Cursor c = db.rawQuery("select * from f_favorite order by id desc", null);
        while (c.moveToNext()) {
            AniPreSimBean bean = new AniPreSimBean();
            bean.setTitle(c.getString(1));
            bean.setPicSmall(c.getString(2));
            bean.setAid(c.getString(3));
            bean.setNewTitle(c.getString(4));
            list.add(bean);
        }
        c.close();
        return list;
    }

    /**
     * 收藏or删除收藏
     *
     * @param bean
     * @return true 收藏成功 false 移除收藏
     */
    public static boolean favorite(AniPreSimBean bean) {
        if (checkFavorite(bean.getTitle())) {
            deleteFavorite(bean.getTitle());
            return false;
        } else {
            addFavorite(bean);
            return true;
        }
    }

    /**
     * 添加到收藏
     *
     * @param bean
     */
    public static void addFavorite(AniPreSimBean bean) {
        db.execSQL("insert into f_favorite values(?,?,?,?,?)",
                new Object[]{null,
                        bean.getTitle(),
                        bean.getPicSmall(),
                        bean.getAid(),
                        null
                });
    }

    /**
     * 删除收藏
     *
     * @param title
     */
    public static void deleteFavorite(String title) {
        db.execSQL("delete from f_favorite where f_title=?", new String[]{title});
    }


    /**
     * 检查番剧是否收藏
     *
     * @param title
     * @return
     */
    public static boolean checkFavorite(String title) {
        String Query = "select * from f_favorite where f_title =?";
        Cursor cursor = db.rawQuery(Query, new String[]{title});
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }

    /**
     *  查询用户收藏总数
     * @return
     */
    public static int queryFavoriteCount() {
        int count;
        String QueryCount = "select * from f_favorite";
        Cursor cursor = db.rawQuery(QueryCount, null);
        count = cursor.getCount();
        cursor.close();
        return count;
    }

}
