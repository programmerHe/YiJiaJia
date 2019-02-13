package com.henan.yijiajia.util;

import android.content.Context;


import com.litesuits.orm.LiteOrm;

/**
 * ormlist类
 */

public class OrmUtil {
    static LiteOrm orm;
    private static OrmUtil instance;

    public OrmUtil(Context context) {
        orm = LiteOrm.newSingleInstance(context, "yijiajia.db");
    }

    public static OrmUtil getInstance(Context context) {
        if (instance == null) {
            instance = new OrmUtil(context);
        }
        return instance;
    }
}
