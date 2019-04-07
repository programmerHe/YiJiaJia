package com.henan.yijiajia.p_order.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.henan.yijiajia.R;
import com.henan.yijiajia.p_base.BaseActivity;
import com.henan.yijiajia.p_base.util.JsonUtils;
import com.henan.yijiajia.p_order.model.OrderModel;
import com.henan.yijiajia.p_push.bean.ServiceRequest;


public class OrderMessageActivity extends BaseActivity implements View.OnClickListener{

    private TextView mOredrIdTextView;
    private TextView mOrderAreaTextView;
    private TextView mOrderTextTextView;
    private TextView mOrderAddressTextView;
    private EditText mOrderMoneyEditText;
    private Button mReceiveButton;
    private Button mRefuserButton;

    @Override
    protected int initLayout() {
        return R.layout.activity_order_message;
    }

    @Override
    protected void initView() {
        mOredrIdTextView = findViewById(R.id.tv_order_id);
        mOrderAreaTextView = findViewById(R.id.tv_order_area);
        mOrderTextTextView = findViewById(R.id.tv_order_text);
        mOrderAddressTextView = findViewById(R.id.tv_order_address);
        mOrderMoneyEditText = findViewById(R.id.et_money);
        mReceiveButton = findViewById(R.id.bt_receive);
        mRefuserButton = findViewById(R.id.bt_refuse);
    }

    @Override
    protected void initData() {
        String serviceRequestJson = getIntent().getStringExtra("data");
        ServiceRequest serviceRequest = JsonUtils.stringToObject(serviceRequestJson, ServiceRequest.class);
        mOredrIdTextView.setText(serviceRequest.serviceRequestId);
        mOrderAreaTextView.setText(serviceRequest.serviceRequest_area);
        mOrderTextTextView.setText(serviceRequest.serviceRequest_text);
        mOrderAddressTextView.setText(serviceRequest.serviceRequest_address);
        mOrderMoneyEditText.setText(String.valueOf(serviceRequest.serviceRequest_money));
    }

    @Override
    protected void addListener() {
        mReceiveButton.setOnClickListener(this);
        mRefuserButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_receive:
                //检查定价是否填写
                //向服务器发送匹配信息
                OrderModel.clearOrderManage();
                finish();
                break;
            case R.id.bt_refuse:
                OrderModel.clearOrderManage();
                finish();
                break;
        }
    }
}
