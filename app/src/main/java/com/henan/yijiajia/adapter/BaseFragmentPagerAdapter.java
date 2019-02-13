package com.henan.yijiajia.adapter;


import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;


public abstract class BaseFragmentPagerAdapter<T> extends FragmentPagerAdapter {
    protected final Context mContext;
    protected final List<T> mDatas = new ArrayList<>();

    public BaseFragmentPagerAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        this.mContext = context;
    }

    public T getData(int position) {
        return mDatas.get(position);
    }

    public void setDatas(List<T> data) {
        if (data != null && data.size() > 0) {
            mDatas.clear();
            mDatas.addAll(data);
            notifyDataSetChanged();
        }
    }

    public void addDatas(List<T> data) {
        if (data != null && data.size() > 0) {
            mDatas.addAll(data);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }
}
