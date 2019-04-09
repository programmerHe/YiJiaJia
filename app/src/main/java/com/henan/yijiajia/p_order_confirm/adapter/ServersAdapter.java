package com.henan.yijiajia.p_order_confirm.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.henan.yijiajia.R;
import com.henan.yijiajia.p_order_confirm.bean.OrderConfirm;

import java.util.List;

/**
 * Created by 叶满林 on 2019/4/6.
 */

public class ServersAdapter extends BaseAdapter {
    private List<OrderConfirm.ProvideServer> mProvideServerList;
    private Context mContext;

    public ServersAdapter(List<OrderConfirm.ProvideServer> provideServerList, Context context){
        this.mProvideServerList=provideServerList;
        this.mContext=context;
    }
    @Override
    public int getCount() {
        return mProvideServerList.size();
    }

    @Override
    public Object getItem(int position) {
        return mProvideServerList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view= View.inflate(mContext, R.layout.item_server,null);
        OrderConfirm.ProvideServer provideServer = mProvideServerList.get(position);
        TextView nameTextView = view.findViewById(R.id.tv_name);
        nameTextView.setText(provideServer.serverName);
        TextView moneyTextView = view.findViewById(R.id.tv_money);
        if (provideServer.serviceRequest_offerMoney!=0){
            moneyTextView.setText(provideServer.serviceRequest_offerMoney);
        }
        TextView serverMessageTextView =view.findViewById(R.id.tv_serverMessage);
        serverMessageTextView.setText(provideServer.server_total+"");
        Button tv_title=view.findViewById(R.id.bt_confirm);
        return view;
    }

}
