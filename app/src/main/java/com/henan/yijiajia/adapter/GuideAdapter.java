package com.henan.yijiajia.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.henan.yijiajia.fragment.GuideFragment;

public class GuideAdapter extends BaseFragmentPagerAdapter<Integer> {
    public GuideAdapter(Context context, FragmentManager fragmentManager) {
        super(context, fragmentManager);
    }

    @Override
    public Fragment getItem(int i) {
        return GuideFragment.newInstance(getData(i));
    }
}
