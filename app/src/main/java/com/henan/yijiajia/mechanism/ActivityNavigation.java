package com.henan.yijiajia.mechanism;

import android.content.Context;
import android.content.Intent;

import com.henan.yijiajia.MainActivity;
import com.henan.yijiajia.activity.GuideActivity;
import com.henan.yijiajia.activity.LoginActivity;

/**
 * startActivity导航栏
 */
public class ActivityNavigation {
    /**
     * 跳转到主页
     */
    public static void gotoMainActivity(Context context) {
        context.startActivity(new Intent(context,MainActivity.class));
    }
    /**
     * 跳转到引导界面
     */
    public static void gotoGuideActivity(Context context) {
        context.startActivity(new Intent(context,GuideActivity.class));
    }
    /**
     * 跳转到登陆界面
     */
    public static void gotoLoginActivity(Context context) {
        context.startActivity(new Intent(context,LoginActivity.class));
    }
}
