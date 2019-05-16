package com.henan.yijiajia.p_hall.view;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.henan.yijiajia.R;
import com.henan.yijiajia.main.RequestCodeInfo;
import com.henan.yijiajia.main.YijiajiaApplication;
import com.henan.yijiajia.p_base.BaseFragment;
import com.henan.yijiajia.p_base.util.CameraUtils;
import com.henan.yijiajia.p_base.util.JsonUtils;
import com.henan.yijiajia.p_hall.dialog.TypeChoiceDialog;
import com.henan.yijiajia.p_location.bean.LocationEntity;
import com.henan.yijiajia.p_location.model.LocationModel;
import com.henan.yijiajia.p_login.bean.Users;
import com.henan.yijiajia.p_login.model.PhoneLoginModel;
import com.henan.yijiajia.p_login.presenter.PhoneLoginPresenter;
import com.henan.yijiajia.p_network.NetworkMassage;
import com.henan.yijiajia.p_release.bean.ReleaseServerBean;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;


public class ReleaseFragment extends BaseFragment implements View.OnClickListener{

    private Button mSpeedButton;
    private EditText mReleaseText;
    // 用HashMap存储听写结果
    private HashMap<String, String> mIatResults = new LinkedHashMap<String , String>();
    private ImageView mReleaseImage;
    private Button mReleaseButton;
    private RelativeLayout mChoiceTypeLayout;
    private EditText mMoneyEditText;
    private TextView mDetailedAddressTextView;
    private LoadingDialog mLoadingDialog;
    private TextView mDetailedCityTextView;

    @Override
    protected void initView() {
        mSpeedButton = findViewById(R.id.bt_speed);
        mReleaseText = findViewById(R.id.et_release_text);
        mReleaseImage = findViewById(R.id.iv_release_image);
        mReleaseButton = findViewById(R.id.bt_release);
        mChoiceTypeLayout = findViewById(R.id.rl_type_choice);
        mMoneyEditText = findViewById(R.id.et_money);
        mDetailedAddressTextView = findViewById(R.id.tv_detailed_address);
        mDetailedCityTextView = findViewById(R.id.tv_city);
    }

    @Override
    protected void initData(Bundle arguments) {
        //初始化语音识别
        SpeechUtility.createUtility(YijiajiaApplication.getContext(), SpeechConstant.APPID +"=5c8f1959");
        //注册eventBus
        EventBus.getDefault().register(this);
        //写一下地址
        LocationEntity saveLocation = LocationModel.getSaveLocation();
        mDetailedCityTextView.setText(saveLocation.province+saveLocation.city+saveLocation.district);
        String address = saveLocation.address;
        mDetailedAddressTextView.setText( address.substring(address.indexOf(saveLocation.district)+saveLocation.district.length()));
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_release;
    }

    @Override
    protected void addListener() {
        mSpeedButton.setOnClickListener(this);
        mReleaseImage.setOnClickListener(this);
        mReleaseButton.setOnClickListener(this);
        mChoiceTypeLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_speed: //语音识别（把声音转文字）
                startSpeechDialog();
                break;
            case R.id.iv_release_image:
                showPopueWindow();
                break;
            case R.id.bt_release:
                releaseService();
                break;
            case R.id.rl_type_choice:
                final TypeChoiceDialog typeDialog = new TypeChoiceDialog(getContext(),null);
                typeDialog.setCancelable(true);
                typeDialog.show();
                break;
        }
    }

    private void releaseService() {
        ReleaseServerBean releaseServerBean = new ReleaseServerBean();
        //用户信息
        Users loginManage = PhoneLoginModel.getLoginManage();
        if (loginManage==null){
            Toast.makeText(getContext(),"请先登录",Toast.LENGTH_LONG).show();
            return;
        }else {
            releaseServerBean.userID=loginManage.id+"";
        }
        //获取分类
        releaseServerBean.typeCode=1+"";
        //获取内容
        String releaseText=mReleaseText.getText().toString().trim();
        if (TextUtils.isEmpty(releaseText)){
            Toast.makeText(getContext(),"请输入信息",Toast.LENGTH_LONG).show();
            return;
        }else{
            releaseServerBean. servicerequest_text=releaseText;
        }
        //获取图片
        //获取预算并判断类型
        String money=mMoneyEditText.getText().toString().trim();
        if (TextUtils.isEmpty(money)){
            releaseServerBean.servicerequest_method="2";
        }else {
            releaseServerBean.servicerequest_method="1";
            releaseServerBean.servicerequest_money=Integer.parseInt(money);
        }
        //联系电话暂时用用户
        releaseServerBean.servicerequest_user_phone=loginManage.phone;
        //获取服务地址并封装gps
        LocationEntity saveLocation = LocationModel.getSaveLocation();
        releaseServerBean.location_longitude=saveLocation.longitude;//经度
        releaseServerBean.location_latitude=saveLocation.latitude;//纬度
        releaseServerBean.location_cityCode=saveLocation.cityCode;//城市编码
        releaseServerBean.location_areaCode=saveLocation.adCode;//区编码
        releaseServerBean.location_area=saveLocation.city+saveLocation.district;//市区
        String address=mDetailedAddressTextView.getText().toString().trim();
        if (TextUtils.isEmpty(address)){
            Toast.makeText(getContext(),"请填写详细地址",Toast.LENGTH_LONG).show();
            return;
        }else {
            releaseServerBean.location_address=address;
        }

        String releaseJson=JsonUtils.ObjectString(releaseServerBean);
        NetworkMassage.getInstance().releaseService(loginManage.id.toString(),releaseServerBean);

        mLoadingDialog = new LoadingDialog(getContext());
        mLoadingDialog.setLoadingText("加载中")
                .setSuccessText("发布成功")//显示加载成功时的文字
                .setFailedText("加载失败")
                .setInterceptBack(false)//是否拦截用户back，如果设置为true，那么一定要调用close()，
                .setRepeatCount(0);
            // .setLoadSpeed(LoadingDialog.Speed.SPEED_ONE)//参数是一个枚举，一共两个值，SPEED_ONE是比较慢的，SPEED_TWO比前一个快一点
            // .closeSuccessAnim()是否动画显示
            //设置动态绘制的次数，比如你设置了值为1，那么除了加载的时候绘制一次，还会再绘制一次。
            // .setDrawColor(color)//可以改变绘制的颜色，圆和里面的勾啊，叉啊的颜色
        mLoadingDialog.show();
    }

    private void clearPageMessage() {
        mReleaseText.setText("");
        mMoneyEditText.setText("");
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

    //
    private void showPopueWindow(){
        View popView = View.inflate(getContext(),R.layout.popupwindow_camera_need,null);
        Button bt_album = (Button) popView.findViewById(R.id.btn_pop_album);
        Button bt_camera = (Button) popView.findViewById(R.id.btn_pop_camera);
        Button bt_cancle = (Button) popView.findViewById(R.id.btn_pop_cancel);
        //获取屏幕宽高
        int weight = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels*1/3;

        final PopupWindow popupWindow = new PopupWindow(popView,weight,height);
        popupWindow.setAnimationStyle(R.style.anim_popup_dir);
        popupWindow.setFocusable(true);
        //点击外部popueWindow消失
        popupWindow.setOutsideTouchable(true);

        bt_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RequestCodeInfo.RESULT_LOAD_IMAGE);
                popupWindow.dismiss();
            }
        });
        bt_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CameraUtils.takeCamera(RequestCodeInfo.RESULT_CAMERA_IMAGE,getContext());
                popupWindow.dismiss();

            }
        });
        bt_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        //popupWindow消失屏幕变为不透明
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp =  getActivity().getWindow().getAttributes();
                lp.alpha = 1.0f;
                getActivity().getWindow().setAttributes(lp);
            }
        });
        //popupWindow出现屏幕变为半透明
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = 0.5f;
        getActivity().getWindow().setAttributes(lp);
        popupWindow.showAtLocation(popView, Gravity.BOTTOM,0,0);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == getActivity().RESULT_OK ) {
            if (requestCode == RequestCodeInfo.RESULT_LOAD_IMAGE && null != data) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor =  getActivity().getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                final String picturePath = cursor.getString(columnIndex);
                Glide.with(this).load(picturePath).into(mReleaseImage);
                cursor.close();
            }else if (requestCode == RequestCodeInfo.RESULT_CAMERA_IMAGE){

            }
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    //eventBus消息处理
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEventBus(ReleaseMessage msg) {
        String data=msg.message;
        switch (msg.message){
            case "SUCCESS":
                //发布成功
                mLoadingDialog.loadSuccess();
                break;
            default:
                mLoadingDialog.loadFailed();
                break;
        }
    }
    public static class ReleaseMessage{
        public String message;
        public ReleaseMessage(String message){
            this.message=message;
        }
    }
}
