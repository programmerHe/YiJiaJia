package com.henan.yijiajia.p_login.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.henan.yijiajia.p_base.util.JsonUtils;
import com.henan.yijiajia.p_base.util.PhoneNumberUtils;
import com.henan.yijiajia.p_login.PhoneLoginContract;
import com.henan.yijiajia.p_login.bean.Users;
import com.henan.yijiajia.p_login.model.PhoneLoginModel;
import com.henan.yijiajia.p_network.NetworkMassage;
import com.henan.yijiajia.util.ConstantValue;
import com.henan.yijiajia.util.SharedPreferencesUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by 叶满林 on 2019/3/1.
 */

public class PhoneLoginPresenter implements PhoneLoginContract.IPhoneLoginPresenter {
    private PhoneLoginContract.IPhoneLoginView mPhoneLoginView;
    private PhoneLoginModel mPhoneLoginModel;


    public PhoneLoginPresenter(PhoneLoginContract.IPhoneLoginView phoneLoginView) {
        this.mPhoneLoginView = phoneLoginView;
        mPhoneLoginModel = new PhoneLoginModel();
        EventBus.getDefault().register(this);
    }

    @Override
    public void reqLogin(String phone,String PIN) {
        NetworkMassage.getInstance().loginPhone(phone,PIN);
    }

    @Override
    public void reqPIN(String phone) {
        NetworkMassage.getInstance().loginPIN(phone);
        mPhoneLoginView.showIdentifying();
    }

    @Override
    public void release() {
        EventBus.getDefault().unregister(this);
    }

    //eventBus消息处理
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEventBus(LoginMessage msg) {
        String data=msg.message;
        switch (msg.message){
            case "PIN_ERROR":
                //弹出吐司
                mPhoneLoginView.showToast("验证码错误");
                break;
            case "SERVICE_ERROR":
                //网络异常
                mPhoneLoginView.showToast("请检查网络");
                break;
            default:
                PhoneLoginModel.saveLoginManage(JsonUtils.stringToObject(data, Users.class));
                mPhoneLoginView.loginSuccess();
                break;
        }
    }

    public static class LoginMessage{
        public String message;
        public LoginMessage(String message){
            this.message=message;
        }
    }
}
