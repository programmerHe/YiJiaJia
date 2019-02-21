package com.henan.yijiajia.p_location.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.henan.yijiajia.R;

import java.util.List;


public class HotCityGridViewAdapter extends BaseAdapter {

	private List<String> list;
	private LayoutInflater inflater;

	public HotCityGridViewAdapter(Context context, List<String> list){
		this.list=list;
		inflater= LayoutInflater.from(context);
	}

	@Override
    public int getCount() {
		return list.size();
	}

	@Override
    public Object getItem(int position) {
		return list.get(position);
	}

	@Override
    public long getItemId(int position) {
		return position;
	}

	@Override
    public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder=null;
		if(convertView==null){
			convertView=inflater.inflate(R.layout.item_gridview_hot_city,null);
			viewHolder=new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		}else {
			viewHolder=(ViewHolder)convertView.getTag();
		}
		viewHolder.item_gridview_cyb_change_city_tv.setText(list.get(position));
		return convertView;
	}

	class ViewHolder{
		private TextView item_gridview_cyb_change_city_tv;
		public ViewHolder(View view){
			item_gridview_cyb_change_city_tv=(TextView)view.findViewById(R.id.item_gridview_cyb_change_city_tv);
		}
	}
}
