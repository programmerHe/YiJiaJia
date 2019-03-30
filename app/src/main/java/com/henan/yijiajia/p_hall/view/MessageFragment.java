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
import com.henan.yijiajia.p_location.bean.LocationEntity;
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

    }


}