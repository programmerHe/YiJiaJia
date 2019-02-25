package com.henan.yijiajia.p_hall.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.henan.yijiajia.R;

import java.util.List;

/**
 * Created by 叶满林 on 2019/2/23.
 */

public class ClassesMenuAdapter extends BaseAdapter {

    private Context context;
    private int selectItem = 0;
    private List<String> mClassesMenuList;

    public ClassesMenuAdapter(Context context, List<String> classesMenuList) {
        this.mClassesMenuList = classesMenuList;
        this.context = context;
    }
    @Override
    public int getCount() {
        return mClassesMenuList.size();
    }

    @Override
    public Object getItem(int position) {
        return mClassesMenuList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_classes_menu, null);
            holder.textView = (TextView) convertView.findViewById(R.id.item_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (position == selectItem) {
            holder.textView.setBackgroundColor(Color.WHITE);
            holder.textView.setTextColor(Color.RED);
        } else {
            holder.textView.setBackgroundColor(Color.parseColor("#F1F2F6"));
            holder.textView.setTextColor(Color.BLACK);
        }
        holder.textView.setText(mClassesMenuList.get(position));
        return convertView;
    }

    public void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
    }

    static class ViewHolder {
        private TextView textView;
    }
}
