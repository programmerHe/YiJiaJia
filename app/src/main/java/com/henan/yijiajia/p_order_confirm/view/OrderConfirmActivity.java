package com.henan.yijiajia.p_order_confirm.view;

import android.widget.ListView;
import android.widget.TextView;

import com.henan.yijiajia.R;
import com.henan.yijiajia.p_base.BaseActivity;
import com.henan.yijiajia.p_base.util.JsonUtils;
import com.henan.yijiajia.p_login.bean.Users;
import com.henan.yijiajia.p_login.model.PhoneLoginModel;
import com.henan.yijiajia.p_network.NetworkMassage;
import com.henan.yijiajia.p_order_confirm.adapter.ServersAdapter;
import com.henan.yijiajia.p_order_confirm.bean.OrderConfirm;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class OrderConfirmActivity extends BaseActivity {

    private ListView mServerListView;
    private OrderConfirm mOrderConfirm;
    private TextView mOrderIdTextView;
    private TextView mOrderAreaTextView;
    private TextView mOrderTextTextView;
    private TextView mOrderAddressTextView;
    private TextView mOrderOffermoneyTextView;

    @Override
    protected int initLayout() {
        return R.layout.activity_order_confirm;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        mServerListView = findViewById(R.id.lv_server_list);
        mOrderIdTextView = findViewById(R.id.tv_order_id);
        mOrderAreaTextView = findViewById(R.id.tv_order_area);
        mOrderTextTextView = findViewById(R.id.tv_order_text);
        mOrderAddressTextView = findViewById(R.id.tv_order_address);
        mOrderOffermoneyTextView = findViewById(R.id.tv_offermoney);
    }

    @Override
    protected void initData() {
        Users loginmessage = PhoneLoginModel.getLoginManage();
        if (loginmessage==null){
            finish();
        }else {
            NetworkMassage.getInstance().orderConfirm(loginmessage.id);
        }
    }

    private void refresh() {
        if (mOrderConfirm == null) {
            return;
        }
        mOrderIdTextView.setText(mOrderConfirm.id+"");
        mOrderAreaTextView.setText(mOrderConfirm.location_area);
        mOrderTextTextView.setText(mOrderConfirm.servicerequest_text);
        mOrderAddressTextView.setText(mOrderConfirm.location_address);
        mOrderOffermoneyTextView.setText(mOrderConfirm.servicerequest_money+"");
        ServersAdapter serversAdapter = new ServersAdapter(mOrderConfirm.ProvideServerList, this,mOrderConfirm.id+"");
        mServerListView.setAdapter(serversAdapter);
    }

    @Override
    public void finish() {
        EventBus.getDefault().unregister(this);
        super.finish();
    }

    //eventBus消息处理
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEventBus(OrderConfirmMessage msg) {
        String data = msg.message;
        List<OrderConfirm> orderConfirms = JsonUtils.jsonToArrayList(data, OrderConfirm.class);
        if (orderConfirms != null) {
            mOrderConfirm = orderConfirms.get(0);
            refresh();
        }
    }


    public static class OrderConfirmMessage {
        public String message;

        public OrderConfirmMessage(String message) {
            this.message = message;
        }
    }
}
