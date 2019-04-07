package com.henan.yijiajia.p_hall.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.henan.yijiajia.R;
import com.henan.yijiajia.main.YijiajiaApplication;
import com.henan.yijiajia.p_base.BaseFragment;
import com.henan.yijiajia.p_base.util.JsonUtils;
import com.henan.yijiajia.p_hall.adapter.MessageAdapter;
import com.henan.yijiajia.p_location.bean.LocationEntity;
import com.henan.yijiajia.p_location.model.LocationModel;
import com.henan.yijiajia.p_order.model.OrderModel;
import com.henan.yijiajia.p_push.bean.ServiceRequest;
import com.igexin.sdk.PushManager;

import java.util.ArrayList;
import java.util.List;

public class MessageFragment extends BaseFragment {
    private ListView mMessageListView;

    @Override
    protected void initView() {
        mMessageListView = findViewById(R.id.lv_message);
    }

    @Override
    protected void initData(Bundle arguments) {
        initMessageListView();
    }

    private void initMessageListView() {
        ServiceRequest orderManage = OrderModel.getOrderManage();
        List<ServiceRequest> serviceRequestsList = new ArrayList<ServiceRequest>();
        if (orderManage!=null){
            serviceRequestsList.add(orderManage);
        }
        mMessageListView.setAdapter(new MessageAdapter(serviceRequestsList,getContext()));
    }

    @Override
    public void onResume() {
        initData(null);
        super.onResume();
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_message;
    }

}