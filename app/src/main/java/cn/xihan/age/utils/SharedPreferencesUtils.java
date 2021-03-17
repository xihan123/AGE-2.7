package cn.xihan.age.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.tencent.mmkv.MMKV;

/**
 * @项目名 : AGE动漫
 * @作者 : MissYang
 * @创建时间 : 2021/3/10 10:47
 * @介绍 :
 */
public class SharedPreferencesUtils {
    private static final String FILE_NAME = "data";

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param key
     * @param object
     */
    public static void setParam2(String key, Object object) {
        String type = object.getClass().getSimpleName();
        MMKV kv = MMKV.defaultMMKV();
        assert kv != null;
        if ("String".equals(type)) {
            kv.encode(key, (String) object);
        } else if ("Integer".equals(type)) {
            kv.encode(key, (Integer) object);
        } else if ("Boolean".equals(type)) {
            kv.encode(key, (Boolean) object);
        } else if ("Float".equals(type)) {
            kv.encode(key, (Float) object);
        } else if ("Long".equals(type)) {
            kv.encode(key, (Long) object);
        }
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object getParam2(String key, Object defaultObject) {
        String type = defaultObject.getClass().getSimpleName();
        MMKV kv = MMKV.defaultMMKV();
        assert kv != null;
        if ("String".equals(type)) {
            return kv.decodeString(key, (String) defaultObject);
        } else if ("Integer".equals(type)) {
            return kv.decodeInt(key, (Integer) defaultObject);
        } else if ("Boolean".equals(type)) {
            return kv.decodeBool(key, (Boolean) defaultObject);
        } else if ("Float".equals(type)) {
            return kv.decodeFloat(key, (Float) defaultObject);
        } else if ("Long".equals(type)) {
            return kv.decodeLong(key, (Long) defaultObject);
        }
        return null;
    }


    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param context
     * @param key
     * @param object
     */
    public static void setParam(Context context, String key, Object object) {

        String type = object.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if ("String".equals(type)) {
            editor.putString(key, (String) object);
        } else if ("Integer".equals(type)) {
            editor.putInt(key, (Integer) object);
        } else if ("Boolean".equals(type)) {
            editor.putBoolean(key, (Boolean) object);
        } else if ("Float".equals(type)) {
            editor.putFloat(key, (Float) object);
        } else if ("Long".equals(type)) {
            editor.putLong(key, (Long) object);
        }

        editor.commit();
    }


    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param context
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object getParam(Context context, String key, Object defaultObject) {
        String type = defaultObject.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);

        if ("String".equals(type)) {
            return sp.getString(key, (String) defaultObject);
        } else if ("Integer".equals(type)) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if ("Boolean".equals(type)) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if ("Float".equals(type)) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if ("Long".equals(type)) {
            return sp.getLong(key, (Long) defaultObject);
        }
        return null;
    }


}
