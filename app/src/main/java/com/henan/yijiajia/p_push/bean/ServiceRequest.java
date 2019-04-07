package com.henan.yijiajia.p_push.bean;

/**
 * Created by 叶满林 on 2019/4/5.
 */

public class  ServiceRequest {
    public String serviceRequestId;//订单ID
    public String serviceRequest_longitude;//经度
    public String serviceRequest_latitude;//纬度
    public String serviceRequest_area;//区编码
    public String serviceRequest_address;//具体地址
    public String serviceRequest_text;//文字描述
    public float serviceRequest_money;//服务金钱
    public String serviceRequest_method;//服务方式
    public String serviceRequest_user_phone;//联系电话
    public String serviceRequest_type;//所属分类

    public String serviceRequest_offerMoney;//出价
    public String serviceRequest_state;//现在进行的状态
}
