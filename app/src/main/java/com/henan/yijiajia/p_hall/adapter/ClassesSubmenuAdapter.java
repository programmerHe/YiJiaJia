package com.henan.yijiajia.p_hall.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.henan.yijiajia.R;
import com.henan.yijiajia.p_hall.baseview.GridViewForScrollView;
import com.henan.yijiajia.p_hall.bean.ClassifyMenuBean;

import java.util.List;

/**
 * Created by 叶满林 on 2019/2/24.
 */

public class ClassesSubmenuAdapter extends BaseAdapter {
    private Context context;
    private List<ClassifyMenuBean.MenuBean> mMenuDatas;

    public ClassesSubmenuAdapter(Context context, List<ClassifyMenuBean.MenuBean> menuDatas) {
        this.context = context;
        this.mMenuDatas = menuDatas;
    }

    @Override
    public int getCount() {
        if (mMenuDatas != null) {
            return mMenuDatas.size();
        } else {
            return 10;
        }
    }

    @Override
    public Object getItem(int position) {
        return mMenuDatas.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ClassifyMenuBean.MenuBean menuBean  = mMenuDatas.get(position);
        List<ClassifyMenuBean.MenuBean.SubmenuBean> submenuList = menuBean.dataList;
        ViewHold viewHold = null;
        if (convertView == null) {
            convertView = View.inflate(context,R.layout.item_classes_submenu_gridview, null);
            viewHold = new ViewHold();
            viewHold.gridView = (GridViewForScrollView) convertView.findViewById(R.id.gridView);
            viewHold.blank = (TextView) convertView.findViewById(R.id.blank);
            convertView.setTag(viewHold);
        } else {
            viewHold = (ViewHold) convertView.getTag();
        }
        ClassesSubmenuItemAdapter adapter = new ClassesSubmenuItemAdapter(context, submenuList);
        viewHold.blank.setText(menuBean.moduleTitle);
        viewHold.gridView.setAdapter(adapter);
        return convertView;
    }

    private static class ViewHold {
        private GridViewForScrollView gridView;
        private TextView blank;
    }
}
