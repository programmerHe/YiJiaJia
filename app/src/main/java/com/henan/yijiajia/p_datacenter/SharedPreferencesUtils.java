package com.henan.yijiajia.p_datacenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.henan.yijiajia.main.YijiajiaApplication;

/**
 * Created by 叶满林 on 2018/8/16.
 */

public class SharedPreferencesUtils {
    private static SharedPreferences sp =
            YijiajiaApplication.getContext().getSharedPreferences("config", Context.MODE_PRIVATE);

    public static boolean getBoolean(String key, boolean defvalue) {
        return sp.getBoolean(key, defvalue);
    }

    public static void putBoolean(String key, boolean value) {
        sp.edit().putBoolean(key, value).commit();
    }

    public static void putString(String key, String value) {
        sp.edit().putString(key, value).commit();
    }

    public static String getString(String key, String defvalue) {
        return sp.getString(key, defvalue);
    }

    public static void putInt(String key, int value) {
        sp.edit().putInt(key, value).commit();
    }

    public static int getInt(String key, int defvalue) {
        return sp.getInt(key, defvalue);
    }

    public static void remove(String key) {
        sp.edit().remove(key).commit();
    }
}
