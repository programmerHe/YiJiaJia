package com.henan.yijiajia.p_network;

import com.henan.yijiajia.p_push.bean.ServiceRequest;
import com.henan.yijiajia.p_release.bean.ReleaseServerBean;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by 叶满林 on 2019/3/2.
 */

public interface NetworkServers {

    //手机登录
    @GET("api_phoneLogin")
    Observable<NetBasebean> login(@Query("phone") String phone,@Query("PIN") String PIN,@Query("clientId") String clientId);

    //手机验证
    @GET("api_phonePIN")
    Observable<NetBasebean> phonePIN(@Query("phone") String phone);

    //请求服务
    @POST("api_releaseServer")
    Observable<NetBasebean>releaseServer(@Query("UserID") String UserID,@Body ReleaseServerBean releaseServerBean);

    //接单请求
    @POST("api_serviceOrderTaking")
    Observable<NetBasebean>serviceOrderTaking(@Query("UserID") String UserID,@Body ServiceRequest serviceRequest);

    //请求订单匹配列表
    @GET("api_orderConfirm")
    Observable<NetBasebean>orderConfirm(@Query("UserID") String UserID);

    //订单匹配
    @GET("api_orderContract")
    Observable<NetBasebean>orderContract(@Query("orderID") String orderID,@Query("orderTakingID") String orderTakingID);

}
