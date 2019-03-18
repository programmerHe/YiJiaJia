package com.henan.yijiajia.p_typeshow.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.henan.yijiajia.R;
import com.henan.yijiajia.p_base.BaseActivity;

public class TypeShowActivity extends BaseActivity implements View.OnClickListener{

    private TextView mTypenameTextView;
    private ImageView mReturnImageView;

    @Override
    protected int initLayout() {
        return R.layout.activity_type_show;
    }

    @Override
    protected void initView() {
        mTypenameTextView = findViewById(R.id.tv_typename);
        mReturnImageView = findViewById(R.id.iv_return);
    }

    @Override
    protected void initData() {
        //获取意图中的信息
        Intent intent=getIntent();
        String typename = intent.getStringExtra("typename");
        mTypenameTextView.setText(typename);
    }

    @Override
    protected void addListener() {
        mReturnImageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_return:
                finish();
        }
    }
}
