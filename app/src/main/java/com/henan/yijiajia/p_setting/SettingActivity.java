package com.henan.yijiajia.p_setting;

import android.content.Intent;
import android.graphics.Canvas;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.henan.yijiajia.R;
import com.henan.yijiajia.main.YijiajiaApplication;
import com.henan.yijiajia.p_base.BaseActivity;
import com.henan.yijiajia.p_login.bean.Users;
import com.henan.yijiajia.p_login.model.PhoneLoginModel;
import com.henan.yijiajia.util.ConstantValue;
import com.henan.yijiajia.util.SharedPreferencesUtil;
import com.igexin.sdk.PushManager;


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
                Users users = PhoneLoginModel.getLoginManage();
                if (users!=null){
                    //解绑
                    boolean result = PushManager.getInstance().unBindAlias(YijiajiaApplication.getContext(), users.id, true);
                    if (result==false){//解绑失败（需要联网才行）【后期给提示】
                        return;
                    }
                    //退出
                    PhoneLoginModel.outLogin();
                    Intent intent=getIntent();
                    setResult(RESULT_OK, intent);
                    finish();
                }else{
                    return;
                }
        }

    }

}
