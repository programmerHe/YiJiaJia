package com.henan.yijiajia.p_hall.view;

import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.henan.yijiajia.R;
import com.henan.yijiajia.p_base.BaseFragment;
import com.henan.yijiajia.p_base.util.JsonUtils;
import com.henan.yijiajia.p_hall.adapter.ClassesMenuAdapter;
import com.henan.yijiajia.p_hall.adapter.ClassesSubmenuAdapter;
import com.henan.yijiajia.p_hall.bean.ClassifyMenuBean;

import java.util.ArrayList;
import java.util.List;

public class ClassesFragment extends BaseFragment {

    private ListView mMenuListView;
    private ListView mSubMenuListView;
    private TextView mTitileTextView;
    private ClassesSubmenuAdapter mSubmenuAdapter;
    private ClassesMenuAdapter mMenuAdapter;
    private List<String> mMenuList;
    private List<ClassifyMenuBean.MenuBean> mSubmenuList;
    private List mShowTitle;
    private int mCurrentItem;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_classes;
    }

    @Override
    protected void initView() {
        mMenuListView = findViewById(R.id.lv_menu);
        mSubMenuListView = findViewById(R.id.lv_submenu);
        mTitileTextView = findViewById(R.id.tv_titile);
    }

    @Override
    protected void initData(Bundle arguments) {
        //容器准备
        mMenuList = new ArrayList();
        mSubmenuList = new ArrayList();

        //数据准备
        String json = JsonUtils.getStringOfFile(getContext(), "category.json");
        ClassifyMenuBean classifyMenuBean = JsonUtils.stringToObject(json, ClassifyMenuBean.class);
        mShowTitle = new ArrayList<>();
        for (int i = 0; i < classifyMenuBean.data.size(); i++) {
            ClassifyMenuBean.MenuBean menuBean = classifyMenuBean.data.get(i);
            mMenuList.add(menuBean.moduleTitle);
            mShowTitle.add(i);
            mSubmenuList.add(menuBean);
        }
        //设置标题
        mTitileTextView.setText(classifyMenuBean.data.get(0).moduleTitle);

        setMenuAdapter();
        setSubMeunAdapter();
    }

    @Override
    protected void addListener() {
        mMenuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mMenuAdapter.setSelectItem(position);
                mMenuAdapter.notifyDataSetInvalidated();
                mTitileTextView.setText(mMenuList.get(position));
                mSubMenuListView.setSelection((Integer) mShowTitle.get(position));
            }
        });

        mSubMenuListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int scrollState;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                this.scrollState = scrollState;
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    return;
                }
                int current = mShowTitle.indexOf(firstVisibleItem);
                if (mCurrentItem != current && current >= 0) {
                    mCurrentItem = current;
                    mTitileTextView.setText(mMenuList.get(mCurrentItem));
                    mMenuAdapter.setSelectItem(mCurrentItem);
                    mMenuAdapter.notifyDataSetInvalidated();
                }
            }
        });
    }

    private void setSubMeunAdapter() {
        mSubmenuAdapter = new ClassesSubmenuAdapter(getContext(), mSubmenuList);
        mSubMenuListView.setAdapter(mSubmenuAdapter);
    }

    private void setMenuAdapter() {
        mMenuAdapter = new ClassesMenuAdapter(getContext(), mMenuList);
        mMenuListView.setAdapter(mMenuAdapter);
    }


}
