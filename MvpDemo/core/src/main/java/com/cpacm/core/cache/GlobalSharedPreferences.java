package com.cpacm.core.cache;

import android.content.Context;
import android.content.SharedPreferences;

import com.cpacm.core.CoreApplication;

/**
 * @author: cpacm
 * @date: 2017/2/17
 * @desciption:
 */

public class GlobalSharedPreferences {

    private static GlobalSharedPreferences instance;

    /**
     * singleton
     *
     * @return
     */
    public static GlobalSharedPreferences getInstance() {
        if (instance == null) {
            synchronized (GlobalSharedPreferences.class) {
                if (instance == null) {
                    instance = new GlobalSharedPreferences();
                }
            }
        }
        return instance;
    }

    private static final String GLOBAL_PREFERENCE = "setting";//设置文件的文件名

    public static String SPLASH_TIMESTAMP = "splash";

    private SharedPreferences sharedPreferences;
    private Context context;

    private GlobalSharedPreferences() {
        context = CoreApplication.getInstance().getApplicationContext();
    }

    /**
     * 获取setting的sp文件
     *
     * @return
     */
    public SharedPreferences getSharedPreferences() {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(GLOBAL_PREFERENCE, Context.MODE_PRIVATE);
        }
        return sharedPreferences;
    }

    public void setSetting(String key, String value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getSetting(String key) {
        return getSharedPreferences().getString(key, "");
    }

    public String getSetting(String key, String defaultValue) {
        return getSharedPreferences().getString(key, defaultValue);
    }

    public void setSetting(String key, boolean flag) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(key, flag);
        editor.apply();
    }

    public Boolean getSetting(String key, boolean defaultValue) {
        return getSharedPreferences().getBoolean(key, defaultValue);
    }

    public void setSetting(String key, int flag) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putInt(key, flag);
        editor.apply();
    }

    public Integer getSetting(String key, int defaultValue) {
        return getSharedPreferences().getInt(key, defaultValue);
    }

    /**
     * 清空配置
     */
    public void clear() {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.clear();
        editor.apply();
    }

}
