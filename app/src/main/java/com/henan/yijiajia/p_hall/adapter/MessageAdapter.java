package com.henan.yijiajia.p_hall.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.henan.yijiajia.R;
import com.henan.yijiajia.p_base.util.JsonUtils;
import com.henan.yijiajia.p_order.model.OrderModel;
import com.henan.yijiajia.p_order.view.OrderMessageActivity;
import com.henan.yijiajia.p_push.bean.ServiceRequest;

import java.util.List;

/**
 * Created by 叶满林 on 2019/4/6.
 */

public class MessageAdapter extends BaseAdapter {
    private List<ServiceRequest> mServiceRequestList;
    private Context mContext;

    public MessageAdapter(List<ServiceRequest> orderModelList, Context context){
        this.mServiceRequestList=orderModelList;
        this.mContext=context;
    }
    @Override
    public int getCount() {
        return mServiceRequestList.size();
    }

    @Override
    public Object getItem(int position) {
        return mServiceRequestList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view= View.inflate(mContext, R.layout.item_message,null);
        Button tv_title=view.findViewById(R.id.bt_check);
        tv_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, OrderMessageActivity.class);
                intent.putExtra("data", JsonUtils.ObjectString(mServiceRequestList.get(position)));
                mContext.startActivity(intent);
            }
        });
        return view;
    }
}
