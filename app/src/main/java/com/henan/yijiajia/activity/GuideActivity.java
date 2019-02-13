package com.henan.yijiajia.activity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.henan.yijiajia.R;
import com.henan.yijiajia.adapter.GuideAdapter;
import com.henan.yijiajia.mechanism.ActivityNavigation;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

public class GuideActivity extends BaseActivity implements View.OnClickListener {

    private ViewPager mVp_guide;
    private GuideAdapter mAdapter;
    private CircleIndicator mIndicator;
    private Button mBt_gomain;
    private Button mbt_gologin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int intiLayout() {
        return R.layout.activity_guide;
    }

    @Override
    protected void initListener() {
        mbt_gologin.setOnClickListener(this);
        mBt_gomain.setOnClickListener(this);
    }

    @Override
    protected void initView() {
        mVp_guide = findViewById(R.id.vp_guide);
        mIndicator = findViewById(R.id.indicator);
        mbt_gologin = findViewById(R.id.bt_gologin);
        mBt_gomain = findViewById(R.id.bt_gomain);
    }

    @Override
    protected void initData() {
        mAdapter = new GuideAdapter(this, getSupportFragmentManager());
        mVp_guide.setAdapter(mAdapter);

        mIndicator.setViewPager(mVp_guide);
        mAdapter.registerDataSetObserver(mIndicator.getDataSetObserver());

        ArrayList<Integer> datas = new ArrayList<>();
        datas.add(R.mipmap.guideimage1);
        datas.add(R.mipmap.guideimage2);
        datas.add(R.mipmap.guideimage3);
        mAdapter.setDatas(datas);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_gomain:
                startMainActivity();
                break;
            case R.id.bt_gologin:
                startLoginActivity();
                break;
        }
    }

    /**
     * 进入主页
     */
    private void startMainActivity() {
        ActivityNavigation.gotoMainActivity(this);
        finish();
    }
    /**
     * 进入登陆页面
     */
    private void startLoginActivity() {
        ActivityNavigation.gotoMainActivity(this);
        finish();
    }

    /**
     * 禁止返回
     */
    @Override
    public void onBackPressed() {

    }
}
