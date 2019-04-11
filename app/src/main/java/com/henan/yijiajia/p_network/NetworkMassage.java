package com.henan.yijiajia.p_network;

import android.util.Log;

import com.henan.yijiajia.main.YijiajiaApplication;
import com.henan.yijiajia.p_base.util.JsonUtils;
import com.henan.yijiajia.p_hall.view.ReleaseFragment;
import com.henan.yijiajia.p_login.presenter.PhoneLoginPresenter;
import com.henan.yijiajia.p_order_confirm.view.OrderConfirmActivity;
import com.henan.yijiajia.p_release.bean.ReleaseServerBean;
import com.henan.yijiajia.p_push.bean.ServiceRequest;
import com.igexin.sdk.PushManager;

import org.greenrobot.eventbus.EventBus;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 叶满林 on 2019/3/2.
 */

public class NetworkMassage {
    private static final String TAG = "NetworkMassage";
    private String BASE_URL="http://120.78.207.248:80/YiJiaJia/";

    private NetworkMassage(){}
    public static NetworkMassage getInstance(){
        return NetworkMassageSingleton.INSTANCE;
    }
    private static class NetworkMassageSingleton{
        private static final NetworkMassage INSTANCE= new NetworkMassage();
    }

    Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//支持Rxjava
            .baseUrl(BASE_URL)
            .build();
    NetworkServers service = retrofit.create(NetworkServers.class);

    public void loginPIN(String phone){
        service.phonePIN(phone)              //获取Observable对象
                .subscribeOn(Schedulers.newThread())//请求在新的线程中执行0
                .observeOn(Schedulers.io())         //请求完成后在io线程中执行
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new Subscriber<NetBasebean>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        //请求失败(打印错误日志)
                        Log.i(TAG, "Error:请求验证码失败");
                    }
                    @Override
                    public void onNext(NetBasebean PINInfo) {
                        //请求失败
                        Log.i(TAG, "Success:请求验证码成功");
                    }
                });
    }

    public void loginPhone(String phone,String PIN){
        String clientId = PushManager.getInstance().getClientid(YijiajiaApplication.getContext());
        service.login(phone,PIN,clientId)              //获取Observable对象
                .subscribeOn(Schedulers.newThread())//请求在新的线程中执行0
                .observeOn(Schedulers.io())         //请求完成后在io线程中执行
//                .doOnNext(new Action1<NetBasebean>() {
//                    @Override
//                    public void call(NetBasebean userInfo) {
//                        Log.i(TAG, "登录请求");
//                    }
//                })
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new Subscriber<NetBasebean>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        //请求失败
                        EventBus.getDefault().post(new PhoneLoginPresenter.LoginMessage("SERVICE_ERROR"));
                    }
                    @Override
                    public void onNext(NetBasebean userInfo) {
                        //登录成功
                        switch (userInfo.msg){
                            case "success":
                                String message= JsonUtils.ObjectString(userInfo.data);
                                EventBus.getDefault().post(new PhoneLoginPresenter.LoginMessage(message));
                                break;
                            default:
                                EventBus.getDefault().post(new PhoneLoginPresenter.LoginMessage("PIN_ERROR"));
                                break;
                        }
                    }
                });
    }

    public void releaseService(String userID,ReleaseServerBean releaseServerBean){
        service.releaseServer(userID,releaseServerBean)              //获取Observable对象
                .subscribeOn(Schedulers.newThread())//请求在新的线程中执行0
                .observeOn(Schedulers.io())         //请求完成后在io线程中执行
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new Subscriber<NetBasebean>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        //请求失败
                        EventBus.getDefault().post(new ReleaseFragment.ReleaseMessage("SERVICE_ERROR"));
                    }
                    @Override
                    public void onNext(NetBasebean releaseServiceInfo) {
                        //登录成功
                        switch (releaseServiceInfo.msg){
                            case "success":
                                EventBus.getDefault().post(new ReleaseFragment.ReleaseMessage("SUCCESS"));
                                break;
                            default:
                                EventBus.getDefault().post(new ReleaseFragment.ReleaseMessage("MESSAGE_ERROR"));
                                break;
                        }
                    }
                });
    }

    public void takingOrder(String userID,ServiceRequest serviceRequest){
        Log.i(TAG, "takingOrder: "+JsonUtils.ObjectString(serviceRequest));
        service.serviceOrderTaking(userID,serviceRequest)              //获取Observable对象
                .subscribeOn(Schedulers.newThread())//请求在新的线程中执行0
                .observeOn(Schedulers.io())         //请求完成后在io线程中执行
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new Subscriber<NetBasebean>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        //请求失败
                        Log.i(TAG, "Error:接单失败");
                    }
                    @Override
                    public void onNext(NetBasebean rtakingOrdernfo) {
                        //登录成功
                        Log.i(TAG, "Success:接单成功");
                    }
                });
    }

    public void orderConfirm(String userID){
        service.orderConfirm(userID)              //获取Observable对象
                .subscribeOn(Schedulers.newThread())//请求在新的线程中执行0
                .observeOn(Schedulers.io())         //请求完成后在io线程中执行
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new Subscriber<NetBasebean>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        //请求失败
                        Log.i(TAG, "Error:检出失败");
                    }
                    @Override
                    public void onNext(NetBasebean orderConfirminfo) {
                        //请求成功
                        String message= JsonUtils.ObjectString(orderConfirminfo.data);
                        Log.i(TAG, message);
                        EventBus.getDefault().post(new OrderConfirmActivity.OrderConfirmMessage(message));
                    }
                });
    }

    public void orderContract(String orderID,String orderTakingID){
        service.orderContract(orderID,orderTakingID)              //获取Observable对象
                .subscribeOn(Schedulers.newThread())//请求在新的线程中执行0
                .observeOn(Schedulers.io())         //请求完成后在io线程中执行
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new Subscriber<NetBasebean>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        //请求失败
                        Log.i(TAG, "Error:确认失败");
                    }
                    @Override
                    public void onNext(NetBasebean orderConfirminfo) {
                        //请求成功
                        Log.i(TAG, "Success:确认成功");
                    }
                });
    }

    private void printErrorMessage(Throwable e){
        Writer result = new StringWriter();
        PrintWriter printWriter = new PrintWriter(result);
        e.printStackTrace(printWriter);
        Log.i(TAG, "onError:"+result.toString());
    }
}
