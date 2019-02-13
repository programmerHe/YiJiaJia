package com.henan.yijiajia.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public abstract class BaseFragment extends Fragment {

    private View mContentView;
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = inflater.inflate(setLayoutResourceID(),container,false);
        initViews();
        initStyles();
        initDatas();
        initListener();
        return mContentView;

    }

    protected abstract int setLayoutResourceID();

    /**
     * 找控件
     */
    protected abstract void initViews();

    /**
     * 动态设置样式，颜色，宽高，背景
     */
    protected abstract void initStyles();

    /**
     * 设置数据
     */
    protected abstract void initDatas();

    /**
     * 绑定监听器
     */
    protected abstract void initListener();


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    /**
     * 判断界面是否可见
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            onVisibleToUser();
        } else {
            onInvisibleToUser();
        }
    }

    protected void onInvisibleToUser() {
    }

    protected void onVisibleToUser() {
    }

    final public <VIEW extends View> VIEW findViewById(@IdRes int viewId) {
        return (VIEW) mContentView.findViewById(viewId);
    }

}
