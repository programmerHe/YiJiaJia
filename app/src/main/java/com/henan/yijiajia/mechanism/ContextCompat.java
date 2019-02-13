package com.henan.yijiajia.mechanism;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

/**
 * Context Compat
 *
 * http://developer.huawei.com/consumer/cn/devservice/doc/50115
 * 在P版本，如果不在Intent添加FLAG_ACTIVITY_NEW_TASK，将无法通过非Activity的Context启动一个Activity，并且会抛异常。
 *
 */
public class ContextCompat{


    public static void startActivity(@NonNull Context context,@NonNull Intent intent) {

        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }

        context.startActivity(intent);

    }
}
