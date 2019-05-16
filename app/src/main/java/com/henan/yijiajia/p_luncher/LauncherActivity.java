package com.henan.yijiajia.p_luncher;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import com.henan.yijiajia.R;
import com.henan.yijiajia.p_base.BaseActivity;
import com.henan.yijiajia.p_hall.view.MainActivity;

import java.util.ArrayList;
import java.util.List;

//启动动画与权限申请
public class LauncherActivity extends BaseActivity implements ActivityCompat.OnRequestPermissionsResultCallback{

    public String[] permArray = {
            Manifest.permission.ACCESS_COARSE_LOCATION,//定位权限

            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.KILL_BACKGROUND_PROCESSES,
            Manifest.permission.RESTART_PACKAGES
    };

    @Override
    protected int initLayout() {
        return R.layout.activity_launcher;
    }

    protected boolean isPermissionsAllGranted(){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            return true;
        }
        //获得批量请求但被禁止的权限列表
        List<String> deniedPerms = new ArrayList<String>();
        for(int i=0;permArray!=null&&i<permArray.length;i++){
            if(PackageManager.PERMISSION_GRANTED != checkSelfPermission(permArray[i])){
                deniedPerms.add(permArray[i]);
            }
        }
        //进行批量请求
        int denyPermNum = deniedPerms.size();
        if(denyPermNum != 0){
            requestPermissions(deniedPerms.toArray(new String[denyPermNum]),1);
            //return false;
        }
        return true;
    }


    public class AuthorizationTask extends AsyncTask<Integer, Integer, String> {
        //后面尖括号内分别是参数（例子里是线程休息时间），进度(publishProgress用到)，返回值 类型
        @Override
        protected void onPreExecute() {
            //第一个执行方法
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Integer... params) {
            //第二个执行方法,onPreExecute()执行完后执行
            isPermissionsAllGranted();
            publishProgress();
            return "";
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            //这个函数在doInBackground调用publishProgress时触发，虽然调用时只有一个参数
            //但是这里取到的是一个数组,所以要用progesss[0]来取值
            //第n个参数就用progress[n]来取值
            new Handler().postDelayed(new Runnable(){
                public void run() {
                    Intent intent = new Intent(LauncherActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 5000);
            super.onProgressUpdate(progress);
        }

    }



    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        AuthorizationTask authorizationTask = new AuthorizationTask();
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.HONEYCOMB_MR1) {
            authorizationTask.execute();
        } else {
            authorizationTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    }
}
