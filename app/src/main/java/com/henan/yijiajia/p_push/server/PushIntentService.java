package com.henan.yijiajia.p_push.server;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.henan.yijiajia.R;
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
                //显示不重复通知
                int requestCode = (int) System.currentTimeMillis();

                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "default");
                mBuilder.setContentTitle("宜家佳")
                        //设置内容
                        .setContentText("您有一个新订单注意查收")
                        //设置大图标
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.yijiajia))
                        //设置小图标
                        .setSmallIcon(R.mipmap.ic_launcher_round)
                        //设置通知时间
                        .setWhen(System.currentTimeMillis())
                        //首次进入时显示效果
                        .setTicker("我是测试内容")
                        //设置通知方式，声音，震动，呼吸灯等效果，这里通知方式为声音
                        .setDefaults(Notification.DEFAULT_SOUND);
                //发送通知请求
                notificationManager.notify(10, mBuilder.build());

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
