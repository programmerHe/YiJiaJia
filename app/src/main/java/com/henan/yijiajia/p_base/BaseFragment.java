package com.henan.yijiajia.p_base;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * BaseFragement的主要职责：
 *
 * */

public abstract class BaseFragment extends Fragment {

    private View mContentView;
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = inflater.inflate(setLayoutResourceID(), container, false);

        initView();

        initData(getArguments());

        addListener();
        return mContentView;

    }

    /**
     * 添加监听器（不强制重写）
     */
    protected  void addListener(){};

    /**
     * 初始化控件
     */
    protected abstract void initView();

    /**
     * 获取从父Activity中获取的值
     */
    protected abstract void initData(Bundle arguments);

    /**
     * 设置布局资源文件
     */
    protected abstract int setLayoutResourceID();


    /**
     * 寻找控件时候的便捷手段
     */
    final public <VIEW extends View> VIEW findViewById(@IdRes int viewId) {
        if (mContentView == null){
            return null;
        }
        return (VIEW) mContentView.findViewById(viewId);
    }

}
