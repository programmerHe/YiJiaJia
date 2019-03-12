package com.henan.yijiajia.p_network;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by 叶满林 on 2019/3/2.
 */

public interface NetworkServers {

    //手机登录
    @GET("api_phoneLogin")
    Observable<NetBasebean> login(@Query("phone") String phone,@Query("PIN") String PIN);

    //手机验证
    @GET("api_phonePIN")
    Observable<NetBasebean> phonePIN(@Query("phone") String phone);

}
