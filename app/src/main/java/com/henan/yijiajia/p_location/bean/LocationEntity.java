package com.henan.yijiajia.p_location.bean;

/**
 * Created by 叶满林 on 2019/3/20.
 */

public class LocationEntity {
    public boolean isSuccess;
    public int locationType;//定位类型 0定位失败,1GPS,2前次,4缓存,5wifi,6基站
    public double longitude;//经度
    public double latitude;//维度
    public float accuracy;//精度
    public double altitude;//海拔
    public float speed;//速度
    public String country;//国家
    public String province;//省
    public String city;//城市
    public String cityCode;//城市代码
    public String district;//区
    public String adCode;//区代码
    public String address;//地址
    public String time;//定位时间
}
