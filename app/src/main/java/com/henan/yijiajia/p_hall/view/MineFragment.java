package com.henan.yijiajia.p_hall.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.henan.yijiajia.R;
import com.henan.yijiajia.main.RequestCodeInfo;
import com.henan.yijiajia.p_base.BaseFragment;
import com.henan.yijiajia.p_base.util.JsonUtils;
import com.henan.yijiajia.p_login.bean.Users;
import com.henan.yijiajia.p_login.model.PhoneLoginModel;
import com.henan.yijiajia.p_login.view.PhoneLoginActivity;
import com.henan.yijiajia.p_setting.SettingActivity;
import com.henan.yijiajia.util.ConstantValue;
import com.henan.yijiajia.util.SharedPreferencesUtil;

public class MineFragment extends BaseFragment implements View.OnClickListener {

    private ImageView mLoginOfPhoneImageView;
    private RelativeLayout mIsloginRelativeLayout;
    private RelativeLayout mNologinRelativeLayout;
    private ImageButton mSettingImageButton;
    private TextView mUserNameTextView;
    private SimpleDraweeView mUserHeadSDV;

    @Override
    protected void initView() {
        mSettingImageButton = findViewById(R.id.ib_setting);
        mLoginOfPhoneImageView = findViewById(R.id.iv_phone);
        mIsloginRelativeLayout = findViewById(R.id.islogin_rl);
        mNologinRelativeLayout = findViewById(R.id.nologin_rl);
        mUserNameTextView = findViewById(R.id.tv_user_name);
        mUserHeadSDV = findViewById(R.id.iv_user_head);
    }

    @Override
    protected void initData(Bundle arguments) {
        //根据登录状态显示状态栏
        showStatusBar();
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void addListener() {
        mLoginOfPhoneImageView.setOnClickListener(this);
        mSettingImageButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_phone:
                startActivityForResult(new Intent(getContext(), PhoneLoginActivity.class), RequestCodeInfo.USER_MANAGE);
                break;
            case R.id.ib_setting:
                startActivityForResult(new Intent(getContext(), SettingActivity.class), RequestCodeInfo.SETTING);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case RequestCodeInfo.USER_MANAGE:
                   //更新界面就ok
                    showStatusBar();
                case RequestCodeInfo.SETTING:
                    showStatusBar();
            }
        }
    }

    private void showStatusBar() {
        //从confit文件中判断有没有登录
        Users users= PhoneLoginModel.getLoginManage();
        //选择显示那种模式
        if (users != null){//已经登录
            mIsloginRelativeLayout.setVisibility(View.VISIBLE);
            mNologinRelativeLayout.setVisibility(View.GONE);
            //展示登录信息
            String headimgURL =users.headimg;
            mUserHeadSDV.setImageURI("http://120.78.207.248:80/YiJiaJia/"+headimgURL);
            mUserNameTextView.setText(users.nickname);
        }else{//没有登录
            mIsloginRelativeLayout.setVisibility(View.GONE);
            mNologinRelativeLayout.setVisibility(View.VISIBLE);
        }
    }
}
