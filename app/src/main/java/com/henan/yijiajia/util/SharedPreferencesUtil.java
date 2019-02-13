package com.henan.yijiajia.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

/**
 * 存储常用的数值(可以做单例，不过我觉得没有必要)
 */
public class SharedPreferencesUtil {
    public static final String TAG = "config";

    private static SharedPreferences mPreferences;
    private static SharedPreferences.Editor mEditor;
    private static SharedPreferencesUtil mSharedPreferencesUtil;

    public SharedPreferencesUtil(Context context) {
        mPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();
    }

    /**
     *获取单例
     */
    public static SharedPreferencesUtil getInstance(Context context) {
        if (mSharedPreferencesUtil == null) {
            mSharedPreferencesUtil = new SharedPreferencesUtil(context);
        }
        return mSharedPreferencesUtil;
    }

    /**
     *存放string类型的数值
     */
    public void putString(String key, String value) {
        mEditor.putString(key,value);
        mEditor.commit();
    }

    /**
     *存放boolean类型的数值
     */
    public void putBoolean(String key,boolean value) {
        mEditor.putBoolean(key,value);
        mEditor.commit();
    }
    /**
     *存放int类型的数值
     */
    public void putInt(String key,int value) {
        mEditor.putInt(key,value);
        mEditor.commit();
    }
    /**
     *获取string类型数值
     */
    public String getString(String key,String defValue) {
        return mPreferences.getString(key,defValue);
    }
    /**
     *获取boolean类型数值
     */
    public boolean getBoolean(String key,boolean defaultValue) {
        return mPreferences.getBoolean(key,defaultValue);
    }
    /**
     *获取int类型数值
     */
    public int getInt(String key,int defValue) {
        return mPreferences.getInt(key,defValue);
    }

}
