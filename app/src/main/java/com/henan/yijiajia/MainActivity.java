package com.henan.yijiajia;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;

import com.henan.yijiajia.fragment.CelebrationFragment;
import com.henan.yijiajia.fragment.HomeFragment;
import com.henan.yijiajia.fragment.MessageFragment;
import com.henan.yijiajia.fragment.MineFragment;
import com.henan.yijiajia.fragment.ReleaseFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Fragment> mFragments = new ArrayList<Fragment>();
    private RadioGroup mHomeMenuRadioGroup;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mTransaction;

    private HomeFragment mHomeFragment;
    private CelebrationFragment mCelebrationFragment;
    private ReleaseFragment mReleaseFragment;
    private MessageFragment mMessageFragment;
    private MineFragment mMineFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mHomeMenuRadioGroup = (RadioGroup) findViewById(R.id.rg_homeMenu);
        mFragmentManager = getSupportFragmentManager();
        showHomeFragment();

        mHomeMenuRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {


            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home:
                        hideOthersFragment(mHomeFragment, false);
                        break;
                    case R.id.rb_celebration:
                        //活动
                        if (mCelebrationFragment == null) {
                            mCelebrationFragment= new CelebrationFragment();
                            mFragments.add(mCelebrationFragment);
                            hideOthersFragment(mCelebrationFragment, true);
                        } else {
                            hideOthersFragment(mCelebrationFragment, false);
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
}
