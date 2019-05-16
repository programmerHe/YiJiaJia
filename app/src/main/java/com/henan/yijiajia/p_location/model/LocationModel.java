package com.henan.yijiajia.p_location.model;

import android.text.TextUtils;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.henan.yijiajia.main.YijiajiaApplication;
import com.henan.yijiajia.p_base.util.JsonUtils;
import com.henan.yijiajia.p_location.bean.LocationEntity;
import com.henan.yijiajia.p_login.model.PhoneLoginModel;
import com.henan.yijiajia.util.ConstantValue;
import com.henan.yijiajia.util.SharedPreferencesUtil;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by 叶满林 on 2019/3/17.
 */

public class LocationModel {
    private AMapLocationClient locationClientSingle = null;
    private BlockingQueue<LocationEntity> blockingQueue = new ArrayBlockingQueue<LocationEntity>(1);
    private static SimpleDateFormat sdf = null;

    private void startSingleLocation() {
        if (null == locationClientSingle) {
            locationClientSingle = new AMapLocationClient(YijiajiaApplication.getContext());
        }
        AMapLocationClientOption locationClientOption = new AMapLocationClientOption();
        //使用单次定位
        locationClientOption.setOnceLocation(true);
        // 地址信息
        locationClientOption.setNeedAddress(true);
        locationClientOption.setLocationCacheEnable(false);
        locationClientSingle.setLocationOption(locationClientOption);
        locationClientSingle.setLocationListener(locationSingleListener);
        locationClientSingle.startLocation();
    }

    AMapLocationListener locationSingleListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation location) {
            long callBackTime = System.currentTimeMillis();
            LocationEntity locationEntity = new LocationEntity();
            if (null == location) {
                locationEntity.isSuccess = false;
            } else {
                //定位成功
                locationEntity.isSuccess = true;
                LocationModel.getLocationStr(location, locationEntity);
            }
            blockingQueue.offer(locationEntity);
        }

        ;
    };

    //单次定位 返回一个bean
    public LocationEntity getLocation() {
        LocationEntity locationEntity = null;
        startSingleLocation();
        try {
            locationEntity = blockingQueue.poll(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            return null;
        }
        locationClientSingle.stopLocation();
        if (locationEntity.isSuccess) {
            return locationEntity;
        } else {
            return getSaveLocation();
        }
    }

    //定位存储
    public LocationEntity saveLocation() {
        LocationEntity locationEntity = getLocation();
        SharedPreferencesUtil.getInstance().putString(ConstantValue.USER_lOCATION, JsonUtils.ObjectString(locationEntity));
        PhoneLoginModel.mIslogin = true;
        return locationEntity;
    }

    //取出存储
    public static LocationEntity getSaveLocation() {
        String locationJson = SharedPreferencesUtil.getInstance().getString(ConstantValue.USER_lOCATION, null);
        LocationEntity locationEntity = JsonUtils.stringToObject(locationJson, LocationEntity.class);
        if (locationEntity != null) {
            return locationEntity;
        } else {
            locationEntity.city = "未知";
            locationEntity.district = "";
            return locationEntity;
        }

    }
    //删除定位


    public synchronized static void getLocationStr(AMapLocation location, LocationEntity locationEntity) {
        if (null == location) {
            return;
        }
        //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
        if (location.getErrorCode() == 0) {
            locationEntity.locationType = location.getLocationType();
            locationEntity.longitude = location.getLongitude();
            locationEntity.latitude = location.getLatitude();
            locationEntity.accuracy = location.getAccuracy();
            locationEntity.altitude = location.getAltitude();
            locationEntity.speed = location.getSpeed();
            locationEntity.country = location.getCountry();
            locationEntity.province = location.getProvince();
            locationEntity.city = location.getCity();
            locationEntity.cityCode = location.getCityCode();
            locationEntity.district = location.getDistrict();
            locationEntity.adCode = location.getAdCode();
            locationEntity.address = location.getAddress();
            locationEntity.time = formatUTC(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss");
        } else {
            //定位失败
            locationEntity.isSuccess = false;
        }
    }

    public synchronized static String formatUTC(long l, String strPattern) {
        if (TextUtils.isEmpty(strPattern)) {
            strPattern = "yyyy-MM-dd HH:mm:ss";
        }
        if (sdf == null) {
            try {
                sdf = new SimpleDateFormat(strPattern, Locale.CHINA);
            } catch (Throwable e) {
            }
        } else {
            sdf.applyPattern(strPattern);
        }
        return sdf == null ? "NULL" : sdf.format(l);
    }
}
