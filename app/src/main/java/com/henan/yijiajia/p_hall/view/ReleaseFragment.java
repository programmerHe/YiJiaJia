package com.henan.yijiajia.p_hall.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;;

import com.henan.yijiajia.R;
import com.henan.yijiajia.main.YijiajiaApplication;
import com.henan.yijiajia.p_base.BaseFragment;
import com.henan.yijiajia.p_base.util.JsonUtils;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;


public class ReleaseFragment extends BaseFragment implements View.OnClickListener{

    private Button mSpeedButton;
    private EditText mReleaseText;
    // 用HashMap存储听写结果
    private HashMap<String, String> mIatResults = new LinkedHashMap<String , String>();

    @Override
    protected void initView() {
        mSpeedButton = findViewById(R.id.bt_speed);
        mReleaseText = findViewById(R.id.et_release_text);
    }

    @Override
    protected void initData(Bundle arguments) {
        //初始化语音识别
        SpeechUtility.createUtility(YijiajiaApplication.getContext(), SpeechConstant.APPID +"=5c8f1959");
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_release;
    }

    @Override
    protected void addListener() {
        mSpeedButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_speed: //语音识别（把声音转文字）
                startSpeechDialog();
                break;
        }
    }

    private void startSpeechDialog() {
        //1. 创建RecognizerDialog对象
        RecognizerDialog mDialog = new RecognizerDialog(getContext(), new MyInitListener()) ;
        //2. 设置accent、 language等参数
        mDialog.setParameter(SpeechConstant. LANGUAGE, "zh_cn" );// 设置中文
        mDialog.setParameter(SpeechConstant. ACCENT, "mandarin" );
        // 若要将UI控件用于语义理解，必须添加以下参数设置，设置之后 onResult回调返回将是语义理解
        // 结果
        // mDialog.setParameter("asr_sch", "1");
        // mDialog.setParameter("nlp_version", "2.0");
        //3.设置回调接口
        mDialog.setListener( new MyRecognizerDialogListener()) ;
        //4. 显示dialog，接收语音输入
        mDialog.show() ;
    }

    class MyInitListener implements InitListener {
        @Override
        public void onInit(int code) {
            if (code != ErrorCode.SUCCESS) {
                Log.i("Release", "初始化失败");
            }
        }
    }

    class MyRecognizerDialogListener implements RecognizerDialogListener {
        /**
         * @param results
         * @param isLast  是否说完了
         */
        @Override
        public void onResult(RecognizerResult results, boolean isLast) {
            String result = results.getResultString(); //为解析的
            System. out.println(" 没有解析的 :" + result);
            String text = JsonUtils.parseIatResult(result) ;//解析过后的
            System. out.println(" 解析后的 :" + text);
            String sn = null;
            // 读取json结果中的 sn字段
            try {
                JSONObject resultJson = new JSONObject(results.getResultString()) ;
                sn = resultJson.optString("sn" );
            } catch (JSONException e) {
                e.printStackTrace();
            }

            mIatResults.put(sn, text) ;//没有得到一句，添加到

            StringBuffer resultBuffer = new StringBuffer();
            for (String key : mIatResults.keySet()) {
                resultBuffer.append(mIatResults .get(key));
            }

            mReleaseText.setText(resultBuffer.toString());// 设置输入框的文本
            mReleaseText .setSelection(mReleaseText.length()) ;//把光标定位末尾
        }
        @Override
        public void onError(SpeechError speechError) {
        }
    }
}
