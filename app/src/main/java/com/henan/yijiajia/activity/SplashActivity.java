package com.henan.yijiajia.activity;


import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.henan.yijiajia.R;
import com.henan.yijiajia.mechanism.ActivityNavigation;
import com.henan.yijiajia.util.Consts;
import com.henan.yijiajia.util.PackageUtil;
import com.henan.yijiajia.util.SharedPreferencesUtil;

public class SplashActivity extends BaseActivity implements View.OnClickListener {
    private VideoView mLuncherVideo;
    private Button mButton_skip;
    private String mVideoPath;
    private boolean mHasClick = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int intiLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initListener() {
        mButton_skip.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mVideoPath = "android.resource://" + getPackageName() + "/" + R.raw.kr36;
        mLuncherVideo.setVideoURI(Uri.parse(mVideoPath));
        mLuncherVideo.start();
        mLuncherVideo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (!mHasClick) {
                    startMainActivity();
                }
            }
        });
    }

    @Override
    protected void initView() {
        mLuncherVideo = findViewById(R.id.video_view);
        mButton_skip = findViewById(R.id.button_skip);
        matchingWindow();
    }


    /**
     * 进入主页
     */
    private void startMainActivity() {
//        if (isShowGuide()) {
//            ActivityNavigation.gotoGuideActivity(this);
//        } else {
//            ActivityNavigation.gotoMainActivity(this);
//        }
        ActivityNavigation.gotoGuideActivity(this);//测试
        finish();
    }

    /**
     * 使用视频画面进行拉伸
     */
    private void matchingWindow() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
        params.addRule(RelativeLayout.ALIGN_LEFT);
        params.addRule(RelativeLayout.ALIGN_RIGHT);
        params.addRule(RelativeLayout.ALIGN_TOP);
        params.addRule(RelativeLayout.ALIGN_BOTTOM);
        mLuncherVideo.setLayoutParams(params);
    }

    /**
     * 判断是否想要引导页
     * 判断标准：1.是否是第一次打开应用 2.是否是同一个版本(可以细化为是否是大版本更新，小版本升级无需替换)
     */
    private boolean isShowGuide() {
        SharedPreferencesUtil sp = SharedPreferencesUtil.getInstance(this);
        if (sp.getBoolean(Consts.FRIST_OPEN, true)) {
            sp.putBoolean(Consts.FRIST_OPEN, false);
            return true;
        } else {
            int VersionCode = PackageUtil.getVersionCode(this);
            if (VersionCode != sp.getInt(Consts.VERSIONCODE_RECORD, -1)) {
                sp.putInt(Consts.VERSIONCODE_RECORD, VersionCode);
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_skip:
                startMainActivity();
                mHasClick = true;
                break;
        }
    }
}
