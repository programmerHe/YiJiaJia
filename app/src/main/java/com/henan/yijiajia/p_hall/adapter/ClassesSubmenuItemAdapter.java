package com.henan.yijiajia.p_hall.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.henan.yijiajia.R;
import com.henan.yijiajia.p_hall.bean.ClassifyMenuBean;

import java.util.List;

/**
 *  Created by 叶满林 on 2019/2/24.
 */

public class ClassesSubmenuItemAdapter extends BaseAdapter {

    private Context context;
    private List<ClassifyMenuBean.MenuBean.SubmenuBean> mSubmenuBeanDatas;

    public ClassesSubmenuItemAdapter(Context context, List<ClassifyMenuBean.MenuBean.SubmenuBean> menuBean) {
        this.context = context;
        this.mSubmenuBeanDatas = menuBean;
    }


    @Override
    public int getCount() {
        if (mSubmenuBeanDatas != null) {
            return mSubmenuBeanDatas.size();
        } else {
            return 10;
        }
    }

    @Override
    public Object getItem(int position) {
        return mSubmenuBeanDatas.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ClassifyMenuBean.MenuBean.SubmenuBean submenuBean = mSubmenuBeanDatas.get(position);
        ViewHold viewHold = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_classes_submenu, null);
            viewHold = new ViewHold();
            viewHold.tv_name = (TextView) convertView.findViewById(R.id.item_submenu_name);
            convertView.setTag(viewHold);
        } else {
            viewHold = (ViewHold) convertView.getTag();
        }
        viewHold.tv_name.setText(submenuBean.title);
        return convertView;
    }

    private static class ViewHold {
        private TextView tv_name;
    }

}
