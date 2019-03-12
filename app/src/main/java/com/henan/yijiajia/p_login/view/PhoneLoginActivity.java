package com.henan.yijiajia.p_login.view;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.henan.yijiajia.R;
import com.henan.yijiajia.p_base.BaseActivity;
import com.henan.yijiajia.p_login.PhoneLoginContract;
import com.henan.yijiajia.p_login.presenter.PhoneLoginPresenter;
import com.henan.yijiajia.p_network.NetworkMassage;

public class PhoneLoginActivity extends BaseActivity implements PhoneLoginContract.IPhoneLoginView{

    private PhoneLoginContract.IPhoneLoginPresenter mPhoneLoginPresenter;
    private ImageView mCloseImageView;
    private Button mNextButton;
    private EditText mIdentifyingView;
    private EditText mPhoneNumberView;

    @Override
    protected int initLayout() {
        return R.layout.activity_phone_login;
    }

    @Override
    protected void initView() {
        mNextButton = findViewById(R.id.bt_next);
        mCloseImageView = findViewById(R.id.iv_close);
        mPhoneNumberView = findViewById(R.id.et_phone_number);
        mIdentifyingView = findViewById(R.id.et_identifying);
    }

    @Override
    protected void initData() {
        mPhoneLoginPresenter=new PhoneLoginPresenter(this);
    }

    @Override
    protected void addListener() {
        mCloseImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mNextButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mPhoneLoginPresenter.reqLogin(mPhoneNumberView.getText().toString().trim(),mIdentifyingView.getText().toString().trim());
            }
        });
    }

    @Override
    public void loginSuccess() {

    }

    @Override
    public void showIdentifying() {
        mIdentifyingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideIdentifying() {

    }

    @Override
    public void finish() {
        mPhoneLoginPresenter.release();
        super.finish();
    }
}

