package com.henan.yijiajia.p_hall.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.henan.yijiajia.R;
import com.henan.yijiajia.p_base.BaseFragment;
import com.henan.yijiajia.p_login.view.PhoneLoginActivity;

public class MineFragment extends BaseFragment implements View.OnClickListener {

    private ImageView mLoginOfPhoneImageView;

    @Override
    protected void initView() {
        mLoginOfPhoneImageView = findViewById(R.id.iv_phone);
    }

    @Override
    protected void initData(Bundle arguments) {

    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void addListener() {
        mLoginOfPhoneImageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_phone:
                Intent intent=new Intent(getContext(), PhoneLoginActivity.class);
                getContext().startActivity(intent);
                break;
        }
    }
}
