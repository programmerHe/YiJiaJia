package com.henan.yijiajia.p_push.server;

import android.content.Context;
import android.util.Log;

import com.henan.yijiajia.p_base.util.JsonUtils;
import com.henan.yijiajia.p_network.NetBasebean;
import com.henan.yijiajia.p_order.model.OrderModel;
import com.henan.yijiajia.p_push.bean.ServiceRequest;
import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTNotificationMessage;
import com.igexin.sdk.message.GTTransmitMessage;

import java.util.Arrays;

/**
 * Created by 叶满林 on 2019/3/16.
 */

public class PushIntentService extends GTIntentService {

    public PushIntentService() {
    }

    @Override
    public void onReceiveServicePid(Context context, int pid) {
    }

    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage msg) {
        String dataJson = new String(msg.getPayload());
        NetBasebean netBasebean = JsonUtils.stringToObject(dataJson, NetBasebean.class);
        switch (netBasebean.type){
            case "serviceRequestPush":
                ServiceRequest serviceRequest = JsonUtils.stringToObject(JsonUtils.ObjectString(netBasebean.data), ServiceRequest.class);
                OrderModel.saveOrderManage(serviceRequest);
                break;
        }
    }

    @Override
    public void onReceiveClientId(Context context, String clientid) {
        Log.e(TAG, "onReceiveClientId -> " + "clientid = " + clientid);
    }

    @Override
    public void onReceiveOnlineState(Context context, boolean online) {
    }

    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage cmdMessage) {
    }

    @Override
    public void onNotificationMessageArrived(Context context, GTNotificationMessage msg) {
    }

    @Override
    public void onNotificationMessageClicked(Context context, GTNotificationMessage msg) {
    }
}
