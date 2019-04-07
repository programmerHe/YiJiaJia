package com.henan.yijiajia.p_order.model;

import android.text.TextUtils;
import android.util.Log;

import com.henan.yijiajia.p_base.util.JsonUtils;
import com.henan.yijiajia.p_push.bean.ServiceRequest;
import com.henan.yijiajia.p_release.bean.ReleaseServerBean;
import com.henan.yijiajia.util.ConstantValue;
import com.henan.yijiajia.util.SharedPreferencesUtil;

/**
 * Created by 叶满林 on 2019/4/5.
 */

public class OrderModel {

    //能存数据
    public static void saveOrderManage(ServiceRequest serviceRequest) {
        SharedPreferencesUtil.getInstance().putString(ConstantValue.SERVER_ORDER, JsonUtils.ObjectString(serviceRequest));
    }

    //能取数据
    public static ServiceRequest getOrderManage() {
        String usersJson = SharedPreferencesUtil.getInstance().getString(ConstantValue.SERVER_ORDER, null);
        if (TextUtils.isEmpty(usersJson)){
            return null;
        }else {
            return  JsonUtils.stringToObject(usersJson,ServiceRequest.class);
        }
    }

    //能清除数据
    public static void clearOrderManage(){
        SharedPreferencesUtil.getInstance().delete(ConstantValue.SERVER_ORDER);
    }
}
