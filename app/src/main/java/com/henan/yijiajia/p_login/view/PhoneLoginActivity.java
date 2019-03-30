package com.henan.yijiajia.p_login.view;

import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.henan.yijiajia.R;
import com.henan.yijiajia.p_base.BaseActivity;
import com.henan.yijiajia.p_base.util.PhoneNumberUtils;
import com.henan.yijiajia.p_login.PhoneLoginContract;
import com.henan.yijiajia.p_login.presenter.PhoneLoginPresenter;
import com.henan.yijiajia.p_network.NetworkMassage;

public class PhoneLoginActivity extends BaseActivity implements PhoneLoginContract.IPhoneLoginView {

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
        mPhoneLoginPresenter = new PhoneLoginPresenter(this);
    }

    @Override
    protected void addListener() {
        mCloseImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mPhoneNumberView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                //验证码未出现时生效
                if (mIdentifyingView.getVisibility() == View.GONE) {
                    //手机号改变之后调用，如果不是电话号码 不能按
                    if (!PhoneNumberUtils.isPhoneNumber(s.toString())) {
                        mNextButton.setEnabled(false);
                        mNextButton.setBackgroundColor(Color.parseColor("#F08F92"));
                    } else {
                        mNextButton.setEnabled(true);
                        mNextButton.setBackgroundColor(Color.parseColor("#FF3030"));
                    }
                }
            }
        });
        mIdentifyingView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after){
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length()!=4) {
                    mNextButton.setEnabled(false);
                    mNextButton.setBackgroundColor(Color.parseColor("#F08F92"));
                } else {
                    mNextButton.setEnabled(true);
                    mNextButton.setBackgroundColor(Color.parseColor("#FF3030"));
                }
            }
        });
        mNextButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIdentifyingView.getVisibility() == View.GONE) {
                    mPhoneNumberView.setEnabled(false);
                    mNextButton.setText("登  录");
                    mNextButton.setEnabled(false);
                    mNextButton.setBackgroundColor(Color.parseColor("#F08F92"));
                    mPhoneLoginPresenter.reqPIN(mPhoneNumberView.getText().toString().trim());
                }else{
                    mPhoneLoginPresenter.reqLogin(mPhoneNumberView.getText().toString().trim(), mIdentifyingView.getText().toString().trim());
                }

            }
        });
    }

    @Override
    public void loginSuccess() {
        Intent intent = getIntent();
        setResult(RESULT_OK, intent);
        finish();
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

