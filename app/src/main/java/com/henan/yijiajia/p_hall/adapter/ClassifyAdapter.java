package com.henan.yijiajia.p_hall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.henan.yijiajia.R;

import java.util.List;

/**
 * Created by 叶满林 on 2019/2/18.
 */

public class ClassifyAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List mFristClassList;


    public ClassifyAdapter(List FristClassList,Context context) {
        this.mFristClassList = FristClassList;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mFristClassList.size();
    }

    @Override
    public Object getItem(int position) {
        return mFristClassList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView==null){
            convertView = mInflater.inflate(R.layout.item_first_classes, parent, false);
            holder = new ViewHolder();
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        //设置holder
//        holder.img.setImageResource(R.drawable.ic_launcher);
//        holder.name.setText(list.get(position).partname);
        return convertView;
    }

    private class  ViewHolder{
//        ImageView img;
//        TextView name;
    }


}
