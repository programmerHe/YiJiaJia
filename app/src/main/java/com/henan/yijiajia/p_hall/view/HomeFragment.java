package com.henan.yijiajia.p_hall.view;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.henan.yijiajia.R;
import com.henan.yijiajia.main.RequestCodeInfo;
import com.henan.yijiajia.p_base.BaseFragment;
import com.henan.yijiajia.p_base.view.CircleIndicatorView;
import com.henan.yijiajia.p_hall.adapter.BannerAdapter;
import com.henan.yijiajia.p_location.bean.LocationEntity;
import com.henan.yijiajia.p_location.model.LocationModel;
import com.henan.yijiajia.p_location.view.LocationActivity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class HomeFragment extends BaseFragment {
    private List<Integer> mAdImageList = new LinkedList<Integer>();
    private ViewPager mAdviewPage;
    private CircleIndicatorView mAdIndicator;
    private ImageButton mHomeMenuButton;
    private PopupWindow mPopWindow;
    private Button mPositionButton;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        mAdviewPage = (ViewPager) findViewById(R.id.viewPage_ad);
        mAdIndicator = (CircleIndicatorView) findViewById(R.id.civ_ad);
        mHomeMenuButton = (ImageButton) findViewById(R.id.ib_home_menu);
        mPositionButton = (Button) findViewById(R.id.bt_position);
    }

    @Override
    protected void initData(Bundle arguments) {
        getLocation();
        initAdImageData();
    }

    @Override
    protected void addListener() {
        mHomeMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu();
            }
        });
        mPositionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getContext(), LocationActivity.class), RequestCodeInfo.GETCITY);
            }
        });
    }

    boolean flag = false;

    private void showMenu() {
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.frist_menu_popupwindows, null);

        mPopWindow = new PopupWindow(contentView, 480, RelativeLayout.LayoutParams.WRAP_CONTENT, true);
        mPopWindow.setContentView(contentView);


        //设置各个控件的点击响应

        //显示PopupWindow

        View rootview = LayoutInflater.from(getContext()).inflate(R.layout.fragment_home, null);
        mPopWindow.showAsDropDown(mHomeMenuButton);
//        mPopWindow.showAsDropDown(View anchor, int xoff, int yoff)：
    }

    private void initAdImageData() {
        mAdImageList.add(R.drawable.ad_image1);
        mAdImageList.add(R.drawable.ad_image2);
        mAdImageList.add(R.drawable.ad_image3);
        mAdImageList.add(R.drawable.ad_image4);
        mAdImageList.add(R.drawable.ad_image5);

        List<ImageView> mImageList = new ArrayList<>();
        ImageView iv;
        for (int i = 0; i < mAdImageList.size(); i++) {
            iv = new ImageView(getContext());
            iv.setBackgroundResource(mAdImageList.get(i));
            mImageList.add(iv);
        }
        mAdIndicator.setCount(mAdImageList.size());
        mAdviewPage.setAdapter(new BannerAdapter(mImageList, mAdviewPage));
        mAdviewPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                //伪无限循环，滑到最后一张图片又从新进入第一张图片
                int newPosition = i % mAdImageList.size();
                // 把当前选中的点给切换了, 还有描述信息也切换
                mAdIndicator.setSelectPosition(newPosition);
                // 把当前的索引赋值给前一个索引变量, 方便下一次再切换.
                //previousPosition = newPosition;
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
        mAdIndicator.setSelectPosition(1);
        // 把ViewPager设置为默认选中Integer.MAX_VALUE / 2，从十几亿次开始轮播图片，达到无限循环目的;
        int m = (Integer.MAX_VALUE / 2) % mImageList.size();
        int currentPosition = Integer.MAX_VALUE / 2 - m;
        mAdviewPage.setCurrentItem(currentPosition);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case RequestCodeInfo.GETCITY:
                    String city=data.getExtras().getString("city");
                    mPositionButton.setText(city);
                    break;
            }
        }
    }

    private void getLocation(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                final LocationEntity locationEntity = new LocationModel().saveLocation();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mPositionButton.setText(locationEntity.city+locationEntity.district);
                    }
                });
            }
        }).start();
    }
}
