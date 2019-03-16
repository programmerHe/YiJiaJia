package com.henan.yijiajia.p_setting;

import android.content.Intent;
import android.graphics.Canvas;
import android.view.View;
import android.widget.Button;

import com.henan.yijiajia.R;
import com.henan.yijiajia.p_base.BaseActivity;
import com.henan.yijiajia.util.ConstantValue;
import com.henan.yijiajia.util.SharedPreferencesUtil;


public class SettingActivity extends BaseActivity implements View.OnClickListener{

    private Button mLoginOffButton;

    @Override
    protected int initLayout() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
        mLoginOffButton = findViewById(R.id.bt_login_off);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void addListener() {
        mLoginOffButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_login_off:
                SharedPreferencesUtil.getInstance().delete(ConstantValue.USER_MESSAGE);
                Intent intent=getIntent();
                setResult(RESULT_OK, intent);
                finish();
        }

    }

}
