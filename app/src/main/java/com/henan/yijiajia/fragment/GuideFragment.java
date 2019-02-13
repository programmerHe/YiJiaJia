package com.henan.yijiajia.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.henan.yijiajia.R;
import com.henan.yijiajia.mechanism.ActivityNavigation;
import com.henan.yijiajia.util.Consts;
import com.henan.yijiajia.util.ImageUtil;
import com.henan.yijiajia.util.LogUtil;


public class GuideFragment extends BaseFragment {

    private ImageView mIv_guide;
    private Integer mIv_guidemageId;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_guide;
    }



    @Override
    protected void initViews() {
        mIv_guide = (ImageView) findViewById(R.id.iv_guide);

    }

    @Override
    protected void initStyles() {

    }

    @Override
    protected void initDatas() {
        //getArguments,setArguments是activity与fragment的传值方式
        mIv_guidemageId = getArguments().getInt(Consts.ID, -1);
        if (mIv_guidemageId == -1) {
            LogUtil.w("没有放置图片");
            ActivityNavigation.gotoGuideActivity(getActivity());
            getActivity().finish();
            return;
        }
        ImageUtil.showOriginalImage(getActivity(), mIv_guide, mIv_guidemageId);
    }

    @Override
    protected void initListener() {
    }

    public static GuideFragment newInstance(int imageId) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Consts.ID, imageId);
        GuideFragment fragment = new GuideFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
}
