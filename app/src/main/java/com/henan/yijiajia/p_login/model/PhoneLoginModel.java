package com.henan.yijiajia.p_login.model;

import android.text.TextUtils;

import com.henan.yijiajia.p_base.util.JsonUtils;
import com.henan.yijiajia.p_login.bean.Users;
import com.henan.yijiajia.util.ConstantValue;
import com.henan.yijiajia.util.SharedPreferencesUtil;

/**
 * Created by 叶满林 on 2019/3/3.
 */

public class PhoneLoginModel {
    public static boolean mIslogin = false;

    //能存数据
    public static void saveLoginManage(Users users) {
        SharedPreferencesUtil.getInstance().putString(ConstantValue.USER_MESSAGE, JsonUtils.ObjectString(users));
        PhoneLoginModel.mIslogin = true;
    }

    //能取数据
    public static Users getLoginManage() {
        String usersJson = SharedPreferencesUtil.getInstance().getString(ConstantValue.USER_MESSAGE, null);
        if (TextUtils.isEmpty(usersJson)){
            return null;
        }else {
            return  JsonUtils.stringToObject(usersJson,Users.class);
        }
    }

    //能清除数据
    public static void outLogin(){
        SharedPreferencesUtil.getInstance().delete(ConstantValue.USER_MESSAGE);
        mIslogin = false;
    }

    //能判断是否登陆
    public static boolean islogin(){
        String usersJson = SharedPreferencesUtil.getInstance().getString(ConstantValue.USER_MESSAGE, null);
        if (TextUtils.isEmpty(usersJson)){
            return false;
        }else {
            return true;
        }
    }
}
