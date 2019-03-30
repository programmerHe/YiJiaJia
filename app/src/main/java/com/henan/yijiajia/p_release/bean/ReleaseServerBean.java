package com.henan.yijiajia.p_release.bean;

import java.util.List;

/**
 * Created by 叶满林 on 2019/3/19.
 */

public class ReleaseServerBean {
    public String userID;//用户识别id
    public double location_longitude;//经度
    public double location_latitude;//纬度
    public String location_cityCode;//城市编码
    public String location_areaCode;//区编码
    public String location_area;//市区
    public String location_address;//具体地址
    public String typeCode;//所属分类
    public String servicerequest_text;//文字描述
    //private List servicerequest_img;//服务照片
    //private String servicerequest_speech;//语音输入
    public int servicerequest_money;//服务金钱
    public String servicerequest_method;//服务方式
    public String servicerequest_user_phone;//联系电话
}
