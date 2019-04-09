package com.henan.yijiajia.p_order.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.henan.yijiajia.R;
import com.henan.yijiajia.p_base.BaseActivity;
import com.henan.yijiajia.p_base.util.JsonUtils;
import com.henan.yijiajia.p_login.bean.Users;
import com.henan.yijiajia.p_login.model.PhoneLoginModel;
import com.henan.yijiajia.p_network.NetworkMassage;
import com.henan.yijiajia.p_order.model.OrderModel;
import com.henan.yijiajia.p_push.bean.ServiceRequest;

import static com.iflytek.sunflower.FlowerCollector.UserState.Login;


public class OrderMessageActivity extends BaseActivity implements View.OnClickListener{

    private TextView mOredrIdTextView;
    private TextView mOrderAreaTextView;
    private TextView mOrderTextTextView;
    private TextView mOrderAddressTextView;
    private EditText mOrderMoneyEditText;
    private Button mReceiveButton;
    private Button mRefuserButton;
    private ServiceRequest mServiceRequest;
    private Button mConfirmButton;

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
        mConfirmButton = findViewById(R.id.bt_confirm);
    }

    @Override
    protected void initData() {
        String serviceRequestJson = getIntent().getStringExtra("data");
        mServiceRequest = JsonUtils.stringToObject(serviceRequestJson, ServiceRequest.class);
        mOredrIdTextView.setText(mServiceRequest.serviceRequestId);
        mOrderAreaTextView.setText(mServiceRequest.serviceRequest_area);
        mOrderTextTextView.setText(mServiceRequest.serviceRequest_text);
        mOrderAddressTextView.setText(mServiceRequest.serviceRequest_address);
        mOrderMoneyEditText.setText(String.valueOf(mServiceRequest.serviceRequest_money));
    }

    @Override
    protected void addListener() {
        mReceiveButton.setOnClickListener(this);
        mRefuserButton.setOnClickListener(this);
        mConfirmButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_receive:
                //检查定价是否填写
                if ( "2".equals(mServiceRequest.serviceRequest_method)){
                    String offerMoney = mOrderMoneyEditText.getText().toString().trim();
                    if (TextUtils.isEmpty(offerMoney)){
                        return;
                    }else{
                        mServiceRequest.serviceRequest_offerMoney=offerMoney;
                    }
                }
                //检查是否登录
                Users loginManage = PhoneLoginModel.getLoginManage();
                if (loginManage == null){
                    return;
                }
                //向服务器发送匹配信息
                NetworkMassage.getInstance().takingOrder(loginManage.id,mServiceRequest);
                OrderModel.clearOrderManage();
                finish();
                break;
            case R.id.bt_refuse:
                OrderModel.clearOrderManage();
                finish();
                break;
            case R.id.bt_confirm:
                break;
        }
    }
}
