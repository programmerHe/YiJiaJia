package com.henan.yijiajia.p_push.server;

import android.content.Context;
import android.util.Log;

import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTNotificationMessage;
import com.igexin.sdk.message.GTTransmitMessage;

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
        // 透传消息的处理，详看SDK demo
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
