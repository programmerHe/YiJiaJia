package com.henan.yijiajia.p_hall.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;


import com.henan.yijiajia.R;
import com.henan.yijiajia.p_base.BaseFragment;
import com.henan.yijiajia.p_base.view.CircleIndicatorView;
import com.henan.yijiajia.p_hall.adapter.BannerAdapter;
import com.henan.yijiajia.p_hall.adapter.ClassifyAdapter;
import com.henan.yijiajia.p_hall.bean.FristClassesBean;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class HomeFragment extends BaseFragment {
    private List<Integer>  mAdImageList= new LinkedList<Integer>();
    private RecyclerView mAdRecyclerView;
    private LinearLayoutManager mAdLayoutManager;
    private CircleIndicatorView mAdIndicator;
    private ListView mListViewClasses;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        mAdRecyclerView = (RecyclerView) findViewById(R.id.recycler_ad);
        mAdIndicator = (CircleIndicatorView)findViewById(R.id.civ_ad);
        mListViewClasses = (ListView)findViewById(R.id.lv_classes);
    }

    @Override
    protected void initData(Bundle arguments) {
        initAdImageData();

        initClassifyList();

    }

    private void initClassifyList() {
        List<FristClassesBean> FCBList=new ArrayList<>();
        for(int i=0;i<5;i++){
            FristClassesBean fristClassesBean=new FristClassesBean();
            FCBList.add(fristClassesBean);
        }
        ClassifyAdapter classifyAdapter=new ClassifyAdapter(FCBList,getContext());
        mListViewClasses.setAdapter(classifyAdapter);
    }


    private void initAdImageData() {
        mAdImageList.add(R.mipmap.ad_image1);
        mAdImageList.add(R.mipmap.ad_image2);
        mAdImageList.add(R.mipmap.ad_image3);
        mAdImageList.add(R.mipmap.ad_image4);
        mAdImageList.add(R.mipmap.ad_image5);

        BannerAdapter adapter = new BannerAdapter(getContext(),mAdImageList);
        mAdIndicator.setCount(mAdImageList.size());
        mAdLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mAdRecyclerView.setLayoutManager(mAdLayoutManager);
        mAdRecyclerView.setHasFixedSize(true);
        mAdRecyclerView.setAdapter(adapter);

        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(mAdRecyclerView);

        mAdRecyclerView.scrollToPosition(mAdImageList.size()*10);

        //循环开始
//        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
//        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
//            @Override
//            public void run() {
//                mAdRecyclerView.smoothScrollToPosition(mAdLayoutManager.findFirstVisibleItemPosition() + 1);
//            }
//        }, 2000, 2000, TimeUnit.MILLISECONDS);

        //获取红点位置
        mAdRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int i = mAdLayoutManager.findFirstVisibleItemPosition() % mAdImageList.size();
                    mAdIndicator.setSelectPosition(i);
                }
            }
        });
    }

}
