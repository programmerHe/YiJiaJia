package com.henan.yijiajia.p_order_confirm.bean;

import java.util.List;

/**
 * Created by 叶满林 on 2019/4/9.
 */

public class OrderConfirm {
    public Integer id;// 识别ID
    public String location_area;// 市区
    public String location_address;// 具体地址
    public String servicerequest_text;// 文字描述
    // private List servicerequest_img;//服务照片
    // private String servicerequest_speech;//语音输入
    public float servicerequest_money;// 服务金钱
    public String servicerequest_method;// 服务方式
    public String servicerequest_user_phone;// 联系电话
    public List<ProvideServer> ProvideServerList;//接单列表

    public static class ProvideServer {
        public String serverId;// 服务商ID
        public String serverName;// 服务商姓名
        public int server_good;// 好评数
        public int server_nomall;// 中评数
        public int server_bad;// 差评数
        public int server_total;// 总服务次数
        public int serviceRequest_offerMoney;// 出价
    }

    @Override
    public String toString() {
        return "OrderConfirm{" +
                "id=" + id +
                ", location_area='" + location_area + '\'' +
                ", location_address='" + location_address + '\'' +
                ", servicerequest_text='" + servicerequest_text + '\'' +
                ", servicerequest_money=" + servicerequest_money +
                ", servicerequest_method='" + servicerequest_method + '\'' +
                ", servicerequest_user_phone='" + servicerequest_user_phone + '\'' +
                ", ProvideServerList=" + ProvideServerList +
                '}';
    }
}