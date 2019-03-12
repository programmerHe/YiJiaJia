package com.henan.yijiajia.p_base.util;

import android.text.TextUtils;

/**
 * Created by 叶满林 on 2019/3/6.
 */

public class PhoneNumberUtils {
    public static boolean isPhoneNumber(String phone) {
        String telRegex = "^((1[3,5,7,8][0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
        if (TextUtils.isEmpty(phone)) {
            return false;
        } else {
            return phone.matches(telRegex);
        }
    }
}
