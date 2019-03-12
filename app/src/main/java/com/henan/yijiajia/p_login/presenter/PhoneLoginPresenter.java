package com.henan.yijiajia.p_login.presenter;

import android.text.TextUtils;
import com.henan.yijiajia.p_base.util.PhoneNumberUtils;
import com.henan.yijiajia.p_login.PhoneLoginContract;
import com.henan.yijiajia.p_login.model.PhoneLoginModel;
import com.henan.yijiajia.p_network.NetworkMassage;

/**
 * Created by 叶满林 on 2019/3/1.
 */

public class PhoneLoginPresenter implements PhoneLoginContract.IPhoneLoginPresenter {
    private PhoneLoginContract.IPhoneLoginView mPhoneLoginView;
    private PhoneLoginModel mPhoneLoginModel;


    public PhoneLoginPresenter(PhoneLoginContract.IPhoneLoginView phoneLoginView) {
        this.mPhoneLoginView = phoneLoginView;
        mPhoneLoginModel = new PhoneLoginModel();
    }

    @Override
    public void reqLogin(String phone,String PIN) {
        if (!PhoneNumberUtils.isPhoneNumber(phone)){
            //非电话号码直接返回
            return ;
        }
        if (!PhoneLoginModel.mLoginStat) {
            new NetworkMassage().loginPIN(phone);
            mPhoneLoginView.showIdentifying();
            PhoneLoginModel.mLoginStat = true;
        }else{
            if (TextUtils.isEmpty(PIN)){
                return;
            }
            new NetworkMassage().loginPhone(phone,PIN);
        }
    }

    @Override
    public void release() {
        PhoneLoginModel.mLoginStat = false;
    }
}
