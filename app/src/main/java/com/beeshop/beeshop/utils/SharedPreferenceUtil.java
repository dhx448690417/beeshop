package com.beeshop.beeshop.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


import com.beeshop.beeshop.application.BeeShopApplication;

import java.util.Observable;


/**
 * SpUtil
 *
 * @author 333
 */
//存放用户相关信息：用户名，token，手机号等
public class SharedPreferenceUtil extends Observable {
    // 登陆状态
    public static final String KEY_TOKEN = "token";
    public static final String KEY_ID = "id";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_ICON = "icon";
    public static final String KEY_IS_SUPER_USER = "is_super_user";
    public static final String KEY_USER_ENTITY = "user_entity";
    public static final String KEY_IS_DO_NOT_SHOW_UPDATE_VIEW = "is_do_not_show_update_view"; //是否不显示更新view
    public static final String KEY_IS_NOT_SHOW_YI_WEI_WU_DU_REA_POINT = "key_is_first_modules_show"; //是否是第一次展示一维五度
    public static final String KEY_IS_NOT_SHOW_APPLICATION_REA_POINT = "key_is_not_show_application_rea_point"; //底部tab是否是显示红点

    public static final String KEY_GUIDE_CITY_POWER = "key_guide_city_power"; // 城市能力 引导
    public static final String KEY_GUIDE_CITY_DETAIL = "key_guide_city_detail"; // 城市详情 引导

    public static SharedPreferences getSpInstance(Context context) {
        return context.getSharedPreferences("user", Context.MODE_PRIVATE);
    }

    public static SharedPreferences getBeeShopConfig(Context context) {
        return context.getSharedPreferences("beeshop", Context.MODE_PRIVATE);
    }

    public static SharedPreferences getBeeShopCache(Context context) {
        return context.getSharedPreferences("beeshop_cache", Context.MODE_PRIVATE);
    }


    public static String getSessionId(Context context) {
        return getSpInstance(context).getString("session_id", "");
    }


    /**
     * 存储用户数据
     *
     * @param key
     * @param value
     * @param <T>
     */
    public static <T> void putUserPreferences(String key, T value) {
        Editor editor = getSpInstance(BeeShopApplication.getInstance().getApplicationContext()).edit();
        putValue(key, value, editor);
    }

    /**
     * 获取用户数据
     *
     * @param key
     * @param value
     * @param <T>
     * @return
     */
    public static <T> T getUserPreferences(String key, T value) {
        SharedPreferences spInstance = getSpInstance(BeeShopApplication.getInstance().getApplicationContext());
        return getValue(key, value, spInstance);
    }

    /**
     * 清除用户数据
     */
    public static <T> void clearUserPreferences() {
        Editor editor = getSpInstance(BeeShopApplication.getInstance().getApplicationContext()).edit();
        editor.clear().commit();
    }

    /**
     * 存储北极星相关数据
     */
    public static <T> void putBeeShopPreferences(String key, T value) {
        Editor editor = getBeeShopConfig(BeeShopApplication.getInstance().getApplicationContext()).edit();
        putValue(key, value, editor);
    }

    /**
     * 读取北极星相关数据
     */
    public static <T> T getBeeShopPreferences(String key, T value) {
        SharedPreferences sharedPreferences = getBeeShopConfig(BeeShopApplication.getInstance().getApplicationContext());
        return getValue(key, value, sharedPreferences);
    }


    /**
     * SharedPreferences.Editor 存储
     *
     * @param key
     * @param value
     * @param editor
     * @param <T>
     */
    private static <T> void putValue(String key, T value, Editor editor) {
        if (value instanceof String) {
            editor.putString(key, value.toString());
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, ((Boolean) value).booleanValue());
        } else if (value instanceof Integer) {
            editor.putInt(key, ((Integer) value).intValue());
        } else if (value instanceof Float) {
            editor.putFloat(key, ((Float) value).floatValue());
        } else if (value instanceof Long) {
            editor.putLong(key, ((Long) value).longValue());
        }
        editor.commit();
    }


    /**
     * SharedPreferences 获取值
     */
    private static <T> T getValue(String key, T value, SharedPreferences sharedPreferences) {
        Object o = null;
        if (value instanceof String) {
            o = sharedPreferences.getString(key, value.toString());
        } else if (value instanceof Boolean) {
            o = sharedPreferences.getBoolean(key, ((Boolean) value).booleanValue());
        } else if (value instanceof Integer) {
            o = sharedPreferences.getInt(key, ((Integer) value).intValue());
        } else if (value instanceof Float) {
            o = sharedPreferences.getFloat(key, ((Float) value).floatValue());
        } else if (value instanceof Long) {
            o = sharedPreferences.getLong(key, ((Long) value).longValue());
        }
        T t = (T) o;
        return t;
    }

}
