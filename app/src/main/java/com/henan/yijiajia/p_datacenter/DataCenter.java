package com.henan.yijiajia.p_datacenter;

/**
 * Created by 叶满林 on 2019/2/28.
 * 暂时把数据封装到这一层，以后再做拆分处理
 */

public class DataCenter {
    private DataCenter() {}

    private static class SingleDataCenter {
        private static DataCenter INSTANCE = new DataCenter();
    }

    public static DataCenter getInstance() {
        return SingleDataCenter.INSTANCE;
    }
    public boolean isLogin(){
        boolean loginFlag=false;

        return loginFlag;
    }

}
