package com.henan.yijiajia.p_hall.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by 叶满林 on 2019/2/17.
 */

public class BannerAdapter extends PagerAdapter{

    private List<ImageView> mImageList;
    private ViewPager mViewPager;

    public BannerAdapter(List<ImageView> imageIdList, ViewPager viewPager) {
        this.mImageList=imageIdList;
        this.mViewPager = viewPager;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    /**
     * 判断是否使用缓存, 如果返回的是true, 使用缓存. 不去调用instantiateItem方法创建一个新的对象
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * 初始化一个条目
     * position 就是当前需要加载条目的索引
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // 把position对应位置的ImageView添加到ViewPager中
        ImageView iv = mImageList.get(position % mImageList.size());
        mViewPager.addView(iv);
        // 把当前添加ImageView返回回去.
        return iv;
    }

    /**
     * 销毁一个条目
     * position 就是当前需要被销毁的条目的索引
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // 把ImageView从ViewPager中移除掉
        mViewPager.removeView(mImageList.get(position % mImageList.size()));
    }

}