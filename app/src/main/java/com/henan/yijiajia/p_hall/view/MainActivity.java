package com.henan.yijiajia.p_hall.view;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

import com.henan.yijiajia.R;
import com.henan.yijiajia.p_base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity{
    private List<Fragment> mFragments = new ArrayList<Fragment>();
    private RadioGroup mHomeMenuRadioGroup;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mTransaction;

    private HomeFragment mHomeFragment;
    private ClassesFragment mClassesFragment;
    private ReleaseFragment mReleaseFragment;
    private MineFragment mMineFragment;
    private MessageFragment mMessageFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mHomeMenuRadioGroup = (RadioGroup) findViewById(R.id.rg_homeMenu);
        mFragmentManager = getSupportFragmentManager();
    }

    @Override
    protected void initData() {
        showHomeFragment();
        setMenuRadioGroup();
    }

    private void showHomeFragment() {
        mHomeMenuRadioGroup.check(R.id.rb_home);
        mHomeFragment = new HomeFragment();
        mFragments.add(mHomeFragment);
        hideOthersFragment(mHomeFragment, true);
    }

    private void hideOthersFragment(Fragment showFragment, boolean add) {
        mTransaction = mFragmentManager.beginTransaction();
        if (add) {
            mTransaction.add(R.id.main_container_content, showFragment);
        }
        for (Fragment fragment : mFragments) {
            if (showFragment.equals(fragment)) {
                mTransaction.show(fragment);
            } else {
                mTransaction.hide(fragment);
            }
        }
        mTransaction.commit();
    }
    private  void setMenuRadioGroup(){
        mHomeMenuRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home:
                        hideOthersFragment(mHomeFragment, false);
                        break;
                    case R.id.rb_class:
                        //分类
                        if (mClassesFragment == null) {
                            mClassesFragment= new ClassesFragment();
                            mFragments.add(mClassesFragment);
                            hideOthersFragment(mClassesFragment, true);
                        } else {
                            hideOthersFragment(mClassesFragment, false);
                        }
                        break;
                    case R.id.rb_release:
                        // 发布
                        if (mReleaseFragment == null) {
                            mReleaseFragment = new ReleaseFragment();
                            mFragments.add(mReleaseFragment);
                            hideOthersFragment(mReleaseFragment, true);
                        } else {
                            hideOthersFragment(mReleaseFragment, false);
                        }
                        break;
                    case R.id.rb_message:
                        // 消息
                        if (mMessageFragment == null) {
                            mMessageFragment = new MessageFragment();
                            mFragments.add(mMessageFragment);
                            hideOthersFragment(mMessageFragment, true);
                        } else {
                            hideOthersFragment(mMessageFragment, false);
                        }
                        break;
                    case R.id.rb_mine:
                        // 我的
                        if (mMineFragment == null) {
                            mMineFragment = new MineFragment();
                            mFragments.add(mMineFragment);
                            hideOthersFragment(mMineFragment, true);
                        } else {
                            hideOthersFragment(mMineFragment, false);
                        }
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
