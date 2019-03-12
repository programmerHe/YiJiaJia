package com.henan.yijiajia.main;

import android.app.Application;
import android.content.Context;

/**
 * Created by 叶满林 on 2019/2/17.
 */

public class YijiajiaApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }
}
