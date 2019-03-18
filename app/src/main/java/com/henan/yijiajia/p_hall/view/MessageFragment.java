package com.henan.yijiajia.p_hall.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.henan.yijiajia.R;
import com.henan.yijiajia.main.YijiajiaApplication;
import com.henan.yijiajia.p_base.BaseFragment;
import com.henan.yijiajia.p_location.model.LocationModel;

public class MessageFragment extends BaseFragment {

    private TextView mTest;
    private Button mButton;
    private AMapLocationClient locationClientSingle = null;

    @Override
    protected void initView() {
        mTest = findViewById(R.id.test);
        mButton = findViewById(R.id.button);
    }

    @Override
    protected void initData(Bundle arguments) {

    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_message;
    }

    @Override
    protected void addListener() {
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mButton.getText().equals("开始定位")) {
                    startSingleLocation();
                    mButton.setText("停止定位");
                    mTest.setText("正在定位...");
                } else {
                    stopSingleLocation();
                    mButton.setText("开始定位");
                }
            }
        });
    }

    void startSingleLocation(){
        if(null == locationClientSingle){
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
            StringBuffer sb = new StringBuffer();
            sb.append("单次定位完成\n");
            sb.append("回调时间: " + LocationModel.formatUTC(callBackTime, null) + "\n");
            if(null == location){
                sb.append("定位失败：location is null!!!!!!!");
            } else {
                sb.append(LocationModel.getLocationStr(location));
            }
            mTest.setText(sb.toString());
        }
    };

    void stopSingleLocation(){
        if(null != locationClientSingle){
            locationClientSingle.stopLocation();
        }
    }
}