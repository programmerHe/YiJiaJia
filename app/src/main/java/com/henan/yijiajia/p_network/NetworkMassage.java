package com.henan.yijiajia.p_network;

import android.text.TextUtils;
import android.util.Log;

import com.henan.yijiajia.p_base.util.JsonUtils;
import com.henan.yijiajia.p_login.presenter.PhoneLoginPresenter;
import com.henan.yijiajia.p_release.bean.ReleaseServerBean;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by 叶满林 on 2019/3/2.
 */

public class NetworkMassage {
    private static final String TAG = "NetworkMassage";

    private String BASE_URL="http://120.78.207.248:80/YiJiaJia/";

    Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//新的配置
            .baseUrl(BASE_URL)
            .build();
    NetworkServers service = retrofit.create(NetworkServers.class);

    public void loginPIN(String phone){
        service.phonePIN(phone)              //获取Observable对象
                .subscribeOn(Schedulers.newThread())//请求在新的线程中执行0
                .observeOn(Schedulers.io())         //请求完成后在io线程中执行
                .doOnNext(new Action1<NetBasebean>() {
                    @Override
                    public void call(NetBasebean userInfo) {
                        Log.i(TAG, "登录请求");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new Subscriber<NetBasebean>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        //请求失败
                        Log.i(TAG, "PIN请求失败");
                        try {
                            onDispatchError(e);
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }
                    @Override
                    public void onNext(NetBasebean userInfo) {
                        //登录成功
                        Log.i(TAG, "PIN请求成功");
                    }
                });
    }
    public void onDispatchError(Throwable e) {
    }

    public void loginPhone(String phone,String PIN){
        service.login(phone,PIN)              //获取Observable对象
                .subscribeOn(Schedulers.newThread())//请求在新的线程中执行0
                .observeOn(Schedulers.io())         //请求完成后在io线程中执行
                .doOnNext(new Action1<NetBasebean>() {
                    @Override
                    public void call(NetBasebean userInfo) {
                        Log.i(TAG, "登录请求");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new Subscriber<NetBasebean>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        //请求失败
                        EventBus.getDefault().post(new PhoneLoginPresenter.LoginMessage("error"));
                    }
                    @Override
                    public void onNext(NetBasebean userInfo) {
                        //登录成功
                        if(TextUtils.equals("success",userInfo.msg)){
                            String message= JsonUtils.ObjectString(userInfo.data);
                            EventBus.getDefault().post(new PhoneLoginPresenter.LoginMessage(message));
                        }else{
                            EventBus.getDefault().post(new PhoneLoginPresenter.LoginMessage("error"));
                        }
                    }
                });
    }

    public void releaseService(String phone,ReleaseServerBean releaseServerBean){
        String releaseJson=JsonUtils.ObjectString(releaseServerBean);
        service.releaseServer(phone,releaseJson)              //获取Observable对象
                .subscribeOn(Schedulers.newThread())//请求在新的线程中执行0
                .observeOn(Schedulers.io())         //请求完成后在io线程中执行
                .doOnNext(new Action1<NetBasebean>() {
                    @Override
                    public void call(NetBasebean userInfo) {
                        Log.i(TAG, "登录请求");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new Subscriber<NetBasebean>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        //请求失败
                        Log.i(TAG, "Error: ");
                    }
                    @Override
                    public void onNext(NetBasebean userInfo) {
                        Log.i(TAG, "success: "+userInfo.toString());
                    }
                });
    }
}
