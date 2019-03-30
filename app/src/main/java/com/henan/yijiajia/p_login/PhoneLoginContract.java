package com.henan.yijiajia.p_login;

import com.henan.yijiajia.p_login.bean.Users;

/**
 * Created by 叶满林 on 2019/2/28.
 */

public class PhoneLoginContract {
    public interface IPhoneLoginPresenter {
        //校验密码
        void reqLogin(String phone,String PIN);
        //请求验证码值
        void reqPIN(String phone);
        //释放资源
        void release();
    }

    public interface IPhoneLoginView {
        //登录成功
        void loginSuccess();

        //验证码框显示
        void showIdentifying();

        //验证码框隐藏
        void hideIdentifying();
    }
}
